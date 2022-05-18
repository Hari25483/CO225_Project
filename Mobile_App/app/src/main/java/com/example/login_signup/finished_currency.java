package com.example.login_signup;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class finished_currency extends AppCompatActivity {
    String auction_id1;
    List<String> currency = new ArrayList<String>();
    List<String> initial_value = new ArrayList<String>();
    List<String> current_value = new ArrayList<String>();
    List<String> auction_id = new ArrayList<String>();
    List<String> empty_val1 = new ArrayList<String>();
    List<String> auction_winners = new ArrayList<String>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    private void get_auctions() {
        System.out.println("Auction_ID FC: "+ auction_id1);
        db.collection("Auctions").document(auction_id1).collection("crypto_currencies")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                auction_id.add(auction_id1);
                                auction_winners.add(document.getData().get("current_bidder_id").toString());
                                empty_val1.add(" ");
                                currency.add((String)document.getId());
                                current_value.add(document.getData().get("current_value").toString());
                                initial_value.add(document.getData().get("initial_value").toString());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


    }

//    private void find_winner_name(ArrayList<String> auction_winners){
//        for (String winner_id : auction_winners) {
//            DocumentReference docRef = db.collection("Users").document(winner_id);
//            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                    if (task.isSuccessful()) {
//                        DocumentSnapshot document = task.getResult();
//                        if (document.exists()) {
//                            System.out.println(document.getData("name"));
//                        } else {
//                            Log.d(TAG, "No such document");
//                        }
//                    } else {
//                        Log.d(TAG, "get failed with ", task.getException());
//                    }
//                }
//            });
//        }
//    }



    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        auction_id1=intent.getStringExtra("auction_id");
        get_auctions();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished_currency);
        listView = (ListView) findViewById(R.id.CustomList);
        CustomBaseAdopter customBaseAdopter = new CustomBaseAdopter(getApplicationContext(), currency, empty_val1, empty_val1);
        listView.setAdapter(customBaseAdopter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("custom","item position" + position);
//                startActivity(new Intent(availableAuctions.this,viewAuction.class));
                Intent intent = new Intent(getApplicationContext(),auctionWinner.class);
                intent.putExtra("auction_id",auction_id.get(position));
                intent.putExtra("crypto_name",currency.get(position));
                intent.putExtra("initial_value",initial_value.get(position));
                intent.putExtra("current_value",current_value.get(position));
                intent.putExtra("winner",auction_winners.get(position));
                intent.putExtra("",currency.get(position));
                startActivity(intent);
            }
        });
    }
}