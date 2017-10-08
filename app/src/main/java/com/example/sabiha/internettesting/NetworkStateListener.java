package com.example.sabiha.internettesting;

import android.app.NotificationManager;
import android.app.usage.NetworkStatsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.net.InetAddress;

/**
 * Created by Sabiha on 10/8/2017.
 */

public class NetworkStateListener extends BroadcastReceiver {

    String TAG = "";
    com.example.sabiha.internettesting.ConnectivityManager myCM;
    @Override
    public void onReceive(Context context, Intent intent) {
        myCM = new com.example.sabiha.internettesting.ConnectivityManager(context);
        TAG = NetworkStateListener.class.getSimpleName();

        Log.i(TAG, "Inside BroadcastReceiver Class");
        Bundle bundle = intent.getExtras();
        myCM.connectionCheck((NetworkInfo) bundle.getParcelable("networkInfo"));
    }
}
