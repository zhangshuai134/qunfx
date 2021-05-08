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
import org.springframework.boot.SpringApplication;
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
public class BeanConfig {

    public static void main(String[] args) {
        System.out.println(123);
        RestTemplate restTemplate = new BeanConfig().restTemplate();

        List<String> userInfoList = new ArrayList<>();

        String aa = "MS4wLjABAAAAVxVleLXHBCQNiZxB3t9ZfiV4rUyoWWlPJR0CAtZxZ1Y&&&cz9ZpwAAE6qrGzLVRY2veHM.Wb\n" +
                "MS4wLjABAAAAY8zeQzigoI2ytRlztDqMxEuSOs6JgHHE_kBK8nWh6Fw&&&a97M4QAAC0mz-qeTpoAmlGvezP&dytk\n" +
                "MS4wLjABAAAAHECdEYjxRWIcVMQcghyixlg6XzSV2sKmesi4wkzLLMY&&&bKkvxwAADEC0jUS1WhomAmypL9\n" +
                "MS4wLjABAAAAW7N5THwmVCPHFddlmrGqlJHYhOSpMPmKWPd0-xuz29M&&&bHOoTgAADOa0V8M8oMIHeGxzqF\n" +
                "MS4wLjABAAAAfpVbB84M_p48-8fpc2rdsiX7uoeSn0ctBM55D18M-dA0&&&bTVHfQAADaO1ESwPH7mWY201R2\n" +
                "MS4wLjABAAAAtX-fvvg_NidwyoE-8TSOoR4QpTiQA8zM-lXn0l37OZw&&&bpHwzQAADgi2tZu.B25g126R8N\n" +
                "MS4wLjABAAAAvjZgoQ1b3eOk44Z80XsEULut0CjqmUb0ENYLCXmJhZ0&&&bv37uAAADmy22ZDK-GFgzW79-6\n" +
                "MS4wLjABAAAAthMoavB8H6Ptz8OeMn1_TPUP1iNvA9ke4gCA6CQpTGg&&&bkl6IwAADt-2bRFROU.5gW5Jej\n" +
                "MS4wLjABAAAAzoeisgplIS2cYkeJHMVF_5C7GcONoSqwxRTeEZZ_ooU&&&b9yBugAAD0u3-OrIYcUwFW.cga\n" +
                "MS4wLjABAAAAvWPEEt6endGHGYTZE7RQh0KccRnKcDTtPunTh8BpLco&&&b1eKtgAAD8K3c-HEQmZox29Xiq\n" +
                "MS4wLjABAAAAz5amdpNGRTC2LHBwJDsydfgga592JpHDmFugRjXLO_k&&&cIQlMgAAEBOooE5AXqwJAXCEJS\n" +
                "MS4wLjABAAAAj3EDm0qKlaLkIDo0kC_4hTbFTwX41kj9Tp8ZVgCsGZM&&&cd8D2QAAEUqp-2irmr7dPXHfA8\n" +
                "MS4wLjABAAAAWlNg0zCsf8gnM8qpvBR2NDQoZ6zQiRtS9Xu5KxeIxC8&&&cQ74oAAAEZmpKpPSPctf33EO-L\n" +
                "MS4wLjABAAAAXDP-l8rGAqwm7QXwFTQmaK8gGXy0OTSLxN8Ub1w-MTU&&&cUFW-wAAEdipZT2JYyEXgHFBVu\n" +
                "MS4wLjABAAAAEv4TNABjcEKiUjcBQe4IHKqEDl9pCcVF3FGkQN2h-NM&&&conuIwAAEiCqrYVRYqn6cnKJ7j\n" +
                "MS4wLjABAAAARj2dYv7qQQzWMHTOp7t33mIicl2Lg9ZRPNwQQiTSO8I&&&cgZlYwAAEpGqIg4RxE4p33IGZX\n" +
                "MS4wLjABAAAAB-CMpII7clMiYFxy28b1OT9AzYUTGzNffhXfXGlLgiE&&&cnWQEgAAEuWqUftgiuSY.HJ1kA\n" +
                "MS4wLjABAAAADw-sInLJ3d4jvvSj7o8dEI9jBmHSwT6WHfmks9N1pfg&&&c6qOZgAAEz6rjuUUbbPC7nOqjn\n" +
                "MS4wLjABAAAAa80JFpY_Cx0mWPCMqMwmcXzDB0YR2OaeVdgvCUr-2JY&&&c.VcuQAAE2Sr0TfLqMB2-3P1XK\n" +
                "MS4wLjABAAAAgBbv4hKY7AfU3JKJhebGEhdgXGXSOFUUtABWMe2U7v0&&&akzZ.wAACtuClvuCI28Q-2pM2e\n" +
                "MS4wLjABAAAAl7Bz2AFldkqdqxBvuc99dwFH4KT524qbVooLxSuba5Q&&&a.mp-AAAC3CDI4uFAQkJDmv5qe\n" +
                "MS4wLjABAAAANdcU-96jmVq0oSMU9sGF8Z6i9os14HjzqI6gu_EyAWM&&&bOPd-gAADHaEOf-HoDu0Fmzj3e\n" +
                "MS4wLjABAAAAR1z949dHZ3kVEpZgCsxUoBjNp27oegKkZ4jFqqvPARk&&&ciXi0QAAErSa.8CsxlEcM3Il4s\n" +
                "MS4wLjABAAAANwrhBmszAFVUr0aqhCAjH66G8P55hYusjx7HqRgbBOs&&&c47b1QAAExmbVPmoJ.nHb3OO28\n" +
                "MS4wLjABAAAAWGz4-ZeG-CpN_QyVGkQqgvC94dUngckTi-YWiYn8z2M&&&cxYROgAAE4GbzDNHsloIUXMWES\n" +
                "MS4wLjABAAAAFCQpN6EljXqiROzifW_HPq7seaNkMidNiXyC4la7k9hc7OGcTGiaQiMyrgs-DFGw&&&c13KEgAAE8ybh-hvUrfWgHNdyg\n" +
                "MS4wLjABAAAAtT_8ZZ000CbyROK1mYgTx_BdIcaspDysAD6kzY_q8bk&&&dLmGhAAAFDGcY6T5UG3liXS5hp\n" +
                "MS4wLjABAAAATzeYmmTfbhnJsDe2E7a2eAhiRpzOm1p8pdPgAlBsxm_yFvCg-mMUGQkxv-vNhnvw&&&dAej.AAAFJKc3YGBI82p9HQHo-\n" +
                "MS4wLjABAAAAUX5vHEjdmyLb3-Nx8TsQHCozdYVDJyFGUKTHAbt-M3fw4dK7EgSWsUeVxE65oc7g&&&dHBObwAAFOecqmwS3AtymHRwTn\n" +
                "MS4wLjABAAAAlo1n1Mv6scXYaR7iojeBwgnlfX0ZqIIuyr60gfQ_8eE&&&daPp8QAAFTadecuMr2Co-3Wj6e\n" +
                "MS4wLjABAAAAilqLI9OA5nLKxVDG_Ay5Fw8d6AywNNF-zK_f-kbxVaM&&&deu0qgAAFX-dMZbXW3vDfXXrtL\n" +
                "MS4wLjABAAAAMMgJu7nzhRm78av2UabuuBD4CP7FRLZH3nVehqZSUB7-hLELHBPFOKnrfzF2aHmK&&&dUv2OgAAFd6dkdRHfmlwBXVL9i\n" +
                "MS4wLjABAAAAmwNZMdS4SC6BirTpLlobzql5OrGgpEsY4gBR4-37uj8&&&dosjrQAAFh6eUQHQaBZ7k3aLI7\n" +
                "MS4wLjABAAAAmNMLiyKB_DuysG9skZhOL9zFYyqNpx6apg5V7wW7fXo&&&dhVLLQAAFoSez2lQT6RW13YVSz\n" +
                "MS4wLjABAAAAt_csJZq2cWuQeXX9DBoFyaAEgu6fwZpRpbEhWay1qhkvPBhdM_idB-f0xUtyHrRk&&&a.nzFQAAC3AYD.l62AyrDGv58w&dytk=\n" +
                "MS4wLjABAAAA8dEtKU6Pb7cytk64dICenU6t4FsSNRtLdFsfuq3I8vjvDPRpjlqgDvZB25pCwGLX&&&bObEKgAADHEfEM5FLay.zmzmxD&dytk=\n" +
                "MS4wLjABAAAA15GEhRsF8b5KZh-BFAScNNSZOOqI1A24WPUPH5wpdXc&&&bfoxnwAADW0eDDvwaWMCCG36MY&dytk=\n" +
                "MS4wLjABAAAA1jwx6pROeG8NYI9I6sVLVHYFFcJvoW1x_944Ue4LHh-i0U5-OPobd5_v5BOOmLwQ&&&bo5B6AAADhgdeEuHLTdJBG6OQf&dytk=\n" +
                "MS4wLjABAAAAKnbP2pGuub0tHzCcJkFLNPL_qzQAnbj2Z7_YUlJEars&&&b93i6wAAD0wcK-iEuJYJPm.d4v&dytk=\n" +
                "MS4wLjABAAAAIOFHgvhrfdsIC8JnuLPDZ5t68gNGi7BylnjxqrfIVFc&&&byGLlAAAD7gc14H7ucoOeW8hi4&dytk=\n" +
                "MS4wLjABAAAABznWn0lgdXqLNPi-69qOajIKS07RLzSOEMKJS6Wv7NY&&&cK4TRAAAEDkDWBkrnFEYyXCuE1&dytk=\n" +
                "MS4wLjABAAAAk25Or-dFPydIViKvDSbZSHvIK8lWeS5qXwDXcuNpwN4&&&cOKdbAAAEHUDFJcDJSq1PHDinX&dytk=\n" +
                "MS4wLjABAAAAX5jlHLMbg47BxR1NHQDlfN6SwONyHaFv9ySFJz6yaSY&&&cEA08gAAENcDtj6dfVGw6XBANO&dytk=\n" +
                "MS4wLjABAAAAxkaH9WjY71BdUbuCHKfIKttDbiVsYWKTX_03r2wUdD4&&&cfZDUwAAEWECAEk8hpHjfHH2Q0&dytk=\n" +
                "MS4wLjABAAAA2itQGueaPE_V6iQv69Y1oSf42KrEYyTwME9eyNsIz0551BrH--HLj8acjNJx-Wre&&&cTRvcAAAEaMCwmUfOI6JSXE0b2&dytk=\n" +
                "MS4wLjABAAAAD7Q3kozzVEEzJQeSF0-ubtMkT_2eeEcqBWBSHMtKkZE&&&cWcKZAAAEfICkQALrQaO63FnCn&dytk=\n" +
                "MS4wLjABAAAAzIh3IW2veUJOHiMFnGOZ9G3SLUPBD9jp0AyrK8hXY7o&&&cuDHHAAAEncBFs1zrSb2vXLgxw&dytk=\n" +
                "MS4wLjABAAAAmeDszUIyQUVsK2AWVKkZDA3biQVo_1vx-3ykSowtke6eY-cZyzu_J7q8QodpPKTM&&&cmHZKQAAEvgBl9NGkEO1XXJh2T&dytk=\n" +
                "MS4wLjABAAAAIIwnWDuys_1dpODJKco-RB_JsXPkszYHJTzMkFw_nJs&&&c9HvQgAAE0gAJ-UtF.XpinPR71&dytk=\n" +
                "MS4wLjABAAAAO_YrSGwXrV7KIVmFmqVUKKdE3tHOdP1pzVJwAn2LtgM&&&cwuvDAAAE54A.aVjHFJtTXMLrx&dytk=\n" +
                "MS4wLjABAAAAxfWrw-kqMXab5a5_i0lLnxjvh1qfnGngLIOoLy37S7A&&&dJVWUwAAFAQHY1w8mtEWJHSVVk&dytk=\n" +
                "MS4wLjABAAAAfr2adfvksgDvXKhvdyKAcnvSJV7lKOO3gzhWTAKxAnY&&&dEK0swAAFNYHtL7cDpxFinRCtK&dytk=\n" +
                "MS4wLjABAAAAYfPcjerswt-QyjIr445FVEoemNObFogeX0CnHCStyL8&&&db6rIwAAFSoGSKFMvH6sZ3W-qz&dytk=\n" +
                "MS4wLjABAAAAToPcM4hZm3gcX4xj2rjN2flE7iFbbXPsyHNNXSTyV38&&&de-TBwAAFXsGGZloEimC33Xvkx&dytk=\n" +
                "MS4wLjABAAAAnxvlCr3qJ9NT7MPTgQtL_1hIWRQwlWVD9sgEEZixwKsVnswSHswkrmwFoil3Pdib&&&dUbd6AAAFdEGsNeHsGRzHHVG3f&dytk=";
        String bb = "MS4wLjABAAAAghrAeVQtYQcS0Wx-YU_PZ3Nhe8JDmXN6S36yt422dzU&&&RvkrLwAAJnGuIwlSxpC8skb5Kz\n" +
                "MS4wLjABAAAAMqbN_MXj3JERdZVOTZBTVJzLelK8bc6JOyOjx02cUSCqc72BnVfKOUC4Mgu9jQl5&&&RnQ45wAAJuSurhqaQj.VWUZ0OP\n" +
                "MS4wLjABAAAAaeQFq_-whfXnr4yV500x46p03DB0Ri-pGNdjSjcLoAGHu2BD6_h-YPI76hgaP0Vh&&&R8-5IwAAJ1yvFZteBTK3-EfPuT\n" +
                "MS4wLjABAAAAXcpsMT6dgxpYbyMkq2KutazYImyU8RkvdKFfm4cyB9A&&&R0FDhwAAJ9mvm2H6OtMrTEdBQ5\n" +
                "MS4wLjABAAAAKu_gZbxlRJK_ZFtet5i4vEJxt0gOZAIH-2p8KG66QBepWkuzlAY_W-ZgduQCePPH&&&SCdJHwAAKLOg.Wti8UxpCUgnSQ\n" +
                "MS4wLjABAAAABKlZ3vVsZv7xda84Vrx8obypXO5bWFh5ejjodDHOk8jg1iAbOlRNZwo2TbxKSzAQ&&&SYvYwwAAKSChUfq-zx.naUmL2N\n" +
                "MS4wLjABAAAAyEhd1DdmHHaPwOMv0J20kl4JDqw0j3FS1BG9Tq9zFMo&&&SStzxwAAKcCh8VG62ohl1Ekrc9\n" +
                "MS4wLjABAAAALz7IbjCha1dcs9eMv-ZBtckXN6012A_Ye3LXxjQ0obbsjD-9sh4xMG8yyfLQLHGC&&&SqQYQwAAKjWifjo-vNUzV0qkGF\n" +
                "MS4wLjABAAAA6PANPNAddzDQVVm_n9VMlHRbjvzYg93p0_o1eRnUNqQ&&&SjS8hAAAKqSi7p75T6Rasko0vJ\n" +
                "MS4wLjABAAAAjDgp1KsutcHlZOhlRSkUEXckGDayxotUVtoaw8yMyY8&&&S4wSkQAAKx2jVjDsdAyxnkuMEo\n" +
                "MS4wLjABAAAAuhKPI8fJtM8hAF5nga5urCKbPGmYiQpSh-OSYEhZBsg&&&SzwbxAAAK62j5jm5XJkhsUs8G9\n" +
                "MS4wLjABAAAA_C27BSgJb6N6zQLPWDrXY4BEnJd7n_zD_eFhgssdCrY&&&S33DQgAAK-2jp-E.YBRc2Et9w1\n" +
                "MS4wLjABAAAAO1ITGl0Nefwls7VDAO5hYjr_K8z7lYDl6M0lSwidHFs&&&S0sYFgAAK-CTb3NkTmBoeUtLGA\n" +
                "MS4wLjABAAAA3H92lBsSlLZuYKnRttrzlD_VE2L_7qT9hdhWyUG1gBs&&&Sr0c2QAAKi6SmXerh-BbkUq9HM\n" +
                "MS4wLjABAAAAxdTWztFKcFuUvSo3OGl7HfZEB9DTiFUAcQCiriGs3GM&&&STVmyQAAKaaREQ27H3t5fEk1Zt\n" +
                "MS4wLjABAAAAoTUAJqXPaWOINQstIOYblh0z8UL_GTUxdUVzQo61uKU&&&R4ww2QAAJxyfqFurUAivq0eMMM\n" +
                "MS4wLjABAAAA7WSruoPjCK8jpVFepXKqJ7-56EVGOxi8Z__c51NB5ZhduH7qel43mQHflJmg6_6b&&&R-oCBAAAJ36fzml2lj1P5EfqAh\n" +
                "MS4wLjABAAAAO9SlBMuJV2QtC6fu6DzUJ_zgToqUaPdnu_-SLgQ0_Fk&&&R0pXTAAAJ96fbjw-S12tR0dKV1\n" +
                "MS4wLjABAAAAidXqZBwlK9rPZguAf_b2G1IGyz4w1tGOTUT3mO7Chig&&&SMw2xgAAKFyQ6F20usEeo0jMNt";
        String[] split1 = bb.split("\n");
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

class MapValueComparator implements Comparator<Map.Entry<String, Integer>> {

    @Override
    public int compare(Map.Entry<String, Integer> me1, Map.Entry<String, Integer> me2) {

        return me1.getValue().compareTo(me2.getValue());
    }
}
