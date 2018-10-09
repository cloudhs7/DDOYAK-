package com.example.caucse.ddoyak_g;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.StringTokenizer;


public class ShowPatientInfo extends AppCompatActivity {

    CalendarView monthView;
    public static CalendarAdapter monthViewAdapter;
    TextView monthText;

    int curYear;
    int curMonth;
    int curDay;
    int count = 0;

    CheckingAdapter adapter;

    RecyclerView checkingView;
    RecyclerView.LayoutManager layoutManager;
    public static ArrayList<History> data = new ArrayList<>();
    ArrayList<History> checkingData = new ArrayList<>();

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_info);

        TextView name = (TextView)findViewById(R.id.name);
        TextView number = (TextView)findViewById(R.id.number);

        Intent intent = getIntent();
        name.setText(intent.getStringExtra("patientname"));
        number.setText(intent.getStringExtra("patientphone"));
        final String thisNumber = intent.getStringExtra("patientphone");

        myRef = database.getReference(name.getText().toString()).child("HISTORY");

        ImageButton callButton = (ImageButton)findViewById(R.id.callButton);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent("android.intent.action.DIAL",Uri.parse("tel:"+thisNumber)));
            }

        });

        checkingView = (RecyclerView) findViewById(R.id.checking_view);
        checkingView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        checkingView.setLayoutManager(layoutManager);

        adapter = new CheckingAdapter(getApplicationContext(), checkingData);

        //data 모두 삭제
        data.clear();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                count=0;

                //History reference 에 해당되는 data 모두 받아오기
                for(DataSnapshot historyData : dataSnapshot.getChildren()){
                    String history = historyData.getValue().toString(); //String 형식으로 하나하나 data 받아오기
                    StringTokenizer st = new StringTokenizer(history,"#");
                    String year, month, day, hour, min, check, name;

                    //token #으로 구분된 data 가공하기
                    year = st.nextToken();
                    month=st.nextToken();
                    day = st.nextToken();
                    hour = st.nextToken();
                    min = st.nextToken();
                    check = st.nextToken();
                    name = st.nextToken();

                    //data에 History class 형식으로 add
                    data.add(new History(year, month, day, hour, min, check, name));
                    count++;
                }
                adapter.notifyDataSetChanged();
                monthViewAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        monthView = (CalendarView) findViewById(R.id.monthView);
        monthViewAdapter = new CalendarAdapter(getApplicationContext(), R.layout.month_item);
        monthView.setAdapter(monthViewAdapter);

        //CalendarView item 클릭 시 실행
        monthView.setOnDataSelectionListener(new OnDataSelectionListener() {
            @Override
            public void onDataSelected(AdapterView parent, View v, int position, long id) {
                UpdateAdapter(position);
            }
        });

        monthText = (TextView)findViewById(R.id.monthText);
        setMonthText();

        //previous month button setting
        Button monthPrevious = (Button)findViewById(R.id.monthPrevious);
        monthPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthViewAdapter.setPreviousMonth();
                monthViewAdapter.notifyDataSetChanged();
                setMonthText();
            }
        });

        //next month button setting
        Button monthNext = (Button)findViewById(R.id.monthNext);
        monthNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthViewAdapter.setNextMonth();
                monthViewAdapter.notifyDataSetChanged();
                setMonthText();
            }
        });

    }

    //month text setting
    private void setMonthText(){
        curYear = monthViewAdapter.getCurYear();
        curMonth = monthViewAdapter.getCurMonth();

        monthText.setText(curYear + "년 " + (curMonth+1) + "월");
    }

    //gridView Adapter Update
    public void UpdateAdapter(int position){
        MonthItem item = (MonthItem) monthViewAdapter.getItem(position);
        curDay = item.getDay();
        checkingData.clear();
        //해당 년월일에 맞는 복용여부 data add
        for(int i=0;i<count;i++) {
            if (Integer.parseInt(data.get(i).getYear()) == curYear)
                if (Integer.parseInt(data.get(i).getMonth()) == (curMonth) + 1)
                    if (Integer.parseInt(data.get(i).getDay()) == curDay)
                        checkingData.add(data.get(i));
        }

        //adapter set
        checkingView.setAdapter(adapter);
    }

}