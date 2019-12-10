package com.xxx.willing.model.utils;

import android.annotation.SuppressLint;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 字符串工具类
 */
public class StringUtil {

    /**
     * 把科学计数法显示出全部数字
     */
    public static long getTime(String s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:MM:ss");
        try {
            Date parse = simpleDateFormat.parse(s);
            return parse.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }


    /**
     * 把科学计数法显示出全部数字
     */
    public static String getDoubleStr(double d) {
        NumberFormat nf = NumberFormat.getInstance();
        return nf.format(d);
    }

    /**
     * 手机号脱敏
     */
    public static String getPhoneCode(String phone) {
        if (phone != null && phone.trim().length() == 11) {
            StringBuilder sb = new StringBuilder(phone.trim());
            sb.replace(3, 7, "****");
            return sb.toString();
        }
        return phone;
    }

    /**
     * 获取时间
     */
    public static String getSimpleDataFormatTime(String pattern, long time) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(new Date(time));
    }

    /**
     * 获取币
     */
    public static String getMoney(double money) {
        if (money == 0) {
            return "0";
        }
        return new BigDecimal(String.valueOf(money)).setScale(4, RoundingMode.DOWN).stripTrailingZeros().toPlainString();
    }

    /**
     * 获取美元
     */
    public static String getUS(double money) {
        if (money == 0) {
            return "0";
        }
        return new BigDecimal(String.valueOf(money)).setScale(2, RoundingMode.DOWN).stripTrailingZeros().toPlainString();
    }

    public static String getAddress(String address) {
        if (address != null && address.trim().length() >= 42) {
            StringBuilder sb = new StringBuilder(address.trim());
            sb.replace(10, 32, "***");
            return sb.toString();
        }
        return address;
    }

    public static String getPhone(String phone) {
        if (phone != null && phone.trim().length() >= 11) {
            StringBuilder sb = new StringBuilder(phone.trim());
            sb.replace(3, 9, "***");
            return sb.toString();
        }
        return phone;
    }
}
