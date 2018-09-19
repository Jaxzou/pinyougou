package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbTypeTemplate;
import com.pinyougou.service.BaseService;
import com.pinyougou.vo.PageResult;

import java.util.List;
import java.util.Map;

/**
 * @author Jax.zou
 * @create 2018-09-12 12:21
 * @desc 模板业务接口
 **/
public interface TypeTemplateService extends BaseService<TbTypeTemplate> {
    /**
     * 根据条件分页查询模板列表
     * @param tbTypeTemplate 条件对象
     * @param page 当前页
     * @param rows 页面大小
     * @return 分页数据
     */
    PageResult search(TbTypeTemplate tbTypeTemplate, Integer page, Integer rows);

    /**
     * 删除模板数据
     * @param ids 模板id数组
     * @return 操作结果
     */
    void delete(Long[] ids);

    /**
     * 根据分类模板id查询其对应的规格及其规格的选项
     * @param id 分类模板id
     * @return 规格及其规格的选项
     */
    List<Map> findSpecList(Long id);
}
