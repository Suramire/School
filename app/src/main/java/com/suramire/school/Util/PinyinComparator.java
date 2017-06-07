package com.suramire.school.Util;

import com.suramire.school.haomabaishitong.Child;

import java.util.Comparator;

public class PinyinComparator implements Comparator<Child> {

	@Override
	public int compare(Child lhs, Child rhs) {
		return sort(lhs, rhs);
	}

	private int sort(Child lhs, Child rhs) {
		// 获取ascii值
		int lhs_ascii = lhs.getP().toUpperCase().charAt(0);
		int rhs_ascii = rhs.getP().toUpperCase().charAt(0);
		// 判断若不是字母，则排在字母之后
		if (lhs_ascii < 65 || lhs_ascii > 90)
			return 1;
		else if (rhs_ascii < 65 || rhs_ascii > 90)
			return -1;
		else
			return lhs.getP().compareTo(rhs.getP());
	}

}
