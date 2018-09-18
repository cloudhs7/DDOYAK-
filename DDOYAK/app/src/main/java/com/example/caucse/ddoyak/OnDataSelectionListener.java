package com.example.caucse.ddoyak;

import android.view.View;
import android.widget.AdapterView;

interface OnDataSelectionListener {
    public void onDataSelected(AdapterView parent, View v, int position, long id);
}
