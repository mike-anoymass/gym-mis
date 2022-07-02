package com.example.myfirstapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import androidx.annotation.NonNull;

public class MemberDetails extends Activity {
    String id;
    String name;
    String gender;
    String email;
    String mobile;
    String doj;
    String address;
    TextView nameTv, genderTv, mobileTv, emailTv, dojTv, addressTv, dobTv, mobileTv1;
    CircularImageView imageView;


    @Override
    protected void onResume() {
        super.onResume();
        getMemberData();
    }

    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.member_details);

        initializeVars();

    }

    private void getMemberData() {

        DBOpenHelper dbOpenHelper = new DBOpenHelper(this);
        SQLiteDatabase database;

        dbOpenHelper.openDatabase();
        database = dbOpenHelper.getReadableDatabase();

        String query = "SELECT id, full_name, mobile , email , join_date, " +
                "address , dob , gender , image FROM member " +
                "WHERE id = " + getIntent().getIntExtra("member", 1) + " ";
        Cursor c = database.rawQuery(query, null);


        if (c.moveToFirst()) {
            do {
                nameTv.setText(c.getString(c.getColumnIndex("full_name")));
                mobileTv.setText(c.getString(c.getColumnIndex("mobile")));
                emailTv.setText(c.getString(c.getColumnIndex("email")));
                dojTv.setText(c.getString(c.getColumnIndex("join_date")));
                addressTv.setText(c.getString(c.getColumnIndex("address")));
                dobTv.setText(c.getString(c.getColumnIndex("dob")));
                genderTv.setText(c.getString(c.getColumnIndex("gender")));
                mobileTv1.setText(mobileTv.getText());

                email = c.getString(c.getColumnIndex("email"));
                mobile = c.getString(c.getColumnIndex("mobile"));
                name = c.getString(c.getColumnIndex("full_name"));

                byte[] image = c.getBlob(c.getColumnIndex("image"));
                Bitmap bmp = null;

                if (image != null) {
                    bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
                }

                if (bmp == null) {
                    imageView.setImageResource(R.drawable.image_32);
                } else {
                    imageView.setImageBitmap(bmp);
                }

            } while (c.moveToNext());
        } else {
            finish();
        }
        dbOpenHelper.close();
        c.close();
    }

    private void initializeVars() {
        nameTv = findViewById(R.id.name);
        genderTv = findViewById(R.id.gender);
        mobileTv = findViewById(R.id.mobile);
        emailTv = findViewById(R.id.email);
        addressTv = findViewById(R.id.address);
        dojTv = findViewById(R.id.doj);
        dobTv = findViewById(R.id.dob);
        imageView = findViewById(R.id.image);
        mobileTv1 = findViewById(R.id.mobile1);
    }

    public void call(View view) {
        Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mobile));
        startActivity(i);
    }

    public void message(View view) {
        Intent i = new Intent(this, SendMessage.class);
        i.putExtra("mobile", mobile);
        startActivity(i);
    }

    public void email(View view) {
        String emails[] = {email};

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, emails);
        emailIntent.setType("plain/text");

        startActivity(emailIntent);
    }


    public void whatsapp(View view) {
        String url = null;
        try {
            url = "https://api.whatsapp.com/send?phone=" + mobile
                    + "&text=" + URLEncoder.encode("Welcome to Poly Gym " + name, "UTF-8");

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater blowUp = getMenuInflater();
        blowUp.inflate(R.menu.member_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                Intent i = new Intent(this, EditMember.class);
                i.putExtra("memberID", getIntent().getIntExtra("member", 1));
                startActivity(i);
                break;
            case R.id.delete:
                Intent d = new Intent(this, RemoveMember.class);
                d.putExtra("id", getIntent().getIntExtra("member", 1));
                startActivity(d);
                break;

            case R.id.exit:
                finish();
                break;
        }
        return false;
    }
}
