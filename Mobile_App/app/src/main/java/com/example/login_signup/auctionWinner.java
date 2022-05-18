package com.example.login_signup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class auctionWinner extends AppCompatActivity {

    String winnerName = "Thulasiyan";
    String  cryptoName = "Bitcoin";
    int highestBid = 10000;
    int initialValue = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_winner);

        TextView winnername = findViewById(R.id.textView21);
        winnername.setText(winnerName);
        TextView cryptocurrencyname = findViewById(R.id.textView22);
        cryptocurrencyname.setText(cryptoName);
        TextView highestBidVal = findViewById(R.id.textView23);
        highestBidVal.setText(highestBid+" $");
        TextView initialBidVal = findViewById(R.id.textView23);
        initialBidVal.setText("Initial Value "+initialValue+" $");

        Button create = (Button) findViewById(R.id.button5);


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("I'm going to bid in other auctions");

            }
        });
    }
}