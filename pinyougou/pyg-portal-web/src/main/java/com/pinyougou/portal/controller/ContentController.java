package com.pinyougou.portal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.content.service.ContentService;
import com.pinyougou.pojo.TbContent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Jax.zou
 * @create 2018-09-20 21:57
 * @desc 广告展示控制器
 **/
@RequestMapping("/content")
@RestController
public class ContentController {

    @Reference
    private ContentService contentService;


    /**
     * 根据内容分类id查询该分类下的内容
     * @param categoryId 分类id
     * @return 内容集合
     */
    @GetMapping("/findContentListByCategoryId")
    public List<TbContent> findContentListByCategoryId(Long categoryId){
        return contentService.findContentListByCategoryId(categoryId);
    }

}
