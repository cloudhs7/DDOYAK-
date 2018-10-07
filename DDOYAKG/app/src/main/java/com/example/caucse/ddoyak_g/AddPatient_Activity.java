package com.example.caucse.ddoyak_g;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/*
* MainActivity에서 add 버튼을 누르면 실행.
* 보호자가 관리할 환자를 추가로 등록.
*/
public class AddPatient_Activity extends AppCompatActivity {

    ImageView titleBar,title;
    EditText patientID;
    EditText patientName;
    Button register;
    String patientId,patientname;
    String tempname, tempphone;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpatient);


        myId = database.getReference("MY_ID");

        titleBar = findViewById(R.id.yellowbar);
        title = findViewById(R.id.title);
        patientID = findViewById(R.id.patient_id);
        patientName = findViewById(R.id.patient_name);

        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {            //새 환자 추가 버튼
            @Override
            public void onClick(View view) {
                patientId = patientID.getText().toString();         //EditText에서 새로 등록할 환자의 이름과 전화번호를 입력받음.
                patientname = patientName.getText().toString();
                patientID.setText("");
                patientName.setText("");


                DatabaseReference myRef = database.getReference(patientId).child("INFO");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try {
                            String infoData = dataSnapshot.getValue().toString();
                            changeViewData(infoData);

                        }catch (NullPointerException e){
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                if(patientname.equals(tempname)) {
                    myId.child(patientId).setValue(patientId);
                    Toast.makeText(AddPatient_Activity.this, "등록",Toast.LENGTH_SHORT).show();
                    //MainActivity.patient_items.add(new PatientInfo(patientname,tempphone));          //해당 환자를 어레이리스트에 저장
                }
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                finish();
                startActivity(intent);              //액티비티 전환
            }
        });
    }

    private void changeViewData(String data){
        try {
            String[] arrayTemp = data.split(",");
            int idx = arrayTemp[0].length();
            arrayTemp[0] = arrayTemp[0].substring(7,idx);
            idx = arrayTemp[1].length();
            arrayTemp[1] = arrayTemp[1].substring(6,idx-1);
            tempphone = arrayTemp[0];
            tempname = arrayTemp[1];
        }catch ( java.lang.ArrayIndexOutOfBoundsException e){
            //예외 발생 무시(리스트 업데이트에 이상 없음)
        }

    }

}