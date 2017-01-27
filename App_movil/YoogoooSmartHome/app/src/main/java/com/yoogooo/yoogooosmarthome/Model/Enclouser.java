package com.yoogooo.yoogooosmarthome.Model;


public class Enclouser {
    String id;
    String id_site;
    String title;
    String image;

    public Enclouser(String id, String id_site, String title, String image) {
        this.id = id;
        this.id_site = id_site;
        this.title = title;
        this.image = image;
    }

    public Enclouser() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_site() {
        return id_site;
    }

    public void setId_site(String id_site) {
        this.id_site = id_site;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
