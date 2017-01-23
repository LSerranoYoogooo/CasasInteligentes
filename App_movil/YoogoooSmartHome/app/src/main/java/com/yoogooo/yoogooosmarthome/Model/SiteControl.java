package com.yoogooo.yoogooosmarthome.Model;


public class SiteControl {
    String title;
    int image;

    public SiteControl(String title, int image) {
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
