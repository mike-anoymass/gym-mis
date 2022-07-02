package com.example.myfirstapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database {
    public static final String ID = "ID";
    public static final String FNAME = "fname";
    public static final String LNAME = "lname";
    public static final String PASSWORD = "pwd";

    public static final String DATABASE_NAME = "poly_gym";
    public static final String TABLE_NAME = "members";
    public static final int DATABASE_VERSION = 1;
    private final Context context;
    private DBHelper helper;
    private SQLiteDatabase database;

    public Database(Context c) {
        context = c;
    }

    public Database open() throws SQLException {
        helper = new DBHelper(context);
        database = helper.getWritableDatabase();
        return this;
    }

    public void close() {
        helper.close();
    }

    public long dataToDb(String fname, String lname, String pwd) {
        ContentValues cv = new ContentValues();
        cv.put(FNAME, fname);
        cv.put(LNAME, lname);
        cv.put(PASSWORD, pwd);

        return database.insert(TABLE_NAME, null, cv);
    }

    public String getData() {
        String[] cols = new String[]{ID, FNAME, LNAME, PASSWORD};

        Cursor c = database.query(TABLE_NAME, cols, null, null, null, null, null);
        String result = "";

        int id = c.getColumnIndex(ID);
        int fname = c.getColumnIndex(FNAME);
        int lname = c.getColumnIndex(LNAME);
        int pwd = c.getColumnIndex(PASSWORD);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            result = result + c.getString(id) + c.getString(fname) + c.getString(lname) + c.getString(pwd) + "\n";
        }
        return result;
    }


    public class DBHelper extends SQLiteOpenHelper {


        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(
                    "CREATE TABLE " + TABLE_NAME +
                            "(" + ID + "INTEGER PRIMARY KEY AUTOINCREMENT , " +
                            FNAME + "TEXT NOT NULL ," +
                            FNAME + "TEXT NOT NULL," +
                            PASSWORD + "TEXT NOT NULL)"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }


}
