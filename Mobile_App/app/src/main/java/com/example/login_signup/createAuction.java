package com.example.login_signup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class createAuction extends AppCompatActivity {

    private EditText auctionName, startDate,startTime, endDate, endTime ;
    private Button create;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_auction);

        auctionName = (EditText)findViewById(R.id.editTextTextPersonName);
        startDate = (EditText)findViewById(R.id.editTextDate);
        startTime = (EditText)findViewById(R.id.editTextTime);
        endDate = (EditText)findViewById(R.id.editTextDate2);
        endTime = (EditText)findViewById(R.id.editTextTime2);
        create = (Button) findViewById(R.id.button);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(auctionName);
                System.out.println(startDate);
                System.out.println(startTime);
                System.out.println(endDate);
                System.out.println(endTime);
            }
        });}
}