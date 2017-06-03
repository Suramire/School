package com.suramire.school.xiaoyuanshenghuo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.suramire.school.R;

/**
 * Created by Suramire on 2017/5/20.
 * 用于显示新生指南的子内容
 */

public class MyFragment extends Fragment {

    private  String text ="";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myfragment,container,false);//使用此布局文件显示内容
        text =  getArguments().getString("text").toString();//获取上级传过来的数据
        TextView textView = (TextView) view.findViewById(R.id.textView1);//找到控件
        textView.setText(text);//设置文本
        return view;
    }
}
