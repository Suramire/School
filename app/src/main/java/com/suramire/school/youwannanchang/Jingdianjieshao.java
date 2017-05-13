package com.suramire.school.youwannanchang;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.suramire.school.MyActivity;
import com.suramire.school.R;

/**
 * Created by Suramire on 2017/5/10.
 */

public class Jingdianjieshao extends MyActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jingdianjieshao);
        //根据获取到的intent内的值设置图片以及文字
        Intent intent = getIntent();
        int image = intent.getIntExtra("image",0);
        int textindex = intent.getIntExtra("text",0);
        String text = getResources().getStringArray(R.array.texts)[textindex];
        ImageView imageView = (ImageView) findViewById(R.id.imageView3);
        TextView textView = (TextView) findViewById(R.id.textView12);
        imageView.setImageResource(image);
        textView.setText(text);

    }
}
