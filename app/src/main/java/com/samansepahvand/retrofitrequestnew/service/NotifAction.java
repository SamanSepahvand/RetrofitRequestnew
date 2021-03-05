package com.samansepahvand.retrofitrequestnew.service;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;


import com.google.gson.Gson;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class NotifAction extends BroadcastReceiver {


    private static final String TAG = "maybe";

    @Override
    public void onReceive(final Context context, final Intent intent) {

        String action = intent.getAction();

        if (action.equals("ActionReadMessage")) {
            Bundle extra = intent.getExtras();
            if (extra != null) {
                int id = extra.getInt("NotifIdMessage");
                int groupId = extra.getInt("NotifGroupMessage");
                setMessageRead(id,groupId,context);

            } else {

            }


        }


    }


    private  void setMessageRead(int id,int groupid,Context context){
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(id);

    }
}

