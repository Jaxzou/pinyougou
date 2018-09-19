package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbTypeTemplate;
import com.pinyougou.sellergoods.service.TypeTemplateService;
import com.pinyougou.vo.PageResult;
import com.pinyougou.vo.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Jax.zou
 * @create 2018-09-11 8:23
 * @desc 模板controller
 **/
@RequestMapping("/typeTemplate")
@RestController
public class TypeTemplateController {


    @Reference
    private TypeTemplateService typeTemplateService;


    /**
     * 根据id查询模板信息
     * @param id 模板id
     * @return 模板
     */
    @GetMapping("/findOne")
    public TbTypeTemplate findOne (Long id){
        return typeTemplateService.findOne(id);
    }

    /**
     * 根据分类模板id查询其对应的规格及其规格的选项
     * @param id 分类模板id
     * @return 规格及其规格的选项
     */
    @GetMapping("/findSpecList")
    public List<Map> findSpecList(Long id){
        return typeTemplateService.findSpecList(id);
    }


}
