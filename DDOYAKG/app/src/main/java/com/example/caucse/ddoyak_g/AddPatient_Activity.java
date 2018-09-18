package com.example.caucse.ddoyak_g;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/*
* MainActivity에서 add 버튼을 누르면 실행.
* 보호자가 관리할 환자를 추가로 등록.
*/
public class AddPatient_Activity extends AppCompatActivity {

    ImageView titleBar,title;
    EditText patientname;
    EditText phoneNumeber;
    Button register;
    String patientName,patientPhone;

    //FirebaseDatabase database = FirebaseDatabase.getInstance();
    //DatabaseReference myRef = database.getReference("OUTING");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpatient);

        titleBar = findViewById(R.id.yellowbar);
        title = findViewById(R.id.title);
        patientname = findViewById(R.id.patient_name);
        phoneNumeber = findViewById(R.id.patient_phone);

        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {            //새 환자 추가 버튼
            @Override
            public void onClick(View view) {
                patientName = patientname.getText().toString();         //EditText에서 새로 등록할 환자의 이름과 전화번호를 입력받음.
                patientPhone = phoneNumeber.getText().toString();
                patientname.setText("");
                phoneNumeber.setText("");

                //myRef.child(patientName).child("0").setValue(patientName);

                MainActivity.patient_items.add(new PatientInfo(patientName,patientPhone));          //해당 환자를 어레이리스트에 저장

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);              //액티비티 전환
                finish();
            }
        });
    }

}