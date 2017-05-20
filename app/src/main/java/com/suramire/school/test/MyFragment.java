package com.suramire.school.test;

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
 */

public class MyFragment extends Fragment {

    private  String text ="";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myfragment,container,false);
        text =  getArguments().getString("text").toString();
        TextView textView = (TextView) view.findViewById(R.id.textView1);
        textView.setText(text);
        return view;
    }
}
