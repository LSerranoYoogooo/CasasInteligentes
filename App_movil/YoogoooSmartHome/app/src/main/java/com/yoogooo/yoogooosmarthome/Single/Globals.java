package com.yoogooo.yoogooosmarthome.Single;

import android.view.Menu;

import com.yoogooo.yoogooosmarthome.Model.Control;
import com.yoogooo.yoogooosmarthome.Model.Enclouser;
import com.yoogooo.yoogooosmarthome.Model.Site;

import java.util.ArrayList;

public class Globals {
    private static Globals instance;
    // Global variable
    private Menu menu;
    private String usr_id;
    private String usr_name;
    private String usr_email;
    private String usr_service;
    private String id_st;
    private String id_enc;
    private ArrayList<Enclouser> listEnclouser;
    private ArrayList<Site> listSite;
    private ArrayList<Control> listControl;
    // Restrict the constructor from being instantiated
    private Globals(){}

    public static synchronized Globals getInstance(){
        if(instance==null){
            instance=new Globals();
        }
        return instance;
    }

    public String getUsr_id() {
        return usr_id;
    }

    public String getUsr_name() {
        return usr_name;
    }

    public String getUsr_email() {
        return usr_email;
    }

    public String getUsr_service() {
        return usr_service;
    }

    public void setUsr(String usr_id, String usr_name, String usr_email, String usr_service, String site) {
        this.usr_id = usr_id;
        this.usr_name = usr_name;
        this.usr_email = usr_email;
        this.usr_service = usr_service;
        this.id_st = site;
    }

    public String getId_st() {
        return id_st;
    }

    public void setId_st(String id_st) {
        this.id_st = id_st;
    }

    public ArrayList<Enclouser> getListEnclouser() {
        return listEnclouser;
    }

    public void setListEnclouser(ArrayList<Enclouser> listEnclouser) {
        this.listEnclouser = listEnclouser;
    }

    public ArrayList<Site> getListSite() {
        return listSite;
    }

    public void setListSite(ArrayList<Site> listSite) {
        this.listSite = listSite;
    }

    public ArrayList<Control> getListControl() {
        return listControl;
    }

    public void setListControl(ArrayList<Control> listControl) {
        this.listControl = listControl;
    }

    public String getId_enc() {
        return id_enc;
    }

    public void setId_enc(String id_enc) {
        this.id_enc = id_enc;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
