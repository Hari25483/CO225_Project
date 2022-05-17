package com.example.login_signup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class availableAuctions extends AppCompatActivity {

    String auction[] = {"Auction 1", "Auction 2", "Auction 3"};
    String date[] = {"Closing  10/10/2022", "Closing  10/11/2022", "Closing  10/12/2022"};
    String time[] = {"Time at 8.00 pm", "Time at 3.00 pm", "Time at 5.00 pm"};

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_auctions);
        listView = (ListView) findViewById(R.id.CustomList);
        CustomBaseAdopter customBaseAdopter = new CustomBaseAdopter(getApplicationContext(), auction, time, date);
        listView.setAdapter(customBaseAdopter);
    }
}