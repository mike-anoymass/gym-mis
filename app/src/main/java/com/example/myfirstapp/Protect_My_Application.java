package com.example.myfirstapp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import androidx.annotation.NonNull;

public class Protect_My_Application extends Activity {
    Button editPwdBtn;
    FileInputStream fis = null;
    String storePassword = "";
    String FILENAME = "PasswordData";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.protection);

        checkIfPasswordIsSet();

        editPwdBtn = (Button) findViewById(R.id.editPwdBtn);

        if (storePassword.length() > 0) {
            editPwdBtn.setEnabled(true);
        }

        ActionBar bar = getActionBar();
        bar.setDisplayHomeAsUpEnabled(true);

    }


    public void editPassword(View view) {
        Intent i = new Intent(Protect_My_Application.this, Edit_Password.class);
        i.putExtra("pwd", storePassword);
        startActivityForResult(i, 0);
    }

    public void checkIfPasswordIsSet() {
        try {
            fis = openFileInput(FILENAME);
            byte[] data = new byte[fis.available()];
            while (fis.read(data) != -1) {
                storePassword = new String(data);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater blowUp = getMenuInflater();
        blowUp.inflate(R.menu.menu, menu);

        createMenu(menu);
        return true;

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

            case 0:
                Toast.makeText(this, "Add Clicked", Toast.LENGTH_LONG).show();
                break;

            case 1:
                Toast.makeText(this, "Refresh Clicked", Toast.LENGTH_LONG).show();
                break;

            case 2:
                Toast.makeText(this, "Delete Clicked", Toast.LENGTH_LONG).show();
                break;

            case android.R.id.home:
                finish();
                break;

        }
        return false;
    }

    public void createMenu(android.view.Menu menu) {
        MenuItem mn1 = menu.add(0, 0, 0, "Add");
        {
            mn1.setIcon(R.mipmap.ic_launcher);
            mn1.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM |
                    MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        }

        MenuItem mn2 = menu.add(0, 1, 1, "Refresh");
        {
            mn2.setIcon(R.mipmap.ic_launcher);
            mn2.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM |
                    MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        }

        MenuItem mn3 = menu.add(0, 2, 2, "Delete");
        {
            mn3.setIcon(R.mipmap.ic_launcher);
            mn3.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM |
                    MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        }
    }
}
