package com.bulid;

/**
 * @Author: zhangshuai
 * @Date: 2020-03-25 22:53
 * @Description:
 **/
public class 看电影作文 extends Builder {
    @Override
    String 写标题() {
        return "看电影";
    }

    @Override
    String 写开头() {
        return "今天，我们几个人一起去看电影。";
    }

    @Override
    String 写内容() {
        return "去哪个电影院，看什么电影，有什么感触。。。";
    }

    @Override
    String 写结尾() {
        return "啊！电影真好看，下次还想看！";
    }
}
