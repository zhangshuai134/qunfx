package com.jiami;

/**
 * @Author: zhangshuai
 * @Date: 2020-04-27 00:49
 * @Description:
 **/
public class StringArrayTest {

    public static void main(String[] args)
    {
        //Original String
        String string = "hello world";

        //Convert to byte[]
        byte[] bytes = string.getBytes();

        //Convert back to String
        String s = new String(bytes);

        //Check converted string against original String
        System.out.println("Decoded String : " + s);
        byte[] bytes1 = s.getBytes();
        System.out.println(11);
    }
}
