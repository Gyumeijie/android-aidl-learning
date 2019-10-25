package com.github.gyumeijie.aidlexample;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    private Button buttonPrintHelloWorld;
    private HelloWorldInterface service;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            service = HelloWorldInterface.Stub.asInterface(iBinder);
            Log.d(TAG, "onService");
            Toast.makeText(MainActivity.this, "AIDL service connected", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            service = null;
            Toast.makeText(MainActivity.this, "AIDL service disconnected", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonPrintHelloWorld = (Button) findViewById(R.id.button_print_helloworld);
        buttonPrintHelloWorld.setClickable(false);

        buttonPrintHelloWorld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Toast.makeText(MainActivity.this, service.printHelloWorld(), Toast.LENGTH_LONG).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        initService();
    }

    private void initService() {
        Intent intent = new Intent();
        intent.setClassName(this.getPackageName(), HelloWorldService.class.getName());
        boolean bindResult = bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        Log.i(TAG, "initService() bindResult: " + bindResult);

        if (bindResult) {
            buttonPrintHelloWorld.setClickable(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
        mConnection = null;
        Log.d(TAG, "onDestroy");
    }
}
