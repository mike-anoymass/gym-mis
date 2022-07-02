package com.example.myfirstapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

public class ExternalData extends Activity implements View.OnClickListener {
    static final int READ_BLOCK_SIZE = 100;
    EditText str;
    Button btnSave, btnLoad;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.external_data);

        str = (EditText) findViewById(R.id.str);
        btnSave = (Button) findViewById(R.id.b1);
        btnLoad = (Button) findViewById(R.id.b2);

        btnLoad.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        String[] permissions = {"android.permission.WRITE_EXTERNAL_STORAGE"};
        ActivityCompat.requestPermissions(ExternalData.this, permissions, 1);
    }


    public void load() {
        try {
            File sdCard = Environment.getExternalStorageDirectory();
            File directory = new File(sdCard.getAbsolutePath() + "/MyFiles");
            directory.mkdirs();

            File file = new File(directory, "textfile.txt");

            FileInputStream fIn = new FileInputStream(file);

            /*File location = Environment.getRootDirectory();
            File directory = new File(location.getAbsolutePath() + "/MyFiles");
            directory.mkdirs();

            File file = new File(directory , "textfile.txt");

            FileInputStream fIn = new FileInputStream(file);*/

            // FileInputStream fIn= openFileInput("textfile.txt");

            InputStreamReader isr = new InputStreamReader(fIn);

            char[] buffer = new char[READ_BLOCK_SIZE];
            String s = "";
            int charRead;

            while ((charRead = isr.read(buffer)) > 0) {
                String readString = String.valueOf(buffer, 0, charRead);
                s += readString;

                buffer = new char[READ_BLOCK_SIZE];
            }

            str.setText(s);
            Toast.makeText(ExternalData.this, "Data Loaded", Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            e.printStackTrace();
            String err = e.toString();
            Toast.makeText(ExternalData.this, err, Toast.LENGTH_LONG).show();
        }
    }

    public void save() {
        String text = str.getText().toString();
        try {
            File sdCard = Environment.getExternalStorageDirectory();
            File directory = new File(sdCard.getAbsolutePath() + "/MyFiles");
            directory.mkdirs();

            File file = new File(directory, "textfile.txt");
            FileOutputStream fOut = new FileOutputStream(file);

            /*File location = Environment.getRootDirectory();
            File directory = new File(location.getAbsolutePath() + "/MyFiles");
            directory.mkdirs();

            File file = new File(directory , "textfile.txt");

            FileOutputStream fOut = new FileOutputStream(file);*/

            // FileOutputStream fOut = openFileOutput("textfile.txt", 0);

            OutputStreamWriter osw = new OutputStreamWriter(fOut);
            osw.write(text);
            osw.flush();
            osw.close();

            Toast.makeText(ExternalData.this, "Data saved", Toast.LENGTH_LONG).show();
            str.setText("");

        } catch (IOException e) {
            String err = e.toString();
            Toast.makeText(ExternalData.this, err, Toast.LENGTH_LONG).show();
            str.setText(err);

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b1:
                save();
                break;
            case R.id.b2:
                load();
                break;
        }

    }
}