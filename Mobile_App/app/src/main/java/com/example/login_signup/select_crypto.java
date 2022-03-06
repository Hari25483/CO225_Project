package com.example.login_signup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class select_crypto extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_crypto);

        Spinner myspinner = (Spinner) findViewById(R.id.crypto_select);
        ArrayAdapter<String> myAdaptor =new ArrayAdapter<String>(select_crypto.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Crypto));
        myAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myspinner.setAdapter(myAdaptor);
    }
}