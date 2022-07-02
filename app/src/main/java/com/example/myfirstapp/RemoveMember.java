package com.example.myfirstapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

public class RemoveMember extends Activity {
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        showDialog(0);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 0:
                return new AlertDialog.Builder(this)
                        .setIcon(R.drawable.delete_64)
                        .setTitle("Delete Selected Member ?")
                        .setPositiveButton("Yes",
                                (dialog, which) -> {
                                    DBOpenHelper dbOpenHelper = new DBOpenHelper(getBaseContext());
                                    SQLiteDatabase database;

                                    dbOpenHelper.openDatabase();
                                    database = dbOpenHelper.getReadableDatabase();


                                    if (database.delete("member", "id" + "=" + getIntent().getIntExtra("id", 1),
                                            null) > 0) {
                                        Toast.makeText(getBaseContext(), "Member Deleted", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getBaseContext(), "Member not Deleted", Toast.LENGTH_SHORT).show();
                                    }

                                    database.close();
                                    dbOpenHelper.close();

                                    finish();
                                })
                        .setNegativeButton("No",
                                (dialog, which) -> finish()).create();
        }
        return null;
    }
}
