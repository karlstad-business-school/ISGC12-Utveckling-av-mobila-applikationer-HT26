package com.example.pokemonfinder;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class APICall {
    public void get(RequestQueue requestQueue, APICallback callback, String url){
        JsonObjectRequest jsonObjectRequest;

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject object) {
                Log.e("sdasd", "test 3" );
                callback.onSuccess(object);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onFailure(volleyError);
            }
        });

        Log.e("sdasd", "test 2" );
        requestQueue.add(jsonObjectRequest);
    }
}
