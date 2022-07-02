package com.example.myfirstapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Set_Password extends Activity {
    ToggleButton tBtn;
    EditText passwordTxt, repasswordTxt;
    FileInputStream fis = null;
    String storePassword;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.set_password);

        initialize();
    }

    public void initialize() {
        tBtn = (ToggleButton) findViewById(R.id.tBtn);
        passwordTxt = (EditText) findViewById(R.id.passwordTxt);
        repasswordTxt = (EditText) findViewById(R.id.repasswordTxt);
    }

    public void savePassword(View view) {
        String pass1 = passwordTxt.getText().toString();
        String pass2 = repasswordTxt.getText().toString();
        FileOutputStream fos = null;
        String FILENAME = "PasswordData";

        if (pass1.isEmpty() || pass2.isEmpty()) {
            Toast.makeText(this, "Empty Fields!!", Toast.LENGTH_LONG).show();
        } else {
            if (!pass1.contentEquals(pass2)) {
                Toast.makeText(this, "Passwords do not Match!!", Toast.LENGTH_LONG).show();
            } else {
                //File out Stream Here
                try {
                    fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
                    fos.write(pass1.getBytes());
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                Toast.makeText(this, "Password Set You are Protected!!\nGo " +
                        "and enable Login with password in preferences", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    public void toggleButtonClicked(View view) {
        if (tBtn.isChecked()) {
            passwordTxt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            repasswordTxt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            passwordTxt.setInputType(InputType.TYPE_CLASS_TEXT);
            repasswordTxt.setInputType(InputType.TYPE_CLASS_TEXT);
        }
    }


}
