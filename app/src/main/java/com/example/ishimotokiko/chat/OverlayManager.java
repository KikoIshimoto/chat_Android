package com.example.ishimotokiko.chat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by abedaigorou on 2017/08/08.
 */

public class OverlayManager
{
    private WindowManager wm;
    private View view;
    private OverlayView overlayView;
    private WindowManager.LayoutParams params;
    private int wSizeX,wSizeY;
    private Display display;
    private String TAG="OverLayManager";
    private boolean isVisivle;

    public OverlayManager(Context context){
        this.isVisivle=true;
        // 重ね合わせするViewの設定を行う
        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT);

        // WindowManagerを取得する
        wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);

        Point p=new Point();
        display=wm.getDefaultDisplay();
        display.getSize(p);
        wSizeX=p.x;
        wSizeY=p.y;

        // インフレータを作成する
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        // レイアウトファイルから重ね合わせするViewを作成する
        view = layoutInflater.inflate(R.layout.activity_overlay, null);
        overlayView=(OverlayView)view.findViewById(R.id.overlay_view);
        Bitmap icon1 = BitmapFactory.decodeResource(context.getResources(),R.drawable.don);
        Bitmap olImage=Bitmap.createScaledBitmap(icon1,400,400,false);
        overlayView.setImage(olImage);
        // Viewを画面上に重ね合わせする
        wm.addView(view, params);
    }

    public void setVisibility(boolean b){
        if(isVisivle);
        else if(b)
            wm.addView(view,params);
        else
            wm.removeView(view);
    }

    public void setImage(Bitmap bmp1){
        overlayView.setImage(bmp1);
    }

    public void show(){
        overlayView.startAnimation();
    }

    public void setOverlayArea(int x,int y,boolean isRight){
        params.x=(isRight)?x+wSizeX/2:x-wSizeX/2;
        params.y=y-wSizeY/2;
        wm.updateViewLayout(view,params);
    }
}
