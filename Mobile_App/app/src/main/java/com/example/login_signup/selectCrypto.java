package com.example.login_signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class selectCrypto extends AppCompatActivity {
    TextView auctionHeading;
    TextView time;
    TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_crypto2);

        auctionHeading = findViewById(R.id.auctionHeading);
        time = findViewById(R.id.time);
        date = findViewById(R.id.date);

        Intent intent = getIntent();
        auctionHeading.setText(intent.getStringExtra("auctionHeading"));
        time.setText(intent.getStringExtra("time"));
        date.setText(intent.getStringExtra("date"));
    }
}