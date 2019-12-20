package com.xxx.willing.model.http.bean;

public class CommodityDetailBean {

    /**
     * shopBrand : {"id":3,"name":"时尚精品","img":null,"gviName":"时尚精品","gviDetails":"颠倒潮流，秒变时尚达人","phone":null,"details":"颠倒潮流，秒变时尚达人","status":0,"createTime":"2019-12-16 17:14:24","lastUpdateTime":"2019-12-16 17:14:24"}
     * commodity : {"id":4,"brandId":3,"name":"POLO品牌男装","price":3000,"gviPrice":2700,"stock":98,"logos":"/POLO_1.png","img":"['https://img.alicdn.com/imgextra/i4/288240373/TB2jQK0nrwTMeJjSszfXXXbtFXa_!!288240373-0-beehive-scenes.jpg_460x460xz.jpg','https://img.alicdn.com/tfscom/i1/90489528/TB24h0WelTH8KJjy0FiXXcRsXXa_!!90489528.jpg']","describes":"你你你你你","details":"不对不对不对不对不对不对不代表低版本·","explains":"谁对谁错电动车","express":"吧吧吧吧","freight":11,"status":1,"creator":"吧吧吧吧","createTime":"2019-12-13 15:33:29","lastUpdateTime":"2019-12-13 15:33:29","units":"发大发","timeValidity":null,"discount":null}
     */

    private ShopBrandBean shopBrand;
    private CommodityBean commodity;

    public ShopBrandBean getShopBrand() {
        return shopBrand;
    }

    public void setShopBrand(ShopBrandBean shopBrand) {
        this.shopBrand = shopBrand;
    }

    public CommodityBean getCommodity() {
        return commodity;
    }

    public void setCommodity(CommodityBean commodity) {
        this.commodity = commodity;
    }

    public static class ShopBrandBean {
        /**
         * id : 3
         * name : 时尚精品
         * img : null
         * gviName : 时尚精品
         * gviDetails : 颠倒潮流，秒变时尚达人
         * phone : null
         * details : 颠倒潮流，秒变时尚达人
         * status : 0
         * createTime : 2019-12-16 17:14:24
         * lastUpdateTime : 2019-12-16 17:14:24
         */

        private int id;
        private String name;
        private String img;
        private String gviName;
        private String gviDetails;
        private String phone;
        private String details;
        private int status;
        private String createTime;
        private String lastUpdateTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getGviName() {
            return gviName;
        }

        public void setGviName(String gviName) {
            this.gviName = gviName;
        }

        public String getGviDetails() {
            return gviDetails;
        }

        public void setGviDetails(String gviDetails) {
            this.gviDetails = gviDetails;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
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
         * timeValidity : null
         * discount : null
         */

        private int id;
        private int brandId;
        private String name;
        private double price;
        private double gviPrice;
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
        private String timeValidity;
        private String discount;

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

        public double getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public double getGviPrice() {
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

        public String getTimeValidity() {
            return timeValidity;
        }

        public void setTimeValidity(String timeValidity) {
            this.timeValidity = timeValidity;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }
    }
}
