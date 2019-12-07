package com.xxx.willing.ui.my.activity.join;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class JoinEntry implements Serializable {

    private File fileHand;
    private File fileReverse;
    private File filePositive;

    private File filePhoto;

    private List<File> fileList;

    JoinEntry() {

    }

    public void setFilePhoto(File filePhoto) {
        this.filePhoto = filePhoto;
    }

    public void setFileList(List<File> fileList) {
        this.fileList = fileList;
    }

    public void setFileHand(File fileHand) {
        this.fileHand = fileHand;
    }

    public void setFilePositive(File filePositive) {
        this.filePositive = filePositive;
    }

    public void setFileReverse(File fileReverse) {
        this.fileReverse = fileReverse;
    }

    public File getFileHand() {
        return fileHand;
    }

    public File getFilePositive() {
        return filePositive;
    }

    public File getFileReverse() {
        return fileReverse;
    }

    public File getFilePhoto() {
        return filePhoto;
    }

    public List<File> getFileList() {
        return fileList;
    }
}
