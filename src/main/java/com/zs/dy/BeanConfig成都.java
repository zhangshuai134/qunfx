package com.zs.dy;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * @Auther: curry.zhang
 * @Date: 2019/8/21 19:05
 * @Description:
 */
@Configuration
@Slf4j
public class BeanConfig成都 {

    public static void main(String[] args) {
        System.out.println(123);
        RestTemplate restTemplate = new BeanConfig().restTemplate();

        List<String> userInfoList = new ArrayList<>();

        String aa = "MS4wLjABAAAAIY_CPdT3c5l4EkcP1NLcUnzRMUwJXdHF1MWlSVyc3b0&&&kTl2HQAA8gPiz3xy3lAF-JE5dg&dytk=\n" +
                "MS4wLjABAAAAHqGKw9CNQ4QthQNe8WXTe3LwGyhDvlkITMd98p3QIAA&&&knlvZQAA8sPhj2UKKJpMsZJ5b3&dytk=\n" +
                "MS4wLjABAAAAOPT4YJo6SKlVUzLUP3gywznwCArCXF_AtfSrrYuq2Ww&&&k4W5DAAA81jgc7NjFHfwFJOFuR&dytk=\n" +
                "MS4wLjABAAAAiysVUxeEgasiZMSY-_JNwMlaAbIsP7cPmurDjD0IuBg&&&k39-sAAA88HgiXTfcAf.yZN.fq&dytk=\n" +
                "MS4wLjABAAAAjUtHJWnw2ZYvbrzUcnCes8p6hawWV5qGXveu0uZFZdo&&&lIzYKgAA9E7netJF-7YO3pSM2D&dytk=\n" +
                "MS4wLjABAAAA_Sns0orOmB42PFtmxhBmmEkkUG5rG4Mj-jtJjWQONZc&&&F8d6wAA9KHnqReEENtImpRfHf&dytk=\n" +
                "MS4wLjABAAAAb1GDaRHNSUydir2BizLa6u2a6i0Aftw9wm6uH48bO3A&&&lBKLJQAA9O3n5IFKlT8-AZQSiz&dytk=\n" +
                "MS4wLjABAAAA3EsyVWdDg0iUsIQq_ClpyY6ocMf-mcgv2p-ImX1bTC0&&&lfWmlQAA9UjmA6z6uB9FHZX1po&dytk=\n" +
                "MS4wLjABAAAAMJSH55qWLYIwNzOjXLnO8wW3394kEZN13d3iZ1LAXaE&&&lbDl9QAA9YvmRu-arPHs2ZWw5e&dytk=\n" +
                "MS4wLjABAAAAm-rqApDFLmAH_dI9HVizMOeYGXJ77jOjcbyLz33eP891HAWjPkkByLxBdNl1QwX1&&&lQlWUgAA9dPm.1w9teEyNZUJVk&dytk=\n" +
                "MS4wLjABAAAAJ0wSPMTuFq2Tw-4jwqbNVGRqisfsMAFi13qdKXzij-g-Rgd-m_FiAwFH5ado3yAi&&&ltzj0QAA9h.lKum-LEhESZbc48&dytk=\n" +
                "MS4wLjABAAAALRV32u4uTtG-SgOKVMJorycBJ1O4h5kTgYUaCkCrGUU&&&lpm.mgAA9mTlb7X1Cal1i5aZv4&dytk=\n" +
                "MS4wLjABAAAAzjylpzrUhht5TeqOVA5NO8EOpM8wldWUys6aNYH1fsg&&&lm3uMwAA9rDlm-RcI77GH5Zt7i&dytk=\n" +
                "MS4wLjABAAAAELA-Yd7Clgvnggvc1bW21lGtd7569o7byk803bx0r18&&&li24WgAA9vDl27I1MxjMKZYtuE&dytk=\n" +
                "MS4wLjABAAAAkmGb8zrVE-9CdOUPYLwDQZQBZU_wJ-YGtmwNFz4KJCI&&&l-m87wAA9zTkH7aAKpVj25fpvP&dytk=\n" +
                "MS4wLjABAAAAQCJzTLIf-haUuzsSeEuzOtvAPqvq4gWXXnK5ivHdRXQ&&&l6v-4gAA93bkXfSNO1gvDZer.v&dytk=\n" +
                "MS4wLjABAAAAssDC8X_YtBeZWAInlFa1Aukx9RSpQixwvUVhLWydTC8&&&l2cAAwAA97nkkQpsMi6vgZdnAB&dytk=\n" +
                "MS4wLjABAAAAccRtSec2g9Msy8z-__oB70zpypN8yJFym18205-DRJU&&&mb68ywAA-YHqSLak37kfZJm-vN&dytk=\n" +
                "MS4wLjABAAAAC9ANaGshfUqZahr6TeN_zoXQ3GevSbMHXa1vBaJOJxoUa5IMXMxB7BIcVizOO8Gs&&&mRFIAwAA-evq50JszL9IB5kRSB&dytk=\n" +
                "MS4wLjABAAAAFrSIswr41nCARNM8sRIuOE14J4kmT02cBkjbJAYSBw8&&&moebowAA-lrpcZHM7YlCV5qHm7&dytk=\n" +
                "MS4wLjABAAAAIzsMOsYL2i9CwlUGevUFJgp_hGiaB9hGSb0MymZmtSE&&&mgRt-gAA-tbp8meVtGZDvpoEbe&dytk=\n" +
                "MS4wLjABAAAAWg90OXaI-e4nMmpSZ-AhqHCN7af-Oii9NkJqWhazLAg&&&nWdGYgAA.bnukUwNARX45J1nRn&dytk=\n" +
                "MS4wLjABAAAAGHy11qJTwtGTaMhgQkQCP5ra8P2VAnci1YDN3vtdLaY&&&nT98vQAA.gLuyXbS1B59mJ0.fK&dytk=\n" +
                "MS4wLjABAAAA64hSMCDAf3b_YkoqBZXKFpIyPbdjU7LtRvmd1vFGDAw&&&nviNYwAA.kPtDocMAWbgIp74jX&dytk=\n" +
                "MS4wLjABAAAAhXnIxzzr3Ta0WSXJvnMZ8Jh4Aw13f5MZpV-nu4Oy-AYeN7ueXKwe0jYbmIhHYC1o&&&nkkHxAAA.pTtvw2rCNUxvp5JB9&dytk=\n" +
                "MS4wLjABAAAAwUcDkfaaTs6ljCzG5NxSmiX51B6sn6vQOmmmWEIEYOI&&&n-Iz1AAA.z3sFDm7itelvZ.iM8&dytk=\n" +
                "MS4wLjABAAAAdZdJF2MWchwlwk_EFTWc4gaad126YARdQF41d-42wPQ&&&n76uJAAA.4HsSKRLb3sxMp--rj&dytk=\n" +
                "MS4wLjABAAAAnH7ETAYQ1_IDeSQgPM7EMQ3wpRo9GuSG67Imy4_H8ig&&&nxhvdgAA.-Ps7mUZL1IU4J8Yb2&dytk=\n" +
                "MS4wLjABAAAAcfdgom_RwUqVVvMJvYDlqYE_jZTQdAvuVzfNQ7zK1f4&&&=YKrbZgAAAGYTXNEJZPCVnWCq23&dytk=\n" +
                "MS4wLjABAAAALSRqouniRcVQLMc6MpcN4sUis9of7jCpMWp8Uis9EV4&&&YCdy7AAAAOsT0XiDwbNF8mAncv&dytk=\n" +
                "MS4wLjABAAAAFE5yTUCdsJDLOc6b0gzQmlyydfU2hZHsIQJiunPXGIo&&&YfMX.QAAAT8SBR2S-Rs2CWHzF-&dytk=\n" +
                "MS4wLjABAAAAOpEv5jyi_pPUEZCJu4nL2-VXdMoyIAECHkj_929be1g&&&YWXjtwAAAakSk-nYUfoyVGFl46&dytk=\n" +
                "MS4wLjABAAAA8VVnmxlhsIgcMlqAmZQPIgKFGPFRNK2mYR6YolSCsek&&&YSUCEAAAAekS0wh.DtymmmElAg&dytk=";
        String[] split1 = aa.split("\n");
        int length = split1.length;
        for (int i = 0; i < length; i++) {
            String user = split1[i];
            userInfoList.add(user);
        }

        List<String> addrList = new ArrayList<>();

        for (String user : userInfoList) {
            try {
                String[] split = user.split("&&&");
                String secUid = split[0];
                String signature = split[1];
                String maxCursor = "0";
                while (true) {
                    maxCursor = getByUser(restTemplate, secUid, maxCursor, signature, addrList);
                    if (StringUtils.isBlank(maxCursor)) {
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("===================报错====================" + e);
            }
        }


        System.out.println("=======================================开始打印原始数据=============");
        for (String s : addrList) {
            System.out.println(s);
        }
        HashMap<String, Integer> map = new HashMap<>();
        for (String s : addrList) {
            String[] split = s.split("&&&");
            String addrName = split[0];
            Integer integer = map.get(split[0]);
            if (integer == null) {
                map.put(addrName, 1);
            } else {
                map.put(addrName, integer + 1);
            }
        }

        Set<String> strings = map.keySet();
        System.out.println("=========================================总共被探过的店的总数：" + strings.size());
        System.out.println("=========================================开始打印map数据======================");
        for (String string : strings) {
            Integer integer = map.get(string);
            System.out.print(integer);
            System.out.print("===");
            System.out.println(string);
        }
        System.out.println("=========================================开始排序================");

        Map<String, Integer> resultMap = sortMapByValue(map); //按Value进行排序

        for (Map.Entry<String, Integer> entry : resultMap.entrySet()) {
            System.out.println(entry.getValue() + " " + entry.getKey());
        }
    }

    // 使用 Map按value进行排序
    public static Map<String, Integer> sortMapByValue(Map<String, Integer> oriMap) {
        if (oriMap == null || oriMap.isEmpty()) {
            return null;
        }
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        List<Map.Entry<String, Integer>> entryList = new ArrayList<Map.Entry<String, Integer>>(
                oriMap.entrySet());
        Collections.sort(entryList, new MapValueComparator1());

        Iterator<Map.Entry<String, Integer>> iter = entryList.iterator();
        Map.Entry<String, Integer> tmpEntry = null;
        while (iter.hasNext()) {
            tmpEntry = iter.next();
            sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
        }
        return sortedMap;
    }

    private static String getByUser(RestTemplate restTemplate, String secUid, String maxCursor, String signature, List<String> addrList) {
        String url = "http://www.iesdouyin.com/web/api/v2/aweme/post/?sec_uid=" + secUid + "&count=40&max_cursor=" + maxCursor + "&aid=1128&_signature=" + signature;
        HttpHeaders headers = new HttpHeaders();
        headers.set("user-agent",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36");
        HttpEntity<String> forEntity = restTemplate
                .exchange(url, HttpMethod.GET, new HttpEntity<>(null, headers),
                        String.class);

        if (forEntity != null && forEntity.getBody() != null) {
            String body = forEntity.getBody();
            JSONObject jsonObject = JSONObject.parseObject(body);
            JSONArray awemeList = jsonObject.getJSONArray("aweme_list");
            if (awemeList == null || awemeList.size() == 0) {
                return null;
            }
            int length = awemeList.size();
            maxCursor = jsonObject.getString("max_cursor");
            for (int i = 0; i < length; i++) {
                JSONObject item = awemeList.getJSONObject(i);
                String videoId = item.getString("aweme_id");
                String nickname = item.getJSONObject("author").getString("nickname");

                String addrName = getVideo(restTemplate, videoId);

                if (!StringUtils.isBlank(addrName)) {
                    addrList.add(addrName + "&&&" + nickname);
                }
                System.out.println(addrName + "&&&" + nickname);
            }
            return maxCursor;
        }
        return null;
    }

    private static String getVideo(RestTemplate restTemplate, String videoId) {


        String url = "https://www.iesdouyin.com/web/api/v2/aweme/iteminfo/?item_ids=" + videoId;

        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        if (forEntity != null && forEntity.getBody() != null) {
            String body = forEntity.getBody();
            JSONObject jsonObject = JSONObject.parseObject(body);
            JSONArray itemList = jsonObject.getJSONArray("item_list");
            if (itemList != null && itemList.size() > 0) {
                JSONObject item = itemList.getJSONObject(0);
                JSONObject anchorInfo = item.getJSONObject("anchor_info");
                if (anchorInfo != null) {
                    String addrId = anchorInfo.getString("id");
                    String addr = getAddr(restTemplate, addrId);
//                    System.out.println(addr);
                    return addr;
                }
            }
        }
        return "";
    }


    // 根据位置锚点id获取位置名字
    public static String getAddr(RestTemplate restTemplate, String addrId) {
        String url = "https://www.iesdouyin.com/web/api/v2/poi/detail/?poi_id=" + addrId;

        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        if (forEntity != null && forEntity.getBody() != null) {
            String body = forEntity.getBody();
            JSONObject jsonObject = JSONObject.parseObject(body);

            JSONObject poiInfo = jsonObject.getJSONObject("poi_info");

            if (poiInfo != null) {
            JSONObject poiExt = jsonObject.getJSONObject("poi_ext");
            String cost = poiExt == null ? "" : poiExt.getString("cost");
                String poiName = poiInfo.getString("poi_name");
//            JSONObject addressInfo = poiInfo.getJSONObject("address_info");
//            String simpleAddr = addressInfo.getString("simple_addr");
                return poiName + "===" + cost;
            }
        }
        return "";
    }


    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(clientHttpRequestFactory());
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
        return restTemplate;
    }

    @Bean
    public HttpComponentsClientHttpRequestFactory clientHttpRequestFactory() {
        try {
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                    return true;
                }
            }).build();
            httpClientBuilder.setSSLContext(sslContext);
            HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .register("https", sslConnectionSocketFactory).build();// 注册http和https请求
            // 开始设置连接池
            PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            poolingHttpClientConnectionManager.setMaxTotal(100); // 最大连接数100
            poolingHttpClientConnectionManager.setDefaultMaxPerRoute(20); // 同路由并发数20
            httpClientBuilder.setConnectionManager(poolingHttpClientConnectionManager);
            httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(3, true)); // 重试次数
            HttpClient httpClient = httpClientBuilder.build();
            HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient); // httpClient连接配置
            clientHttpRequestFactory.setConnectTimeout(20000);              // 连接超时
            clientHttpRequestFactory.setReadTimeout(50000);                 // 数据读取超时时间
            clientHttpRequestFactory.setConnectionRequestTimeout(20000);    // 连接不够用的等待时间
            return clientHttpRequestFactory;
        } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
            log.error("初始化HTTP连接池出错", e);
        }
        return null;
    }
}

class MapValueComparator1 implements Comparator<Map.Entry<String, Integer>> {

    @Override
    public int compare(Map.Entry<String, Integer> me1, Map.Entry<String, Integer> me2) {

        return me1.getValue().compareTo(me2.getValue());
    }
}
