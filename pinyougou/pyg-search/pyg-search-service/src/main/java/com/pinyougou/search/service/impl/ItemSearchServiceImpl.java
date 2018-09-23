package com.pinyougou.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.HighlightOptions;
import org.springframework.data.solr.core.query.SimpleHighlightQuery;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.core.query.result.ScoredPage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jax.zou
 * @create 2018-09-23 10:10
 * @desc 搜索业务实现
 **/
@Service
public class ItemSearchServiceImpl implements ItemSearchService {

    @Autowired
    private SolrTemplate solrTemplate;

    @Override
    public Map<String, Object> search(Map<String, Object> searchMap) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        /*SimpleQuery query = new SimpleQuery();
        //查询条件
        Criteria criteria = new Criteria("item_keywords").is(searchMap.get("keywords"));
        query.addCriteria(criteria);
        //查询
        ScoredPage<TbItem> scoredPage = solrTemplate.queryForPage(query, TbItem.class);
        //设置返回的商品列表
        resultMap.put("rows", scoredPage.getContent());*/

        SimpleHighlightQuery query = new SimpleHighlightQuery();

        Criteria criteria = new Criteria("item_keywords").is(searchMap.get("keywords"));
        query.addCriteria(criteria);

        //设置高亮
        HighlightOptions highlightOptions = new HighlightOptions();
        highlightOptions.addField("item_title");//高亮域
        highlightOptions.setSimplePrefix("<font style='color:red'>");//高亮起始标签
        highlightOptions.setSimplePostfix("</font>");//高亮结束标签
        query.setHighlightOptions(highlightOptions);

        //查询
        HighlightPage<TbItem> itemHighlightPage = solrTemplate.queryForHighlightPage(query, TbItem.class);

        //处理
        List<HighlightEntry<TbItem>> highlighted = itemHighlightPage.getHighlighted();
        if (highlighted != null && highlighted.size() > 0){
            for (HighlightEntry<TbItem> entry : highlighted) {
                List<HighlightEntry.Highlight> highlights = entry.getHighlights();
                if (highlights != null && highlights.size() > 0 && highlights.get(0).getSnipplets() != null){
                    entry.getEntity().setTitle(highlights.get(0).getSnipplets().get(0));
                }
            }
        }

        resultMap.put("rows", itemHighlightPage.getContent());

        return resultMap;

    }
}
