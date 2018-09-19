package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbGoods;
import com.pinyougou.service.BaseService;
import com.pinyougou.vo.Goods;
import com.pinyougou.vo.PageResult;

public interface GoodsService extends BaseService<TbGoods> {

    /**
     * 分页查询列表
     * @param goods 查询条件
     * @param page 页号
     * @param rows 每页大小
     * @return 分页数据
     */
    PageResult search(Integer page, Integer rows, TbGoods goods);

    /**
     * 保存商品信息
     * @param goods 商品信息封装对象
     */
    void addGoods(Goods goods);

    /**
     * 根据商品id查询商品基本、描述、sku信息
     * @param id 商品id
     * @return 商品基本、描述、sku信息
     */
    Goods findGoodsById(Long id);

    /**
     * 更新商品信息（商品基本、描述、sku）
     * @param goods 商品基本、描述、sku
     * @return 操作结果
     */
    void updateGoods(Goods goods);

    /**
     * 根据商品id数组，将状态修改为要修改的状态
     * @param ids 商品id数组
     * @param status 要修改成的状态
     * @return 操作结果
     */
    void updateStatus(Long[] ids, String status);

    /**
     * 根据商品id删除商品（修改商品的is_delete的值）
     * @param ids 商品id数组
     * @return 操作结果
     */
    void deleteGoodsById(Long[] ids);

    /**
     * 根据商品id，将上下架状态修改为要修改的状态
     * @param ids 商品id数组
     * @param marketable 上下架状态
     * @return 操作结果
     */
    void updateMarketable(Long[] ids, String marketable);
}