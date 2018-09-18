package com.example.caucse.ddoyak;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

public class GridView extends android.widget.GridView {

    private OnDataSelectionListener selectionListener;
    GridAdapter adapter;

    //GridView 생성자
    public GridView(Context context) {
        super(context);
        init();
    }

    public GridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    //GridView 설정 초기화
    private void init() {
        setBackgroundColor(Color.WHITE);
        setVerticalSpacing(1);
        setHorizontalSpacing(1);
        setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        setNumColumns(7);

        //모든 item들의 click listener 설정
        setOnItemClickListener(new OnItemClickAdapter());
    }

    class OnItemClickAdapter implements OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            if (adapter != null) {
                adapter.setSelectedPosition(position);
                adapter.notifyDataSetInvalidated();
            }

            if (selectionListener != null) {
                selectionListener.onDataSelected(parent, view, position, id);
            }
        }
    }

    //adapter 설정
    public void setAdapter(BaseAdapter adapter) {
        super.setAdapter(adapter);
        this.adapter = (GridAdapter) adapter;
    }

    //adapter 반환
    public BaseAdapter getAdapter() {
        return (BaseAdapter) super.getAdapter();
    }

    //item이 선택되었는지 확인해주는 listener 설정
    public void setOnDataSelectionListener(OnDataSelectionListener listener) {
        this.selectionListener = listener;
    }
}
