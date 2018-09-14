package com.pinyougou.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojo.TbTypeTemplate;
import com.pinyougou.sellergoods.service.SpecificationService;
import com.pinyougou.sellergoods.service.TypeTemplateService;
import com.pinyougou.vo.PageResult;
import com.pinyougou.vo.Result;
import com.pinyougou.vo.Specification;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jax.zou
 * @create 2018-09-11 8:23
 * @desc 模板controller
 **/
@RequestMapping("typeTemplate")
@RestController
public class TypeTemplateController {


    @Reference
    private TypeTemplateService typeTemplateService;


    /**
     * 根据分页信息查询模板分页数据
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("findPage")
    public PageResult findPage(@RequestParam(name = "page",defaultValue = "1")Integer page,
                               @RequestParam(name = "rows",defaultValue = "10")Integer rows){

        return typeTemplateService.findPage(page, rows);
    }

    /**
     * 新增模板
     * @param tbTypeTemplate 模板数据
     * @return 操作结果
     */
    @PostMapping("add")
    public Result add (@RequestBody TbTypeTemplate tbTypeTemplate){

        try {
            //调用service
            typeTemplateService.add(tbTypeTemplate);
            //成功
            return Result.ok("新增成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("新增失败");
    }

    /**
     * 根据id查询模板信息
     * @param id 模板id
     * @return 模板
     */
    @GetMapping("findOne")
    public TbTypeTemplate findOne (Long id){
        return typeTemplateService.findOne(id);
    }

    /**
     * 更新模板数据
     * @param tbTypeTemplate 模板数据
     * @return 操作结果
     */
    @PostMapping("update")
    public Result update (@RequestBody TbTypeTemplate tbTypeTemplate){

        try {
            //调用service
            typeTemplateService.update(tbTypeTemplate);
            //成功
            return Result.ok("更新成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("更新失败");
    }


    /**
     * 删除模板数据
     * @param ids 模板id数组
     * @return 操作结果
     */
    @GetMapping("delete")
    public Result delete(Long[] ids){

        try {
            typeTemplateService.delete(ids);
            return Result.ok("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("删除失败");
    }

    /**
     * 根据条件分页查询模板列表
     * @param tbTypeTemplate 条件对象
     * @param page 当前页
     * @param rows 页面大小
     * @return 分页数据
     */
    @PostMapping("search")
    public PageResult search(@RequestBody TbTypeTemplate tbTypeTemplate,
                             @RequestParam(name = "page",defaultValue = "1")Integer page,
                             @RequestParam(name = "rows",defaultValue = "10")Integer rows){

        return  typeTemplateService.search(tbTypeTemplate,page,rows);
    }


}
