package com.example.login_signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

    private EditText reg_email,reg_password;
    private TextView reg_signup_text;
    private Button reg_signup;
    private FirebaseAuth reg_Auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reg_email=findViewById(R.id.email_reg);
        reg_password=findViewById(R.id.pass_reg);
        reg_signup=findViewById(R.id.signup);
        reg_signup_text=findViewById(R.id.textView_signup);

        //Create firebase authentication
        reg_Auth=FirebaseAuth.getInstance();
        reg_signup_text.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this , SignIn.class));
        }
        });
        reg_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create_user();
            }
        });


    }
    private void create_user(){
        String email=reg_email.getText().toString();
        String password=reg_password.getText().toString();

        if (!email.isEmpty()&& Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            if (!password.isEmpty()){
                reg_Auth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(MainActivity.this,"Registered Successfully!",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this,SignIn.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this,"Registered Failed!",Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else{
                reg_password.setError("Empty Fields are not allowed");
            }
        }
        else if (email.isEmpty()){
            reg_email.setError("Empty Fields are not allowed");
        }
        else{
            reg_email.setError("Please Enter a correct email");
        }
    }
}