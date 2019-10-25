package com.github.gyumeijie.aidlexample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class HelloWorldService extends Service {
    private static final String TAG = "HelloWorldService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate()");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new HelloWorldInterface.Stub() {
            @Override
            public String printHelloWorld() {
                return "Hello World";
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }
}
