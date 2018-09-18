package com.example.caucse.ddoyak;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class DDoyakActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chicken);

        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        imageView.setOnClickListener(new ImageView.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(DDoyakActivity.this, "약을 제때 제때 챙겨먹자!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
