package com.example.login_signup;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class viewAuction extends AppCompatActivity {

    String startDate = "12/06/2021";
    String endDate = "13/06/2021";
    String startTime = "13.00 PM";
    String endTime = "14.00 PM";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
    Button Continue,End;
    String auction_id,auction_id1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        startDate=intent.getStringExtra("start_date");
        startTime=intent.getStringExtra("start_time");
        endTime=intent.getStringExtra("end_time");
        endDate=intent.getStringExtra("end_date");
        get_available_owner_auction_id(uid);
        System.out.println("Closing auction id:"+auction_id);
        setContentView(R.layout.activity_view_auction);

        TextView textView_6 = findViewById(R.id.start_date);
        textView_6.setText(startDate);
        TextView textView_11 = findViewById(R.id.end_date);
        textView_11.setText(endDate);
        TextView textView_8 = findViewById(R.id.start_time);
        textView_8.setText(startTime);
        TextView textView_13 = findViewById(R.id.end_time);
        textView_13.setText(endTime);

        End = findViewById(R.id.End);
        Continue=findViewById(R.id.Continue);
        End.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                End_Auction(auction_id);
                System.out.println("End Auction");
                startActivity(new Intent(viewAuction.this, createAuction.class));

            }
        });


        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(viewAuction.this, createAuction.class));
            }
        });

    }

    private void get_available_owner_auction_id(String user_id){

        db.collection("Auctions")
                .whereEqualTo("owner_id", user_id).whereEqualTo(  "active",true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                auction_id=document.getId();
                                System.out.println(auction_id);
                            }
                        } else {
                            System.out.println("Error getting documents: ");
                        }
                    }
                });

    }

    private void End_Auction(String auction_id1) {
        System.out.println("argument id:"+auction_id1);
        Map<String, Object> end_auction_data = new HashMap<>();
        end_auction_data.put("active", false);
        db.collection("Auctions").document(auction_id).update(end_auction_data)
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