package com.example.exempel_background_service_async;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyBackgroundService extends Service {


    public int onStartCommand(Intent intent, int flags, int startId){
        MyAsyncTask mat = new MyAsyncTask(this);
        mat.execute();

        return START_NOT_STICKY;
    }


    @Override
    public void onDestroy() {
        Log.e("IN SERVICE DESTROY", "Service was destroyed");

        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
