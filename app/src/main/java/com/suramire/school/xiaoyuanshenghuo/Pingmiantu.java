package com.suramire.school.xiaoyuanshenghuo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

import com.suramire.school.MyActivity;
import com.suramire.school.R;

/**
 * Created by Suramire on 2017/5/1.
 */

public class Pingmiantu extends MyActivity {
    int[] images  = {R.drawable.jiaotong,R.drawable.jiaoqiaoxiaoqu,R.drawable.mailuxiaoqu,R.drawable.fenglinxiaoqu};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pingmiantu);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final ImageView imageView = (ImageView) findViewById(R.id.imageView1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                imageView.setImageResource(images[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}