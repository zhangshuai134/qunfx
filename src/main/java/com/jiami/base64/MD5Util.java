package com.jiami.base64;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

public class MD5Util {
    public static void main(String[] args) throws IOException {
        System.out.println(encodeString("123"));
    }

    public static String encodeString(String plainText) throws UnsupportedEncodingException {
        return encodeBytes(plainText.getBytes("UTF-8"));
    }

    public static String encodeBytes(byte[] bytes) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(bytes);
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
