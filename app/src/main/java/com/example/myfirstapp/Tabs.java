package com.example.myfirstapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;

public class Tabs extends Activity implements View.OnClickListener {

    TabHost th;
    CharSequence[] items = {"Full Member", "Aerobics", "Martial Arts"};
    boolean[] itemsChecked = new boolean[items.length];
    ProgressDialog progressDialog;
    ViewFlipper viewFlipper;


    @Override
    public void onCreate(Bundle mike) {
        super.onCreate(mike);

        setContentView(R.layout.tabs);

        th = (TabHost) findViewById(R.id.tab_host);
        th.setup();

        TabHost.TabSpec tabSpec = th.newTabSpec("tag1");
        tabSpec.setContent(R.id.tab1);
        tabSpec.setIndicator("Chats");
        th.addTab(tabSpec);

        tabSpec = th.newTabSpec("tag2");
        tabSpec.setContent(R.id.tab2);
        tabSpec.setIndicator("Statuses");
        th.addTab(tabSpec);

        tabSpec = th.newTabSpec("tag3");
        tabSpec.setContent(R.id.tab3);
        tabSpec.setIndicator("Calls");
        th.addTab(tabSpec);

        Button addTab = (Button) findViewById(R.id.addTab);

        addTab.setOnClickListener(this);

        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        viewFlipper.setFlipInterval(500);
        viewFlipper.startFlipping();

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


    @Override
    public void onClick(View v) {

        TabHost.TabSpec myTabSpec = th.newTabSpec("tag1");

        myTabSpec.setContent(new TabHost.TabContentFactory() {

            @Override
            public View createTabContent(String tag) {
                TextView text = new TextView(Tabs.this);
                text.setText("New Tab Created");

                return (text);
            }
        });

        myTabSpec.setIndicator("New Tab");
        th.addTab(myTabSpec);


    }

    public void createDialog(View view) {
        showDialog(0);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 0:
                return new AlertDialog.Builder(this)
                        .setIcon(R.mipmap.ic_launcher)
                        .setTitle("Select Membership Type")
                        .setPositiveButton("OK",
                                (dialog, which) -> {
                                    Toast.makeText(getBaseContext(), "You have Selected Type", Toast.LENGTH_LONG).show();
                                })
                        .setNegativeButton("Cancel",
                                (dialog, which) -> {
                                    Toast.makeText(getBaseContext(), "Cancelled", Toast.LENGTH_LONG).show();
                                })
                        .setMultiChoiceItems(items, itemsChecked,
                                (dialog, which, isChecked) -> {
                                    Toast.makeText(getBaseContext(),
                                            items[which] + (isChecked ? " checked" : " unchecked"),
                                            Toast.LENGTH_LONG).show();
                                })
                        .create();

            case 1:
                progressDialog = new ProgressDialog(this);
                progressDialog.setIcon(R.mipmap.ic_launcher);
                progressDialog.setTitle("Downloading files");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                        (dialog, which) -> {
                            Toast.makeText(getBaseContext(), "OK clicked", Toast.LENGTH_LONG).show();
                        });
                progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
                        (dialog, which) -> {
                            Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_LONG).show();
                        });
                return progressDialog;

        }
        return null;
    }

    public void createProgressDialog(View view) {
        final ProgressDialog dialog = ProgressDialog.show(this, "Downloading Files", "Please Wait...", true);

        new Thread(() -> {
            try {
                Thread.sleep(5000);
                dialog.dismiss();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void createDetailedProgressDialog(View view) {
        showDialog(1);
        progressDialog.setProgress(0);

        new Thread(() -> {
            for (int i = 0; i <= 15; i++) {
                try {
                    Thread.sleep(5000);

                    //update dialog
                    progressDialog.incrementProgressBy((int) (100 / 15));

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            progressDialog.dismiss();
        }).start();

    }

    public void viewFlipperClicked(View view) {
        //viewFlipper.showNext();
    }
}
