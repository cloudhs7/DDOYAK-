package com.example.caucse.ddoyak;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MedicineHolder>{

    ArrayList<MedicineData> data;
    private Context context;

    //MedicineAdapter 생성자
    public MedicineAdapter(Context context, ArrayList<MedicineData> data){
        this.data = data;
        this.context = context;
    }

    public static class MedicineHolder extends RecyclerView.ViewHolder {
        TextView medicineName;
        TextView startDay;
        TextView finishDay;

        public MedicineHolder(View v) {
            super(v);

            medicineName =(TextView) v.findViewById(R.id.medicine_name); //약 이름
            startDay= (TextView) v.findViewById(R.id.startDay); //복용 첫 날짜
            finishDay = (TextView) v.findViewById(R.id.finishDay); //복용 마지막 날짜
        }
    }

    public MedicineAdapter(ArrayList data) {
        this.data = data;
    }

    //MedicineHolder 생성
    @Override
    public MedicineAdapter.MedicineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_item, parent, false);
        return new MedicineHolder(v);
    }

    //MedicineHolder Update
    @Override
    public void onBindViewHolder(MedicineHolder holder, int position) {
        MedicineHolder myHolder = (MedicineHolder) holder;

        myHolder.medicineName.setText(data.get(position).getName());
        myHolder.startDay.setText(data.get(position).getStime());
        myHolder.finishDay.setText(data.get(position).getEtime());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
