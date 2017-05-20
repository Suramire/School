package com.suramire.school.xiaoyuanshenghuo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.ViewSwitcher;

import com.suramire.school.MyActivity;
import com.suramire.school.R;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Suramire on 2017/5/1.
 */

public class Xiaoyuanfengjing extends MyActivity {
    private GridView gridView;
    private ImageSwitcher imageSwitcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xiaoyuanfengjing);
        gridView = (GridView) findViewById(R.id.gridView1);
        imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher1);
        final ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        final int[] images ={R.drawable.a1,R.drawable.a2,R.drawable.a3,R.drawable.a4,R.drawable.a5,R.drawable.a6,
                R.drawable.a7,R.drawable.a9,R.drawable.a10,R.drawable.a12,R.drawable.a13,
                R.drawable.a15,R.drawable.a17,R.drawable.a19};
        for(int i=0;i<images.length;i++){
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("image", images[i]);
            data.add(map);
        }
        //重装git测试
        String[] from = {"image"};
        int[] to = {R.id.imageView1};
        SimpleAdapter adapter = new SimpleAdapter(Xiaoyuanfengjing.this, data, R.layout.xiaoyuanfengjing_item, from, to);
        gridView.setAdapter(adapter);
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {

            @Override
            public View makeView() {
                ImageView imageView = new ImageView(Xiaoyuanfengjing.this);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                return imageView;
            }
        });
        imageSwitcher.setImageResource(R.drawable.a10);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                imageSwitcher.setImageResource(images[position]);
                for(int i=0;i<gridView.getChildCount();i++){
                    gridView.getChildAt(i).setAlpha(0.3f);
                    gridView.getChildAt(i).setBackground(null);
                }
                gridView.getChildAt(position).setAlpha(1.0f);
                gridView.getChildAt(position).setBackground(null);

            }
        });
    }

}