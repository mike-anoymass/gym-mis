package com.example.myfirstapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    DrawerLayout drawerLayout;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation);
        setNavigationDrawer();

    }

    private void setNavigationDrawer() {
        drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) item -> {
            Fragment fragment = null;
            int itemId = item.getItemId();

            switch (itemId) {
                case R.id.members:
                    fragment = new MemberFragment();
                    break;

                case R.id.home:
                    fragment = new HomeFragment();
                    break;

                case R.id.trainers:
                    fragment = new TrainerFragment();
                    break;

                case R.id.finance:
                    fragment = new FinanceFragment();
                    break;

                case R.id.aboutUs:
                    fragment = new AboutFragment();
                    break;

                case R.id.help:
                    fragment = new HelpFragment();
                    break;
            }

            Snackbar.make(this, navigationView, item.getTitle(), Snackbar.LENGTH_SHORT).show();

            if (fragment != null) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame, fragment);
                transaction.commit();
                drawerLayout.closeDrawers();
                return true;
            }
            return false;
        });
    }

    public void sendMessage(View view) {


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

            case android.R.id.home:
                finish();
                break;
        }
        return false;
    }

    public void addMember(View view) {
        Intent i = new Intent(this, AddMember.class);
        startActivity(i);
    }

    public void refresh(View view) {
        initializeVars();

        listView.setItemsCanFocus(false);

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            Intent i = new Intent(this, MemberDetails.class);
            i.putExtra("member", this.id.get(position));
            startActivity(i);

        });

        // searchView.setOnQueryTextListener(this);


        String dbQuery = "SELECT id, full_name, mobile , email , join_date, image FROM member ";
        displayData(dbQuery);
        searchView.setQuery("", false);

    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onPause() {
        super.onPause();
        searchView.setQuery("", false);

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
            Toast.makeText(this, "no match found for " + query, Toast.LENGTH_LONG).show();
        }
        return false;
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


    private void initializeVars() {
        listView = findViewById(R.id.list);
        searchView = findViewById(R.id.searchView);
        linearLayout = findViewById(R.id.linearLayout);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void loadData(View view) {
    }
}