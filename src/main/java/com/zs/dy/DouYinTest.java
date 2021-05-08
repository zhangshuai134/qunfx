package com.zs.dy;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @Author: zhangshuai
 * @Date: 2021-05-08 15:25
 * @Description:
 **/
public class DouYinTest {
    public static void main(String[] args) {
        System.out.println(123);
        SpringApplication.run(DouYinTest.class, args);
        RestTemplate restTemplate = ApplicationContextUtil.getBean(RestTemplate.class);

        String url = "www.baidu.com";

        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        System.out.println(1234);
        if (forEntity != null && forEntity.getBody() != null) {
            String body = forEntity.getBody();
            System.out.println(12345);
        }
    }
}
