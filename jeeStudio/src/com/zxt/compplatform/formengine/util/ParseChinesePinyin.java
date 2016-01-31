package com.zxt.compplatform.formengine.util;
/***
 *  输入汉字，反回其拼音首字母 
 * @author guo-pc
 */
public class ParseChinesePinyin {
    private static String _ToEncode_ = "GBK";
    private static String _FromEncode_ = "GBK";

    public static int compare(String str1, String str2) {
        int result = 0;
        String m_s1 = null;
        String m_s2 = null;
        try {
            m_s1 = new String(str1.getBytes(_FromEncode_), _ToEncode_);
            m_s2 = new String(str2.getBytes(_FromEncode_), _ToEncode_);
        } catch (Exception e) {
            return str1.compareTo(str2);
        }
        result = chineseCompareTo(m_s1, m_s2);
        return result;
    }
    public static int getCharCode(String s) {
        if (s == null || s.equals(""))
            return -1;
        byte b[] = s.getBytes();
        int value = 0;
        for (int i = 0; i < b.length && i <= 2; i++) {
            value = value * 100 + b[i];
        }
        return value;
    }

    public static int chineseCompareTo(String s1, String s2) {
        int len1 = s1.length();
        int len2 = s2.length();
        int n = Math.min(len1, len2);
        for (int i = 0; i < n; i++) {
            int s1_code = getCharCode(s1.charAt(i) + "");
            int s2_code = getCharCode(s2.charAt(i) + "");
            if (s1_code * s2_code < 0)
                return Math.min(s1_code, s2_code);
            if (s1_code != s2_code)
                return s1_code - s2_code;
        }
        return len1 - len2;
    }
    public static String getBeginCharacter(String res) {
        String a = res;
        String result = "";
        for (int i = 0; i < a.length(); i++) {
            String current = a.substring(i, i + 1);
            if (compare(current, "\u554A ") < 0)
                result = result + current;
            else if (compare(current, "\u554A ") >= 0
                    && compare(current, "\u5EA7 ") <= 0)
                if (compare(current, "\u531D ") >= 0)
                    result = result + "z";
                else if (compare(current, "\u538B ") >= 0)
                    result = result + "y";
                else if (compare(current, "\u6614 ") >= 0)
                    result = result + "x";
                else if (compare(current, "\u6316 ") >= 0)
                    result = result + "w";
                else if (compare(current, "\u584C ") >= 0)
                    result = result + "t";
                else if (compare(current, "\u6492 ") >= 0)
                    result = result + "s";
                else if (compare(current, "\u7136 ") >= 0)
                    result = result + "r";
                else if (compare(current, "\u671F ") >= 0)
                    result = result + "q";
                else if (compare(current, "\u556A ") >= 0)
                    result = result + "p";
                else if (compare(current, "\u54E6 ") >= 0)
                    result = result + "o";
                else if (compare(current, "\u62FF ") >= 0)
                    result = result + "n";
                else if (compare(current, "\u5988 ") >= 0)
                    result = result + "m";
                else if (compare(current, "\u5783 ") >= 0)
                    result = result + "l";
                else if (compare(current, "\u5580 ") >= 0)
                    result = result + "k";
                else if (compare(current, "\u51FB ") > 0)
                    result = result + "j";
                else if (compare(current, "\u54C8 ") >= 0)
                    result = result + "h";
                else if (compare(current, "\u5676 ") >= 0)
                    result = result + "g";
                else if (compare(current, "\u53D1 ") >= 0)
                    result = result + "f";
                else if (compare(current, "\u86FE ") >= 0)
                    result = result + "e";
                else if (compare(current, "\u642D ") >= 0)
                    result = result + "d";
                else if (compare(current, "\u64E6 ") >= 0)
                    result = result + "c";
                else if (compare(current, "\u82AD ") >= 0)
                    result = result + "b";
                else if (compare(current, "\u554A ") >= 0)
                    result = result + "a";
        }

        return result;
    }
    public static String getFirstStr(String str) {
        String result = "";
        char a= ' ';
        for(int i = 0; i < str.length(); i++){
            a = str.charAt(i);
            char aa[] = {a};
            String sss = new String(aa);
            if (Character.isDigit(aa[0]))
                sss = "data";
            else if (a >= 'a' && a <= 'z' || a >= 'A' && a <= 'Z')
                //sss = "character";
            	return a+"";
            else
                sss = getBeginCharacter(sss);
            result = result + sss;
        }
        return result;
    }

    public static void main(String[] args){
        System.out.println(getFirstStr("是不是这样呀"));
        System.out.println(ParseChinesePinyin.getFirstStr("sfafdaf"));
    }

}