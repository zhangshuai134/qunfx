package com.zs.qunfx.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @Auther: curry.zhang
 * @Date: 2020/6/23 18:05
 * @Description:
 */
public class Base62Util {

    public static void main(String[] args) {
        String base62Encode = base62Encode(100010001);
        System.out.println(base62Encode);
        long l = base62Decode(base62Encode);
        System.out.println(l);
    }

    /**
     * 将数字转为62进制，返回6个长度的字符串，不够前面补0
     * @param num    Long 型数字
     * @return 62进制字符串
     */

    public static String base62Encode(long num) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        int remainder = 0;
        int scale = 62;
        while (num > scale - 1) {
            remainder = Long.valueOf(num % scale).intValue();
            sb.append(chars.charAt(remainder));
            num = num / scale;
        }
        sb.append(chars.charAt(Long.valueOf(num).intValue()));
        String value = sb.reverse().toString();
        return StringUtils.leftPad(value, 6, '0');
    }

    /**
     * 62进制字符串转为数字
     * @param str 编码后的62进制字符串
     * @return 解码后的 10 进制字符串
     */
    public static long base62Decode(String str) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        int scale = 62;
        str = str.replace("^0*", "");
        long num = 0;
        int index = 0;
        for (int i = 0; i < str.length(); i++) {
            index = chars.indexOf(str.charAt(i));
            num += (long) (index * (Math.pow(scale, str.length() - i - 1)));
        }
        return num;
    }
}
