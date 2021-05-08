package com.jiami.base64;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

public class Base64Test {

    public static void main(String[] args) throws IOException {
        String str = "hello word";
        BASE64Encoder base64Encoder = new BASE64Encoder();
        // JDK Base64加密
        String encode = base64Encoder.encode(str.getBytes());
        System.out.println(encode);

        BASE64Decoder base64Decoder = new BASE64Decoder();
        // JDK Base64解密
        byte[] bytes = base64Decoder.decodeBuffer(encode);
        String res = new String(bytes);
        System.out.println(res);
    }
}
