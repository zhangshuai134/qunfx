package com.zs.dy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: zhangshuai
 * @Date: 2021-06-08 18:04
 * @Description:
 **/
public class BeanConfig3 {
    public static void main(String[] args) {
        List<String> addrList = getArrayList("/Users/zhangshuai/Desktop/精益创新/6月8号品牌非西安总店的总店数据.txt");
        ArrayList<String> 西安总店list = new ArrayList<>();
        ArrayList<String> 西安本土店list = new ArrayList<>();
        for (String s : addrList) {
            String name = s.split("：")[0];
            String s1 = s.split("：")[1];
            if (s1.endsWith("总店)&&&陕西省===西安市")) {
                西安总店list.add(s);
            }
            if (s1.endsWith("陕西省===西安市")) {
                西安本土店list.add(s);
            }
        }
        System.out.println("连锁店（源数据中排除西安总店）总共" + addrList.size() + "家");
        System.out.println("店名有总店，且在西安的有" + 西安总店list.size() + "家");
        System.out.println("店名没有总店，且在西安的有" + 西安本土店list.size() + "家");
        System.out.println(123);

        List<String> addrList2 = getArrayList("/Users/zhangshuai/Desktop/精益创新/6月8号品牌非西安总店的总店数据详情.txt");
        HashMap<String, String> resMap = new HashMap<>();
        for (String s : addrList) {
            String 品牌 = s.split("：")[0];
            ArrayList<String> 品牌位置list = new ArrayList<>();
            for (String s1 : addrList2) {
                if (s1.contains(品牌)) {
                    String 位置 = s1.split("===")[1];
                    if (位置.equals("&&&")) {
                        continue;
                    }
                    品牌位置list.add(位置.split("-")[0]);
                }
            }
            if (品牌位置list.size() > 0) {
                String mostItem = ListOptUtils.getMostItem(品牌位置list);
                int num = 0;
                for (String s1 : 品牌位置list) {
                    if (s1.equals(mostItem)) {
                        num++;
                    }
                }
                System.out.println(品牌 + "：" + "总共有" + 品牌位置list.size() + "家店，最多的在" + mostItem + "，这个地方有" + num + "家");
            }
        }

        System.out.println(123);
    }


    private static List<String> getArrayList(String fileName) {
        List<String> addrList = new ArrayList<>();
//        File file = new File("/Users/zhangshuai/Desktop/精益创新/第十一轮/源数据.txt");
        File file = new File(fileName);
        BufferedReader reader = null;
//        StringBuffer sbf = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
//                sbf.append(tempStr);
                addrList.add(tempStr);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return addrList;
    }
}
