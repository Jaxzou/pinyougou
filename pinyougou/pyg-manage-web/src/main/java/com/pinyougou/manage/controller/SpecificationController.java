package com.pinyougou.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.sellergoods.service.SpecificationService;
import com.pinyougou.vo.PageResult;
import com.pinyougou.vo.Result;
import com.pinyougou.vo.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Jax.zou
 * @create 2018-09-11 8:23
 * @desc 规格controller
 **/
@RequestMapping("specification")
@RestController
public class SpecificationController {


    @Reference
    private SpecificationService specificationService;


    /**
     * 根据分页信息查询规格分页数据
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("findPage")
    public PageResult findPage(@RequestParam(name = "page",defaultValue = "1")Integer page,
                               @RequestParam(name = "rows",defaultValue = "10")Integer rows){

        return specificationService.findPage(page, rows);
    }

    /**
     * 新增规格及规格选项数据
     * @param specification 规格及规格选项数据
     * @return 操作结果
     */
    @PostMapping("add")
    public Result add (@RequestBody Specification specification){

        try {
            //调用service
            specificationService.add(specification);
            //成功
            return Result.ok("新增成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("新增失败");
    }

    /**
     * 根据id查询规格信息
     * @param id 规格id
     * @return 规格
     */
    @GetMapping("findOne")
    public Specification findOne (Long id){
        return specificationService.findOne(id);
    }

    /**
     * 更新规格及规格选项数据
     * @param specification 规格及规格选项数据
     * @return 操作结果
     */
    @PostMapping("update")
    public Result update (@RequestBody Specification specification){

        try {
            //调用service
            specificationService.update(specification);
            //成功
            return Result.ok("更新成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("更新失败");
    }


    /**
     * 删除规格及规格选项数据
     * @param ids 规格id数组
     * @return 操作结果
     */
    @GetMapping("delete")
    public Result delete(Long[] ids){

        try {
            specificationService.deleteSpecificationByIds(ids);
            return Result.ok("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("删除失败");
    }

    /**
     * 根据条件分页查询规格列表
     * @param tbSpecification 条件对象
     * @param page 当前页
     * @param rows 页面大小
     * @return 分页数据
     */
    @PostMapping("search")
    public PageResult search(@RequestBody TbSpecification tbSpecification,
                             @RequestParam(name = "page",defaultValue = "1")Integer page,
                             @RequestParam(name = "rows",defaultValue = "10")Integer rows){

        return  specificationService.search(tbSpecification,page,rows);
    }


    /**
     * 查询规格列表，返回的数据格式符合select2的格式
     * @return
     */
    @RequestMapping("selectOptionList")
    public List<Map<String, Object>> selectOptionList(){
        return specificationService.selectOptionList();
    }
}
