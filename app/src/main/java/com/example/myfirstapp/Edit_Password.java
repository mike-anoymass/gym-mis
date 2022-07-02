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

public class Edit_Password extends Activity {
    ToggleButton tBtn;
    EditText oldpwd, passwordTxt, repasswordTxt;
    FileInputStream fis = null;
    String storePassword;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.edit_password);

        initialize();

        storePassword = getIntent().getStringExtra("pwd");

    }

    public void initialize() {
        tBtn = (ToggleButton) findViewById(R.id.edit_tBtn);
        passwordTxt = (EditText) findViewById(R.id.edit_passwordTxt);
        repasswordTxt = (EditText) findViewById(R.id.edit_repasswordTxt);
        oldpwd = (EditText) findViewById(R.id.old_passwordTxt);

    }

    public void editPassword(View view) {
        String pass1 = passwordTxt.getText().toString();
        String pass2 = repasswordTxt.getText().toString();
        String oldPass = oldpwd.getText().toString();

        FileOutputStream fos = null;
        String FILENAME = "PasswordData";

        if (oldPass.contentEquals(storePassword)) {
            if (pass1.isEmpty() || pass2.isEmpty() || oldPass.isEmpty()) {
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


                    Toast.makeText(this, "Password Edited You are Protected!!\nGo " +
                            "and enable Login with password in preferences", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

        } else {
            Toast.makeText(this, "Old Password do not match", Toast.LENGTH_LONG).show();
        }
    }

    public void toggleButtonClicked(View view) {
        if (tBtn.isChecked()) {
            passwordTxt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            repasswordTxt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            oldpwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            passwordTxt.setInputType(InputType.TYPE_CLASS_TEXT);
            repasswordTxt.setInputType(InputType.TYPE_CLASS_TEXT);
            oldpwd.setInputType(InputType.TYPE_CLASS_TEXT);
        }
    }


}
