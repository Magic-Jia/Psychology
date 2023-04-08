package com.xf.psychology.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SomeUtil {
    public static Boolean isPhone(String phone) {
        String pattern = "^1(3\\d|4[5-9]|5[0-35-9]|6[567]|7[0-8]|8\\d|9[0-35-9])\\d{8}$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(phone);
        return m.matches();
    }

    public static Boolean isTruePwd(String pwd) {
        String pattern = "^\\d{6,}$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(pwd);
        return m.matches();
    }

    public static String getTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }


}
