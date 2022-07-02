package com.example.myfirstapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

public class MyClass extends View {
    Bitmap gImage;
    float changingY;

    public MyClass(Context context) {
        super(context);
        gImage = BitmapFactory.decodeResource(getResources(), R.drawable.bg2);
        changingY = 0;

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(gImage, (canvas.getWidth() / 2), changingY, null);
        if (changingY < canvas.getHeight()) {
            changingY += 10;
        } else {
            changingY = 0;

        }
        invalidate();
    }

}
