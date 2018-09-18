package com.example.caucse.ddoyak;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("OUTING");

    //생성자. 표시할 데이터 전달 받음
    private ArrayList<ScheduleInfo> scheduleInfoArrayList;
    private static Context sContext;

    public ListAdapter(Context context, ArrayList<ScheduleInfo> scheduleitems){
        scheduleInfoArrayList = scheduleitems;
        this.sContext = context;
    }

    //RecyclerView의 item 표시
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView date, startTime, endTime, schedule;

        RecyclerViewHolder(View view) {
            super(view);
            date = view.findViewById(R.id.date);
            startTime = view.findViewById(R.id.startTime);
            endTime = view.findViewById(R.id.endTime);
            schedule = view.findViewById(R.id.schedule);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(sContext,"click "+scheduleInfoArrayList.get(getAdapterPosition()).getSchedule(),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(v.getContext(),RemoveItem.class);
                    intent.putExtra("scheduleName",scheduleInfoArrayList.get(getAdapterPosition()).getSchedule());
                    intent.putExtra("date",scheduleInfoArrayList.get(getAdapterPosition()).getDate());
                    intent.putExtra("startTime",scheduleInfoArrayList.get(getAdapterPosition()).getStartTime());
                    intent.putExtra("endTime",scheduleInfoArrayList.get(getAdapterPosition()).getEndTime());
                    try {
                        ((Activity) sContext).finish();
                    }catch (java.lang.ClassCastException e){

                    }
                    sContext.startActivity(intent);
                }
            });

        }
    }

    //item 표시에 필요한 xml 가져옴
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_itemview, parent, false);
        return new RecyclerViewHolder(v);
    }

    //표시되어질 텍스트 설정
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        RecyclerViewHolder myViewHolder = (RecyclerViewHolder) holder;

        myViewHolder.date.setText(scheduleInfoArrayList.get(position).date);
        myViewHolder.startTime.setText(scheduleInfoArrayList.get(position).startTime);
        myViewHolder.endTime.setText(scheduleInfoArrayList.get(position).endTime);
        myViewHolder.schedule.setText(scheduleInfoArrayList.get(position).schedule);
    }

    //item 개수 리턴
    @Override
    public int getItemCount() {
        return scheduleInfoArrayList.size();
    }

}