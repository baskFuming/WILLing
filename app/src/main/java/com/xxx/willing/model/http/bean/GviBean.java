package com.xxx.willing.model.http.bean;


import java.util.List;

public class GviBean {

    /**
     * name : 1
     * details : 1
     * list : [{"id":1,"brandId":2,"name":"wewrw","price":12,"gviPrice":12,"stock":123,"logos":"12321","img":"123","describes":null,"details":null,"explains":null,"express":null,"freight":null,"status":null,"creator":null,"createTime":null,"lastUpdateTime":null}]
     */

    private String name;
    private String details;
    private List<ListBean> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 1
         * brandId : 2
         * name : wewrw
         * price : 12
         * gviPrice : 12
         * stock : 123
         * logos : 12321
         * img : 123
         * describes : null
         * details : null
         * explains : null
         * express : null
         * freight : null
         * status : null
         * creator : null
         * createTime : null
         * lastUpdateTime : null
         */

        private int id;
        private int brandId;
        private String name;
        private int price;
        private int gviPrice;
        private int stock;
        private String logos;
        private String img;
        private Object describes;
        private Object details;
        private Object explains;
        private Object express;
        private Object freight;
        private Object status;
        private Object creator;
        private Object createTime;
        private Object lastUpdateTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getBrandId() {
            return brandId;
        }

        public void setBrandId(int brandId) {
            this.brandId = brandId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getGviPrice() {
            return gviPrice;
        }

        public void setGviPrice(int gviPrice) {
            this.gviPrice = gviPrice;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public String getLogos() {
            return logos;
        }

        public void setLogos(String logos) {
            this.logos = logos;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public Object getDescribes() {
            return describes;
        }

        public void setDescribes(Object describes) {
            this.describes = describes;
        }

        public Object getDetails() {
            return details;
        }

        public void setDetails(Object details) {
            this.details = details;
        }

        public Object getExplains() {
            return explains;
        }

        public void setExplains(Object explains) {
            this.explains = explains;
        }

        public Object getExpress() {
            return express;
        }

        public void setExpress(Object express) {
            this.express = express;
        }

        public Object getFreight() {
            return freight;
        }

        public void setFreight(Object freight) {
            this.freight = freight;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public Object getCreator() {
            return creator;
        }

        public void setCreator(Object creator) {
            this.creator = creator;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public Object getLastUpdateTime() {
            return lastUpdateTime;
        }

        public void setLastUpdateTime(Object lastUpdateTime) {
            this.lastUpdateTime = lastUpdateTime;
        }
    }
}
