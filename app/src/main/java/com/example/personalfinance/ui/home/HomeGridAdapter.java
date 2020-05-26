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

public class HomeGridAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater inflater;

    public HomeGridAdapter(Context c)
    {
        mContext = c;
        inflater = (LayoutInflater.from(c));
    }
    @Override
    public int getCount() {
        return money.length;
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
        txtAmount.setText(money[position].getAmount()+"");

        TextView txtDate = convertView.findViewById(R.id.txtDate);
        txtDate.setText(money[position].getDate());

        TextView txtTitle = convertView.findViewById(R.id.txtTitle);
        txtTitle.setText(money[position].getTitle());

        ImageView imgType = convertView.findViewById(R.id.imgType);
        int resId = convertView.getResources().getIdentifier(money[position].getType(),"drawable", mContext.getPackageName());
        imgType.setImageResource(resId);

        return convertView;
    }

    private Money[] money = {
        new Money(1.11f, "3-Jan","Buy phone","spending"),
        new Money(1.13f, "3-Feb","Buy iphone","earning"),
        new Money(1.14f, "3-Mac","Buy aphone", "lending"),
        new Money(2.11f, "3-Apr","Buy cphone", "lending"),
        new Money(6.11f, "3-Jul","Buy vphone", "lending"),
        new Money(3.11f, "3-Jun","Buy bphone", "lending"),
        new Money(11.11f, "3-Oct","Buy tphone", "lending"),
            new Money(1.11f, "3-Jan","Buy phone","spending"),
            new Money(1.13f, "3-Feb","Buy iphone","earning"),
            new Money(1.14f, "3-Mac","Buy aphone", "lending"),
            new Money(2.11f, "3-Apr","Buy cphone", "lending"),
            new Money(6.11f, "3-Jul","Buy vphone", "lending"),
            new Money(3.11f, "3-Jun","Buy bphone", "lending"),
            new Money(11.11f, "3-Oct","Buy tphone", "lending"),
            new Money(1.11f, "3-Jan","Buy phone","spending"),
            new Money(1.13f, "3-Feb","Buy iphone","earning"),
            new Money(1.14f, "3-Mac","Buy aphone", "lending"),
            new Money(2.11f, "3-Apr","Buy cphone", "lending"),
            new Money(6.11f, "3-Jul","Buy vphone", "lending"),
            new Money(3.11f, "3-Jun","Buy bphone", "lending"),
            new Money(11.11f, "3-Oct","Buy tphone", "lending"),
            new Money(1.11f, "3-Jan","Buy phone","spending"),
            new Money(1.13f, "3-Feb","Buy iphone","earning"),
            new Money(1.14f, "3-Mac","Buy aphone", "lending"),
            new Money(2.11f, "3-Apr","Buy cphone", "lending"),
            new Money(6.11f, "3-Jul","Buy vphone", "lending"),
            new Money(3.11f, "3-Jun","Buy bphone", "lending"),
            new Money(11.11f, "3-Oct","Buy tphone", "lending"),
    };
}
