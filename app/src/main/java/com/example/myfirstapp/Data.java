package com.example.myfirstapp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class Data extends Activity implements View.OnClickListener {
    Button startBtn, startForBtn;
    EditText name;
    TextView result;

    @Override
    public void onCreate(Bundle SavedInstanceState) {

        super.onCreate(SavedInstanceState);

        setContentView(R.layout.get);

        initialize();

        ActionBar bar = getActionBar();
        bar.setDisplayHomeAsUpEnabled(true);

    }

    public void initialize() {
        startBtn = (Button) findViewById(R.id.startActivityBtn);
        startForBtn = (Button) findViewById(R.id.startActivityForResultBtn);
        result = (TextView) findViewById(R.id.results);
        name = (EditText) findViewById(R.id.firstName);

        startForBtn.setOnClickListener(this);
        startBtn.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startActivityBtn:
                String getName = name.getText().toString();
                Bundle holdName = new Bundle();
                holdName.putString("key", getName);
                Intent a = new Intent(Data.this, OpenedClass.class);
                a.putExtras(holdName);
                startActivity(a);
                break;

            case R.id.startActivityForResultBtn:
                Intent i = new Intent(Data.this, OpenedClass.class);
                startActivityForResult(i, 0);
                break;
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle getHoldGender = data.getExtras();

            String genderValue = getHoldGender.getString("answer");
            result.setText(genderValue);
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
