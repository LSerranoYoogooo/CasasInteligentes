package com.yoogooo.yoogooosmarthome.Model;

public class Control {
    private String id;
    private String enc_id;
    private String name;
    private String voice_on;
    private String voice_off;
    private String channel;
    private String state;
    private String img;

    public Control() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnc_id() {
        return enc_id;
    }

    public void setEnc_id(String enc_id) {
        this.enc_id = enc_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVoice_on() {
        return voice_on;
    }

    public void setVoice_on(String voice_on) {
        this.voice_on = voice_on;
    }

    public String getVoice_off() {
        return voice_off;
    }

    public void setVoice_off(String voice_off) {
        this.voice_off = voice_off;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
