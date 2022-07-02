package com.example.myfirstapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class OpenedClass extends Activity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    TextView question, answer;
    Button returnBtn;
    RadioGroup gender;
    String getName, setData;

    @Override
    public void onCreate(Bundle SavedInstanceState) {

        super.onCreate(SavedInstanceState);

        setContentView(R.layout.send);

        initialize();

        SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String getUsername = getPrefs.getString("name", "You");
        getUsername += "is/are";
        String values = getPrefs.getString("list", "4");

        if (values.contentEquals("1")) question.setText(getUsername);

        /*Bundle getHoldName = getIntent().getExtras();
        getName = getHoldName.getString("key");
        question.setText(getName);*/

    }

    public void initialize() {
        question = (TextView) findViewById(R.id.question);
        answer = (TextView) findViewById(R.id.answer);
        returnBtn = (Button) findViewById(R.id.returnBtn);
        gender = (RadioGroup) findViewById(R.id.gender);

        returnBtn.setOnClickListener(this);
        gender.setOnCheckedChangeListener(this);

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.returnBtn) {
            Intent person = new Intent();
            Bundle holdGenderValue = new Bundle();
            holdGenderValue.putString("answer", setData);
            person.putExtras(holdGenderValue);
            setResult(RESULT_OK, person);
            finish();
        }

    }

    /**
     * <p>Called when the checked radio button has changed. When the
     * selection is cleared, checkedId is -1.</p>
     *
     * @param group     the group in which the checked radio button has changed
     * @param checkedId the unique identifier of the newly checked radio button
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.male:
                setData = "Your are Male";
                break;
            case R.id.female:
                setData = "Your are Fe Male";
                break;
            case R.id.other:
                setData = "None of above";
                break;

        }

        answer.setText(setData);

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
