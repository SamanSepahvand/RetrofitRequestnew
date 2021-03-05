package com.samansepahvand.retrofitrequestnew.service;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;


import com.google.gson.Gson;
import com.samansepahvand.retrofitrequestnew.MainActivity;
import com.samansepahvand.retrofitrequestnew.R;
import com.samansepahvand.retrofitrequestnew.model.ModelNotification;
import com.samansepahvand.retrofitrequestnew.ui.ActivityLogin;
import com.samansepahvand.retrofitrequestnew.ui.ActivitySignUp;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class CustomNotificationBroadcast extends BroadcastReceiver {

    private static final String TAG = "HandlenNotifOpenChat";

    private Notification notification;
    private NotificationManager notificationManager;
    private PendingIntent pendingIntent;
    private Intent intent;
    private String tempAction;
    public static final String INTENT_FILTER = "NEW_MESSAGE";
    SharedPreferences preferences;
    private int notfiId = 0;
    private Context mContext;
    ModelNotification modelNotification = new ModelNotification();

    int min = 2000;
    int max = 5000;

    Random r = new Random();
    int i1;
    @Override
    public void onReceive(final Context context, final Intent intent) {
        mContext = context;


        Bundle extra=intent.getExtras();

        if (extra != null) {
             i1 = r.nextInt(max - min + 1) + min;
            modelNotification.setNotifTitle(extra.getString("Title"));
            modelNotification.setNotifBody(extra.getString("Body"));



            Log.e(TAG, "onReceive extra.getString(\"ClickAction\"): " + extra.getString("ClickAction"));

            tempAction = context.getPackageName() + ".ui." + "ActivityLogin";
            try {
                Class<?> c = Class.forName(tempAction);
                getNotification(modelNotification, c);
                Log.e(TAG, "with  DATA");

            } catch (ClassNotFoundException c) {
                Log.e(TAG, "getNotification: " + c.getMessage());
                getNotification(modelNotification, null);
                Log.e(TAG, "no action link");
            }
        } else {
            getNotification(modelNotification, null);
            Log.e(TAG, "No DATA");

        }


    }


    public void getNotification(ModelNotification remoteMessage, Class<?> action_click) {
        Log.e(TAG, "getNotification Id ididiidi : "+i1 );

        if (action_click != null) {
            try {

                        if (remoteMessage.getInvoiceId() != 0) {
                            intent = new Intent(mContext, action_click);
                            intent.putExtra("extra_notif_title", remoteMessage.getNotifTitle());
                            intent.putExtra("extra_notif_body", remoteMessage.getNotifBody());
                            intent.putExtra("extra_notif_invoice_id", String.valueOf(remoteMessage.getInvoiceId()));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        } else {
                            intent = new Intent(mContext, action_click);
                            Log.e(TAG, "getNotification:  remoteMessage.getGroupId()" + remoteMessage.getGroupId());
                            intent.putExtra("FlagGroupId", String.valueOf(remoteMessage.getGroupId()));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// | FLAG_ACTIVITY_REORDER_TO_FRONT);//Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            Log.e(TAG, "getNotification: getString if temp.equals(\"OK\")");
                        }

            } catch (Exception e) {
                intent = new Intent(mContext, ActivityLogin.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
        } else {
            intent = new Intent(mContext, ActivitySignUp.class);
            Log.e(TAG, "getNotification:  remoteMessage.getGroupId()" + remoteMessage.getGroupId());
            intent.putExtra("FlagGroupId", String.valueOf(remoteMessage.getGroupId()));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// | FLAG_ACTIVITY_REORDER_TO_FRONT);//Intent.FLAG_ACTIVITY_CLEAR_TASK);
            Log.e(TAG, "getNotification: getString if temp.equals(\"OK\")");
        }


        Uri alarmsound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);


        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(mContext);
        //taskStackBuilder.addParentStack(MainActivity.class);
        taskStackBuilder.addNextIntent(intent);
//        pendingIntent = PendingIntent.getActivity(mContext,
//                1, intent, PendingIntent.FLAG_CANCEL_CURRENT);

       PendingIntent intent2 = taskStackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);

        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel("my_channel_01", "hello", NotificationManager.IMPORTANCE_HIGH);
        }




        NotificationCompat.BigTextStyle bStyle = new NotificationCompat.BigTextStyle()
                .setBigContentTitle("موفقیت اتفاقی نیست");

        Notification notification = builder.setContentTitle("یادآور")
                .setContentText(intent.getStringExtra("Message")).setAutoCancel(true)
                .setSound(alarmsound).setSmallIcon(R.mipmap.ic_launcher_round)
                .setStyle(bStyle)
                .setContentIntent(intent2)
                .setChannelId("my_channel_01")
                .build();

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(i1, notification);






    }


    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);

        } catch (Exception e) {
            Log.e("awesome", "Error in getting notification image: " + e.getLocalizedMessage());
            return null;
        }
    }





}

