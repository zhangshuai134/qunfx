package com.zs.qunfx.controller;

import com.zs.qunfx.dto.BaseResponseDTO;
import com.zs.qunfx.entity.Product;
import com.zs.qunfx.mapper.ProductMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class TestController {

    @Resource
    private ProductMapper productMapper;

    @RequestMapping("/test")
    public String test(){
        System.out.println(123456);
        return "hello";
    }
    @RequestMapping("/test2")
    public BaseResponseDTO<Product> testSql(){
        List<Product> res = productMapper.selectAll();

        return BaseResponseDTO.buildSuccess(res);
    }
}
