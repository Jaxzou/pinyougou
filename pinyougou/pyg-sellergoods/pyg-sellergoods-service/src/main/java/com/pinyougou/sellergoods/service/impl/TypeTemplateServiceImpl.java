package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.fabric.xmlrpc.base.Param;
import com.pinyougou.mapper.BrandMapper;
import com.pinyougou.mapper.SpecificationMapper;
import com.pinyougou.mapper.SpecificationOptionMapper;
import com.pinyougou.mapper.TypeTemplateMapper;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.pojo.TbTypeTemplate;
import com.pinyougou.sellergoods.service.TypeTemplateService;
import com.pinyougou.service.impl.BaseServiceImpl;
import com.pinyougou.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private SpecificationOptionMapper specificationOptionMapper;

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

    @Override
    public List<Map> findSpecList(Long id) {
        //查询出模板
        TbTypeTemplate tbTypeTemplate = findOne(id);

        //将数据转为json
        List<Map> specList = JSONArray.parseArray(tbTypeTemplate.getSpecIds(), Map.class);

        //遍历查出对应的规格选项
        for (Map map : specList) {

            TbSpecificationOption param = new TbSpecificationOption();
            param.setSpecId(Long.parseLong(map.get("id").toString()));
            List<TbSpecificationOption> options = specificationOptionMapper.select(param);
            map.put("options",options);
        }

        return specList;
    }
}
