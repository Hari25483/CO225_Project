package com.example.login_signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class select_crypto extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public TextView baseprice;
    String price;
    TextView auctionHeading;
    TextView time;
    Button start_bidding;
    TextView date;
    String cryptocoin_name;
    ArrayList<String> cryptos = new ArrayList<String>(); // Create an ArrayList object

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_crypto);
        auctionHeading = findViewById(R.id.auctionHeading);
        time = findViewById(R.id.time);
        start_bidding=findViewById(R.id.start_bidding);
        date = findViewById(R.id.date);

        Intent intent = getIntent();
        auctionHeading.setText(intent.getStringExtra("auctionHeading"));
        String auction_id=intent.getStringExtra("auction_id");
        cryptos=intent.getStringArrayListExtra("cryptos");

        System.out.println("Auction_ID: "+ auction_id);
//        System.out.println(intent.getStringExtra("time"));
//        System.out.println(intent.getStringExtra("date"));


        baseprice=findViewById(R.id.baseprice);
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }

//        Spinner myspinner = (Spinner) findViewById(R.id.crypto_select);
        Spinner myspinner = (Spinner) findViewById(R.id.crypto_select);
        System.out.println(cryptos);
//        ArrayAdapter<String> myAdaptor =new ArrayAdapter<String>(select_crypto.this,
                ArrayAdapter<String> myAdaptor = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cryptos);
//        myAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        myspinner.setAdapter(myAdaptor);
//        myspinner.setOnItemSelectedListener(this);
        myAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myspinner.setAdapter(myAdaptor);
        myspinner.setOnItemSelectedListener(this);


        start_bidding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),biddingPage.class);
                intent.putExtra("auction_id",auction_id);
                intent.putExtra("cryptocoin_name",cryptocoin_name);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
         cryptocoin_name=adapterView.getItemAtPosition(i).toString();
//        Toast.makeText(adapterView.getContext(),price,Toast.LENGTH_SHORT).show();
        System.out.println(cryptocoin_name);

}

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}