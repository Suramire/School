package com.suramire.school.youwannanchang;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.suramire.school.R;

/**
 * Created by Suramire on 2017/6/3.
 */

public class MyFragment2 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.jingdianjieshao, container, false);
        String text = getArguments().getString("text");
        int image = getArguments().getInt("image");
        TextView textView = (TextView) view.findViewById(R.id.textView12);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView3);
        textView.setText(text);
        imageView.setImageResource(image);
        return view;
    }
}
