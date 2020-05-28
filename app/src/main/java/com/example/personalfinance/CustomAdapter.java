package com.example.personalfinance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
    Context context;
    int imgId[];
    String[] txtId;
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, int[] img, String[] txt) {
        this.context = applicationContext;
        this.imgId = img;
        this.txtId = txt;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return imgId.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner, null);
        ImageView icon = (ImageView) view.findViewById(R.id.add_imgType);
        TextView names = (TextView) view.findViewById(R.id.add_txtType);
        icon.setImageResource(imgId[i]);
        names.setText(txtId[i]);
        return view;
    }
}