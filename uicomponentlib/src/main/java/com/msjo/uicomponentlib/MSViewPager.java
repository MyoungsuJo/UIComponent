package com.msjo.uicomponentlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import com.msjo.uicomponentlib.adapter.MSLoopViewPagerAdapter;

public class MSViewPager extends ViewPager {

    private static final int DEFAULT_INTERVAL = 3000;

    private Handler autoScrollHandler = new Handler();

    private Context context;

    private int scrollState = SCROLL_STATE_IDLE;

    private int interval = DEFAULT_INTERVAL;
    private int currentPosition = 0;

    private int padding;
    private int pageMargin;

    private boolean isInfinite;
    private boolean isAutoScroll;
    private boolean isShowPreview;

    public MSViewPager(Context context) {
        super(context);

        this.context = context;

        init();
    }

    public MSViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;

        getAttrs(attrs);

        init();
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MSViewPager);
        setTypeArray(typedArray);
    }

    private void setTypeArray(TypedArray typedArray) {

//        isInfinite = typedArray.getBoolean(R.styleable.MSViewPager_isInfinite, false);
        isAutoScroll = typedArray.getBoolean(R.styleable.MSViewPager_autoScroll, false);
        isShowPreview = typedArray.getBoolean(R.styleable.MSViewPager_isShowPreview, false);

        interval = typedArray.getInt(R.styleable.MSViewPager_scrollInterval, DEFAULT_INTERVAL);
        padding = typedArray.getInt(R.styleable.MSViewPager_padding, 0);
        pageMargin = typedArray.getInt(R.styleable.MSViewPager_pageMargin, 0);

        isInfinite = true;

        typedArray.recycle();
    }


    private void init() {

        if (isShowPreview) {
            setClipToPadding(false);

            setPadding(padding, 0, padding, 0);

            setPageMargin(pageMargin);
        }


        addOnPageChangeListener(onPageChangeListener);

    }


    private Runnable autoScrollRunnable = new Runnable() {
        @Override
        public void run() {

            if (!isInfinite && getAdapter().getCount() - 1 == currentPosition) {
                autoScrollHandler.removeCallbacks(autoScrollRunnable);
            } else {
                currentPosition++;
            }
            setCurrentItem(currentPosition, true);
        }
    };


    OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {
        }

        @Override
        public void onPageSelected(int position) {
            currentPosition = position;

            if (isCanAutoScroll()) {
                autoScrollHandler.removeCallbacks(autoScrollRunnable);
                autoScrollHandler.postDelayed(autoScrollRunnable, interval);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            scrollState = state;

            if (state == SCROLL_STATE_IDLE) {
                if (isInfinite) {

                    if (getAdapter() == null) {
                        return;
                    }
                    int itemCount = getAdapter().getCount();

                    if (itemCount < 2) {
                        return;
                    }
                    int index = getCurrentItem();
                    if (index == 0) {
                        setCurrentItem(itemCount - 2, false);
                    } else if (index == itemCount - 1) {
                        setCurrentItem(1, false);
                    }
                }
            }
        }
    };


    @Override
    public void setAdapter(PagerAdapter adapter) {

        super.setAdapter(adapter);

        if (adapter instanceof MSLoopViewPagerAdapter) {
            MSLoopViewPagerAdapter msLoopViewPagerAdapter = (MSLoopViewPagerAdapter) adapter;
            isInfinite = msLoopViewPagerAdapter.isInfinite();
        }

        if (isInfinite) {
            setCurrentItem(1, false);
        } else {
            setCurrentItem(0, false);

            if (isCanAutoScroll()) {
                autoScrollHandler.removeCallbacks(autoScrollRunnable);
                autoScrollHandler.postDelayed(autoScrollRunnable, interval);
            }

        }

    }


    private boolean isCanAutoScroll() {

        if (isAutoScroll == false) {
            return false;
        }

        if (getAdapter() == null || getAdapter().getCount() < 2) {
            return false;
        }

        return true;
    }


}
