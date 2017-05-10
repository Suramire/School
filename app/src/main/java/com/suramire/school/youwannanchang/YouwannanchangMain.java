package com.suramire.school.youwannanchang;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.suramire.school.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by Suramire on 2017/5/10.
 */

public class YouwannanchangMain extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Item> items;
    private int[] images;
    private String[] texts;
    private String[] names;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youwannanchang);
        listView = (ListView) findViewById(R.id.listView);
        ArrayList<HashMap<String,Objects>> data = new ArrayList<HashMap<String,Objects>>();
        names = new String[]{"景点名1","景点名2","景点名3","景点名4","景点名5","景点名6","景点名7"};
        texts = new String[]{"景点描述1","景点描述2","景点描述3","景点描述4","景点描述5","景点描述6","景点描述7"};
        images = new int[]{R.drawable.badashanrenjinianguan,R.drawable.baosige,R.drawable.beihu,R.drawable.chayuan,R.drawable.fengyuan,R.drawable.guiyuan,R.drawable.hanwangfeng};
        items = new ArrayList<Item>();
        for(int i = 0; i< names.length; i++){
            items.add(new Item(names[i], texts[i], images[i]));
        }


        BaseAdapter baseAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return items.size();
            }

            @Override
            public Object getItem(int i) {
                return items.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                //设置每个item的文字图片
                view = LayoutInflater.from(YouwannanchangMain.this).inflate(R.layout.youwannanchang_item,viewGroup,false);
                ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
                TextView textViewname = (TextView) view.findViewById(R.id.textView11);
                TextView textViewtext = (TextView) view.findViewById(R.id.textView10);
                final Item item = items.get(i);
                imageView.setImageResource(item.getImage());
                textViewname.setText(item.getName());
                textViewtext.setText(item.getText());
                return view;
            }
        };



        listView.setAdapter(baseAdapter);
        //设置每个item的点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               enter(i);
            }
        });

    }
    public void enter(int i){
        Intent intent1 = new Intent(YouwannanchangMain.this, Jingdianjieshao.class);
        intent1.putExtra("text",i);//景点介绍的数组下标
        intent1.putExtra("image",images[i]);//对应的图片
        startActivity(intent1);
    }

}
