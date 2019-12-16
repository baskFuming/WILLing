package com.xxx.willing.model.http.bean;

import java.util.List;

public class JoinInfoBean {

    private List<BrandListBean> brandList;

    public List<BrandListBean> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<BrandListBean> brandList) {
        this.brandList = brandList;
    }

    public static class BrandListBean {
        /**
         * name : 优乐美
         * id : 1
         */

        private String name;
        private int id;
        private Integer quota;

        public Integer getQuota() {
            return quota;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
