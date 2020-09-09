package com.lyht.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 中文字符转拼音
 * @author wzw
 *
 */
public class PinYinUtil {
	
	/**
	 * 首写字母
	 */
	public static String getFirstPinYin(String str) {
		char[] charArray = str.toCharArray();
		StringBuilder pinyin = new StringBuilder();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		// 设置大小写格式
		defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		// 设置声调格式：
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < charArray.length; i++) {
			// 匹配中文,非中文转换会转换成null
			if (Character.toString(charArray[i]).matches("[\\u4E00-\\u9FA5]+")) {
				String[] hanyuPinyinStringArray = null;
				try {
					hanyuPinyinStringArray = PinyinHelper.toHanyuPinyinStringArray(charArray[i], defaultFormat);
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (hanyuPinyinStringArray != null) {
					pinyin.append(hanyuPinyinStringArray[0].charAt(0));
				}
			}
		}
		return pinyin.toString();
	}
	
	/**
	 * 获取全拼
	 * @param str
	 * @return
	 */
	public static String getPinYin(String str) {
		char[] charArray = str.toCharArray();
		StringBuilder pinyin = new StringBuilder();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		// 设置大小写格式
		defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		// 设置声调格式：
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < charArray.length; i++) {
			// 匹配中文,非中文转换会转换成null
			if (Character.toString(charArray[i]).matches("[\\u4E00-\\u9FA5]+")) {
				String[] hanyuPinyinStringArray = null;
				try {
					hanyuPinyinStringArray = PinyinHelper.toHanyuPinyinStringArray(charArray[i], defaultFormat);
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (hanyuPinyinStringArray != null) {
					pinyin.append(hanyuPinyinStringArray[0].charAt(0));
				}
			}
		}
		return pinyin.toString();
	}
}
