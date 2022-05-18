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

public class SignIn extends AppCompatActivity {

    private EditText m_email,m_password;
    boolean admin;
    private TextView m_signin_text;
    private Button m_signin;
    private FirebaseAuth m_Auth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        m_email=findViewById(R.id.email_signin);
        m_password=findViewById(R.id.pass_signin);
        m_signin=findViewById(R.id.signin);
        m_signin_text=findViewById(R.id.textView_signin);
        //Create firebase authentication
        m_Auth=FirebaseAuth.getInstance();
        m_signin_text.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View v) {
            startActivity(new Intent(SignIn.this , MainActivity.class));
        }
        });
        m_signin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            login_user();
        }
    });
}
    //Function to login
    private void login_user(){
        String email=m_email.getText().toString();
        String password=m_password.getText().toString();

        if (!email.isEmpty()&& Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            if (!password.isEmpty()){
                m_Auth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                            @Override
                            public synchronized void onComplete(@NonNull Task<AuthResult> task) {
//                                new Thread(new Runnable() {
//                                    @Override
//                                    public void run() {
                                        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                                        check_admin_role(uid);
//                                    }
//                                }).start();
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignIn.this,"Login Failed!",Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else{
                m_password.setError("Empty Fields are not allowed");
            }
        }
        else if (email.isEmpty()){
            m_email.setError("Empty Fields are not allowed");
        }
        else{
            m_email.setError("Please Enter a correct email");
        }
    }

    private synchronized void check_admin_role(String uid) {
        DocumentReference docRef = db.collection("Users").document(uid);
        docRef.get().
                addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>(){
                    @Override
                    public synchronized void onComplete(@NonNull Task < DocumentSnapshot > task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                               admin= (boolean) document.getData().get("admin");
                               System.out.println("role:" +admin);
//                                boolean admin_check=check_admin_role(uid);
//thuva@gmail.com
//                                System.out.println(admin);
                                System.out.println("Admin credentials:" +admin);
//                                System.out.println(admin_check);
                                if (admin==true){
                                    System.out.println("route to admin page");
                                    startActivity(new Intent(SignIn.this,viewAuction.class));
                                }
                                else{
                                    startActivity(new Intent(SignIn.this,auctionWinner.class));
                                    System.out.println("route to userpage");
                                }




                                Toast.makeText(SignIn.this,"Log In Successful!",Toast.LENGTH_SHORT).show();

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