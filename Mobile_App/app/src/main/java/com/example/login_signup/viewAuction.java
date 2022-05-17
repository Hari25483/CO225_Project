package com.example.login_signup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class viewAuction extends AppCompatActivity {

    String startDate = "12/06/2021";
    String endDate = "13/06/2021";
    String startTime = "13.00 PM";
    String endTime = "14.00 PM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_auction);

        TextView textView_6 = findViewById(R.id.textView6);
        textView_6.setText(startDate);
        TextView textView_11 = findViewById(R.id.textView11);
        textView_11.setText(endDate);
        TextView textView_8 = findViewById(R.id.textView8);
        textView_8.setText(startTime);
        TextView textView_13 = findViewById(R.id.textView13);
        textView_13.setText(endTime);
    }
}