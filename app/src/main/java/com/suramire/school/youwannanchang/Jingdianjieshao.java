package com.suramire.school.youwannanchang;

import android.content.Intent;
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
 * Created by Suramire on 2017/5/10.
 */

public class Jingdianjieshao extends MyActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jingdianjieshao1);
        //根据获取到的intent内的值设置图片以及文字
        int[] images = new int[]{R.drawable.tengwangge,
                R.drawable.badashanrenjinianguan, R.drawable.hanwangfeng,
                R.drawable.xiangshangongyuan, R.drawable.xishanwanshougong,
                R.drawable.meiling};
        Intent intent = getIntent();
        int textindex = intent.getIntExtra("textindex",0);
        final String[] title = getResources().getStringArray(R.array.title2);
        String[] texts = getResources().getStringArray(R.array.texts);
        int i = 0;
        final ArrayList<Fragment> fragments= new ArrayList<>();
        for(String t:texts){
            MyFragment2 myFragment2 = new MyFragment2();
            Bundle bundle = new Bundle();
            bundle.putString("text",t);
            bundle.putInt("image",images[i++%images.length]);
            myFragment2.setArguments(bundle);
            fragments.add(myFragment2);
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager1);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout1);

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return title[position];
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTitle(title[position]);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setCurrentItem(textindex);

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
