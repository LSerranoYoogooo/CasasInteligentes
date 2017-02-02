package com.yoogooo.yoogooosmarthome.Adapter;


public class ComandVoice {
    private String comando;
    private String channel;

    public ComandVoice(String comando, String channel) {
        this.comando = comando;
        this.channel = channel;
    }

    public String getComando() {
        return comando;
    }

    public void setComando(String comando) {
        this.comando = comando;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
