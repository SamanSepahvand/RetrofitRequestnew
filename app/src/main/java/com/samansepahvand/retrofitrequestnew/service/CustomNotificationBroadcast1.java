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

public class CustomNotificationBroadcast1 extends BroadcastReceiver {

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

            tempAction = context.getPackageName() + ".UI.Activities." + extra.getString("ClickAction");
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
                    intent = new Intent(mContext, ActivitySignUp.class);
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
        pendingIntent = PendingIntent.getActivity(mContext,
                1, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.e(TAG, "getNotification   Notif Type:  if ");

            String channelID = "1";
            String channelName = "news";
            String channelDesc = "news description";
            NotificationChannel notificationChannel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription(channelDesc);
            notificationChannel.enableLights(true);
            notificationChannel.setSound(null, null);
            notificationChannel.setLightColor(Color.GREEN);

            RingtoneManager.getRingtone(mContext,
                    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)).play();

            NotificationCompat.Builder   builder = new NotificationCompat.Builder(mContext, channelID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(remoteMessage.getNotifTitle())
                    .setContentText(remoteMessage.getNotifBody())
                    .setVibrate(new long[]{100, 500, 500, 500, 500})
                    .setAutoCancel(true)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher))
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setContentIntent(pendingIntent);

            Intent actionReadMessage = new Intent(mContext, NotifAction.class);
            actionReadMessage.setAction("ACTION_READ_MESSAGE");
            actionReadMessage.putExtra("NotifIdMessage", notfiId);
            actionReadMessage.putExtra("NotifGroupMessage", modelNotification.getGroupId());
            PendingIntent pendingIntentYes = PendingIntent.getBroadcast(mContext, 123456, actionReadMessage, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.addAction(R.mipmap.ic_launcher, "درج به عنوان خوانده شده", pendingIntentYes);


            if (remoteMessage.getDataImage() != null) {
                Bitmap bitmap = getBitmapfromUrl(remoteMessage.getDataImage());
                builder.setStyle(
                        new NotificationCompat.BigPictureStyle()
                                .bigPicture(bitmap)
                                .bigLargeIcon(null)
                ).setLargeIcon(bitmap);
            }




            notification = builder.build();
            notificationManager.createNotificationChannel(notificationChannel);


            Log.e(TAG, "getNotification i1: "+i1 );
            notificationManager.notify(i1,  notification);


        } else {


            Log.e(TAG, "getNotification   Notif Type:  else ");
            String channeld = "saman";
            NotificationCompat.Builder    builder = new NotificationCompat.Builder(mContext, channeld)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(remoteMessage.getNotifTitle())
                    .setContentText(remoteMessage.getNotifBody())
                    .setVibrate(new long[]{100, 500, 500, 500, 500})
                    .setAutoCancel(true)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setContentIntent(pendingIntent);

            Intent actionReadMessage = new Intent(mContext, NotifAction.class);
            actionReadMessage.setAction("ACTION_READ_MESSAGE");
            actionReadMessage.putExtra("NotifIdMessage", notfiId);
            actionReadMessage.putExtra("NotifGroupMessage", modelNotification.getGroupId());
            PendingIntent pendingIntentYes = PendingIntent.getBroadcast(mContext, 123456, actionReadMessage, PendingIntent.FLAG_UPDATE_CURRENT);

            builder.addAction(R.mipmap.ic_launcher, "درج به عنوان خوانده شده", pendingIntentYes);

            notification = builder.build();
            notificationManager.notify(i1,  notification);

        }




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

