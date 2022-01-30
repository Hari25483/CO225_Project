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

public class SignIn extends AppCompatActivity {

    private EditText m_email,m_password;
    private TextView m_signin_text;
    private Button m_signin;
    private FirebaseAuth m_Auth;
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
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignIn.this,"Log In Successful!",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignIn.this,GetStarted.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignIn.this,"Registered Failed!",Toast.LENGTH_SHORT).show();
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
}