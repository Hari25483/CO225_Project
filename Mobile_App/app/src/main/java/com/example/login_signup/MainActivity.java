package com.example.login_signup;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private EditText reg_email, reg_password;
    private TextView reg_signup_text;
    private Button reg_signup;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth reg_Auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reg_email = findViewById(R.id.email_reg);
        reg_password = findViewById(R.id.pass_reg);
        reg_signup = findViewById(R.id.signup);
        reg_signup_text = findViewById(R.id.textView_signup);

        //Create firebase authentication
        reg_Auth = FirebaseAuth.getInstance();
        reg_signup_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignIn.class));
            }
        });
        reg_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create_user();
            }
        });


    }

    private void create_user() {
        String email = reg_email.getText().toString();
        String password = reg_password.getText().toString();

        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if (!password.isEmpty()) {
                reg_Auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                saveToFireStore(email, uid);
                                Toast.makeText(MainActivity.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, SignIn.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Registered Failed!", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                reg_password.setError("Empty Fields are not allowed");
            }
        } else if (email.isEmpty()) {
            reg_email.setError("Empty Fields are not allowed");
        } else {
            reg_email.setError("Please Enter a correct email");
        }
    }

    private void saveToFireStore(String email, String uid) {

        if (!email.isEmpty()) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("email", email);
//            map.put("title" , name);
            map.put("uid", uid);

            db.collection("Users").document(uid).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Data Saved !!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "Failed !!", Toast.LENGTH_SHORT).show();
                }
            });

        } else
            Toast.makeText(this, "Empty Fields not Allowed", Toast.LENGTH_SHORT).show();
    }
    private void check_admin_role(String uid) {
    DocumentReference docRef = db.collection("Users").document(uid);
    docRef.get().

    addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
        @Override
        public void onComplete (@NonNull Task < DocumentSnapshot > task) {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    System.out.println(document.getData());
//                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                } else {
                    Log.d(TAG, "No such document");
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        }
    });
}
}