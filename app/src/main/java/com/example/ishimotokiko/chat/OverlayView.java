package com.example.ishimotokiko.chat;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.zip.Inflater;

/**
 * Created by abedaigorou on 2017/08/03.
 */

public class OverlayView extends LinearLayout {
    private ImageView overlayImage1;
    private AlphaAnimation alphaAnimation1;

    public OverlayView(Context context) {
        super(context);
    }

    public OverlayView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view=LayoutInflater.from(context).inflate(R.layout.overlay,this);
        overlayImage1 = (ImageView)findViewById(R.id.imageView1);
        overlayImage1.setVisibility(INVISIBLE);

        alphaAnimation1 = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation1.setDuration(1000);
        alphaAnimation1.setFillAfter(true);
    }

    public OverlayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public OverlayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

    }

    public void setImage(Bitmap bmp1){
        overlayImage1.setImageBitmap(bmp1);
    }

    public void startAnimation(){
        overlayImage1.setVisibility(VISIBLE);
        overlayImage1.startAnimation(alphaAnimation1);
    }
}
