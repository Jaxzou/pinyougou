package com.pinyougou.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author Jax.zou
 * @create 2018-09-07 20:53
 * @desc 分页数据类
 **/
public class PageResult implements Serializable{

    private long total;//总记录数
    private List<?> rows;//记录列表

    public PageResult(long total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
