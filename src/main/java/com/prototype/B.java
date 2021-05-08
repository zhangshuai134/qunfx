package com.prototype;

import java.io.Serializable;

/**
 * @Author: zhangshuai
 * @Date: 2020-04-01 01:04
 * @Description:
 **/
public class B implements Serializable,Cloneable{
    private String id;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
