package com.zs.qunfx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zs.qunfx.entity.Product;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface ProductMapper extends BaseMapper<Product> {

    @Select("select * from product")
    List<Product> selectAll();

}
