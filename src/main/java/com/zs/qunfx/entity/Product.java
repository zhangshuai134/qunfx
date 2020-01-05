package com.zs.qunfx.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Product {
    private Long id;
    private String name;
    private String describe;
    private String url;
    private String ownerWx;
    private Date createTime;
    private Date updateTime;
    private Integer weight;
}
