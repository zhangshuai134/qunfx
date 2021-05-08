package com.bulid;

/**
 * @Author: zhangshuai
 * @Date: 2020-03-25 22:59
 * @Description: 指挥者
 **/
public class Director {

    public String 写作文(Builder builder){
        String 标题 = builder.写标题();
        String 开头 = builder.写开头();
        String 内容 = builder.写内容();
        String 结尾 = builder.写结尾();
        return 标题 + "\n" + 开头 + "\n" + 内容 + "\n" + 结尾;
    }
}
