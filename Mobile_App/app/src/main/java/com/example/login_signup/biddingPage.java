package com.example.login_signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class biddingPage extends AppCompatActivity {

    String auctionName = "Auction 1";
    String crypto = "Bitcoin";
    int initialVal = 750;
    int current_bid = initialVal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bidding_page);

        TextView textView_14 = findViewById(R.id.textView14);
        textView_14.setText(auctionName);
        TextView textView_15 = findViewById(R.id.textView15);
        textView_15.setText(crypto);
        TextView textView_17 = findViewById(R.id.textView17);
        textView_17.setText(String.valueOf(current_bid));
        TextView textView_19 = findViewById(R.id.textView19);
        textView_19.setText(initialVal + " $");

        TextView bidValue = (EditText)findViewById(R.id.editTextNumber15);
        Button create = (Button) findViewById(R.id.button4);



        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String biddingValue = bidValue.getText().toString();
                current_bid = Integer.parseInt(biddingValue);

                TextView textView_17 = findViewById(R.id.textView17);
                textView_17.setText(String.valueOf(current_bid));
                System.out.println(current_bid);
                //startActivity(new Intent(biddingPage.this,biddingPage.class));
            }
        });

    }
}