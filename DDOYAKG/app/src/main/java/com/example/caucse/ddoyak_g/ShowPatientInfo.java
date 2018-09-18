package com.example.caucse.ddoyak_g;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


public class ShowPatientInfo extends AppCompatActivity {
    CalendarView monthView;
    CalendarAdapter monthViewAdapter;
    TextView monthText;

    int curYear;
    int curMonth;

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

        ImageButton callButton = (ImageButton)findViewById(R.id.callButton);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent("android.intent.action.DIAL",Uri.parse("tel:"+thisNumber)));
            }

        });



        monthView = (CalendarView) findViewById(R.id.monthView);
        monthViewAdapter = new CalendarAdapter(this);
        monthView.setAdapter(monthViewAdapter);

        monthText = (TextView)findViewById(R.id.monthText);
        setMonthText();

        //previous month button setting
        Button monthPrevious = (Button)findViewById(R.id.monthPrevious);
        monthPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
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
                // TODO Auto-generated method stub
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



}