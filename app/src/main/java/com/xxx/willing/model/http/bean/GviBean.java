package com.xxx.willing.model.http.bean;


import java.util.List;

public class GviBean {
    /**
     * name : 生活缴费
     * details : GVI当钱花，话费随心兑
     * list : [{"id":1,"brandId":1,"name":"话费代金券","price":50,"gviPrice":49,"stock":99,"logos":"/telephoneBill.png","img":"['https://img.alicdn.com/imgextra/i4/288240373/TB2jQK0nrwTMeJjSszfXXXbtFXa_!!288240373-0-beehive-scenes.jpg_460x460xz.jpg','https://img.alicdn.com/tfscom/i1/90489528/TB24h0WelTH8KJjy0FiXXcRsXXa_!!90489528.jpg']","describes":"GVI当钱花，话费随心兑","details":"【商品详情】1: 购买前请查询充值号码话费余额，若已欠费停机请查询话费欠费金额，以免充值金额不足导致您不能正常使用造成误会。2: 购买时，请准确填写需要充值的手机号码，因您提供的充值号码导致充值损失，本店不承担责任。一旦充值成功，本店不提供退换服务。3: 每月月初3日内以及月底最后3日内为缴费高峰期，可能会出现运营商系统繁忙，敬请谅解。","explains":"'商品说明'","express":"'配送快递'","freight":123,"status":0,"creator":null,"createTime":"2019-12-11 10:56:27","lastUpdateTime":"2019-12-12 10:56:14","units":"件"}]
     */

    private String name;
    private int brandId;

    private String details;
    private List<ListBean> list;
    public int getBrandId() {
        return brandId;
    }
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
         * brandId : 1
         * name : 话费代金券
         * price : 50.0
         * gviPrice : 49.0
         * stock : 99
         * logos : /telephoneBill.png
         * img : ['https://img.alicdn.com/imgextra/i4/288240373/TB2jQK0nrwTMeJjSszfXXXbtFXa_!!288240373-0-beehive-scenes.jpg_460x460xz.jpg','https://img.alicdn.com/tfscom/i1/90489528/TB24h0WelTH8KJjy0FiXXcRsXXa_!!90489528.jpg']
         * describes : GVI当钱花，话费随心兑
         * details : 【商品详情】1: 购买前请查询充值号码话费余额，若已欠费停机请查询话费欠费金额，以免充值金额不足导致您不能正常使用造成误会。2: 购买时，请准确填写需要充值的手机号码，因您提供的充值号码导致充值损失，本店不承担责任。一旦充值成功，本店不提供退换服务。3: 每月月初3日内以及月底最后3日内为缴费高峰期，可能会出现运营商系统繁忙，敬请谅解。
         * explains : '商品说明'
         * express : '配送快递'
         * freight : 123.0
         * status : 0
         * creator : null
         * createTime : 2019-12-11 10:56:27
         * lastUpdateTime : 2019-12-12 10:56:14
         * units : 件
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
        private double freight;
        private int status;
        private Object creator;
        private String createTime;
        private String lastUpdateTime;
        private String units;
        private String timeValidity;
        private String discount;

        public String getDiscount() {
            return discount;
        }

        public String getTimeValidity() {
            return timeValidity;
        }

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

        public void setPrice(double price) {
            this.price = price;
        }

        public double getGviPrice() {
            return gviPrice;
        }

        public void setGviPrice(double gviPrice) {
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

        public double getFreight() {
            return freight;
        }

        public void setFreight(double freight) {
            this.freight = freight;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
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

        public String getUnits() {
            return units;
        }

        public void setUnits(String units) {
            this.units = units;
        }
    }
}
