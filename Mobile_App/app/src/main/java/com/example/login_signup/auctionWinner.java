package com.example.login_signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class auctionWinner extends AppCompatActivity {

    String winnerName ;
    String  cryptoName;
    String highestBid ;
    String initialValue;
    String auction_id;
    Button view_other_auctions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        view_other_auctions=(Button) findViewById(R.id.view_other_auctions_btn);
        Intent intent = getIntent();
        auction_id=intent.getStringExtra("auction_id");
        cryptoName=intent.getStringExtra("crypto_name");
        highestBid=intent.getStringExtra("current_value");
        initialValue=intent.getStringExtra("initial_value");
        winnerName=intent.getStringExtra("winner");
        setContentView(R.layout.activity_auction_winner);

        TextView winnername = findViewById(R.id.textView21);
        winnername.setText(winnerName);
        TextView cryptocurrencyname = findViewById(R.id.textView22);
        cryptocurrencyname.setText(cryptoName);
        TextView highestBidVal = findViewById(R.id.textView23);
        highestBidVal.setText(highestBid+" $");
        TextView initialBidVal = findViewById(R.id.textView23);
        initialBidVal.setText("Initial Value "+initialValue+" $");

//        view_other_auctions.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                System.out.println("I'm going to bid in other auctions");
//                Intent intent = new Intent(auctionWinner.this,finished_currency.class);
//                startActivity(intent);
//
//            }
//        });
    }
}