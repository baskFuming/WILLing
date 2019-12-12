package com.xxx.willing.model.http.bean;


import java.util.List;

public class VoteDetailBean {

    /**
     * period : 1
     * franNum : e0418777-4973-4f6f-a993-7aa312dc680a
     * releaseTime : null
     * voteNum : 1116.0
     * list : [{"id":1,"franId":1,"userRole":"董事","name":"啊啊啊","phone":"15100000222","userImg":"http://192.168.31.95/img/79c78175-929c-4d20-9e21-3ae79aefd7ba.jpg","cardFront":"http://192.168.31.95/img/71aa6e90-612e-47e2-b341-6164a1b5c683.jpg","cardBack":"http://192.168.31.95/img/90ab03aa-34f6-4923-8e6f-fba31d4d4ed1.jpg","holdCard":"http://192.168.31.95/img/99d94a3d-f00c-4af4-b575-6762675d727b.jpg","datils":null}]
     * exIncome : 1
     * exTurnover : 1000
     * franName : 巴适
     * quota : 1
     * details : 测试
     * endTime : null
     * reCycle : 90
     * brand : 优乐美
     */

    private int period;
    private List<String> banner;
    private String franNum;
    private Object releaseTime;
    private double voteNum;
    private String exIncome;
    private String exTurnover;
    private String franName;
    private int quota;
    private String details;
    private Object endTime;
    private int reCycle;
    private String brand;
    private List<ListBean> list;

    public List<String> getBanner() {
        return banner;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getFranNum() {
        return franNum;
    }

    public void setFranNum(String franNum) {
        this.franNum = franNum;
    }

    public Object getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Object releaseTime) {
        this.releaseTime = releaseTime;
    }

    public double getVoteNum() {
        return voteNum;
    }

    public void setVoteNum(double voteNum) {
        this.voteNum = voteNum;
    }

    public String getExIncome() {
        return exIncome;
    }

    public void setExIncome(String exIncome) {
        this.exIncome = exIncome;
    }

    public String getExTurnover() {
        return exTurnover;
    }

    public void setExTurnover(String exTurnover) {
        this.exTurnover = exTurnover;
    }

    public String getFranName() {
        return franName;
    }

    public void setFranName(String franName) {
        this.franName = franName;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Object getEndTime() {
        return endTime;
    }

    public void setEndTime(Object endTime) {
        this.endTime = endTime;
    }

    public int getReCycle() {
        return reCycle;
    }

    public void setReCycle(int reCycle) {
        this.reCycle = reCycle;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
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
         * franId : 1
         * userRole : 董事
         * name : 啊啊啊
         * phone : 15100000222
         * userImg : http://192.168.31.95/img/79c78175-929c-4d20-9e21-3ae79aefd7ba.jpg
         * cardFront : http://192.168.31.95/img/71aa6e90-612e-47e2-b341-6164a1b5c683.jpg
         * cardBack : http://192.168.31.95/img/90ab03aa-34f6-4923-8e6f-fba31d4d4ed1.jpg
         * holdCard : http://192.168.31.95/img/99d94a3d-f00c-4af4-b575-6762675d727b.jpg
         * datils : null
         */

        private int id;
        private int franId;
        private String userRole;
        private String name;
        private String phone;
        private String userImg;
        private String cardFront;
        private String cardBack;
        private String holdCard;
        private String datils;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getFranId() {
            return franId;
        }

        public void setFranId(int franId) {
            this.franId = franId;
        }

        public String getUserRole() {
            return userRole;
        }

        public void setUserRole(String userRole) {
            this.userRole = userRole;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getUserImg() {
            return userImg;
        }

        public void setUserImg(String userImg) {
            this.userImg = userImg;
        }

        public String getCardFront() {
            return cardFront;
        }

        public void setCardFront(String cardFront) {
            this.cardFront = cardFront;
        }

        public String getCardBack() {
            return cardBack;
        }

        public void setCardBack(String cardBack) {
            this.cardBack = cardBack;
        }

        public String getHoldCard() {
            return holdCard;
        }

        public void setHoldCard(String holdCard) {
            this.holdCard = holdCard;
        }

        public String getDatils() {
            return datils;
        }

        public void setDatils(String datils) {
            this.datils = datils;
        }
    }
}
