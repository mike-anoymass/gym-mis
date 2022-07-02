package com.example.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MemberFragment extends Fragment implements SearchView.OnQueryTextListener {


    Context context;

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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onResume() {
        super.onResume();
        String dbQuery = "SELECT id, full_name, mobile , email , join_date, image FROM member ";
        displayData(dbQuery);
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
            Toast.makeText(getContext(), "no match found for " + query, Toast.LENGTH_LONG).show();
        }
        return false;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.member, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        initializeVars();
        listView.setItemsCanFocus(false);

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            Intent i = new Intent(getContext(), MemberDetails.class);
            i.putExtra("member", this.id.get(position));
            startActivity(i);

        });

        searchView.setOnQueryTextListener(this);


        String dbQuery = "SELECT id, full_name, mobile , email , join_date, image FROM member ";
        displayData(dbQuery);
        searchView.setQuery("", false);
    }

    private void displayData(String dbQuery) {
        DBOpenHelper dbOpenHelper = new DBOpenHelper(getContext());
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

        displayAdapter = new DisplayAdapter(getContext(), id, fullName, mobile, email, doj, image);
        listView.setAdapter(displayAdapter);

        dbOpenHelper.close();
        c.close();
    }

    private void initializeVars() {
        listView = getActivity().findViewById(R.id.list);
        searchView = getActivity().findViewById(R.id.searchView);
        linearLayout = getActivity().findViewById(R.id.linearLayout);
    }
}
