package com.example.personalfinance.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.personalfinance.models.Money;
import com.example.personalfinance.R;

import java.util.ArrayList;
import java.util.List;

public class HomeGridAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater inflater;
    ArrayList<Money> moneyList;

    public HomeGridAdapter(Context c, ArrayList<Money> moneyList)
    {
        this.moneyList = moneyList;
        mContext = c;
        inflater = (LayoutInflater.from(c));
    }
    @Override
    public int getCount() {
        return moneyList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.home_gridview,null);

        TextView txtAmount = convertView.findViewById(R.id.txtAmount);
        txtAmount.setText(moneyList.get(position).getAmount()+"");

        TextView txtDate = convertView.findViewById(R.id.txtDate);
        txtDate.setText(moneyList.get(position).getDate());

        TextView txtTitle = convertView.findViewById(R.id.txtTitle);
        txtTitle.setText(moneyList.get(position).getTitle());

        ImageView imgType = convertView.findViewById(R.id.imgType);
        int resId = convertView.getResources().getIdentifier(moneyList.get(position).getType(),"drawable", mContext.getPackageName());
        imgType.setImageResource(resId);

        return convertView;
    }
}
