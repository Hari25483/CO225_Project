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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class availableAuctions extends AppCompatActivity {
    List<String> auction = new ArrayList<String>();
    List<String> date = new ArrayList<String>();
    List<String> time = new ArrayList<String>();
    List<String> auction_id = new ArrayList<String>();

    //
//    String auction[] = {"Auction 1", "Auction 2", "Auction 3"};
//    String date[] = {"Closing  10/10/2022", "Closing  10/11/2022", "Closing  10/12/2022"};
//    String time[] = {"Time at 8.00 pm", "Time at 3.00 pm", "Time at 5.00 pm"};
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private void get_auctions() {
//        db.collection("Auctions")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
//                            }
//                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
//                        }
//                    }
//                });

        db.collection("Auctions")
                .whereEqualTo("active", true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                auction_id.add((String) document.getData().get("auction_id"));
                                auction.add((String) document.getData().get("name"));
                                date.add((String) document.getData().get("end_date"));
                                time.add((String) document.getData().get("end_time"));


                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


    }


    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        get_auctions();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_auctions);
        listView = (ListView) findViewById(R.id.CustomList);
        CustomBaseAdopter customBaseAdopter = new CustomBaseAdopter(getApplicationContext(), auction, time, date);
        listView.setAdapter(customBaseAdopter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("custom","item position" + position);
//                startActivity(new Intent(availableAuctions.this,viewAuction.class));
                Intent intent = new Intent(getApplicationContext(),biddingPage.class);
                intent.putExtra("auctionHeading",auction.get(position));
                intent.putExtra("auction_id",auction_id.get(position));
                intent.putExtra("time",time.get(position));
                intent.putExtra("date",date.get(position));
                startActivity(intent);
            }
        });
    }
}
