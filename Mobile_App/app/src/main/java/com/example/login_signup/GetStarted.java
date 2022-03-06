package com.example.login_signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class GetStarted extends AppCompatActivity {
    private Button signout;
    private Button select_auctions;
    private FirebaseAuth m_Auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);
        signout=findViewById(R.id.get_started);
        select_auctions=findViewById(R.id.select_auction);
        m_Auth=FirebaseAuth.getInstance();

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_Auth.signOut();
                startActivity(new Intent(GetStarted.this,SignIn.class));
                finish();
            }
        });
        select_auctions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GetStarted.this,select_crypto.class);
                startActivity(intent);
            }
        });
    }
}