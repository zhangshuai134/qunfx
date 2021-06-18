package com.zs.dy;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: zhangshuai
 * @Date: 2021-06-08 16:21
 * @Description:
 **/
public class ListOptUtils {
    public static void main(String[] args) {
        List<String> l =new ArrayList<String>();
        l.add("a") ;
        l.add("a") ;
        l.add("b") ;
        l.add("b") ;
        l.add("b") ;
        l.add("c") ;
        l.add("d") ;
        l.add("d") ;
        l.add("d") ;
        System.out.println(ListOptUtils.getMostItem(l));
    }


    public static String getMostItem(List<String> l) {
        String regex;
        Pattern p;
        Matcher m;
        String tmp = "";
        String tot_str = l.toString();
        //System.out.println(tot_str);   //[aa, aa, aa, aa, bb, bb, cc, cc, dd, ed]
        int max_cnt = 0;
        String max_str = "";
        for (String str : l) {
            if (tmp.equals(str)) continue;
            tmp = str;
            regex = str;
            p = Pattern.compile(regex);
            m = p.matcher(tot_str);
            int cnt = 0;
            while (m.find()) {
                cnt++;
            }
            //System.out.println(str + ":" + cnt);
            if (cnt > max_cnt) {
                max_cnt = cnt;
                max_str = str;
            }
        }
//        System.out.println(" 出现的最大次数的字符串是 " + max_str);
        return max_str;
    }
}
