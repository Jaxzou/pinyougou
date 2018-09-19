package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbItemCat;
import com.pinyougou.sellergoods.service.ItemCatService;
import com.pinyougou.vo.PageResult;
import com.pinyougou.vo.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/itemCat")
@RestController
public class ItemCatController {

    @Reference
    private ItemCatService itemCatService;

    @RequestMapping("/findAll")
    public List<TbItemCat> findAll() {
        return itemCatService.findAll();
    }

    /**
     * 根据id查询分类模板信息
     * @param id
     * @return
     */
    @GetMapping("/findOne")
    public TbItemCat findOne(Long id) {
        return itemCatService.findOne(id);
    }

    /**
     * 根据父类目id查询分类列表
     * @param parentId 父类目id
     * @return 列表数据
     */
    @GetMapping("/findByParentId")
    public List<TbItemCat> findByParentId(@RequestParam(value = "parentId",defaultValue = "0")Long parentId){
        TbItemCat param = new TbItemCat();
        param.setParentId(parentId);
        return itemCatService.findByWhere(param);
    }

}
