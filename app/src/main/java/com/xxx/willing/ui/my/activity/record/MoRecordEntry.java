package com.xxx.willing.ui.my.activity.record;

public class MoRecordEntry {

    private String name;

    private int type;

    MoRecordEntry(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
