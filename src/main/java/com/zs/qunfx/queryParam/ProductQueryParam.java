package com.zs.qunfx.queryParam;

import lombok.Data;

import java.util.Date;

@Data
public class ProductQueryParam {
    private Long id;
    private String name;
    private String describe;
    private String url;
    private String ownerWx;
    private Date createTime;
    private Date updateTime;
    private Integer weight;
}
