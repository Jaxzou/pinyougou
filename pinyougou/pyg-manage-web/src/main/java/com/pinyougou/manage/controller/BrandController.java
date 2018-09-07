package com.pinyougou.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Jax.zou
 * @create 2018-09-07 9:31
 * @desc 品牌controller
 **/
@RequestMapping("brand")
@RestController
public class BrandController {

    //注入service
    @Reference
    private BrandService brandService;


    /**
     * 查询品牌数据
     * @return
     */
    @RequestMapping("findAll")
    public List<TbBrand> queryAll(){
        return brandService.queryAll();
    }
}
