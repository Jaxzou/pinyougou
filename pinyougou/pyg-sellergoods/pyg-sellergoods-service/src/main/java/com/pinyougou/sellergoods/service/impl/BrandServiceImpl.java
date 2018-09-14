package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.mapper.BrandMapper;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
import com.pinyougou.service.impl.BaseServiceImpl;
import com.pinyougou.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;
import java.util.Map;

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

    @Override
    public PageResult search(TbBrand tbBrand, Integer page, Integer rows) {

        //设置分页数据
        PageHelper.startPage(page,rows);

        //创建查询对象
        Example example = new Example(TbBrand.class);

        Example.Criteria criteria = example.createCriteria();
        if(!StringUtil.isEmpty(tbBrand.getName())){
            criteria.andLike("name","%"+tbBrand.getName()+"%");
        }
        if(!StringUtil.isEmpty(tbBrand.getFirstChar())){
            criteria.andEqualTo("firstChar",tbBrand.getFirstChar().toUpperCase());
        }
        //查询
        List<TbBrand> list = brandMapper.selectByExample(example);

        PageInfo<TbBrand> pageInfo = new PageInfo<>(list);

        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
    }

    @Override
    public List<Map<String, Object>> selectOptionList() {
        return brandMapper.selectOptionList();
    }
}
