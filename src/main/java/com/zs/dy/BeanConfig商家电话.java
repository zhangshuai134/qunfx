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
public class BeanConfig商家电话 {

    public static void main(String[] args) {
        System.out.println(123);
        RestTemplate restTemplate = new BeanConfig().restTemplate();

        String token = "eJx1T01vqkAU/S+zhTjDlzDusOLriLYqKkrTBTMgwyCKQBFt+t/fNGkXb/GSm5yPe3Jy7yeoSQJGGkIYIRV0aQ1GQBugwRCooG3kZogcjGyMho4lA+xfDxvSo/VuAkZvtm6p2MLv38Za6jcN60jVkIPe1V9uSq6bcr5TRIYAb9uqGUFIxaBM8/YjPg/YpYSSNzyHTLOhPOQ/ISBbyo1skVj8YPyD7a9eyIdkRZNnZ8nS2S0RTGtd4a34vuOm/wK3874mXbFih8uWbZtxJNDcw1SPRf8nyBSruhXXy9i+EC16tfFyo+DCyzo37dbR5vDKs7Xw+LHHT24HHWVvMHqf5Y9iYiPhw8VuscwpuVcnaswOxAj55slTomXY3aM01ruA6zBy58W09llw8pNhf95F3uGknSfXKrC9YxJTO5iEs+OY+c8hbaamYrrV3NKupAuzHRSY3hTRPjhqxXOkZ0bZOziaohey0OsyaZlFiL3flg9IDJTr4OsvJZyVbQ==";

        Base64.getDecoder().decode(token);


        String cookie = "_lxsdk_cuid=1797e28e139c8-001e28c9fb4709-37617207-1aeaa0-1797e28e139c8; _hc.v=29b68145-4d7e-8135-524b-ac263520c13c.1621319541; uuid=d591636c37f44319ba4f.1623927718.1.0.0; _lx_utm=utm_source%3DBaidu%26utm_medium%3Dorganic; client-id=3cd09ce5-96da-4f9b-944d-74c6d12c7a8b; mtcdn=K; _lxsdk=1797e28e139c8-001e28c9fb4709-37617207-1aeaa0-1797e28e139c8; lat=34.263623; lng=108.946609; userTicket=KcPMbiCMqrUiWIoEPCmrmsnqKoIViTZPPGxXJGwk; u=947470355; n=%E5%BC%A0%E5%B8%853385; lt=m1yta48Q7xXz1ZB1RrYCUX32svUAAAAAyQ0AAArXIcipi3dENOjRaIGlozY8fv9qgChp92AE83OACXth7y9WmnPIYBr94rgM0B8BFA; mt_c_token=m1yta48Q7xXz1ZB1RrYCUX32svUAAAAAyQ0AAArXIcipi3dENOjRaIGlozY8fv9qgChp92AE83OACXth7y9WmnPIYBr94rgM0B8BFA; token=m1yta48Q7xXz1ZB1RrYCUX32svUAAAAAyQ0AAArXIcipi3dENOjRaIGlozY8fv9qgChp92AE83OACXth7y9WmnPIYBr94rgM0B8BFA; lsu=; token2=m1yta48Q7xXz1ZB1RrYCUX32svUAAAAAyQ0AAArXIcipi3dENOjRaIGlozY8fv9qgChp92AE83OACXth7y9WmnPIYBr94rgM0B8BFA; unc=%E5%BC%A0%E5%B8%853385; ci=42; rvct=42%2C1; __mta=150830345.1621319290729.1624004462194.1624005982827.12; firstTime=1624005986229; _lxsdk_s=17a1e345f2c-0f5-d52-d02%7C%7C49";
        String cookie1 = "";
        JSONArray poiInfos = getRequest(restTemplate, cookie);
        int length = poiInfos.size();
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < length; i++) {
            JSONObject item = poiInfos.getJSONObject(i);
            String title = item.getString("title");
            String poiId = item.getString("poiId");
            String phone = getPhone(restTemplate, poiId, cookie);
            map.put(title, phone);
            System.out.println("title:" + title + ",prone:" + phone);
        }

        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "===" + entry.getValue());
        }

    }


    private static JSONArray getRequest(RestTemplate restTemplate, String cookie) {

        // 这个很容易过期，大概十几分钟就用不了了，需要换url的token和headers的cookie
        // https://xa.meituan.com/meishi/
        String url = "https://xa.meituan.com/meishi/api/poi/getPoiList?cityName=西安&cateId=0&areaId=0&sort=&dinnerCountAttrId=&page=1&userId=947470355&uuid=d591636c37f44319ba4f.1623927718.1.0.0&platform=1&partner=126&originUrl=https%3A%2F%2Fxa.meituan.com%2Fmeishi%2F&riskLevel=1&optimusCode=10&_token=eJx1kEtvozAUhf%2BLt4NiO4DzkLqA6bglDSkJEAqjLng4hRAIYCdARvPfx1U7m0qVLJ3jz0dHvvcP6KwMLDFCC4QUcGUdWAI8QRMCFCC4fCFTDSGCkKpjGUi%2FMH2ugKTb34Plb0zmSFEX6PWd7CT4IBjpEn34hTZ7VaaaPO8hS2ZALkTDlxAO8aRihbjE9SQ9V1B6nhdQfuKbAJANlScbpJafGn%2Bq%2BH%2B35TCyghdvtXRs1Z%2BOPr70o7HN2Q%2FX9LltNhfy68zbFjW77FYaG7YpqWEH95TOclMrp7jPG1L7oe62LTw05KDyFyNRSYvf%2BOhoJvGczXAwoOiZCtOrfqQPoyN8a4a2ehTlsWvbgo2eOXLqDifnwVroImBF5w2qGh6ORvSzLdTh%2BRyI9CaK4Rbh%2BOxX1aY6nZzZSmSJWz6u2ozv%2B%2BfUCnDqV9H6iVN41aok2e7iPA87uZbHMdTbmgaRoPHI6oyEL2u%2Bz7xEG%2BZaY63XdQytJ7pKjbs78Pcf7fqaxA%3D%3D";
        HttpHeaders headers = new HttpHeaders();
        headers.set("user-agent",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36");
        headers.set("Cookie", cookie);
        headers.set("Accept", "application/json");
        headers.set("Referer", "https://xa.meituan.com/meishi/");
        headers.set("sec-ch-ua", " Not;A Brand\";v=\"99\", \"Google Chrome\";v=\"91\", \"Chromium\";v=\"91");
        headers.set("sec-ch-ua-mobile", "?0");
        headers.set("Sec-Fetch-Dest", "empty");
        headers.set("Sec-Fetch-Mode", "cors");
        headers.set("Sec-Fetch-Site", "same-origin");
        HttpEntity<String> forEntity = restTemplate
                .exchange(url, HttpMethod.GET, new HttpEntity<>(null, headers),
                        String.class);

        if (forEntity != null && forEntity.getBody() != null) {
            String body = forEntity.getBody();
            JSONObject jsonObject = JSONObject.parseObject(body);
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray poiInfos = data.getJSONArray("poiInfos");
            return poiInfos;
        }
        return null;
    }


    private static String getPhone(RestTemplate restTemplate, String poiId, String cookie) {


        String url = "https://www.meituan.com/meishi/" + poiId + "/";
        HttpHeaders headers = new HttpHeaders();
        headers.set("user-agent",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36");
        headers.set("Cookie", cookie);
        HttpEntity<String> forEntity = restTemplate
                .exchange(url, HttpMethod.GET, new HttpEntity<>(null, headers),
                        String.class);

        if (forEntity != null && forEntity.getBody() != null) {
            String body = forEntity.getBody();
            String body2 = body.split("\"phone\":\"")[1];
            String phone = body2.split("\"")[0];

            return phone;
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
}
