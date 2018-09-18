package com.example.caucse.ddoyak;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //사용자의 토큰 값 업데이트
        MyFirebaseInstanceIdService fiid = new MyFirebaseInstanceIdService();
        fiid.onTokenRefresh();

        //복용 현황 버튼 및 클릭이벤트 지정
        ImageButton takeButton = (ImageButton)findViewById(R.id.take);
        takeButton.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),CalendarMainActivity.class);
                startActivity(intent);
            }
        });

        //알람 복용 버튼 및 클릭이벤트 지정
        ImageButton alarmButton = (ImageButton)findViewById(R.id.alarm);
        alarmButton.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),MedicineList.class);
                startActivity(intent);
            }
        });

        //스케줄 관리 버튼 및 클릭이벤트 지정
        ImageButton scheduleButton = (ImageButton)findViewById(R.id.schedule);
        scheduleButton.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),ScheduleActivity.class);
                startActivity(intent);
            }
        });

        //앱 정보 버튼 및 클릭이벤트 지정
        ImageButton chickenButton = (ImageButton)findViewById(R.id.chicken);
        chickenButton.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(HomeActivity.this,"또약 또약",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),DDoyakActivity.class);
                startActivity(intent);
            }
        });
    }


}
