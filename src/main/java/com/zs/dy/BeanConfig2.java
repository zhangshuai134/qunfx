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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Auther: curry.zhang
 * @Date: 2019/8/21 19:05
 * @Description:
 */
@Configuration
@Slf4j
public class BeanConfig2 {

    public static void main(String[] args) {
        System.out.println(123);

        List<String> addrList = new ArrayList<>();
        File file = new File("D:\\精益创新项目\\第十一轮\\源数据.txt");
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

//        String[] split12 = bb.split("\n");
//        int length2 = split12.length;
//        for (int i = 0; i < length2; i++) {
//            String user = split12[i];
//            addrList.add(user);
//        }

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
        Collections.sort(entryList, new MapValueComparator());

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
                    System.out.println(addr);
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
                String poiName = poiInfo.getString("poi_name");
//            JSONObject addressInfo = poiInfo.getJSONObject("address_info");
//            String simpleAddr = addressInfo.getString("simple_addr");
                return poiName;
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

