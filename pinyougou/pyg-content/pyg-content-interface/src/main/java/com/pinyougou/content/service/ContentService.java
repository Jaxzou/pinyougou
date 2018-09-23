package com.pinyougou.content.service;

import com.pinyougou.pojo.TbContent;
import com.pinyougou.service.BaseService;
import com.pinyougou.vo.PageResult;

import java.util.List;

public interface ContentService extends BaseService<TbContent> {

    PageResult search(Integer page, Integer rows, TbContent content);

    /**
     * 根据内容分类id查询该分类下的内容
     * @param categoryId 分类id
     * @return 内容集合
     */
    List<TbContent> findContentListByCategoryId(Long categoryId);
}