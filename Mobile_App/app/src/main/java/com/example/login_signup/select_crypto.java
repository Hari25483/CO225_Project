package com.example.login_signup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

public class select_crypto extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }
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
        String cryptocoin_name=adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(),cryptocoin_name,Toast.LENGTH_SHORT).show();
        System.out.println(cryptocoin_name);

        try {
            //Public API:
            //https://www.metaweather.com/api/location/search/?query=<CITY>
            //https://www.metaweather.com/api/location/44418/


            URL url = new URL("https://min-api.cryptocompare.com/data/pricemulti?fsyms="+cryptocoin_name+"&tsyms=USD&api_keyR=708ad7c2a133986474a2585a9a36faffa9248de78fd6a3bd6284f16d460f7055");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Check if connect is made
            int responseCode = conn.getResponseCode();

            // 200 OK
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {

                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }
                //Close the scanner
                scanner.close();

                System.out.println("data :"+informationString);


                //JSON simple library Setup with Maven is used to convert strings to JSON
//                JSONParser parse = new JSONParser();
//                JSONArray dataObject = (JSONArray) parse.parse(String.valueOf(informationString));
                JSONParser parser = new JSONParser();
                Object obj  = parser.parse(String.valueOf(informationString));
                JSONArray array = new JSONArray();
                array.add(obj);

                //Get the first JSON object in the JSON array
                System.out.println(array.get(0));

                JSONObject countryData = (JSONObject) array.get(0);

                HashMap<String, Double> capitalCities = new HashMap<String, Double>();

                capitalCities= (HashMap<String, Double>) countryData.get("BTC");
                System.out.println(capitalCities.get("USD"));


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
}

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}