package com.example.login_signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class createAuction extends AppCompatActivity {

    private EditText auctionName, startDate,startTime, endDate, endTime ;
    private Button create;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_auction);

        auctionName = (EditText)findViewById(R.id.editTextTextPersonName);
        startDate = (EditText)findViewById(R.id.editTextDate);
        startTime = (EditText)findViewById(R.id.editTextTime);
        endDate = (EditText)findViewById(R.id.editTextDate2);
        endTime = (EditText)findViewById(R.id.editTextTime2);
        create = (Button) findViewById(R.id.button);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String auctionName1 =auctionName.getText().toString();
                String startDate1 = startDate.getText().toString();
                String startTime1 =startTime.getText().toString();
                String endDate1 = endDate.getText().toString();
                String endTime1 =endTime.getText().toString();

                System.out.println(auctionName1.toString());
                System.out.println(startDate1.toString());
                System.out.println(startTime1.toString());
                System.out.println(endDate1.toString());
                System.out.println(endTime1.toString());

                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                saveToFireStore(auctionName1,startDate1,startTime1,endDate1,endTime1,uid);

            }
        });}

    private void saveToFireStore(String auctionName, String startdate,String startTime,String enddate, String endtime, String uid) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("name",auctionName );
            map.put("start_date" ,startdate);
            map.put("start_time" ,startTime);
            map.put("end_date",enddate);
            map.put("end_time",endtime);
            map.put("owner_id", uid);
            map.put("active",true);

        db.collection("Auctions")
                .add(map)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        System.out.println(documentReference.getId());

//                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                        Log.w(TAG, "Error adding document", e);
                        System.out.println("Error adding document"+ e);
                    }
                });

    }
}