package com.cafe.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class VNString {
    public static char removeAccent(char c) {
        String s = String.valueOf(c);
        s = removeAccent(s);
        return s.charAt(0);
    }

    public static String removeAccent(String str) {
        str = removeUpperCaseAccent(str);
        str = removeLowerCaseAccent(str);
        return str;
    }

    public static String removeUpperCaseAccent(String str) {
        str = str.replaceAll("[ÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴ]", "A");
        str = str.replaceAll("[ÈÉẸẺẼÊỀẾỆỂỄ]", "E");
        str = str.replaceAll("[ÌÍỊỈĨ]", "I");
        str = str.replaceAll("[ÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠ]", "O");
        str = str.replaceAll("[ÙÚỤỦŨƯỪỨỰỬỮ]", "U");
        str = str.replaceAll("[ỲÝỴỶỸ]", "Y");
        str = str.replaceAll("[Đ]", "D");
        return str;
    }

    public static String removeLowerCaseAccent(String str) {
        str = str.replaceAll("[àáạảãâầấậẩẫăằắặẳẵ]", "a");
        str = str.replaceAll("[èéẹẻẽêềếệểễ]", "e");
        str = str.replaceAll("[ìíịỉĩ]", "i");
        str = str.replaceAll("[òóọỏõôồốộổỗơờớợởỡ]", "o");
        str = str.replaceAll("[ùúụủũưừứựửữ]", "u");
        str = str.replaceAll("[ỳýỵỷỹ]", "y");
        str = str.replaceAll("[đ]", "d");
        return str;
    }

    public static String currency(double money) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.of("vi", "VN"));
        return formatter.format(money);
    }
}
