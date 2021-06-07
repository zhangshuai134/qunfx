package com.zs.dy;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.models.auth.In;
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
import java.math.BigDecimal;
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

//        List<String> addrList = getArrayList("/Users/zhangshuai/Desktop/精益创新/第十一轮/带人均收入的源数据（第二版全）.txt");
        List<String> addrList = getArrayList("/Users/zhangshuai/Desktop/精益创新/第十一轮/原始数据（6月4号第三版）.txt");

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
//            addrName = addrName.split("===")[0] + "===";
            Integer integer = map.get(addrName);
            if (integer == null) {
                map.put(addrName, 1);
            } else {
                map.put(addrName, integer + 1);
            }
        }

//        HashMap<String, Integer> mapNew = new HashMap<>();
//        for (String s : addrList2) {
//            String[] split = s.split("&&&");
//            String addrName = split[0];
//            addrName = addrName.split("===")[0] + "===";
//            Integer integer = mapNew.get(addrName);
//            if (integer == null) {
//                mapNew.put(addrName, 1);
//            } else {
//                mapNew.put(addrName, integer + 1);
//            }
//        }

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

        ArrayList<Map.Entry<String, Integer>> num非店铺 = new ArrayList<>();
        ArrayList<Map.Entry<String, Integer>> num店铺 = new ArrayList<>();
        ArrayList<Map.Entry<String, Integer>> num连锁店 = new ArrayList<>();

        Map<String, Integer> map1 = new HashMap<>();

        for (Map.Entry<String, Integer> entry : resultMap.entrySet()) {
            System.out.println(entry.getValue() + " " + entry.getKey());


            if (entry.getKey().contains("市===")
                    || entry.getKey().contains("街===")
                    || entry.getKey().contains("区===")
                    || entry.getKey().contains("城===")
                    || entry.getKey().contains("村===")
                    || entry.getKey().contains("路===")
                    || entry.getKey().contains("口)===")
                    || entry.getKey().contains("口===")
                    || entry.getKey().contains("站===")
                    || entry.getKey().contains("小学===")
                    || entry.getKey().contains("赛格===")
                    || entry.getKey().contains("雁塔===")
                    || entry.getKey().contains("中心===")
                    || entry.getKey().contains("广场===")
                    || entry.getKey().contains("科蜜之家===")
                    || entry.getKey().contains("钟楼===")
                    || entry.getKey().contains("洒金桥===")
                    || entry.getKey().contains("MOMO PARK===")
                    || entry.getKey().contains("(5A)===")
                    || entry.getKey().contains("大学===")
                    || entry.getKey().contains("学院===")
                    || entry.getKey().contains("太平里===")
                    || entry.getKey().contains("中大国际===")
                    || entry.getKey().contains("西安大都荟===")
                    || entry.getKey().contains("西安SKP===")
                    || entry.getKey().contains("永兴坊===")
                    || entry.getKey().contains("曲江创意谷===")
                    || entry.getKey().contains("蹦床===")
                    || entry.getKey().contains("新天地===")
                    || entry.getKey().contains("小寨===")
                    || entry.getKey().contains("酒店===")
                    || entry.getKey().contains("酒馆===")
                    || entry.getKey().contains("名宿===")
                    || entry.getKey().contains("西安鼓楼===")
                    || entry.getKey().contains("民乐园===")
                    || entry.getKey().contains("曲江池===")
                    || entry.getKey().contains("地铁")
                    || entry.getKey().contains("州===")) {
                num非店铺.add(entry);
            } else {
                map1.put(entry.getKey(), entry.getValue());
                num店铺.add(entry);
            }

            if (entry.getKey().endsWith("店)")) {
                num连锁店.add(entry);
            }

        }

        System.out.println(123);
        Map<String, Integer> map11 = sortMapByValue(map1); //按Value进行排序

        Integer sum2 = 0;
        ArrayList<String> 加盟店list = new ArrayList<>();
        ArrayList<String> 加盟总店list = new ArrayList<>();
        ArrayList<String> 探店1次加盟店list = new ArrayList<>();
        ArrayList<String> 探店2次加盟店list = new ArrayList<>();
        ArrayList<String> 探店3次加盟店list = new ArrayList<>();
        ArrayList<String> 探店4次加盟店list = new ArrayList<>();
        ArrayList<String> 探店5次加盟店list = new ArrayList<>();
        ArrayList<String> 探店6次加盟店list = new ArrayList<>();
        ArrayList<String> 探店7次加盟店list = new ArrayList<>();
        ArrayList<String> 探店8次加盟店list = new ArrayList<>();
        ArrayList<String> 探店9次加盟店list = new ArrayList<>();
        ArrayList<String> 探店10次加盟店list = new ArrayList<>();
        ArrayList<String> 探店11次加盟店list = new ArrayList<>();
        ArrayList<String> 探店12次加盟店list = new ArrayList<>();
        ArrayList<String> 探店13次加盟店list = new ArrayList<>();
        ArrayList<String> 探店14次加盟店list = new ArrayList<>();
        ArrayList<String> 探店15次以上加盟店list = new ArrayList<>();
        ArrayList<String> 探店1次list = new ArrayList<>();
        ArrayList<String> 探店2次list = new ArrayList<>();
        ArrayList<String> 探店3次list = new ArrayList<>();
        ArrayList<String> 探店4次list = new ArrayList<>();
        ArrayList<String> 探店5次list = new ArrayList<>();
        ArrayList<String> 探店6次list = new ArrayList<>();
        ArrayList<String> 探店7次list = new ArrayList<>();
        ArrayList<String> 探店8次list = new ArrayList<>();
        ArrayList<String> 探店9次list = new ArrayList<>();
        ArrayList<String> 探店10次list = new ArrayList<>();
        ArrayList<String> 探店11次list = new ArrayList<>();
        ArrayList<String> 探店12次list = new ArrayList<>();
        ArrayList<String> 探店13次list = new ArrayList<>();
        ArrayList<String> 探店14次list = new ArrayList<>();
        ArrayList<String> 探店15次以上list = new ArrayList<>();

        HashMap<String, Integer> 总店map = new HashMap<>();

        HashSet<String> 品牌set = new HashSet<>();
        HashMap<String, Integer> 每个品牌的店数量map = new HashMap<>();

        Integer 连锁店探店次数 = 0;
        Integer 总探店次数 = 0;
        for (Map.Entry<String, Integer> entry : map11.entrySet()) {
//            Integer integer = mapNew.get(entry.getKey());
//            integer = integer == null ? 0 : integer;
//            System.out.print(integer + ":");
            System.out.println(entry.getValue() + " " + entry.getKey());
            String key = entry.getKey();
            Double price = Double.valueOf(key.split("===")[1]);
            String name = key.split("===")[0];
            总探店次数 += entry.getValue();
            if (name.endsWith("店)")) {
                连锁店探店次数 += entry.getValue();
                加盟店list.add(name);
                String 品牌name = name.split("\\(")[0];
                品牌set.add(品牌name);
                if (每个品牌的店数量map.get(品牌name) == null) {
                    每个品牌的店数量map.put(name.split("\\(")[0], 1);
                }else{
                    Integer num = 每个品牌的店数量map.get(品牌name);
                    每个品牌的店数量map.put(name.split("\\(")[0], num + 1);
                }
            }
            if (name.endsWith("总店)")) {
                加盟总店list.add(name);
                总店map.put(entry.getKey(), entry.getValue());
            }
            if (entry.getValue() == 1 && name.endsWith("总店)")) {
                探店1次加盟店list.add(name);
            }
            if (entry.getValue() == 2 && name.endsWith("总店)")) {
                探店2次加盟店list.add(name);
            }
            if (entry.getValue() == 3 && name.endsWith("总店)")) {
                探店3次加盟店list.add(name);
            }
            if (entry.getValue() == 4 && name.endsWith("总店)")) {
                探店4次加盟店list.add(name);
            }
            if (entry.getValue() == 5 && name.endsWith("总店)")) {
                探店5次加盟店list.add(name);
            }
            if (entry.getValue() == 6 && name.endsWith("总店)")) {
                探店6次加盟店list.add(name);
            }
            if (entry.getValue() == 7 && name.endsWith("总店)")) {
                探店7次加盟店list.add(name);
            }
            if (entry.getValue() == 8 && name.endsWith("总店)")) {
                探店8次加盟店list.add(name);
            }
            if (entry.getValue() == 9 && name.endsWith("总店)")) {
                探店9次加盟店list.add(name);
            }
            if (entry.getValue() == 10 && name.endsWith("总店)")) {
                探店10次加盟店list.add(name);
            }
            if (entry.getValue() == 11 && name.endsWith("总店)")) {
                探店11次加盟店list.add(name);
            }
            if (entry.getValue() == 12 && name.endsWith("总店)")) {
                探店12次加盟店list.add(name);
            }
            if (entry.getValue() == 13 && name.endsWith("总店)")) {
                探店13次加盟店list.add(name);
            }
            if (entry.getValue() == 14 && name.endsWith("总店)")) {
                探店14次加盟店list.add(name);
            }
            if (entry.getValue() >= 15 && name.endsWith("总店)")) {
                探店15次以上加盟店list.add(name);
            }
            if (entry.getValue() == 1) {
                探店1次list.add(name);
            }
            if (entry.getValue() == 2) {
                探店2次list.add(name);
            }
            if (entry.getValue() == 3) {
                探店3次list.add(name);
            }
            if (entry.getValue() == 4) {
                探店4次list.add(name);
            }
            if (entry.getValue() == 5) {
                探店5次list.add(name);
            }
            if (entry.getValue() == 6) {
                探店6次list.add(name);
            }
            if (entry.getValue() == 7) {
                探店7次list.add(name);
            }
            if (entry.getValue() == 8) {
                探店8次list.add(name);
            }
            if (entry.getValue() == 9) {
                探店9次list.add(name);
            }
            if (entry.getValue() == 10) {
                探店10次list.add(name);
            }
            if (entry.getValue() == 11) {
                探店11次list.add(name);
            }
            if (entry.getValue() == 12) {
                探店12次list.add(name);
            }
            if (entry.getValue() == 13) {
                探店13次list.add(name);
            }
            if (entry.getValue() == 14) {
                探店14次list.add(name);
            }
            if (entry.getValue() >= 15) {
                探店15次以上list.add(name);
            }
        }
        System.out.println(123);

        for (String name : 品牌set) {
            System.out.println(name);
        }

        System.out.println(123);

        HashMap<String, Integer> 品牌的店数量大于1map = new HashMap<>();

        Map<String, Integer> 每个品牌的店数量map1 = sortMapByValue(每个品牌的店数量map);
        for (Map.Entry<String, Integer> entry : 每个品牌的店数量map1.entrySet()) {
            System.out.println(entry.getValue() + " " + entry.getKey());

            if(entry.getValue() > 1){
                品牌的店数量大于1map.put(entry.getKey(), entry.getValue());
            }
        }

        System.out.println(123);

        Map<String, Integer> 品牌的店数量大于1map1 = sortMapByValue(品牌的店数量大于1map);
        for (Map.Entry<String, Integer> entry : 品牌的店数量大于1map1.entrySet()) {
            System.out.println(entry.getValue() + " " + entry.getKey());

        }



        System.out.println(123);
        Map<String, Integer> 总店map1 = sortMapByValue(总店map);
        ArrayList<String> 店铺NameList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : 总店map1.entrySet()) {
            System.out.println(entry.getValue() + " " + entry.getKey());
            String 店name = entry.getKey().split("\\(")[0];
            店铺NameList.add(店name);
        }

        HashMap<String, Integer> 带总店的店铺map = new HashMap<>();
        for (String name : 店铺NameList) {
            for (Map.Entry<String, Integer> entry : map11.entrySet()) {
                if (entry.getKey().contains(name)) {
                    带总店的店铺map.put(entry.getKey(), entry.getValue());
                }
            }
        }

        Map<String, Integer> 带总店的店铺map1 = sortMapByValue(带总店的店铺map);

        System.out.println(123);
        for (Map.Entry<String, Integer> entry : 带总店的店铺map1.entrySet()) {
            System.out.println(entry.getValue() + " " + entry.getKey());
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

