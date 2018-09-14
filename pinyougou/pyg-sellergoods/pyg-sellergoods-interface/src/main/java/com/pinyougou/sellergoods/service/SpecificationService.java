package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.service.BaseService;
import com.pinyougou.vo.PageResult;
import com.pinyougou.vo.Specification;

import java.util.List;
import java.util.Map;

/**
 * @author Jax.zou
 * @create 2018-09-11 8:17
 * @desc 规格业务接口
 **/
public interface SpecificationService extends BaseService<TbSpecification> {

    /**
     * 根据条件分页查询规格列表
     * @param tbSpecification 条件对象
     * @param page 当前页
     * @param rows 页面大小
     * @return 分页数据
     */
    PageResult search(TbSpecification tbSpecification, Integer page, Integer rows);

    /**
     * 新增规格及规格选项数据
     * @param specification 规格及规格选项数据
     * @return 操作结果
     */
    void add(Specification specification);

    /**
     * 根据id查询规格信息
     * @param id 规格id
     * @return 规格
     */
    Specification findOne(Long id);

    /**
     * 更新规格及规格选项数据
     * @param specification 规格及规格选项数据
     * @return 操作结果
     */
    void update(Specification specification);

    /**
     * 删除规格及规格选项数据
     * @param ids 规格id数组
     * @return 操作结果
     */
    void deleteSpecificationByIds(Long[] ids);

    /**
     * 查询品牌列表，返回的数据格式符合select2的格式
     * @return
     */
    List<Map<String, Object>> selectOptionList();
}
