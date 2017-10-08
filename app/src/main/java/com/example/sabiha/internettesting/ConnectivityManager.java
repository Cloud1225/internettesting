package com.example.sabiha.internettesting;

import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Resources;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.net.InetAddress;

/**
 * Created by Sabiha on 10/8/2017.
 */

public final class ConnectivityManager {

    Context myContext;
    Resources res;
    boolean internetConnection = false;
    String networkMessage = "", internetMessage = "", TAG = "";

    public ConnectivityManager(Context context)
    {
        myContext = context;
        res = context.getResources();
        TAG = ConnectivityManager.class.getSimpleName();
    }

    public void connectionCheck(NetworkInfo networkInfo)
    {
        if(networkInfo.isConnectedOrConnecting())
        {
            networkMessage = "Connected with " + networkInfo.getExtraInfo() + " network";
            checkInternetConnection ci = new checkInternetConnection();
            ci.execute();
        }
        else
        {
            Log.e(TAG, "No Network is available");
            networkMessage = "No Network is available";
            createNotification();
        }
    }

    private class checkInternetConnection extends AsyncTask<Void, Void, Void>
    {
        boolean internetConnection = false;
        @Override
        protected Void doInBackground(Void... params) {
            try {
                InetAddress ia = InetAddress.getByName("www.google.com");
                InetAddress ia1 = InetAddress.getByName("www.amazon.com");
                InetAddress ia2 = InetAddress.getByName("www.facebook.com");
                internetConnection = !ia.equals("") || !ia1.equals("") || !ia2.equals("");
                Log.e(TAG, "Connection: " + internetConnection);
            }
            catch(Exception e)
            {
                internetConnection = false;
                Log.e(TAG, e.toString() + " Connection: " + internetConnection);
            }
            finally {
                internetMessage = internetConnection ? res.getString(R.string.internet_connection_true)
                        : res.getString(R.string.internet_connection_false);
                createNotification();
            }
            return null;
        }
    }
    private void createNotification()
    {
        int notificationID = 1;
        NotificationCompat.Builder ncBuilder = new NotificationCompat.Builder(myContext);
        ncBuilder.setAutoCancel(true)
                .setContentTitle(res.getString(R.string.internet_connection_title))
                .setContentText(networkMessage)
                .setSmallIcon(R.drawable.ic_network_check_white_24dp)
                .setSubText(internetMessage);

        NotificationManager notificationManager = (NotificationManager) myContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID, ncBuilder.build());
    }
}
