package com.pinyougou.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.PageResult;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Jax.zou
 * @create 2018-09-07 9:31
 * @desc 品牌controller
 **/
@RequestMapping("brand")
@RestController
public class BrandController {

    //注入service
    @Reference
    private BrandService brandService;


    @GetMapping("testPage")
    public List<TbBrand> testPage(@RequestParam(name = "page",defaultValue = "1")Integer page,
                                  @RequestParam(name = "rows",defaultValue = "10")Integer rows){

        //return brandService.testPage(page,rows);
        return (List<TbBrand>) brandService.findPage(page,rows).getRows();
    }

    /**
     * 查询品牌数据
     * @return
     */
    @RequestMapping("findAll")
    public List<TbBrand> queryAll(){
        //return brandService.queryAll();
        return brandService.findAll();
    }

    /**
     * 分页查询品牌列表数据
     * @param page 当前页
     * @param rows 页面大小
     * @return 自定义分页数据对象
     */
    @GetMapping("findPage")
    public PageResult findPage(@RequestParam(name = "page",defaultValue = "1")Integer page,
                               @RequestParam(name = "rows",defaultValue = "10")Integer rows){

        //return brandService.testPage(page,rows);
        return brandService.findPage(page,rows);
    }
}
