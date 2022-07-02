package com.example.myfirstapp;

import android.app.Activity;
import android.app.NotificationManager;
import android.os.Bundle;

public class NotiView extends Activity {

    @Override
    public void onCreate(Bundle mike) {
        super.onCreate(mike);
        setContentView(R.layout.noti_details);

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        nm.cancel(getIntent().getExtras().getInt("notiId"));
    }
}
