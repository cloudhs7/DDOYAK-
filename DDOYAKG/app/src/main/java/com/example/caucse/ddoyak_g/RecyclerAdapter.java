package com.example.caucse.ddoyak_g;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //생성자. 표시할 데이터 전달 받음
    private ArrayList<PatientInfo> patientInfoArrayList;
    private static Context sContext;

    public RecyclerAdapter(Context context, ArrayList<PatientInfo> patientitems){
        patientInfoArrayList = patientitems;
        sContext = context;
    }

    //RecyclerView의 item 표시
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        RecyclerViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);

            view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(sContext,"click "+patientInfoArrayList.get(getAdapterPosition()).getName(),Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(v.getContext(),ShowPatientInfo.class);
                        intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("patientname",patientInfoArrayList.get(getAdapterPosition()).getName());
                        intent.putExtra("patientphone",patientInfoArrayList.get(getAdapterPosition()).getPhoneNumber());
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_itemview, parent, false);
        return new RecyclerViewHolder(v);
    }

    //표시되어질 텍스트 설정
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        RecyclerViewHolder myViewHolder = (RecyclerViewHolder) holder;

        myViewHolder.name.setText(patientInfoArrayList.get(position).name);
    }

    //item 개수 리턴
    @Override
    public int getItemCount() {
        return patientInfoArrayList.size();
    }

    private void removeItemView(int position){
        patientInfoArrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,patientInfoArrayList.size());
    }
}
