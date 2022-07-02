package com.example.myfirstapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatePickerSettings extends Activity {

    DatePicker dp;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datepicker);

        dp = findViewById(R.id.datePicker);
    }

    public void okBtnClicked(View view) {
        Intent data = new Intent();

        String date = dp.getDayOfMonth() + "/" + (dp.getMonth() + 1) + "/" + dp.getYear();

        data.setData(Uri.parse(date));
        setResult(RESULT_OK, data);

        Toast.makeText(this, date, Toast.LENGTH_SHORT).show();

        finish();
    }

    public void cancelBtnClicked(View view) {
        finish();
    }
}
