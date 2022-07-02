package com.example.myfirstapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import androidx.annotation.NonNull;

public class Home extends Activity {
    MediaPlayer mySong;
    FileInputStream fis = null;
    String storePassword = "";
    String FILENAME = "PasswordData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        mySong = MediaPlayer.create(Home.this, R.raw.adele);

        SharedPreferences getPreferencesForMusic = getSharedPreferences("appPreferences", 0);
        boolean music = getPreferencesForMusic.getBoolean("music", true);

        SharedPreferences getPreferencesForPwd = getSharedPreferences("appPreferences", 0);
        ;
        boolean passwd = getPreferencesForPwd.getBoolean("password", false);

        if (music == true) mySong.start();

        new LoadPassword().execute(FILENAME);

        copyDatabase();

        Thread timer = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (passwd == true) {
                        getPassword();
                        Intent i = new Intent(Home.this, Log_in.class);
                        i.putExtra("password", storePassword);
                        startActivityForResult(i, 1);
                    } else {
                        Intent openMenuActivity = new Intent("com.example.myfirstapp.MENU");
                        startActivity(openMenuActivity);
                    }
                }

            }
        };

        timer.start();
    }

    private void copyDatabase() {
        String appDataPath = getApplicationInfo().dataDir;
        File dbFolder = new File(appDataPath + "/databases");
        dbFolder.mkdir();

        File dbFilePath = new File(appDataPath + "/databases/mygym.db");

        if (!dbFilePath.exists()) {
            try {
                InputStream inputStream = getAssets().open("mygym.db");
                OutputStream outputStream = new FileOutputStream(dbFilePath);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }

                outputStream.flush();
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        mySong.release();
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

    public void getPassword() {
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

    public class LoadPassword extends AsyncTask<String, Integer, String> {
        ProgressDialog dialog = new ProgressDialog(Home.this);

        @Override
        protected void onPreExecute() {
            dialog.setTitle("Loading Password Data");
            //dialog.setMessage("please wait..");
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setMax(100);
            dialog.setIcon(R.mipmap.ic_launcher);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            for (int i = 0; i < 20; i++) {
                publishProgress(5);

                try {
                    Thread.sleep(88);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            dialog.dismiss();
            try {
                fis = openFileInput(FILENAME);
                byte[] data = new byte[fis.available()];
                while (fis.read(data) != -1) {
                    storePassword = new String(data);
                }

                return storePassword;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            dialog.incrementProgressBy(values[0]);
        }


        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(Home.this, s, Toast.LENGTH_LONG).show();
        }
    }


}
