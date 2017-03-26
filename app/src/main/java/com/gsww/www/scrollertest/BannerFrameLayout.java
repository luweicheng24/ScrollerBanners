package com.gsww.www.scrollertest;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Author : 卢卫成 on 2017/3/24 0024 16:46
 * E-mail ：1769005961@qq.com
 * GitHub : https://github.com/luweicheng24
 */

public class BannerFrameLayout extends FrameLayout implements ImageBannerViewGroup.BannerDotListener {
    private ImageBannerViewGroup mImageBannerViewGroup;
    private LinearLayout mLinearLayout;

    public ImageBannerViewGroup getmImageBannerViewGroup() {
        return mImageBannerViewGroup;
    }

    public void setmImageBannerViewGroup(ImageBannerViewGroup mImageBannerViewGroup) {
        this.mImageBannerViewGroup = mImageBannerViewGroup;
    }

    public LinearLayout getmLinearLayout() {
        return mLinearLayout;
    }

    public void setmLinearLayout(LinearLayout mLinearLayout) {
        this.mLinearLayout = mLinearLayout;
    }

    private Integer[] banners;

    public BannerFrameLayout(@NonNull Context context) {
        this(context, null);
    }

    public BannerFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDragView();
        initLinearLayout();
    }

    /**
     * 设置Banner图的集合
     *
     * @param banners
     * @param screenWidth  屏幕宽度
     * @param screenHeight 屏幕高度
     */
    public void addBanners(final Integer[] banners, int screenWidth, int screenHeight) {
        this.banners = banners;
        for (int i = 0; i < banners.length; i++) {
            ImageView img = new ImageView(getContext());
            img.setBackgroundResource(banners[i]);
            img.setId(i);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(screenWidth * banners.length, screenHeight / 4);
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            img.setLayoutParams(lp);
            mImageBannerViewGroup.addView(img);
        }
        addDotToLinearLayout(banners.length);

    }

    private void addDotToLinearLayout(int dotCount) {
        for (int i = 0; i < dotCount; i++) {
            ImageView dot = new ImageView(getContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(5, 0, 0, 0);
            dot.setBackgroundResource(R.drawable.dot_drawable);
            dot.setLayoutParams(lp);
            mLinearLayout.addView(dot);
        }

    }


    private void initLinearLayout() {
        mLinearLayout = new LinearLayout(getContext());
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
        lp.gravity = Gravity.BOTTOM;
        mLinearLayout.setLayoutParams(lp);
        mLinearLayout.setGravity(Gravity.CENTER);

        addView(mLinearLayout);

    }

    private void initDragView() {
        mImageBannerViewGroup = new ImageBannerViewGroup(getContext());
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mImageBannerViewGroup.setLayoutParams(lp);
        addView(mImageBannerViewGroup);
        mImageBannerViewGroup.setBannerDotListener(this);
    }

    @Override
    public void bannerDotListener(int pos) {
        for (int i = 0; i < mLinearLayout.getChildCount(); i++) {
            if (pos == i) {
                ImageView img = (ImageView) mLinearLayout.getChildAt(i);
                img.setBackgroundResource(R.drawable.dot_select);
            } else {
                ImageView img = (ImageView) mLinearLayout.getChildAt(i);
                img.setBackgroundResource(R.drawable.dot_no_select);

            }

        }
    }
}
