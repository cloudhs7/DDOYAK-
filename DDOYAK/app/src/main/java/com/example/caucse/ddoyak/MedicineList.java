package com.example.caucse.ddoyak;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class MedicineList extends AppCompatActivity {

    ImageButton next;
    int count;

    RecyclerView medicineView;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<History> data = new ArrayList<>();
    ArrayList<MedicineData> medidata = new ArrayList<>();
    ArrayList<String> mediName = new ArrayList<>();
    ArrayList<String> temp = new ArrayList<>();

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("HISTORY");

    MedicineAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_list);

        medicineView=(RecyclerView)findViewById(R.id.medicineList_view);
        medicineView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        medicineView.setLayoutManager(layoutManager);

        adapter = new MedicineAdapter(getApplicationContext(), medidata);

        data.clear();
        mediName.clear();
        medidata.clear();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                count = 0;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String history = ds.getValue().toString();
                    StringTokenizer st = new StringTokenizer(history, "#");
                    String year, month, day, hour, min, check, name;
                    year = st.nextToken();
                    month = st.nextToken();
                    day = st.nextToken();
                    hour = st.nextToken();
                    min = st.nextToken();
                    check = st.nextToken();
                    name = st.nextToken();
                    data.add(new History(year, month, day, hour, min, check, name));
                    count++;
                }
                updateAdapter();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        next = (ImageButton) findViewById(R.id.medicine_plus);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MedicineInfo.class);
                startActivity(intent);
            }
        });
    }

    public void updateAdapter(){
        mediName.clear();
        mediName.add(data.get(0).getName());

        int num=0;

        for(int i=0;i<count;i++){
            if(!data.get(i).getName().equals(mediName.get(num))){
                num++;
                mediName.add(data.get(i).getName());
            }
        }

        ArrayList<String> medi = new ArrayList<>();

        for(int i=0;i<mediName.size();i++) {
            medi.clear();
            for (int j = 0; j < count; j++) {
                if(data.get(j).getName().equals(mediName.get(i)))
                    medi.add(data.get(j).getDays());
            }
            if(medi.size()!=0)
                medidata.add(new MedicineData(mediName.get(i),medi.get(0),medi.get(medi.size()-1)));
        }
        medicineView.setAdapter(adapter);
    }
}
