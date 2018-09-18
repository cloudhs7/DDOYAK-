package com.example.caucse.ddoyak;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class AddSchedule_Activity extends AppCompatActivity{

    ImageView titleBar;
    EditText scheduleTitle;
    TextView dateView, startTimeView, endTimeView;
    DatePicker datePicker;
    TimePicker startTimePicker, endTimePicker;
    Calendar calendar;
    Button register;

    String scheduleName, date, startTime, endTime;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("OUTING");

    //출력 형식 설정
    public SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd.E");
    public SimpleDateFormat timeFormat = new SimpleDateFormat("aa hh:mm");
    //전달 형식 설정
    public SimpleDateFormat dateDatabaseFormat = new SimpleDateFormat("yyyy#MM#dd");
    public SimpleDateFormat timeDatabaseFormat = new SimpleDateFormat("#HH#mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addschedule);

        titleBar = (ImageView)findViewById(R.id.yellowbar);

        scheduleTitle = (EditText)findViewById(R.id.scheduletitle);
        dateView = (TextView)findViewById(R.id.dateView);
        startTimeView = (TextView)findViewById(R.id.startTimeView);
        endTimeView = (TextView)findViewById(R.id.endTimeView);

        datePicker = (DatePicker)findViewById(R.id.datePicker);
        startTimePicker = (TimePicker)findViewById(R.id.startTimePicker);
        startTimePicker.setIs24HourView(false);
        endTimePicker = (TimePicker)findViewById(R.id.endTimePicker);
        endTimePicker.setIs24HourView(false);

        calendar = Calendar.getInstance();

        register = (Button)findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scheduleName = scheduleTitle.getText().toString();
                scheduleTitle.setText("");

                setDateTime();

                changeDataFormat();
                myRef.child(scheduleName).child("0").setValue(scheduleName);
                myRef.child(scheduleName).child("1").setValue("s#"+startTime);
                myRef.child(scheduleName).child("2").setValue("e#"+endTime);

                Intent intent = new Intent(getApplicationContext(),ScheduleActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setDateTime(){
        calendar.set(Calendar.YEAR, datePicker.getYear());
        calendar.set(Calendar.MONTH, datePicker.getMonth());
        calendar.set(Calendar.DAY_OF_MONTH,datePicker.getDayOfMonth());
        date = dateFormat.format(calendar.getTime());

        calendar.set(Calendar.HOUR_OF_DAY, startTimePicker.getHour());
        calendar.set(Calendar.MINUTE, startTimePicker.getMinute());
        calendar.set(Calendar.SECOND, 0);
        startTime = timeFormat.format(calendar.getTime());

        calendar.set(Calendar.HOUR_OF_DAY, endTimePicker.getHour());
        calendar.set(Calendar.MINUTE, endTimePicker.getMinute());
        calendar.set(Calendar.SECOND, 0);
        endTime = timeFormat.format(calendar.getTime());
    }

    private void changeDataFormat(){
        try{
            Date dateTemp = dateFormat.parse(date);
            String newdate = dateDatabaseFormat.format(dateTemp);
            dateTemp = timeFormat.parse(startTime);
            String newtime = timeDatabaseFormat.format(dateTemp);
            startTime = newdate+newtime;
            dateTemp = timeFormat.parse(endTime);
            newtime = timeDatabaseFormat.format(dateTemp);
            endTime = newdate+newtime;
        }catch(ParseException e){
            e.printStackTrace();
        }
    }
}