package com.zs.dy;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: zhangshuai
 * @Date: 2021-06-08 14:28
 * @Description:
 **/
public class BaiduMap {

    public static void main(String[] args) {
        System.out.println(123);
        RestTemplate restTemplate = new BeanConfig().restTemplate();
        HashMap<String, List<String>> ppMap = new HashMap<>();
        String 品牌名字 = "古木春烤猪蹄";

        ArrayList<String> 店铺list = new ArrayList<>();
        JSONObject res = getRes(restTemplate, 品牌名字,"0");
        res = 重试(restTemplate, 品牌名字, res);
        Integer total = res.getInteger("total");
        if(total > 0){
            JSONArray results = res.getJSONArray("results");
            int length = results.size();
            for (int i = 0; i < length; i++) {
                JSONObject result = results.getJSONObject(i);
                String name = result.getString("name");
                if(name.equals(品牌名字) || name.contains(品牌名字+"(")){
                    店铺list.add(name);
                }
            }
            ppMap.put(品牌名字,店铺list);
        }

        System.out.println(123);
    }

    private static JSONObject 重试(RestTemplate restTemplate, String 品牌名字, JSONObject res) {
        String status = res.getString("status");
        if(status.equals("401")){
            System.out.println("重试第一次");
            res = getRes(restTemplate, 品牌名字,"0");
            status = res.getString("status");
            if(status.equals("401")){
                System.out.println("重试第二次");
                res = getRes(restTemplate, 品牌名字,"0");
                status = res.getString("status");
                if(status.equals("401")){
                    System.out.println("重试第三次");
                    res = getRes(restTemplate, 品牌名字,"0");
                }
            }
        }
        return res;
    }

    private static JSONObject getRes(RestTemplate restTemplate, String keyWord, String pageNum) {
        String url = "http://api.map.baidu.com/place/v2/search?query="+keyWord
                +"&tag=美食&region=西安市&output=json&ak=WVAXZ05oyNRXS5egLImmentg&page_num="+pageNum+"&page_size=20";
        HttpHeaders headers = new HttpHeaders();
        headers.set("user-agent",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36");
        HttpEntity<String> forEntity = restTemplate
                .exchange(url, HttpMethod.GET, new HttpEntity<>(null, headers),
                        String.class);

        if (forEntity != null && forEntity.getBody() != null) {
            String body = forEntity.getBody();
            JSONObject jsonObject = JSONObject.parseObject(body);
            return jsonObject;
        }
        return null;
    }
}
