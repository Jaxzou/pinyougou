package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbBrand;
import com.pinyougou.service.BaseService;
import com.pinyougou.vo.PageResult;

import java.util.List;
import java.util.Map;

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

    /**
     * 根据条件分页查询品牌列表
     * @param tbBrand 条件对象
     * @param page 当前页
     * @param rows 页面大小
     * @return 分页数据
     */
    PageResult search(TbBrand tbBrand, Integer page, Integer rows);

    /**
     * 查询品牌列表数据，返回符合select2的数据
     * @return
     */
    List<Map<String,Object>> selectOptionList();
}
