package com.pinyougou.mapper;

import com.pinyougou.pojo.TbSpecification;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author Jax.zou
 * @create 2018-09-11 8:15
 * @desc 规格mapper
 **/
public interface SpecificationMapper extends Mapper<TbSpecification> {

    List<Map<String,Object>> selectOptionList();
}
