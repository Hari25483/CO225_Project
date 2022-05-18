package com.example.login_signup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomBaseAdopter extends BaseAdapter {

    Context context;
    List<String> auction = new ArrayList<String>();
    List<String> date = new ArrayList<String>();
    List<String> time = new ArrayList<String>();
    LayoutInflater inflater;

    public CustomBaseAdopter(Context ctx, List<String> auction, List<String> date, List<String> time){
        this.context = ctx;
        this.auction = auction;
        this.date = date;
        this.time = time;
        inflater = LayoutInflater.from(ctx);
    }
    @Override
    public int getCount() {
        return auction.size();
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
        auction1.setText(auction.get(position));
        time1.setText(time.get(position));
        date1.setText(date.get(position));
        return convertView;
    }
}
