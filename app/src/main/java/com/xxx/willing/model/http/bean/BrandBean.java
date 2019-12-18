package com.xxx.willing.model.http.bean;

import java.io.Serializable;
import java.util.List;

public class BrandBean implements Serializable {

    private Integer id;

    private String name;

    private List<String> img;

    private String phone;

    private String details;

    private String gviName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public  List<String> getImg() {
        return img;
    }

    public void setImg( List<String> img) {
        this.img = img;
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

    public String getGVIName() {
        return gviName;
    }
}
