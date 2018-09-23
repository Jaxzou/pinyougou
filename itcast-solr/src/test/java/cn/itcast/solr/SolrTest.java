package cn.itcast.solr;

import com.pinyougou.pojo.TbItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

/**
 * @author Jax.zou
 * @create 2018-09-23 8:42
 * @desc 测试solr
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext-solr.xml")
public class SolrTest {

    @Autowired
    private SolrTemplate solrTemplate;

    @Test
    public void addTest(){

        TbItem item = new TbItem();
        item.setId(20L);
        item.setTitle("Apple iPhone XS 256G 智能手机");
        item.setBrand("苹果");
        item.setPrice(new BigDecimal(11275));
        item.setGoodsId(338L);
        item.setSeller("苹果旗舰店");
        item.setCategory("手机");

        solrTemplate.saveBean(item);
        solrTemplate.commit();
    }

    @Test
    public void delTest(){

        SimpleQuery query = new SimpleQuery("*:*");

        solrTemplate.delete(query);
        solrTemplate.commit();
    }

}
