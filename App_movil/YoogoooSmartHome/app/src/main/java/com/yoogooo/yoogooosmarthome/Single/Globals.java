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
    private String usr_id, usr_name, usr_email, usr_service, ip, port, id_st, id_enc;
    private ArrayList<Enclouser> listEnclouser;
    private ArrayList<Site> listSite;
    private ArrayList<Control> listControl;
    private Control control;
    private Enclouser enclouser;
    // Restrict the constructor from being instantiated
    private Globals(){}
    //intanciador de la clase bajo los estandares de una clase singleton
    public static synchronized Globals getInstance(){
        if(instance==null){
            instance=new Globals();
        }
        return instance;
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

    public Control getCtrl(String ctrlId){
        ArrayList<Control> listControl = this.listControl;
        Control ctrl = null;
        for (int i = 0; i < listControl.size(); i++) {
            Control ctrlTmp = listControl.get(i);
            if(ctrlTmp.getId().equals(ctrlId)){
                ctrl = ctrlTmp;
            }
        }
        return ctrl;
    }

    public Control getControl() {
        return control;
    }

    public void setControl(Control control) {
        this.control = control;
    }

    public void logOut(){
        this.menu = null;
        this.usr_id = null;
        this.usr_name  = null;
        this.usr_email = null;
        this.usr_service  = null;
        this.ip  = null;
        this.port  = null;
        this.id_st  = null;
        this.id_enc  = null;
        this.listEnclouser  = null;
        this.listSite  = null;
        this.listControl  = null;
    }

    public Enclouser getEnclouserId(String encId){
        ArrayList<Enclouser> listEnclouser = this.listEnclouser;
        Enclouser enc = null;
        for (int i = 0; i < listEnclouser.size(); i++) {
            Enclouser encTmp = listEnclouser.get(i);
            if(encTmp.getId().equals(encId)){
                enc = encTmp;
            }
        }
        return enc;
    }

    public Enclouser getEnclouser() {
        return enclouser;
    }

    public void setEnclouser(Enclouser enclouser) {
        this.enclouser = enclouser;
    }

    public void destroyEnclouser(){
        this.enclouser = null;
    }
}
