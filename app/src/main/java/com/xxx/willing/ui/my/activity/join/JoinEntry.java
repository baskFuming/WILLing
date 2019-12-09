package com.xxx.willing.ui.my.activity.join;

import java.io.File;
import java.io.Serializable;

public class JoinEntry implements Serializable {

    private String role;
    private String name;
    private String phone;
    private String perIntroduce;

    private File fileFront;
    private File fileBack;
    private File fileHand;

    private File filePhoto;

    JoinEntry() {

    }

    public String isSettingCard() {
        if (fileFront != null && fileBack != null && fileHand != null) {
            return "上传完成";
        } else {
            return "点击上传";
        }
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getPerIntroduce() {
        return perIntroduce;
    }

    public void setPerIntroduce(String perIntroduce) {
        this.perIntroduce = perIntroduce;
    }

    public File getFileFront() {
        return fileFront;
    }

    public void setFileFront(File fileFront) {
        this.fileFront = fileFront;
    }

    public File getFileBack() {
        return fileBack;
    }

    public void setFileBack(File fileBack) {
        this.fileBack = fileBack;
    }

    public File getFileHand() {
        return fileHand;
    }

    public void setFileHand(File fileHand) {
        this.fileHand = fileHand;
    }

    public File getFilePhoto() {
        return filePhoto;
    }

    public void setFilePhoto(File filePhoto) {
        this.filePhoto = filePhoto;
    }
}
