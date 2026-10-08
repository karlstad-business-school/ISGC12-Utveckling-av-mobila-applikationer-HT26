package com.example.exempel_api_json_volley;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements APICallback {

    private final String API_KEY = "5588f7e589b4456142ec566c98529252";
    private TextView cityTV, weatherTV, tempTV;
    private EditText cityET;
    private Button searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        cityTV = findViewById(R.id.tv_city);
        weatherTV = findViewById(R.id.tv_weather);
        tempTV = findViewById(R.id.tv_temp);
        cityET = findViewById(R.id.city_ET);
        searchBtn = findViewById(R.id.search_btn);


        Cache cache = new DiskBasedCache(getCacheDir(), 1024*1024);
        Network network = new BasicNetwork(new HurlStack());
        RequestQueue requestQueue = new RequestQueue(cache, network);
        requestQueue.start();


        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = cityET.getText().toString();
                String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + API_KEY + "&mode=json";

                APICall api = new APICall();
                api.get(requestQueue, MainActivity.this, url);
            }
        });
    }

    @Override
    public void onSuccess(JSONObject object) {
        Log.e("TEST", object.toString());

        try{
            String name = object.get("name").toString();
            JSONObject tObject = object.getJSONObject("main");
            String temp = tObject.get("temp").toString();

            JSONArray weather = object.getJSONArray("weather");
            String sky = weather.getJSONObject(0).get("main").toString();


            /*
            for(int i = 0; i < weather.length(); i++){

            }
             */

            cityTV.setText("City: " + name);
            weatherTV.setText("Weather: " + sky);
            tempTV.setText("Temperature: "+ temp);
        }catch (Exception e){

        }

    }

    @Override
    public void onFailure(Exception e) {

    }
}