package com.pinyougou.search.service;

import java.util.Map; /**
 * @author Jax.zou
 * @create 2018-09-23 10:09
 * @desc 搜索业务层接口
 **/
public interface ItemSearchService {
    /**
     * 根据搜索关键字搜索商品列表
     * @param searchMap 搜索条件
     * @return 搜索结果
     */
    Map<String,Object> search(Map<String, Object> searchMap);
}
