package com.longxiang.woniuke.utils;

import android.util.Log;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * Created by jackiechan on 16/1/15.
 */
public class PinYinUtils {
    /**
     * 纯粹通过映射的方式进行转换查找,会有一个效率问题,所以不应该多次被调用
     *
     * @param hanzi
     * @return
     */
    public static String GetPinyin(String hanzi) {
        String result = "";//返回的拼音
        Log.i("aksdjalskdj", "GetPinyin: "+"2222222");
        //把汉字转成拼音
        HanyuPinyinOutputFormat hanyuPinyinOutputFormat = new HanyuPinyinOutputFormat();
        hanyuPinyinOutputFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);//设置输出的拼音为大写字母
        hanyuPinyinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);//不带声调
        char[] chars = hanzi.toCharArray();//转成字符数据
        for (int i = 0; i < chars.length; i++) {
            try {
                if (Character.isWhitespace(chars[i])) {//是不是空格
                    continue;
                }
                if (java.lang.Character.toString(chars[i]).matches(
                        "[\\u4E00-\\u9FA5]+")) {//-128---127之间没有汉字,汉字是两个 byte 大于127,只能说理论不是字母 数字 特殊符号,理论上认为汉字
                    String[] strings = PinyinHelper.toHanyuPinyinStringArray(chars[i], hanyuPinyinOutputFormat);//多音字
                    if (strings == null) {//全角字符
                        result += chars[i];
                    } else {//是汉字并且转成功了
                        result += strings[0];//因为可能存在多音字,并且不知道当前是不是多音字,知道也不知道取第几个,干脆取第一个
                    }
                } else {//不是汉字,直接追加在结果后面
                    result += chars[i];
                }
            } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                result += chars[i];
                badHanyuPinyinOutputFormatCombination.printStackTrace();
            }
        }

        return result;
//            try {
//                for (int i = 0; i < chars.length; i++) {
//                    if (java.lang.Character.toString(chars[i]).matches(
//                            "[\\u4E00-\\u9FA5]+")) {
//                        String[] temp = PinyinHelper.toHanyuPinyinStringArray(
//                                chars[i], hanyuPinyinOutputFormat);
//                        result += temp[0];
//                    } else
//                        result += java.lang.Character.toString(chars[i]);
//                }
//
//            } catch (BadHanyuPinyinOutputFormatCombination e) {
//                e.printStackTrace();
//            }
//            return result;
    }

}
