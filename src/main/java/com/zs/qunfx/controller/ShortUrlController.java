package com.zs.qunfx.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Random;

/**
 * @Description: 发号器随机生成
 */
@RestController
@RequestMapping("/shortUrl")
public class ShortUrlController {

    private String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    // 短链url域名前缀
    private String shortUrlPrefix = "http://a.cn/";
    private HashMap<String, String> map = new HashMap<>();

    @RequestMapping("getShortUrl")
    public String getShortUrl(String longUrl) {
        String key = creatKey();
        while (map.containsKey(key)) {
            key = creatKey();
        }
        map.put(key, longUrl);
        return shortUrlPrefix + key;
    }

    @RequestMapping("getLongUrl")
    public String getLongUrl(String shortUrl) {
        return map.get(shortUrl.replace(shortUrlPrefix, ""));
    }

    private String creatKey() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(BASE62.charAt(rand.nextInt(62)));
        }
        return sb.toString();
    }
}
