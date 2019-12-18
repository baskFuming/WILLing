package com.xxx.willing.model.http.bean;

/**
 * Time: ${Date}
 * Author : mac
 * Des:
 */
public class MyOrderBean {

    /**
     * commodity : {"id":4,"brandId":3,"name":"POLO品牌男装","price":3000,"gviPrice":2700,"stock":98,"logos":"/POLO_1.png","img":"['https://img.alicdn.com/imgextra/i4/288240373/TB2jQK0nrwTMeJjSszfXXXbtFXa_!!288240373-0-beehive-scenes.jpg_460x460xz.jpg','https://img.alicdn.com/tfscom/i1/90489528/TB24h0WelTH8KJjy0FiXXcRsXXa_!!90489528.jpg']","describes":"你你你你你","details":"不对不对不对不对不对不对不代表低版本·","explains":"谁对谁错电动车","express":"吧吧吧吧","freight":11,"status":1,"creator":"吧吧吧吧","createTime":"2019-12-13 15:33:29","lastUpdateTime":"2019-12-13 15:33:29","units":"发大发"}
     * address : 地址：数的深不是不北疆是吧大VAV巴萨，收件人：三，电话：122333333
     * freight : 11
     * orderNum : 9a208f02-e921-4cd4-9275-431e060b8624
     * express : 吧吧吧吧
     * userId : 1
     * createTime : 2019-12-17 18:04:29
     * price : 2700
     * details : 颜色：黑色，尺码：M
     * id : 5
     * commNum : 1
     * status : 0
     * lastUpdateTime : 2019-12-17 18:04:29
     */

    private CommodityBean commodity;
    private String address;
    private int freight;
    private String orderNum;
    private String express;
    private int userId;
    private String createTime;
    private int price;
    private String details;
    private int id;
    private int commNum;
    private int status;
    private String lastUpdateTime;

    public CommodityBean getCommodity() {
        return commodity;
    }


    public String getAddress() {
        return address;
    }


    public int getFreight() {
        return freight;
    }


    public String getOrderNum() {
        return orderNum;
    }


    public String getExpress() {
        return express;
    }


    public int getUserId() {
        return userId;
    }


    public String getCreateTime() {
        return createTime;
    }


    public int getPrice() {
        return price;
    }


    public String getDetails() {
        return details;
    }


    public int getId() {
        return id;
    }


    public int getCommNum() {
        return commNum;
    }


    public int getStatus() {
        return status;
    }


    public String getLastUpdateTime() {
        return lastUpdateTime;
    }


    public static class CommodityBean {
        /**
         * id : 4
         * brandId : 3
         * name : POLO品牌男装
         * price : 3000
         * gviPrice : 2700
         * stock : 98
         * logos : /POLO_1.png
         * img : ['https://img.alicdn.com/imgextra/i4/288240373/TB2jQK0nrwTMeJjSszfXXXbtFXa_!!288240373-0-beehive-scenes.jpg_460x460xz.jpg','https://img.alicdn.com/tfscom/i1/90489528/TB24h0WelTH8KJjy0FiXXcRsXXa_!!90489528.jpg']
         * describes : 你你你你你
         * details : 不对不对不对不对不对不对不代表低版本·
         * explains : 谁对谁错电动车
         * express : 吧吧吧吧
         * freight : 11
         * status : 1
         * creator : 吧吧吧吧
         * createTime : 2019-12-13 15:33:29
         * lastUpdateTime : 2019-12-13 15:33:29
         * units : 发大发
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
        private int status;
        private String creator;
        private String createTime;
        private String lastUpdateTime;
        private String units;

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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
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

        public String getUnits() {
            return units;
        }

        public void setUnits(String units) {
            this.units = units;
        }
    }
}
