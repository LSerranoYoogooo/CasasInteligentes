package com.yoogooo.yoogooosmarthome.Model;


public class SiteControl {
    String title;
    int image;
    boolean realState;

    public SiteControl(String title, int image, boolean realState) {
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public int getImage() {
        return image;
    }

}
