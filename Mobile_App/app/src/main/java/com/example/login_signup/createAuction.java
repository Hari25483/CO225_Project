package com.example.login_signup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class createAuction extends AppCompatActivity {

    private EditText auctionName, startDate,startTime, endDate, endTime ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_auction);

        auctionName = (EditText)findViewById(R.id.editTextTextPersonName);
        startDate = (EditText)findViewById(R.id.editTextDate);
        startTime = (EditText)findViewById(R.id.editTextTime);
        endDate = (EditText)findViewById(R.id.editTextDate2);
        endTime = (EditText)findViewById(R.id.editTextTime2);

    }
}