package com.example.login_signup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class select_crypto extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_crypto);

        Spinner myspinner = (Spinner) findViewById(R.id.crypto_select);
        ArrayAdapter<String> myAdaptor =new ArrayAdapter<String>(select_crypto.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Crypto));
        myAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myspinner.setAdapter(myAdaptor);
        myspinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String crypto=adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(),crypto,Toast.LENGTH_SHORT).show();
        System.out.println(crypto);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}