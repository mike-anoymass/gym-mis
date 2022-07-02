package com.example.myfirstapp;

import android.app.ListActivity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.Nullable;
import androidx.loader.content.CursorLoader;

public class ContactsList extends ListActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts);


        Uri allContacts = ContactsContract.Contacts.CONTENT_URI;

        Cursor c;

        if (Build.VERSION.SDK_INT < 11) {
            c = managedQuery(allContacts, null, null, null, null
            );
        } else {
            CursorLoader cursorLoader = new CursorLoader(
                    this,
                    allContacts,
                    null,
                    null,
                    null,
                    ContactsContract.Contacts._ID + " ASC"
            );
            c = cursorLoader.loadInBackground();
        }

        String[] columns = new String[]{
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.HAS_PHONE_NUMBER
        };

        int[] views = new int[]{R.id.contactsName, R.id.contactsID, R.id.contactNumber};

        SimpleCursorAdapter adapter;

        if (Build.VERSION.SDK_INT < 11) {
            adapter = new SimpleCursorAdapter(this, R.layout.contacts, c, columns, views);
        } else {
            adapter = new SimpleCursorAdapter(this, R.layout.contacts, c, columns, views
                    , CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        }

        this.setListAdapter(adapter);


    }
}
