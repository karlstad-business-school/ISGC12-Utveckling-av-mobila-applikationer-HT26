package com.example.pokemonfinder;

import org.json.JSONObject;

public interface APICallback {
    public void onSuccess(JSONObject object);
    public void onFailure(Exception e);
}
