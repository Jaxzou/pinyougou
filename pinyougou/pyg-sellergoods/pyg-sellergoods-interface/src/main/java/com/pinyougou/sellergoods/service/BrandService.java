package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbBrand;
import com.pinyougou.service.BaseService;

import java.util.List;

/**
 * @author Jax.zou
 * @create 2018-09-07 9:21
 * @desc 品牌业务接口
 **/
public interface BrandService extends BaseService<TbBrand>{

    /**
     * 查询品牌列表方法
     */
    public List<TbBrand> queryAll();

    /**
     * 根据分页数据查询列表
     * @param page 当前页
     * @param rows 页面大小
     * @return 返回列表
     */
    List<TbBrand> testPage(Integer page, Integer rows);
}
