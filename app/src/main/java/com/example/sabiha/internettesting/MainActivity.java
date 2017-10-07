package com.example.sabiha.internettesting;

import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.net.InetAddress;

public class MainActivity extends AppCompatActivity {

    boolean internetConnection = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(MainActivity.CONNECTIVITY_SERVICE);
        NetworkInfo nf = cm.getActiveNetworkInfo();
        if(nf.isConnected())
        {
            Toast.makeText(this, "Connected with " + nf.getExtraInfo() + " network", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "No network connection is available", Toast.LENGTH_LONG).show();
        }

        checkInternetConnection ci = new checkInternetConnection();
        ci.execute();
    }

    private class checkInternetConnection extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                InetAddress ia = InetAddress.getByName("www.google.com");
                InetAddress ia1 = InetAddress.getByName("www.amazon.com");
                InetAddress ia2 = InetAddress.getByName("www.facebook.com");
                internetConnection = !ia.equals("") || !ia1.equals("") || !ia2.equals("");
                Log.e("Testing", "Connection: " + internetConnection);
            }
            catch(Exception e)
            {
                Log.e("Testing", e.toString());
                internetConnection = false;
            }
            finally {
                createNotification();
            }
            return null;
        }
    }

    private void createNotification()
    {
        Resources res = this.getResources();
        int notificationID = 1;
        NotificationCompat.Builder ncBuilder = new NotificationCompat.Builder(MainActivity.this);
        ncBuilder.setAutoCancel(true)
            .setContentTitle(res.getString(R.string.internet_connection_title))
            .setContentText(internetConnection ? res.getString(R.string.internet_connection_true)
                    : res.getString(R.string.internet_connection_false))
            .setSmallIcon(R.drawable.ic_network_check_white_24dp);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID, ncBuilder.build());
    }

    /*public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            try {
                URL url = new URL("http://www.google.com/");
                HttpURLConnection urlc = (HttpURLConnection)url.openConnection();
                urlc.setRequestProperty("User-Agent", "test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1000); // mTimeout is in seconds
                urlc.connect();
                if (urlc.getResponseCode() == 200) {
                    return true;
                } else {
                    return false;
                }
            } catch (IOException e) {
                Log.i("warning", "Error checking internet connection", e);
                return false;
            }
        }

        return false;

    }*/
}
