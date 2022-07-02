package com.example.myfirstapp;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "mygym.db";
    public static final String DB_SUB_PATH = "/databases/" + DB_NAME;
    private static String APP_DATA_PATH = "";
    private final Context context;
    private SQLiteDatabase database;

    public DBOpenHelper(Context context) {
        super(context, DB_NAME, null, 1);
        APP_DATA_PATH = context.getApplicationInfo().dataDir;
        this.context = context;
    }

    public boolean openDatabase() throws SQLException {
        String mPath = APP_DATA_PATH + DB_SUB_PATH;
        database = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.OPEN_READWRITE);
        return database != null;
    }

    @Override
    public synchronized void close() {
        if (database != null) database.close();
        super.close();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
