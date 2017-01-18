package com.yoogooo.yoogooosmarthome.Model;


public class SiteControl {
    String title;
    int image;
    boolean realState;

    public SiteControl(String title, int image, boolean realState) {
        this.title = title;
        this.image = image;
        this.realState = realState;
    }

    public String getTitle() {
        return title;
    }

    public int getImage() {
        return image;
    }

    public boolean isRealState() {
        return realState;
    }
}
