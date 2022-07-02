package com.example.myfirstapp;

import android.app.Activity;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

public class NotificationTuts extends Activity {

    static int notiId = 1;
    String userNameTxt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_tuts);

        SharedPreferences sp = getSharedPreferences("appPreferences", 0);
        userNameTxt = sp.getString("name", "Welcome");

        String[] permissions = {"android.permission.VIBRATE"};
        ActivityCompat.requestPermissions(NotificationTuts.this, permissions, 1);
    }

    public void onClick(View view) {
        makeNotification();
    }

    public void makeNotification() {
        NotificationManager manager;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext(), "notify_01");

        Intent i = new Intent(this, NotiView.class);
        i.putExtra("notiId", notiId);

        PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);

        /*NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.bigText("Mike");
        bigTextStyle.setBigContentTitle("TOdays Message");
        bigTextStyle.setSummaryText("Keep it simple");*/

        builder.setContentIntent(pi);
        builder.setSmallIcon(R.drawable.icon);
        builder.setContentTitle("Hey! " + userNameTxt);
        builder.setContentText("Welcome To poly Gym Management system. Its anoymass programs");
        builder.setPriority(Notification.PRIORITY_MAX);
        //buider.setStyle(bigTextStyle);

        manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE) {
            String channelId = "Your_channel_id";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH
            );

            manager.createNotificationChannel(channel);
            builder.setChannelId(channelId);
        }
        manager.notify(notiId, builder.build());

    }
}
