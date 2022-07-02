package com.example.myfirstapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;


public class Email extends Activity implements View.OnClickListener {

    EditText email, firstName, lastName, subject, message, password;
    String keepEmail, keepFname, keepLname, keepSubject, keepMessage, keepPassword;
    Button sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email);
        initializeVariables();
        sendBtn.setOnClickListener(this);
    }

    public void initializeVariables() {
        email = (EditText) findViewById(R.id.email);
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        subject = (EditText) findViewById(R.id.subject);
        message = (EditText) findViewById(R.id.message);
        password = (EditText) findViewById(R.id.password);
        sendBtn = (Button) findViewById(R.id.send_btn);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send_btn) {
            convertTextToStrings();
            String emails[] = {keepEmail};

            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.putExtra(Intent.EXTRA_EMAIL, emails);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, keepSubject);
            emailIntent.putExtra(Intent.EXTRA_TEXT, keepMessage);
            emailIntent.setType("plain/text");

            startActivity(emailIntent);

        }

    }

    public void convertTextToStrings() {
        keepEmail = email.getText().toString();
        keepFname = firstName.getText().toString();
        keepLname = lastName.getText().toString();
        keepSubject = subject.getText().toString();
        keepMessage = message.getText().toString();
        keepPassword = password.getText().toString();
    }

    @Override
    public void onPause() {
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
}
