package com.yoogooo.yoogooosmarthome.Model;


public class Enclouser {
    String title;
    String id_site;
    int image;

    public Enclouser(String title, int image, String id_st) {
        this.title = title;
        this.image = image;
        this.id_site = id_st;
    }

    public Enclouser() {
    }

    public String getTitle() {
        return title;
    }

    public int getImage() {
        return image;
    }

    public String getId_site() {
        return id_site;
    }

    public void setId_site(String id_site) {
        this.id_site = id_site;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
