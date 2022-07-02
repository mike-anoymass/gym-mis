package com.example.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class DisplayAdapter extends BaseAdapter {

    ArrayList<byte[]> image;
    private Context context;
    private ArrayList<Integer> id;
    private ArrayList<String> fullName;
    private ArrayList<String> mobile;
    private ArrayList<String> email;
    private ArrayList<String> doj;

    public DisplayAdapter(Context c, ArrayList<Integer> id,
                          ArrayList<String> fullName,
                          ArrayList<String> mobile,
                          ArrayList<String> email,
                          ArrayList<String> doj,
                          ArrayList<byte[]> image) {
        this.context = c;
        this.id = id;
        this.fullName = fullName;
        this.mobile = mobile;
        this.email = email;
        this.doj = doj;
        this.image = image;
        // this.amount = amount;
    }

    @Override
    public int getCount() {
        return id.size();
    }


    @Override
    public Object getItem(int position) {
        return null;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        LayoutInflater layoutInflater;
        if (convertView == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.listcell, null);
            holder = new Holder();

            holder.fullName = (TextView) convertView.findViewById(R.id.name);
            holder.mobile = (TextView) convertView.findViewById(R.id.mobile);
            holder.email = (TextView) convertView.findViewById(R.id.email);
            holder.doj = (TextView) convertView.findViewById(R.id.doj);
            holder.imageView = convertView.findViewById(R.id.imageView);

            holder.editBtn = convertView.findViewById(R.id.editBtn);
            holder.deleteBtn = convertView.findViewById(R.id.deleteBtn);
            holder.callBtn = convertView.findViewById(R.id.callBtn);
            holder.messageBtn = convertView.findViewById(R.id.message);
            holder.detailsBtn = convertView.findViewById(R.id.detailsBtn);
            holder.whatsappBtn = convertView.findViewById(R.id.whatsappBtn);

            convertView.setTag(holder);

        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.fullName.setText(fullName.get(position));
        holder.mobile.setText(mobile.get(position));
        holder.email.setText(email.get(position));
        holder.doj.setText(doj.get(position));

        Bitmap bmp = null;

        if (image.get(position) != null) {
            bmp = BitmapFactory.decodeByteArray(image.get(position), 0, image.get(position).length);
        }

        if (bmp == null) {
            holder.imageView.setImageResource(R.drawable.image_32);
        } else {
            holder.imageView.setImageBitmap(bmp);
        }

        holder.callBtn.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mobile.get(position)));
            this.context.startActivity(i);
        });

        holder.messageBtn.setOnClickListener(v -> {
            Intent i = new Intent(context, SendMessage.class);
            i.putExtra("mobile", mobile.get(position));
            this.context.startActivity(i);
        });

        holder.deleteBtn.setOnClickListener(v -> {

            Intent i = new Intent(context, RemoveMember.class);
            i.putExtra("id", id.get(position));
            context.startActivity(i);
        });

        holder.detailsBtn.setOnClickListener(v -> {
            Intent i = new Intent(context, MemberDetails.class);
            i.putExtra("member", this.id.get(position));
            context.startActivity(i);
        });

        holder.whatsappBtn.setOnClickListener(v -> {
            String url = null;
            try {
                url = "https://api.whatsapp.com/send?phone=" + mobile.get(position)
                        + "&text=" + URLEncoder.encode("Welcome to Poly Gym " + fullName.get(position), "UTF-8");

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                context.startActivity(i);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        });

        holder.editBtn.setOnClickListener(v -> {
            Intent i = new Intent(context, EditMember.class);
            i.putExtra("memberID", this.id.get(position));
            context.startActivity(i);
        });


        return convertView;
    }

    public class Holder {
        TextView fullName, mobile, email, doj;
        CircularImageView imageView;
        Button editBtn, deleteBtn, callBtn, whatsappBtn, messageBtn, detailsBtn;
        //TextView amount;
    }

}