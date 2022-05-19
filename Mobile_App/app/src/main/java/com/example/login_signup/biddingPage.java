package com.example.login_signup;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

public class biddingPage extends AppCompatActivity {

    String auctionName = "Auction 1";
    String crypto = "Bitcoin";
    int initialVal;
    Float current_bid ;
    Float current_bid_temp;
    String initialVal1;
    String current_bid1;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView textView_17;
    TextView textView_19;
    String price,baseprice;
    TextView textView_26;
    Integer current_bid2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String auction_id=intent.getStringExtra("auction_id");
        System.out.println("Auction_ID: "+ auction_id);
        String cryptocoin_name=intent.getStringExtra("cryptocoin_name");
        baseprice=get_crypto_base_price(cryptocoin_name);
        System.out.println("CryptoCoin_name_bidding page: "+ cryptocoin_name);
        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        setContentView(R.layout.activity_bidding_page);

        TextView textView_14 = findViewById(R.id.textView14);
        textView_14.setText(auctionName);
        TextView textView_15 = findViewById(R.id.textView15);
        textView_15.setText(cryptocoin_name);
        textView_17 = findViewById(R.id.textView17);
        textView_19 = findViewById(R.id.textView19);
        TextView bidValue = (EditText)findViewById(R.id.editTextNumber15);
        Button create = (Button) findViewById(R.id.button4);
        get_data_from_firestore(auction_id,cryptocoin_name);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                get_data_from_firestore(auction_id,cryptocoin_name);
                String biddingValue = bidValue.getText().toString();
                current_bid_temp = Float.parseFloat(biddingValue);
                System.out.println("CBT"+current_bid_temp);
                System.out.println("INVA"+initialVal1);
                System.out.println("CBT1"+current_bid1);
                if ((current_bid_temp >Float.parseFloat(String.valueOf(initialVal1)))&&(current_bid_temp >Float.parseFloat(current_bid1))) {
                    current_bid=current_bid_temp;
                    Map<String, Object> bidding_data = new HashMap<>();
                    bidding_data.put("current_value", current_bid);
                    bidding_data.put("current_bidder_id", uid);
//                    bidding_data.put("initial_value", initialVal);
                    update_data_to_firestore(auction_id, cryptocoin_name, bidding_data);
                    TextView textView_17 = findViewById(R.id.textView17);
                    textView_17.setText(String.valueOf(current_bid));
                    System.out.println(current_bid);
                    textView_26 = findViewById(R.id.textView26);
                    textView_26.setText("Good luck......");
                }
                else{
                    System.out.println("Incorrect bid value");
                    textView_26 = findViewById(R.id.textView26);
                    textView_26.setText("Bid More......");
                }
            }
        });

    }

    private void get_data_from_firestore(String auction_id, String cryptocoin_name){
        DocumentReference docRef = db.collection("Auctions").document(auction_id).collection("crypto_currencies").document(cryptocoin_name);
        docRef.get().
                //Fmog6jNpnSjulWAfyJ3U
                        addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>(){
                    @Override
                    public synchronized void onComplete(@NonNull Task< DocumentSnapshot > task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {

                                initialVal1 = document.getData().get("initial_value").toString();
                                current_bid1 = document.getData().get("current_value").toString();
//                                current_bid2=Integer.parseInt((String) document.getData().get("current_value"));
                                System.out.println(initialVal1);
                                System.out.println(current_bid1);
                                textView_17.setText(String.valueOf(current_bid1));
                                textView_19.setText(initialVal1 + " $");

                            } else {
                                Log.d(TAG, "No such document");
//                                try {
//                                    Thread.sleep(1000);
//                                }
//                                catch (Exception e){
//                                    System.out.println(e);
//                                }
                                Map<String, Object> crypto_coin_data = new HashMap<>();
                                crypto_coin_data.put("current_value",baseprice );
                                crypto_coin_data.put("current_bidder_id", null);
                                crypto_coin_data.put("initial_value", baseprice);

                                set_data_to_firestore(auction_id,cryptocoin_name,crypto_coin_data);
                                get_data_from_firestore(auction_id,cryptocoin_name);
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });

    }

    private void set_data_to_firestore(String auction_id, String cryptocoin_name, Map<String, Object> data){
        db.collection("Auctions").document(auction_id).collection("crypto_currencies").document(cryptocoin_name)
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });

    }

    private void update_data_to_firestore(String auction_id, String cryptocoin_name, Map<String, Object> data){
        db.collection("Auctions").document(auction_id).collection("crypto_currencies").document(cryptocoin_name)
                .update(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });

    }

    private String get_crypto_base_price(String cryptocurrency){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
        try {
            URL url = new URL("https://min-api.cryptocompare.com/data/pricemulti?fsyms="+cryptocurrency+"&tsyms=USD&api_keyR=708ad7c2a133986474a2585a9a36faffa9248de78fd6a3bd6284f16d460f7055");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Check if connect is made
            int responseCode = conn.getResponseCode();
//                    thanu@gmail.com
            // 200 OK
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {

                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }
                //Close the scanner
                scanner.close();

                System.out.println("data :"+informationString);


                //JSON simple library Setup with Maven is used to convert strings to JSON
//                JSONParser parse = new JSONParser();
//                JSONArray dataObject = (JSONArray) parse.parse(String.valueOf(informationString));
                JSONParser parser = new JSONParser();
                Object obj  = parser.parse(String.valueOf(informationString));
                JSONArray array = new JSONArray();
                array.add(obj);

                //Get the first JSON object in the JSON array
                System.out.println(array.get(0));

                JSONObject countryData = (JSONObject) array.get(0);

                HashMap<String, Double> capitalCities = new HashMap<String, Double>();

                capitalCities= (HashMap<String, Double>) countryData.get(cryptocurrency);
                System.out.println(capitalCities.get("USD"));
                price= capitalCities.get("USD").toString();
                System.out.println(price);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//            }
//        }).start();

        return price;
    }
}