package com.example.myfirstapp;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;

public class Sound extends Activity implements View.OnClickListener, View.OnLongClickListener {

    SoundPool sp;
    int explosion = 0;
    MediaPlayer mp3;

    @Override
    public void onCreate(Bundle mike) {
        super.onCreate(mike);

        View v = new View(this);
        v.setOnClickListener(this);
        v.setOnLongClickListener(this);
        setContentView(v);

        sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        explosion = sp.load(this, R.raw.adele, 1);
        mp3 = MediaPlayer.create(this, R.raw.adele);

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        if (explosion != 0) {
            sp.play(explosion, 1, 1, 0, 0, 1);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        sp.release();
        mp3.release();
        finish();
    }


    @Override
    public boolean onLongClick(View v) {
        mp3.start();
        return false;
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


}
