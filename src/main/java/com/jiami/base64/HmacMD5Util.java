package com.jiami.base64;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class HmacMD5Util {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        System.out.println(encodeString("123"));
    }

    public static String encodeString(String plainText) throws UnsupportedEncodingException {
        return encodeBytes(plainText.getBytes("utf-8"));
    }

    public static String encodeBytes(byte[] bytes) {
        try {
//            // JDK的获取秘钥
//            byte[] key = getKey();
            // 指定秘钥
            byte[] key = Hex.decodeHex(new char[]{'a', 'b', 'c', 'd'});
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "HmacMD5");
            Mac mac = Mac.getInstance("HmacMD5");
            mac.init(secretKeySpec);

            byte[] b = mac.doFinal(bytes);
//            return Hex.encodeHexString(b);
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


    public static byte[] getKey() throws NoSuchAlgorithmException {
        KeyGenerator hmacMD5 = KeyGenerator.getInstance("HmacMD5");
        SecretKey secretKey = hmacMD5.generateKey();
        String algorithm = secretKey.getAlgorithm();// algorithm=HmacMD5

        byte[] encoded = secretKey.getEncoded();
        return encoded;
    }

}
