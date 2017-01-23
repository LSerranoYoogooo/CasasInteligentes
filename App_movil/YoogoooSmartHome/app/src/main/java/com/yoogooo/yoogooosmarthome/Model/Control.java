package com.yoogooo.yoogooosmarthome.Model;

public class Control {
    private String name;
    private String voice_control;
    private int state;
    private int logo;
    private int info;

    public Control(String name, String voice_control, int state, int logo, int info) {
        this.name = name;
        this.voice_control = voice_control;
        this.state = state;
        this.logo = logo;
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public String getVoice_control() {
        return voice_control;
    }

    public int getState() {
        return state;
    }

    public int getLogo() {
        return logo;
    }

    public int getInfo(){
        return info;
    }
}
