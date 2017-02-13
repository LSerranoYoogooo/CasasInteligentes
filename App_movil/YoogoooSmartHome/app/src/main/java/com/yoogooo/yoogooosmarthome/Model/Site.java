package com.yoogooo.yoogooosmarthome.Model;

public class Site {
    private String id, name, latitud, longitud, img, ip, port;

    public Site() {
    }

    public Site(String id, String name, String latitud, String longitud, String img, String ip, String port) {
        this.id = id;
        this.name = name;
        this.latitud = latitud;
        this.longitud = longitud;
        this.img = img;
        this.ip = ip;
        this.port = port;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
