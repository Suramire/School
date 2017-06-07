package com.suramire.school.Util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.suramire.school.R;
import com.suramire.school.haomabaishitong.Child;

import java.util.List;

public class SortAdapter extends BaseAdapter {
	private Context context;
	private List<Child> children;
	private LayoutInflater inflater;

	public SortAdapter(Context context, List<Child> children) {
		this.context = context;
		this.children = children;
		this.inflater = LayoutInflater.from(context);

	}

	@Override
	public int getCount() {
		return children.size();
	}

	@Override
	public Object getItem(int position) {
		return children.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        Child c = children.get(position);
			convertView = inflater.inflate(R.layout.haomachild, null);
			TextView title =  (TextView) convertView.findViewById(R.id.tv_lv_item_tag);
			TextView name = (TextView) convertView.findViewById(R.id.textView5);
			TextView number = (TextView) convertView.findViewById(R.id.textView6);

		// 获取首字母的assii值
		int selection = c.getP().charAt(0);
		// 通过首字母的assii值来判断是否显示字母
		int positionForSelection = getPositionForSelection(selection);
		if (position == positionForSelection) {// 相等说明需要显示字母
            title.setVisibility(View.VISIBLE);
            title.setText(c.getP());
		} else {
            title.setVisibility(View.GONE);

		}
        name.setText(c.getName());
        number.setText(c.getNumber());
		return convertView;
	}
    //判断首字母是否是字母
	public int getPositionForSelection(int selection) {
		for (int i = 0; i < children.size(); i++) {
			String Fpinyin = children.get(i).getP();
			char first = Fpinyin.toUpperCase().charAt(0);
			if (first == selection) {
				return i;
			}
		}
		return -1;
	}



}
