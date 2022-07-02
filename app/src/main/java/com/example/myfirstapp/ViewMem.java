package com.example.myfirstapp;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class ViewMem extends Activity {
    private SQLiteDatabase database;
    private DBOpenHelper dbOpenHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbOpenHelper = new DBOpenHelper(this);
        dbOpenHelper.openDatabase();

        database = dbOpenHelper.getReadableDatabase();

        getMembers();

        dbOpenHelper.close();
    }

    private void getMembers() {
        String query = "SELECT full_name FROM member";
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Toast.makeText(this, cursor.getString(0), Toast.LENGTH_LONG).show();
            } while (cursor.moveToNext());
        }
        cursor.close();
    }
}
