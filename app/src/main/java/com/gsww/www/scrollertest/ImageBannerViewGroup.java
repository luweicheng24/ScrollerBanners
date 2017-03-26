package com.gsww.www.scrollertest;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Author : 卢卫成 on 2017/3/24 0024 09:10
 * E-mail ：1769005961@qq.com
 * GitHub : https://github.com/luweicheng24
 */

/**
 * 1 测量 -》 布局 -》 绘制
 */
public class ImageBannerViewGroup extends ViewGroup {
    private int lastX; //滑动的上一次位置
    private int childerCount; //子控件的个数
    private int width; //屏幕的宽度
    private int height; //Banner图的高度
    private int index = 0; //Banner图索引
    private int startX; //滑动的起始位置
    private Timer timer;
    private TimerTask task;
    private List<Integer> banners;
    private Boolean isClick = true; //是否点击
    private Boolean isAuto = true; //是否自动轮播
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                if (++index >= childerCount) {
                    index = 0;
                }
                if (bannerDotListener != null) {
                    bannerDotListener.bannerDotListener(index);
                }
                scrollTo(index * width, 0);
            }
        }
    };

    public void setAuto(Boolean isAuto) {
        this.isAuto = isAuto;
    }

    public ImageBannerViewGroup(Context context) {
        this(context, null);
    }

    public ImageBannerViewGroup(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageBannerViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (isAuto) {
                    mHandler.sendEmptyMessage(0);

                }
            }
        }, 1000, 3000);
    }

    //测量
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        childerCount = getChildCount();
        if (childerCount == 0) {
            setMeasuredDimension(0, 0);
        } else {
            measureChild(getChildAt(0), widthMeasureSpec, heightMeasureSpec);
            DisplayMetrics dm = new DisplayMetrics();
            ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(dm);
            width = dm.widthPixels;
            height = dm.heightPixels / 3;//全屏的三分之一
            setMeasuredDimension(width * childerCount, height);

        }

    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int leftMargin = 0;
            for (int i = 0; i < childerCount; i++) {
                View view = getChildAt(i);
                view.layout(leftMargin, 0, leftMargin + width, height);
                leftMargin += width;
            }

        }


    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                isClick = true;
                isAuto = false;
                startX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                isClick = false;
                lastX = (int) event.getX();
                int offsetX = startX - lastX;
                if (index == 0 && offsetX < 0) {
                    return true;
                } else if (index == childerCount - 1 && offsetX > 0) {
                    return true;
                }

                scrollBy(offsetX, 0);
                startX = lastX;
                break;
            case MotionEvent.ACTION_UP:
                index = (getScrollX() + width / 2) / width;
                Log.e("点击", "onTouchEvent:click " + isClick);

                if (isClick && bannerImagClickListener != null) {
                    bannerImagClickListener.bannerClick(index);
                    Log.e("点击", "onTouchEvent:click " + index);
                } else {

                    if (index < 0) {
                        index = 0;

                    } else if (index == childerCount - 1) {

                        index = childerCount - 1;
                    }

                    if (bannerDotListener != null) {
                        bannerDotListener.bannerDotListener(index);
                    }
                    scrollTo(index * width, 0);
                    isAuto = true;
                }
                break;

        }
        return true;


    }

    /**
     * banner图点击事件回调接口
     */
    public interface BannerImagClickListener {
        void bannerClick(int pos);
    }

    private BannerImagClickListener bannerImagClickListener;

    public void setBannerImagClickListener(BannerImagClickListener bannerImagClickListener) {
        this.bannerImagClickListener = bannerImagClickListener;
    }

    /**
     * banner图的dot点的滑动监听
     */
    public interface BannerDotListener {
        void bannerDotListener(int pos);
    }

    private BannerDotListener bannerDotListener;

    public void setBannerDotListener(BannerDotListener bannerDotListener) {
        this.bannerDotListener = bannerDotListener;
    }

}
