package com.suramire.school.xiaoyuanshenghuo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.suramire.school.MyActivity;
import com.suramire.school.R;

import java.util.ArrayList;

/**
 * Created by Suramire on 2017/5/1.
 * 显示对应的新生指南页面 采用viewpager与fragment结合
 */

public class Xinshengzhinan1 extends MyActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xinshengzhinan2);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        int index = getIntent().getIntExtra("text",0);//获取传入的下标
        final String[] texts = getResources().getStringArray(R.array.guids);//获取文本内容
        final String[] titles = getResources().getStringArray(R.array.title);//获取标题
        final ArrayList<MyFragment> fragments = new ArrayList<>();
        for(String text :texts){
            MyFragment myFragment = new MyFragment();
            Bundle bundle = new Bundle();
            bundle.putString("text",text);//将文本内容传入fragment
            myFragment.setArguments(bundle);
            fragments.add(myFragment);//将fragment存入list
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
        Toast.makeText(this, "左右滑动切换页面", Toast.LENGTH_LONG).show();
    }
}