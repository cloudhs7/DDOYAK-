package com.example.caucse.ddoyak;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private ArrayList<Time> data;
    private int layout;

    TextView numberText;
    TextView timeText;

    public ListViewAdapter(Context context, int layout, ArrayList<Time> data){
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i).getHour();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item, parent, false);
        }

        Time time = data.get(position);

        numberText = (TextView)convertView.findViewById(R.id.number);
        timeText=(TextView)convertView.findViewById(R.id.listText);

        numberText.setText(++position+"번  ");
        timeText.setText(time.getHour()+"시 "+time.getMin()+"분");

        return convertView;
    }
}