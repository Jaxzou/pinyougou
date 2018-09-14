package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbTypeTemplate;
import com.pinyougou.service.BaseService;
import com.pinyougou.vo.PageResult;

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
}
