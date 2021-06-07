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
public class BeanConfigdazhong {

    public static void main(String[] args) {
        System.out.println(123);
        RestTemplate restTemplate = new BeanConfig().restTemplate();

        String url = "https://www.amap.com/service/poiInfo?query_type=TQUERY&pagesize=20&pagenum=1&qii=true&cluster_state=5&need_utd=true&utd_sceneid=1000&div=PC1000&addr_poi_merge=true&is_classify=true&zoom=17.5&city=610100&geoobj=108.961457%7C34.267549%7C108.97056%7C34.269617&keywords=%E5%B0%8F%E5%A4%AB%E5%A6%BB%E5%85%A8%E7%BE%8A%E9%A6%86";
        HttpHeaders headers = new HttpHeaders();
        headers.set("user-agent",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36");
        headers.set("Cookie",
                "UM_distinctid=17954205ffa2b9-095ac37e171a71-113b6052-1aeaa0-17954205ffb19c; cna=FjhgFhyGSBsCAWok26+h/TuM; xlly_s=1; guid=1808-bd99-4496-d3f4; CNZZDATA1255626299=1640696376-1620633847-https%253A%252F%252Fwww.baidu.com%252F%7C1620633847; _uab_collina=162063412246088857272978; x5sec=7b22617365727665723b32223a2231656335353564623964656431633739643161363766366233393130313664364350765934345147454f62487a72612b3771724f2b41456f416a447675594159227d; tfstk=czBcBbTKuUgChbRlO-9fD62f7Z_Gar09-15lUOWPV17p_ufp0sYoaXkAZzYfoOh1.; l=eBQ7inXPjAOiKaZwBO5ZKurza77OXQdflsPzaNbMiInca1S5GFs1ONCCBzW6PdtjgtfhDFtyH1TtZR3wJIzNw7fZuHO1tTf5cYpw-; isg=BGZmwc4pOmlC7-7NG8BMJuSAt9roR6oB0dIJkVAMrAll0wztvNc_EWjlK8_f-6IZ");
        headers.set("amapuuid","87c6240a-e910-41ed-aa8a-6ba75e5b5576");
        headers.set("Upgrade-Insecure-Requests","1");
        headers.set("amapCache-Control","Cache-Control");
        HttpEntity<String> forEntity = restTemplate
                .exchange(url, HttpMethod.GET, new HttpEntity<>(null, headers),
                        String.class);

        if (forEntity != null && forEntity.getBody() != null) {
            String body = forEntity.getBody();
            JSONObject jsonObject = JSONObject.parseObject(body);
        }
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
