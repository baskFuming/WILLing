package com.xxx.willing.model.http.bean.base;

import java.util.List;

public class PageBean<T> {


    /**
     * pageNum : 1
     * pageSize : 6
     * totalSize : 6
     * totalPages : 1
     * list : null
     */

    private int pageNum;
    private int pageSize;
    private int totalSize;
    private int totalPages;
    private List<T> list;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
