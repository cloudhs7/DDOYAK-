package com.example.caucse.ddoyak;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RemoveItem extends AppCompatActivity{

    Intent intent;
    ImageView titleBar;
    TextView scheduleTitle, dateView, thisDate, startTimeView, thisStartTime, endTimeView, thisEndTime;
    Button remove;
    String scheduleName;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("OUTING");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clickitem);
        intent= getIntent();
        scheduleName = intent.getStringExtra("scheduleName");

        titleBar = findViewById(R.id.yellowbar);
        dateView = findViewById(R.id.dateView);
        startTimeView = findViewById(R.id.startTimeView);
        endTimeView = findViewById(R.id.endTimeView);

        scheduleTitle = findViewById(R.id.scheduletitle);
        scheduleTitle.setText(scheduleName);
        thisDate = findViewById(R.id.thisDate);
        thisDate.setText(intent.getStringExtra("date"));
        thisStartTime = findViewById(R.id.thisStartTime);
        thisStartTime.setText(intent.getStringExtra("startTime"));
        thisEndTime = findViewById(R.id.thisEndTime);
        thisEndTime.setText(intent.getStringExtra("endTime"));

        remove = findViewById(R.id.remove);
        remove.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child(scheduleName).removeValue();
                finish();
            }
        }));
    }

}