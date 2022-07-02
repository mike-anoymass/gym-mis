package com.example.myfirstapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class Members extends Activity implements SearchView.OnQueryTextListener {

    ListView listView;
    LinearLayout linearLayout;
    ArrayList<Integer> id = new ArrayList<>();
    ArrayList<String> fullName = new ArrayList<>();
    ArrayList<String> mobile = new ArrayList<>();
    ArrayList<String> email = new ArrayList<>();
    ArrayList<String> doj = new ArrayList<>();
    ArrayList<byte[]> image = new ArrayList<byte[]>();
    SearchView searchView;
    DisplayAdapter displayAdapter;


    public void onCreate(Bundle savedIBundle) {
        super.onCreate(savedIBundle);
        setContentView(R.layout.member);

        initializeVars();

        this.listView.setItemsCanFocus(false);


        this.listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent i = new Intent(this, MemberDetails.class);
            i.putExtra("member", this.id.get(position));
            startActivity(i);

        });

        searchView.setOnQueryTextListener(this);
    }


    private void initializeVars() {
        listView = findViewById(R.id.list);
        searchView = findViewById(R.id.searchView);
        linearLayout = findViewById(R.id.linearLayout);
    }


    @Override
    protected void onResume() {
        super.onResume();
        String dbQuery = "SELECT id, full_name, mobile , email , join_date, image FROM member ";
        displayData(dbQuery);
    }

    private void displayData(String dbQuery) {
        DBOpenHelper dbOpenHelper = new DBOpenHelper(this);
        SQLiteDatabase database;

        dbOpenHelper.openDatabase();
        database = dbOpenHelper.getReadableDatabase();

        Cursor c = database.rawQuery(dbQuery, null);

        id.clear();
        fullName.clear();
        mobile.clear();
        email.clear();
        doj.clear();
        image.clear();

        if (c.moveToFirst()) {
            do {
                id.add(c.getInt(c.getColumnIndex("id")));
                fullName.add(c.getString(c.getColumnIndex("full_name")));
                mobile.add(c.getString(c.getColumnIndex("mobile")));
                email.add(c.getString(c.getColumnIndex("email")));
                doj.add(c.getString(c.getColumnIndex("join_date")));
                image.add(c.getBlob(c.getColumnIndex("image")));
            } while (c.moveToNext());
        }

        displayAdapter = new DisplayAdapter(this, id, fullName, mobile, email, doj, image);
        listView.setAdapter(displayAdapter);

        dbOpenHelper.close();
        c.close();
    }

    public void addMember(View view) {
        Intent i = new Intent(getBaseContext(), AddMember.class);
        startActivity(i);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (fullName.contains(query.trim())) {
            String dbQuery = "SELECT id, full_name, mobile , email , join_date , image FROM member " +
                    "WHERE full_name = '" + query + "'";
            displayData(dbQuery);
            searchView.setEnabled(false);
            //linearLayout.removeView(searchView);
        } else {
            Toast.makeText(getBaseContext(), "no match found for " + query, Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        searchView.setQuery("", false);

    }

    public void refresh(View view) {
        String dbQuery = "SELECT id, full_name, mobile , email , join_date, image FROM member ";
        displayData(dbQuery);
        searchView.setQuery("", false);

    }
}
