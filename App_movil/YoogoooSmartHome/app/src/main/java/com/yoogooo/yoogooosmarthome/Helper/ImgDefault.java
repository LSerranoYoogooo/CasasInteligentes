package com.yoogooo.yoogooosmarthome.Helper;

import com.yoogooo.yoogooosmarthome.R;

public class ImgDefault {

    public static int getImageId(String name){
        int imgId;
        switch (name) {
            case "Luces": //error
                imgId = R.drawable.luz;
                break;
            case "Toma Corriente":
                imgId = R.drawable.tomacorriente;
                break;
            case "Tuberias":
                imgId = R.drawable.tuberia;
                break;
            case "Portones":
                imgId = R.drawable.porton;
                break;
            case "Puertas":
                imgId = R.drawable.puerta;
                break;
            case "Piscina":
                imgId = R.drawable.piscina;
                break;
            case "Aire Condicionado":
                imgId = R.drawable.aire;
                break;
            case "sala":
                imgId = R.drawable.sala;
                break;
            default:
                imgId = R.drawable.luz;
                break;
        }
        return imgId;
    }
}
