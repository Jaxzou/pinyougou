package com.pinyougou.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.vo.PageResult;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
import com.pinyougou.vo.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

        return brandService.testPage(page,rows);
        //return (List<TbBrand>) brandService.findPage(page,rows).getRows();
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


    /**
     * 新增品牌数据
     * @param tbBrand 品牌
     * @return 操作结果
     */
    @PostMapping("add")
    public Result add (@RequestBody TbBrand tbBrand){

        try {
            //调用service
            brandService.add(tbBrand);
            //成功
            return Result.ok("新增成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("新增失败");
    }

    /**
     * 根据id查询品牌信息
     * @param id 品牌id
     * @return 品牌
     */
    @GetMapping("findOne")
    public TbBrand findOne (Long id){
        return brandService.findOne(id);
    }

    /**
     * 更新品牌数据
     * @param tbBrand 品牌
     * @return 操作结果
     */
    @PostMapping("update")
    public Result update (@RequestBody TbBrand tbBrand){

        try {
            //调用service
            brandService.update(tbBrand);
            //成功
            return Result.ok("更新成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("更新失败");
    }


    /**
     * 删除品牌数据
     * @param ids 品牌id数组
     * @return 操作结果
     */
    @GetMapping("delete")
    public Result delete(Long[] ids){

        try {
            brandService.deleteByIds(ids);
            return Result.ok("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("删除失败");
    }

    /**
     * 根据条件分页查询品牌列表
     * @param tbBrand 条件对象
     * @param page 当前页
     * @param rows 页面大小
     * @return 分页数据
     */
    @PostMapping("search")
    public PageResult search(@RequestBody TbBrand tbBrand,
                             @RequestParam(name = "page",defaultValue = "1")Integer page,
                             @RequestParam(name = "rows",defaultValue = "10")Integer rows){

        return  brandService.search(tbBrand,page,rows);
    }

    /**
     * 查询品牌列表数据，返回符合select2格式的
     * @return
     */
    @RequestMapping("selectOptionList")
    public List<Map<String,Object>> selectOptionList(){
        return brandService.selectOptionList();
    }
 }
