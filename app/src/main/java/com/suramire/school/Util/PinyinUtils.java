package com.suramire.school.Util;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;


public class PinyinUtils {
	 /** 
     * 获取汉字串拼音，英文字符不变
     * 
     * @param inputString 汉字串
     * @return 汉语拼音首字母 
     */ 
	public static String getPingYin(String inputString) {
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		format.setVCharType(HanyuPinyinVCharType.WITH_V);

		char[] input = inputString.trim().toCharArray();
		String output = "";

		try {
			for (char curchar : input) {
				if (java.lang.Character.toString(curchar).matches("[\\u4E00-\\u9FA5]+")) {
					String[] temp = PinyinHelper.toHanyuPinyinStringArray(curchar, format);
					output += temp[0];
				} else
					output += java.lang.Character.toString(curchar);
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		return output;
	}

	 /** 
     *  获取汉字串拼音首字母，英文字符不变 
     * 
     * @param chinese 汉字串 
     * @return 汉语拼音 
     */ 
	public static String getFirstSpell(String chinese) {
        String s = "";
        StringBuffer stringBuffer = new StringBuffer();
        char[] arr = chinese.toCharArray();
        char firstChar = arr[0];
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        if (firstChar > 128) {
//            汉字情况 先获取首字母英文
            try {
                String[] temp = PinyinHelper.toHanyuPinyinStringArray(firstChar, defaultFormat);
                if (temp != null) {
                    stringBuffer.append(temp[0].toUpperCase().charAt(0));
                    s = stringBuffer.toString().toUpperCase();
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }
        } else {
            if (firstChar >= 97 && firstChar <= 122) {
                //小写字母
                stringBuffer.append(firstChar);
                s = stringBuffer.toString().toUpperCase();
            } else if (firstChar >= 65 && firstChar <= 90) {
                s = stringBuffer.append(firstChar).toString();
            }
        }
        return s;
    }
}