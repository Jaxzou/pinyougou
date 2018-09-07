package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbBrand;

import java.util.List;

/**
 * @author Jax.zou
 * @create 2018-09-07 9:21
 * @desc 品牌业务接口
 **/
public interface BrandService {

    /**
     * 查询品牌列表方法
     */
    public List<TbBrand> queryAll();
}
