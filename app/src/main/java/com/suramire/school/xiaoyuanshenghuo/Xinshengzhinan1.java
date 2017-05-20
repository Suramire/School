package com.suramire.school.xiaoyuanshenghuo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.suramire.school.MyActivity;
import com.suramire.school.R;
import com.suramire.school.test.MyFragment;

import java.util.ArrayList;

/**
 * Created by Suramire on 2017/5/1.
 */

public class Xinshengzhinan1 extends MyActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xinshengzhinan2);
        int index = getIntent().getIntExtra("text",0);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        final String[] texts = getResources().getStringArray(R.array.guids);
        final String[] titles = getResources().getStringArray(R.array.title);
        final ArrayList<MyFragment> fragments = new ArrayList<>();
        for(String text :texts){
            MyFragment myFragment = new MyFragment();
            Bundle bundle = new Bundle();
            bundle.putString("text",text);
            myFragment.setArguments(bundle);
            fragments.add(myFragment);
        }


        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return texts.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                setTitle(titles[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        //根据之前页面传过来的下标值,显示相应页面
        viewPager.setCurrentItem(index);
//        设置未选中与选中的标签文本颜色
        tabLayout.setTabTextColors(Color.GRAY,Color.BLACK);
        //设置下划线颜色
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));
        //使tabLayout 可以滚动
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);
    }
}