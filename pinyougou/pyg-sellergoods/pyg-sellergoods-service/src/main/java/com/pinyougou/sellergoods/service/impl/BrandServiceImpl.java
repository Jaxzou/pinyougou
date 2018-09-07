package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.BrandMapper;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
import com.pinyougou.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Jax.zou
 * @create 2018-09-07 9:22
 * @desc 品牌业务实现类
 **/
@Service
public class BrandServiceImpl extends BaseServiceImpl<TbBrand> implements BrandService{

    //注入mapper
    @Autowired
    private BrandMapper brandMapper;

    @Override
    public List<TbBrand> queryAll() {
        return brandMapper.queryAll();
    }

    @Override
    public List<TbBrand> testPage(Integer page, Integer rows) {
        //设置分页查询
        PageHelper.startPage(page,rows);
        return brandMapper.selectAll();
    }
}
