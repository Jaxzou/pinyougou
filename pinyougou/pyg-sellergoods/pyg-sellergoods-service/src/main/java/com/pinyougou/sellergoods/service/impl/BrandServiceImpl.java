package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.mapper.BrandMapper;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Jax.zou
 * @create 2018-09-07 9:22
 * @desc 品牌业务实现类
 **/
@Service
public class BrandServiceImpl implements BrandService{

    //注入mapper
    @Autowired
    private BrandMapper brandMapper;

    /**
     * 查询品牌列表信息
     * @return 品牌列表数据
     */
    @Override
    public List<TbBrand> queryAll() {
        return brandMapper.queryAll();
    }
}
