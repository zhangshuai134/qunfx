package com.jiami.base64;

import org.apache.commons.codec.binary.Base64;

public class ApacheBase64Test {

    public static void main(String[] args) {

        String str = "hello word";

        byte[] encodeBytes = Base64.encodeBase64(str.getBytes());
        System.out.println(new String(encodeBytes));

        byte[] decodeBytes = Base64.decodeBase64(encodeBytes);
        System.out.println(new String(decodeBytes));
    }
}
