package com.pinyougou.content.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.mapper.ContentMapper;
import com.pinyougou.pojo.TbContent;
import com.pinyougou.content.service.ContentService;
import com.pinyougou.service.impl.BaseServiceImpl;
import com.pinyougou.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Service(interfaceClass = ContentService.class)
public class ContentServiceImpl extends BaseServiceImpl<TbContent> implements ContentService {

    @Autowired
    private ContentMapper contentMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String REDIS_CONTENT = "content";

    @Override
    public PageResult search(Integer page, Integer rows, TbContent content) {
        PageHelper.startPage(page, rows);

        Example example = new Example(TbContent.class);
        Example.Criteria criteria = example.createCriteria();

        List<TbContent> list = contentMapper.selectByExample(example);
        PageInfo<TbContent> pageInfo = new PageInfo<>(list);

        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public List<TbContent> findContentListByCategoryId(Long categoryId) {

        List<TbContent> list = null;

        try {
            list = (List<TbContent>) redisTemplate.boundHashOps(REDIS_CONTENT).get(categoryId);
            if (list != null){
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Example example = new Example(TbContent.class);

        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("categoryId",categoryId);
        criteria.andEqualTo("status","1");
        example.orderBy("sortOrder").desc();

        list = contentMapper.selectByExample(example);

        try {
            redisTemplate.boundHashOps(REDIS_CONTENT).put(categoryId,list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void add(TbContent tbContent){
        super.add(tbContent);

        updateContentInRedisByCategryId(tbContent.getCategoryId());
    }

    private void updateContentInRedisByCategryId(Long categoryId){
        try {
            redisTemplate.boundHashOps(REDIS_CONTENT).delete(categoryId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void update(TbContent tbContent){
        TbContent oldContent = super.findOne(tbContent.getId());

        super.update(tbContent);

        if(!oldContent.getCategoryId().equals(tbContent.getCategoryId())){
            updateContentInRedisByCategryId(oldContent.getCategoryId());
        }
        updateContentInRedisByCategryId(tbContent.getCategoryId());
    }

    @Override
    public void deleteByIds(Serializable[] ids) {

        Example example = new Example(TbContent.class);
        example.createCriteria().andEqualTo("id", Arrays.asList(ids));

        List<TbContent> contentList = contentMapper.selectByExample(example);
        if (contentList != null && contentList.size() > 0){
            for (TbContent tbContent : contentList){
                updateContentInRedisByCategryId(tbContent.getCategoryId());
            }
        }
        super.deleteByIds(ids);
    }
}
