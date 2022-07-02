package com.example.myfirstapp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

import androidx.annotation.NonNull;

public class Camera extends Activity implements View.OnClickListener {
    final static int cameraData = 0;
    ImageView viewImage;
    ImageButton buttonImage;
    Button setWallPaper;
    Intent cameraIntent;
    Bitmap btm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo);
        initializeVaribles();

        ActionBar bar = getActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
    }

    public void initializeVaribles() {
        viewImage = (ImageView) findViewById(R.id.image);
        buttonImage = (ImageButton) findViewById(R.id.setImage);
        setWallPaper = (Button) findViewById(R.id.saveImage);

        setWallPaper.setOnClickListener(this);
        buttonImage.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setImage:
                setImage();
                break;
            case R.id.saveImage:
                setWallPaper1();
                break;
        }
    }

    public void setImage() {
        cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, cameraData);
    }

    public void setWallPaper1() {
        try {
            getApplicationContext().setWallpaper(btm);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            btm = (Bitmap) extras.get("data");
            viewImage.setImageBitmap(btm);
            buttonImage.setImageBitmap(btm);
        } else {
            Toast.makeText(this, "Failed to Load Image", Toast.LENGTH_LONG);
            finish();
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

            case android.R.id.home:
                finish();
                break;
        }
        return false;
    }

}
