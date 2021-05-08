package com.zs.qunfx.controller;

import com.zs.qunfx.util.Base62Util;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @Description: 发号器自增生成
 */
@RestController
@RequestMapping("/shortUrl2")
public class ShortUrl2Controller {

    // 短链url域名前缀
    private String shortUrlPrefix = "http://a.cn/";
    private HashMap<String, String> map = new HashMap<>();
    private Long num = 1000000L;

    @RequestMapping("getShortUrl")
    public String getShortUrl(String longUrl) {
        String key = creatKey();
        map.put(key, longUrl);
        return shortUrlPrefix + key;
    }

    @RequestMapping("getLongUrl")
    public String getLongUrl(String shortUrl) {
        return map.get(shortUrl.replace(shortUrlPrefix, ""));
    }

    private String creatKey() {
        String base62 = Base62Util.base62Encode(num);
        num++;
        return base62;
    }
}
