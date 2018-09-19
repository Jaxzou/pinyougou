package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.mapper.*;
import com.pinyougou.pojo.*;
import com.pinyougou.sellergoods.service.GoodsService;
import com.pinyougou.service.impl.BaseServiceImpl;
import com.pinyougou.vo.Goods;
import com.pinyougou.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.*;

@Service(interfaceClass = GoodsService.class)
@Transactional
public class GoodsServiceImpl extends BaseServiceImpl<TbGoods> implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsDescMapper goodsDescMapper;

    @Autowired
    private SellerMapper sellerMapper;

    @Autowired
    private ItemCatMapper itemCatMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public PageResult search(Integer page, Integer rows, TbGoods goods) {
        PageHelper.startPage(page, rows);

        Example example = new Example(TbGoods.class);
        Example.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(goods.getSellerId())) {
            criteria.andEqualTo("sellerId", goods.getSellerId());
        }
        if (!StringUtils.isEmpty(goods.getAuditStatus())) {
            criteria.andEqualTo("auditStatus",  goods.getAuditStatus());
        }
        if (!StringUtils.isEmpty(goods.getGoodsName())) {
            criteria.andLike("goodsName", "%" + goods.getGoodsName() + "%");
        }

        List<TbGoods> list = goodsMapper.selectByExample(example);
        PageInfo<TbGoods> pageInfo = new PageInfo<>(list);

        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public void addGoods(Goods goods) {

        //1.保存商品信息
        add(goods.getGoods());

        //2.保存商品描述信息
        goods.getGoodsDesc().setGoodsId(goods.getGoods().getId());
        goodsDescMapper.insert(goods.getGoodsDesc());

        //3.保存商品sku信息
        saveItemList(goods);

    }

    @Override
    public Goods findGoodsById(Long id) {
        Goods goods = new Goods();
        goods.setGoods(findOne(id));

        goods.setGoodsDesc(goodsDescMapper.selectByPrimaryKey(id));

        TbItem param = new TbItem();
        param.setGoodsId(id);
        goods.setItemList(itemMapper.select(param));

        return goods;
    }

    @Override
    public void updateGoods(Goods goods) {
        goods.getGoods().setAuditStatus("0");//修改过则将审核状态改为未审核
        goodsMapper.updateByPrimaryKeySelective(goods.getGoods());

        goodsDescMapper.updateByPrimaryKeySelective(goods.getGoodsDesc());

        TbItem param = new TbItem();
        param.setGoodsId(goods.getGoods().getId());
        itemMapper.delete(param);

        saveItemList(goods);
    }

    @Override
    public void updateStatus(Long[] ids, String status) {
        TbGoods goods = new TbGoods();
        goods.setAuditStatus(status);

        Example example = new Example(TbGoods.class);
        example.createCriteria().andIn("id", Arrays.asList(ids));

        goodsMapper.updateByExampleSelective(goods,example);

        if("2".equals(status)){//审核通过，将sku设为启用状态
            TbItem item = new TbItem();
            item.setStatus("1");

            Example itemExample = new Example(TbItem.class);
            itemExample.createCriteria().andIn("goodsId",Arrays.asList(ids));
            itemMapper.updateByExampleSelective(item,itemExample);
        }
    }

    @Override
    public void deleteGoodsById(Long[] ids) {
        TbGoods goods = new TbGoods();
        goods.setIsDelete("1");

        Example example = new Example(TbGoods.class);
        example.createCriteria().andIn("id",Arrays.asList(ids));

        goodsMapper.updateByExampleSelective(goods,example);
    }

    @Override
    public void updateMarketable(Long[] ids, String marketable) {

        if("1".equals(marketable)){//如果是上架操作，需要判断是否审核
            for (Long id : ids) {
                TbGoods tbGoods = findOne(id);
                if(!"2".equals(tbGoods.getAuditStatus()))
                    throw new RuntimeException();
            }
        }

        TbGoods goods = new TbGoods();
        goods.setIsMarketable(marketable);

        Example example = new Example(TbGoods.class);
        example.createCriteria().andIn("id", Arrays.asList(ids));

        goodsMapper.updateByExampleSelective(goods,example);
    }

    private void saveItemList(Goods goods) {
        //判断是否启用规格
        if ("1".equals(goods.getGoods().getIsEnableSpec())) {
            if (goods.getItemList() != null && goods.getItemList().size() > 0) {
                for (TbItem tbItem : goods.getItemList()) {
                    //保存标题： goodsname+规格名称
                    String title = goods.getGoods().getGoodsName();

                    Map<String, String> map = JSONObject.parseObject(tbItem.getSpec(), Map.class);
                    Set<Map.Entry<String, String>> entries = map.entrySet();
                    for (Map.Entry<String, String> entry : entries) {
                        title += " " + entry.getValue();
                    }
                    tbItem.setTitle(title);

                    //设置item
                    setItem(tbItem, goods);

                    //保存
                    itemMapper.insertSelective(tbItem);

                }
            }
        } else {
            //不启用规格；将spu转换为一个sku并保存
            TbItem tbItem = new TbItem();
            tbItem.setTitle(goods.getGoods().getGoodsName());

            //设为默认
            tbItem.setIsDefault("1");
            //未审核
            tbItem.setStatus("0");
            //设置数量
            tbItem.setNum(9999);
            //设置价格
            tbItem.setPrice(goods.getGoods().getPrice());
            //设置空规格
            tbItem.setSpec("{}");

            //设置商品信息
            setItem(tbItem, goods);

            //保存
            itemMapper.insertSelective(tbItem);
        }
    }

    /**
     * 设置商品信息（基本、描述、sku列表）
     *
     * @param tbItem
     * @param goods
     */
    private void setItem(TbItem tbItem, Goods goods) {
        //设置图片信息
        if (!StringUtil.isEmpty(goods.getGoodsDesc().getItemImages())) {
            List<Map> list = JSONArray.parseArray(goods.getGoodsDesc().getItemImages(), Map.class);
            tbItem.setImage(list.get(0).get("url").toString());
        }

        //设置商家信息
        tbItem.setSellerId(goods.getGoods().getSellerId());
        TbSeller seller = sellerMapper.selectByPrimaryKey(goods.getGoods().getSellerId());
        tbItem.setSeller(seller.getName());

        //创建时间，更新时间
        tbItem.setCreateTime(new Date());
        tbItem.setUpdateTime(tbItem.getCreateTime());

        //设置商品id
        tbItem.setGoodsId(goods.getGoods().getId());

        //设置分类信息
        tbItem.setCategoryid(goods.getGoods().getCategory3Id());
        TbItemCat itemCat = itemCatMapper.selectByPrimaryKey(goods.getGoods().getCategory3Id());
        tbItem.setCategory(itemCat.getName());

        //设置品牌信息
        TbBrand brand = brandMapper.selectByPrimaryKey(goods.getGoods().getBrandId());
        tbItem.setBrand(brand.getName());
    }
}
