package com.example.login_signup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomBaseAdopter extends BaseAdapter {

    Context context;
    String auction[];
    String date[];
    String time[];
    LayoutInflater inflater;

    public CustomBaseAdopter(Context ctx, String [] auction, String [] date, String [] time){
        this.context = ctx;
        this.auction = auction;
        this.date = date;
        this.time = time;
        inflater = LayoutInflater.from(ctx);
    }
    @Override
    public int getCount() {
        return auction.length;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.activity_single_auction,null);
        TextView auction1 = (TextView) convertView.findViewById(R.id.auction);
        TextView date1 = (TextView) convertView.findViewById(R.id.date);
        TextView time1 = (TextView) convertView.findViewById(R.id.time);
        auction1.setText(auction[position]);
        time1.setText(time[position]);
        date1.setText(date[position]);
        return convertView;
    }
}
