package com.example.myfirstapp;

import android.app.ListActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

public class Menu extends ListActivity {

    static int notificationId = 1234;
    ProgressDialog dialog;
    Notification n;
    NotificationManager nm;
    String userNameTxt;
    String[] classes = {"MainActivity", "Members", "ViewMem", "AddMember", "Email", "Camera", "Data", "GFX", "Sound", "Sliding", "Tabs",
            "WebViewBrowser", "Protect_My_Application", "ExternalData", "NotificationTuts", "ContactsList"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String[] permissions = {"android.permission.READ_CONTACTS"};
        ActivityCompat.requestPermissions(Menu.this, permissions, 2);
        super.onCreate(savedInstanceState);

        SharedPreferences userName = getSharedPreferences("appPreferences", MODE_PRIVATE);

        userNameTxt = userName.getString("name", "");

        Toast.makeText(Menu.this, "Welcome " + userNameTxt,
                Toast.LENGTH_SHORT).show();

        progressDialog();

        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setListAdapter(new ArrayAdapter<String>(Menu.this, android.R.layout.simple_dropdown_item_1line, classes));

    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String counter = classes[position];

        try {
            Class mainActivityClass = Class.forName("com.example.myfirstapp." + counter);
            Intent mainActivityIntent = new Intent(Menu.this, mainActivityClass);
            startActivity(mainActivityIntent);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater blowUp = getMenuInflater();
        blowUp.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.aboutUs:
                Intent i = new Intent("com.example.myfirstapp.ABOUT");
                startActivity(i);
                break;
            case R.id.preference:
                Intent p = new Intent("com.example.myfirstapp.PREFERENCE");
                startActivity(p);
                break;

            case R.id.exit:
                finish();
                break;
        }
        return false;
    }

    public void progressDialog() {
        dialog = ProgressDialog.show(this, "Starting Application",
                "Please Wait...", true);

        new Thread(() -> {
            try {
                Thread.sleep(4000);
                dialog.dismiss();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }


}
