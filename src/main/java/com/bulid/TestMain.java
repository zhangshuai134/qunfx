package com.bulid;

/**
 * @Author: zhangshuai
 * @Date: 2020-03-25 23:04
 * @Description: 测试主类
 **/
public class TestMain {

    public static void main(String[] args) {

        Director director = new Director();
        String 春游作文 = director.写作文(new 春游作文());
        System.out.println(春游作文);

        System.out.println("===================================");
        System.out.println("============下一篇作文===============");
        System.out.println("====================================");

        String 看电影作文 = director.写作文(new 看电影作文());
        System.out.println(看电影作文);
    }
}
