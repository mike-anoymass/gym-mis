package com.example.myfirstapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Log_in extends Activity {
    ToggleButton tBtn;
    EditText passwordTxt;
    ProgressDialog dialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.log_in);
        initialize();
    }

    public void initialize() {
        tBtn = (ToggleButton) findViewById(R.id.tBtn1);
        passwordTxt = (EditText) findViewById(R.id.passwordTxt1);
    }

    public void logInClicked(View view) {
        String password = getIntent().getStringExtra("password");
        String passwordFromUser = passwordTxt.getText().toString();

        if (password.contentEquals(passwordFromUser)) {
            Intent openMenuActivity = new Intent("com.example.myfirstapp.MENU");
            startActivity(openMenuActivity);
        } else {
            Toast.makeText(Log_in.this, "Passwords do not match ", Toast.LENGTH_LONG).show();
        }


    }

    public void toggleButtonClicked1(View view) {
        if (tBtn.isChecked()) {
            passwordTxt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            passwordTxt.setInputType(InputType.TYPE_CLASS_TEXT);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
