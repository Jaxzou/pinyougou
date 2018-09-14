package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.mapper.SpecificationMapper;
import com.pinyougou.mapper.SpecificationOptionMapper;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.sellergoods.service.SpecificationService;
import com.pinyougou.service.impl.BaseServiceImpl;
import com.pinyougou.vo.PageResult;
import com.pinyougou.vo.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Jax.zou
 * @create 2018-09-11 8:19
 * @desc 规格业务实现类
 **/
@Service
public class SpecificationServiceImpl extends BaseServiceImpl<TbSpecification> implements SpecificationService {

    @Autowired
    private SpecificationMapper specificationMapper;

    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;


    @Override
    public PageResult search(TbSpecification tbSpecification, Integer page, Integer rows) {

        //设置分页
        PageHelper.startPage(page,rows);

        Example example = new Example(TbSpecification.class);
        Example.Criteria criteria = example.createCriteria();
        if(!StringUtil.isEmpty(tbSpecification.getSpecName())){
            criteria.andLike("specName","%"+tbSpecification.getSpecName()+"%");
        }
        List<TbSpecification> list = specificationMapper.selectByExample(example);

        PageInfo<TbSpecification> pageInfo = new PageInfo<>(list);

        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
    }


    @Override
    public void add(Specification specification) {
        //保存规格信息
        specificationMapper.insert(specification.getSpecification());

        //保存规格选项信息
        if(specification.getSpecificationOptionList() != null
                && specification.getSpecificationOptionList().size() > 0){

            for (TbSpecificationOption tbSpecificationOption : specification.getSpecificationOptionList()) {
                    tbSpecificationOption.setSpecId(specification.getSpecification().getId());
                    specificationOptionMapper.insert(tbSpecificationOption);
            }
        }

    }

    @Override
    public Specification findOne(Long id) {
        Specification specification = new Specification();

        //查询规格数据
        specification.setSpecification(specificationMapper.selectByPrimaryKey(id));
        //查询规格选项数据
        TbSpecificationOption tbSpecificationOption = new TbSpecificationOption();
        tbSpecificationOption.setSpecId(id);
        specification.setSpecificationOptionList(specificationOptionMapper.select(tbSpecificationOption));

        return specification;
    }


    @Override
    public void update(Specification specification) {
        //更新规格数据
        specificationMapper.updateByPrimaryKeySelective(specification.getSpecification());

        //删除规格选项下的数据，再保存
        TbSpecificationOption tbSpecificationOption = new TbSpecificationOption();
        tbSpecificationOption.setSpecId(specification.getSpecification().getId());
        specificationOptionMapper.delete(tbSpecificationOption);

        //先判断下是否新增的数据为空
        if (specification.getSpecificationOptionList() != null
                && specification.getSpecificationOptionList().size() > 0){
            for (TbSpecificationOption specificationOption : specification.getSpecificationOptionList()) {
                specificationOption.setSpecId(specification.getSpecification().getId());
                specificationOptionMapper.insertSelective(specificationOption);
            }
        }
    }

    @Override
    public void deleteSpecificationByIds(Long[] ids) {

        //先将规格数据删除
       deleteByIds(ids);

       //定义条件对象
       Example example = new Example(TbSpecificationOption.class);
       //设置规格的id
       example.createCriteria().andEqualTo("specId", Arrays.asList(ids));
       //删除规格选项
       specificationOptionMapper.deleteByExample(example);
    }

    @Override
    public List<Map<String, Object>> selectOptionList() {
        return specificationMapper.selectOptionList();
    }
}
