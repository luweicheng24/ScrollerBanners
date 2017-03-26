package com.gsww.www.scrollertest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ImageBannerViewGroup.BannerImagClickListener {
    private BannerFrameLayout banner;
    private Integer[] imgs = {R.drawable.girl_3, R.drawable.girl_5, R.drawable.girl_6,
            R.drawable.girl_4, R.drawable.girl_7, R.drawable.girl_9};
    private int screenWidth;
    private int screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.main);
        banner = (BannerFrameLayout) findViewById(R.id.mBannerFrameLayout);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        banner.addBanners(imgs, screenWidth, screenHeight);
        banner.getmImageBannerViewGroup().setBannerImagClickListener(this);

    }


    @Override
    public void bannerClick(int pos) {
        Toast.makeText(this, ""+pos, Toast.LENGTH_SHORT).show();
    }
}