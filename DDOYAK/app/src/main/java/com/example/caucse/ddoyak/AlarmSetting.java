package com.example.caucse.ddoyak;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AlarmSetting extends AppCompatActivity{

    TimePicker timePicker;

    Button save_data_btn;
    Button finish_setting_btn;
    TextView textView;

    AlarmManager alarmManager;
    Calendar calendar_time;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    private int hour;
    private int min;
    private int num=1;
    private int count = 0;
    public int sqlite_id=1;

    private String info;
    private String onedayNum;
    private String dayNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_layout);

        final DBHelper dbHelper = new DBHelper(getApplicationContext(),"DB.db",null,1);

        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        calendar_time = Calendar.getInstance();

        //recyclerView 초기화
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        final ArrayList<Time> data = new ArrayList<>();

        final ViewAdapter adapter = new ViewAdapter(getApplicationContext(),data);
        recyclerView.setAdapter(adapter);

        //처음 복용 날짜 선택
        Toast.makeText(getApplicationContext(), "처음 복용 날짜를 선택하세요", Toast.LENGTH_SHORT).show();
        new DatePickerDialog(AlarmSetting.this, dateSetListener, calendar_time.get(Calendar.YEAR), calendar_time.get(Calendar.MONTH),calendar_time.get(Calendar.DAY_OF_MONTH)).show();

        //MedicineInfo에서 data 받아오기
        Intent intent =getIntent();
        info = intent.getStringExtra("info");
        onedayNum = intent.getStringExtra("oneday");
        dayNum = intent.getStringExtra("day");

        textView = (TextView) findViewById(R.id.textView);
        textView.setText(num+"번째 알람 설정");

        //데이터 저장버튼
        save_data_btn = (Button)findViewById(R.id.save_select_data);
        save_data_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(count<Integer.parseInt(onedayNum)) { //알람 설정이 끝날 때까지 시간 설정
                    timePicker = (TimePicker) findViewById(R.id.timePicker);

                    calendar_time.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                    calendar_time.set(Calendar.MINUTE, timePicker.getMinute());
                    calendar_time.set(Calendar.SECOND, 0);

                    hour = calendar_time.get(Calendar.HOUR_OF_DAY);
                    min = calendar_time.get(Calendar.MINUTE);
                    Time time = new Time(hour, min);
                    data.add(time); //time data에 입력
                    adapter.notifyDataSetChanged(); //adapter 변경

                    //알람 설정 method
                    setAlarm();

                    if(num<=Integer.parseInt(onedayNum))
                        textView.setText(num + "번째 알람 설정");
                    else
                        textView.setText("<알람설정 완료>");
                }
                else
                    Toast.makeText(getBaseContext(), "알람설정 완료!", Toast.LENGTH_SHORT).show();
            }
        });

        //finish setting 클릭리스너 구현
        finish_setting_btn = (Button) findViewById(R.id.finish_setting);
        finish_setting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //fcm db에 데이터 입력
                dbHelper.writeNewData(info);

                //HomeActivity로 이동
                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    //출력 형식 설정
    public SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy#MM#dd#HH#mm");

    void setAlarm() {
        Intent intent = new Intent(this,AlarmReceiver.class);
        int N = Integer.parseInt(onedayNum);
        int M = Integer.parseInt(dayNum);

        //알람 설정
        PendingIntent[] servicePendings = new PendingIntent[100];
        servicePendings[count+1]=PendingIntent.getBroadcast(AlarmSetting.this, count+1, intent, PendingIntent.FLAG_UPDATE_CURRENT );
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar_time.getTimeInMillis(), servicePendings[count+1]);

        //알람 시간 띄워주기
        Toast.makeText(getBaseContext(),(num++)+"번째 알람 설정: "+ calendar_time.get(Calendar.HOUR_OF_DAY) +":" + calendar_time.get(Calendar.MINUTE),Toast.LENGTH_SHORT).show();
        DBHelper dbHelper = new DBHelper(getApplicationContext(), "DB.db",null,1);

        int dayOfMonth = calendar_time.get(Calendar.DAY_OF_MONTH);
        //한번 알람 설정 시 이후 n일치 data도 설정
        for(int k=0;k<M;k++) {
            calendar_time.set(Calendar.DAY_OF_MONTH,dayOfMonth++);
            String time = simpleDateFormat.format(calendar_time.getTime());
            dbHelper.update(info, time, sqlite_id + k * N);
        }
        //해당 날짜로 reset
        calendar_time.set(Calendar.DAY_OF_MONTH,dayOfMonth-M);
        sqlite_id++;
        count++;
    }

    //처음 복용 날짜로 calendar 설정
    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
            calendar_time.set(Calendar.YEAR, year);
            calendar_time.set(Calendar.MONTH,month);
            calendar_time.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        }
    };
}
