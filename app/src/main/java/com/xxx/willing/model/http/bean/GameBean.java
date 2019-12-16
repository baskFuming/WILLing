package com.xxx.willing.model.http.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Time: ${Date}
 * Author : mac
 * Des:
 */
public class GameBean implements Serializable {


    private int id;
    private String name;
    private double amount;   //游戏消耗数量
    private double ratio;
    private int enableFlag;  //是否开启
    private String picUrl;   //图片地址
    private String gameUrl;  //游戏路径

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public double getAmount() {
        return amount;
    }


    public double getRatio() {
        return ratio;
    }


    public boolean getEnableFlag() {
        return enableFlag == 1;
    }


    public String getPicUrl() {
        return picUrl;
    }


    public String getGameUrl() {
        return gameUrl;
    }

}
