package com.xxx.willing.model.http.bean;


import java.util.List;

public class SearchShopBean {

        /**
         * pageNum : 1
         * pageSize : 10
         * totalSize : 2
         * totalPages : 1
         * list : [{"id":2,"brandId":3,"name":"1","price":1,"gviPrice":1,"stock":1,"logos":"1","img":"1","describes":"1","details":"1","explains":"1","express":"1","freight":1,"status":null,"creator":null,"createTime":"2019-12-11 17:32:26","lastUpdateTime":"2019-12-11 17:32:26"},{"id":1,"brandId":2,"name":"wewrw","price":12,"gviPrice":12,"stock":123,"logos":"12321","img":"123","describes":null,"details":null,"explains":null,"express":null,"freight":null,"status":null,"creator":null,"createTime":null,"lastUpdateTime":null}]
         * firstPage : true
         * lastPage : true
         */

        private int pageNum;
        private int pageSize;
        private int totalSize;
        private int totalPages;
        private boolean firstPage;
        private boolean lastPage;
        private List<ListBean> list;

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

        public boolean isFirstPage() {
            return firstPage;
        }

        public void setFirstPage(boolean firstPage) {
            this.firstPage = firstPage;
        }

        public boolean isLastPage() {
            return lastPage;
        }

        public void setLastPage(boolean lastPage) {
            this.lastPage = lastPage;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 2
             * brandId : 3
             * name : 1
             * price : 1
             * gviPrice : 1
             * stock : 1
             * logos : 1
             * img : 1
             * describes : 1
             * details : 1
             * explains : 1
             * express : 1
             * freight : 1
             * status : null
             * creator : null
             * createTime : 2019-12-11 17:32:26
             * lastUpdateTime : 2019-12-11 17:32:26
             */

            private int id;
            private int brandId;
            private String name;
            private int price;
            private int gviPrice;
            private int stock;
            private String logos;
            private String img;
            private String describes;
            private String details;
            private String explains;
            private String express;
            private int freight;
            private Object status;
            private Object creator;
            private String createTime;
            private String lastUpdateTime;

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

            public String getDescribes() {
                return describes;
            }

            public void setDescribes(String describes) {
                this.describes = describes;
            }

            public String getDetails() {
                return details;
            }

            public void setDetails(String details) {
                this.details = details;
            }

            public String getExplains() {
                return explains;
            }

            public void setExplains(String explains) {
                this.explains = explains;
            }

            public String getExpress() {
                return express;
            }

            public void setExpress(String express) {
                this.express = express;
            }

            public int getFreight() {
                return freight;
            }

            public void setFreight(int freight) {
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

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getLastUpdateTime() {
                return lastUpdateTime;
            }

            public void setLastUpdateTime(String lastUpdateTime) {
                this.lastUpdateTime = lastUpdateTime;
            }
        }
}
