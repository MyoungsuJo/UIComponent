package com.msjo.uicomponent;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.msjo.uicomponentlib.MSViewPager;
import com.msjo.uicomponentlib.adapter.MSLoopViewPagerAdapter;

import java.util.ArrayList;

public class IntroActivity extends AppCompatActivity {


    private ArrayList<String> imageArrayList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_intro);

        MSViewPager msViewPager = (MSViewPager) findViewById(R.id.viewPager);

        imageArrayList.add("https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png");
        imageArrayList.add("https://images.g2a.com/newlayout/323x433/1x1x0/bfde13051dfc/5bb78114ae653a5bd2008af2");
        imageArrayList.add("https://s.yimg.com/ny/api/res/1.2/6P3IyVQjEwFw79YTiAqFmw--~A/YXBwaWQ9aGlnaGxhbmRlcjtzbT0xO3c9ODAw/https://media.zenfs.com/en-GB/the_telegraph_258/8f9924d49e8bc69642e39a38da4b2eed");

        msViewPager.setAdapter(new LoopImageViewPagerAdapter(this,imageArrayList, true));
    }

    private class LoopImageViewPagerAdapter extends MSLoopViewPagerAdapter<String> {

        public LoopImageViewPagerAdapter(Context context, ArrayList<String> itemList, boolean isInfinite) {
            super(context, itemList, isInfinite);
        }

        @Override
        protected View inflateView(int viewType, ViewGroup container, int position) {
            return LayoutInflater.from(IntroActivity.this).inflate(R.layout.item_view_pager, container, false);
        }

        @Override
        protected void bindView(View convertView, int position, int viewType) {

            ImageView imgView = (ImageView) convertView.findViewById(R.id.imgView);
            Glide.with(IntroActivity.this).load(itemList.get(position)).into(imgView);

            TextView textView = (TextView)convertView.findViewById(R.id.textView);
            textView.setText(itemList.get(position));
        }
    }
}
