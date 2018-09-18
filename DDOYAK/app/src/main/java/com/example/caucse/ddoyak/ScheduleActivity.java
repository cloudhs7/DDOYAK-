package com.example.caucse.ddoyak;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ScheduleActivity extends AppCompatActivity {

    RecyclerView scheduleList;
    RecyclerView.LayoutManager layoutManager;
    ImageView title, yellowbar;
    ImageButton addButton;
    String startTemp;
    String endTemp;
    String dateTemp;
    String scheduleTitle;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("OUTING");;

    public static ArrayList<ScheduleInfo> scheduleitems = new ArrayList<>();

    public SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd.E");
    public SimpleDateFormat timeFormat = new SimpleDateFormat("aa hh:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        //Title 생성
        title = (ImageView)findViewById(R.id.title);
        yellowbar=(ImageView)findViewById(R.id.yellowbar);

        //스케줄리스트_RecyclerView 선언
        scheduleList = (RecyclerView)findViewById(R.id.schedule_list);
        scheduleList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        scheduleList.setLayoutManager(layoutManager);

        //리사이클러뷰 어댑터와 어레이리스트 연결
        final ListAdapter myAdapter = new ListAdapter(getApplicationContext(), scheduleitems);
        scheduleList.setAdapter(myAdapter);

        //firebase data 가져오기
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                scheduleitems.clear();          //어레이리스트 초기화
                for(DataSnapshot outingData : dataSnapshot.getChildren()){                  //블럭 반복
                    String titleData = outingData.getValue().toString();                     //한 블럭 전체 데이터를 한 문자열로 가져옴. ','를 토큰으로 나눠서 저장해야함.
                    changeViewData(titleData);
                    scheduleitems.add(new ScheduleInfo(dateTemp, startTemp, endTemp, scheduleTitle));       //어레이리스트에 저장
                }
                myAdapter.notifyDataSetChanged();                                      //어레이리스트에 저장한 items >> 어댑터 업데이트
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //일정 추가 버튼
        addButton = (ImageButton)findViewById(R.id.addButton);
        addButton.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),AddSchedule_Activity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void changeViewData(String data){
        try {
            String[] arrayTemp = data.split(",");
            scheduleTitle = arrayTemp[0];
            startTemp = arrayTemp[1];
            endTemp = arrayTemp[2];
            dateTemp = arrayTemp[2];

            int idx = scheduleTitle.length();
            scheduleTitle = scheduleTitle.substring(1, idx);
            startTemp = startTemp.substring(14, 19);
            endTemp = endTemp.substring(14, 19);
            dateTemp = dateTemp.substring(3, 13);

            startTemp = startTemp.replaceAll("#", ":");
            endTemp = endTemp.replaceAll("#", ":");
            dateTemp = dateTemp.replaceAll("#", ".");
        }catch ( java.lang.ArrayIndexOutOfBoundsException e){
            //예외 발생 무시(리스트 업데이트에 이상 없음)
        }finally {
            changeDataFormat();
        }

    }
    private void changeDataFormat(){
        try{
            Date newdate = new SimpleDateFormat("yyyy.MM.dd").parse(dateTemp);
            dateTemp = dateFormat.format(newdate);

            Date newtime = new SimpleDateFormat("HH:mm").parse(startTemp);
            startTemp = timeFormat.format(newtime);

            newtime = new SimpleDateFormat("HH:mm").parse(endTemp);
            endTemp = timeFormat.format(newtime);
        }catch(ParseException e){
            e.printStackTrace();
        }catch (java.lang.NullPointerException e){

        }
    }
}