package com.pinyougou.mapper;

import com.pinyougou.pojo.TbBrand;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
/**
 * @author Jax.zou
 * @create 2018-09-07 9:17
 * @desc 品牌mapper
 **/
public interface BrandMapper extends Mapper<TbBrand> {

    public List<TbBrand> queryAll();
}
