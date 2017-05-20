package com.suramire.school.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.suramire.school.MyActivity;
import com.suramire.school.R;

import java.util.ArrayList;

/**
 * Created by Suramire on 2017/5/20.
 */

public class TestActivity extends MyActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xinshengzhinan2);

//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        MyFragment myFragment = new MyFragment();
//        fragmentTransaction.add(R.id.linearLayout1,myFragment);
//        fragmentTransaction.commit();
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
        //使tabLayout 可以滚动
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);
    }
}
