package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import androidx.annotation.NonNull;

public class Preference extends PreferenceActivity implements android.preference.Preference.OnPreferenceChangeListener {
    CheckBoxPreference checkBox;
    FileInputStream fis = null;
    String storePassword = "";
    String FILENAME = "PasswordData";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceManager prefMgr = getPreferenceManager();
        prefMgr.setSharedPreferencesName("appPreferences");

        addPreferencesFromResource(R.xml.preferences_xml);

        checkBox = (CheckBoxPreference) findPreference("password");

        checkBox.setOnPreferenceChangeListener(this);


    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
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


    public boolean onPreferenceChange(android.preference.Preference preference, Object newValue) {
        if (preference.isEnabled()) {
            checkIfPasswordIsSet();

            if (storePassword.length() > 0) {
                //Toast.makeText(Preference.this, "Password Login is Enabled", Toast.LENGTH_LONG).show();
                return true;
            } else {
                Intent i = new Intent(Preference.this, Set_Password.class);
                startActivity(i);
            }
        }
        return false;
    }
}
