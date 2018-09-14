package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.mapper.BrandMapper;
import com.pinyougou.mapper.SpecificationMapper;
import com.pinyougou.mapper.TypeTemplateMapper;
import com.pinyougou.pojo.TbTypeTemplate;
import com.pinyougou.sellergoods.service.TypeTemplateService;
import com.pinyougou.service.impl.BaseServiceImpl;
import com.pinyougou.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;

/**
 * @author Jax.zou
 * @create 2018-09-12 12:22
 * @desc 模板业务实现
 **/
@Service
public class TypeTemplateServiceImpl extends BaseServiceImpl<TbTypeTemplate> implements TypeTemplateService{

    @Autowired
    private TypeTemplateMapper typeTemplateMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private SpecificationMapper specificationMapper;

    @Override
    public PageResult search(TbTypeTemplate tbTypeTemplate, Integer page, Integer rows) {

        PageHelper.startPage(page,rows);

        Example example = new Example(TbTypeTemplate.class);
        if (!StringUtil.isEmpty(tbTypeTemplate.getName())){
            example.createCriteria().andLike("name","%"+tbTypeTemplate.getName()+"%");
        }
        List<TbTypeTemplate> list = typeTemplateMapper.selectByExample(example);
        PageInfo<TbTypeTemplate> pageInfo = new PageInfo<>(list);

        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
    }

    @Override
    public void delete(Long[] ids) {
        if (ids != null && ids.length > 0){
            for (Long id : ids) {
                typeTemplateMapper.deleteByPrimaryKey(id);
            }
        }
    }
}
