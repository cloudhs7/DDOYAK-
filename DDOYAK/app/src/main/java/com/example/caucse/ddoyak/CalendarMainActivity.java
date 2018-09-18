package com.example.caucse.ddoyak;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class CalendarMainActivity extends AppCompatActivity{

    TextView month_tv;
    Button prev_btn;
    Button next_btn;

    GridView gridView;
    public static GridAdapter gridAdapter;

    RecyclerView checkingView;
    RecyclerView.LayoutManager layoutManager;
    public static ArrayList<History> data = new ArrayList<>();
    ArrayList<History> checkingData = new ArrayList<>();

    CheckingAdapter adapter;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("HISTORY");

    int curYear;
    int curMonth;
    int curDay;
    int count = 0;

    //해당 년월 text 설정
    private void setMonthText(){
        curYear = gridAdapter.getCurYear();
        curMonth = gridAdapter.getCurMonth();

        month_tv.setText(curYear + "년 " + (curMonth+1) + "월");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_activity_main);

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
                gridAdapter.notifyDataSetChanged();
                gridView.setAdapter(gridAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        gridView = (GridView)findViewById(R.id.gridView);
        gridAdapter = new GridAdapter(getApplicationContext(), R.layout.month_item);
        gridView.setAdapter(gridAdapter);

        //gridView item 클릭 시 실행
        gridView.setOnDataSelectionListener(new OnDataSelectionListener() {
            @Override
            public void onDataSelected(AdapterView parent, View v, int position, long id) {
                UpdateAdapter(position);
            }
        });

        month_tv = findViewById(R.id.month_tv);
        setMonthText();

        //이전 달 버튼 클릭 시 실행
        prev_btn = findViewById(R.id.prev_btn);
        prev_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gridAdapter.setPreviousMonth();
                gridAdapter.notifyDataSetChanged();
                setMonthText();
            }
        });

        //다음 달 버튼 클릭 시 실행
        next_btn = findViewById(R.id.next_btn);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gridAdapter.setNextMonth();
                gridAdapter.notifyDataSetChanged();
                setMonthText();
            }
        });
    }

    //gridView Adapter Update
    public void UpdateAdapter(int position){
        MonthItem item = (MonthItem) gridAdapter.getItem(position);
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
