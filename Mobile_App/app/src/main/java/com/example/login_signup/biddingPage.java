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

public class biddingPage extends AppCompatActivity {

    String auctionName = "Auction 1";
    String crypto = "Bitcoin";
    int initialVal = 750;
    int current_bid = initialVal;
    String initialVal1;
    String current_bid1;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView textView_17;
    TextView textView_19;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String auction_id=intent.getStringExtra("auction_id");
        System.out.println("Auction_ID: "+ auction_id);
        String cryptocoin_name=intent.getStringExtra("cryptocoin_name");
        System.out.println("CryptoCoin_name_bidding page: "+ cryptocoin_name);
        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        setContentView(R.layout.activity_bidding_page);

        TextView textView_14 = findViewById(R.id.textView14);
        textView_14.setText(auctionName);
        TextView textView_15 = findViewById(R.id.textView15);
        textView_15.setText(crypto);
        textView_17 = findViewById(R.id.textView17);
        textView_19 = findViewById(R.id.textView19);
        TextView bidValue = (EditText)findViewById(R.id.editTextNumber15);
        Button create = (Button) findViewById(R.id.button4);
        get_data_from_firestore(auction_id,cryptocoin_name);


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String biddingValue = bidValue.getText().toString();
                current_bid = Integer.parseInt(biddingValue);

                Map<String, Object> bidding_data= new HashMap<>();
                bidding_data.put("current_value", current_bid);
                bidding_data.put("current_bidder_id",uid );
                bidding_data.put("initial_value",initialVal );
                update_data_to_firestore(auction_id,cryptocoin_name,bidding_data);
                TextView textView_17 = findViewById(R.id.textView17);
                textView_17.setText(String.valueOf(current_bid));
                System.out.println(current_bid);
                //startActivity(new Intent(biddingPage.this,biddingPage.class));
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
                                System.out.println(initialVal1);
                                System.out.println(current_bid1);
                                textView_17.setText(String.valueOf(current_bid1));
                                textView_19.setText(initialVal1 + " $");

                            } else {
                                Log.d(TAG, "No such document");

                                Map<String, Object> crypto_coin_data = new HashMap<>();
                                crypto_coin_data.put("current_value", 0);
                                crypto_coin_data.put("current_bidder_id", null);
                                crypto_coin_data.put("initial_value",100.00 );

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


}