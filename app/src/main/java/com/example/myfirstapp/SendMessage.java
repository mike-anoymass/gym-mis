package com.example.myfirstapp;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SendMessage extends Activity {
    EditText messageText;

    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.send_message);


        initializeVars();
    }

    private void initializeVars() {
        messageText = findViewById(R.id.messageText);
    }

    public void sendMessage(View view) {

        if (!messageText.getText().toString().isEmpty()) {
            SmsManager manager = SmsManager.getDefault();
            manager.sendTextMessage(getIntent().getStringExtra("mobile"), null,
                    messageText.getText().toString(), null, null);
            finish();
        } else {
            Toast.makeText(this, "Empty SMS not allowed", Toast.LENGTH_SHORT).show();
        }
    }

    public void cancelBtnClicked(View view) {
        finish();
    }
}
