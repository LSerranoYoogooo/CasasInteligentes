package com.yoogooo.yoogooosmarthome.Single;

import com.yoogooo.yoogooosmarthome.Model.Control;
import com.yoogooo.yoogooosmarthome.Model.SiteControl;
import java.util.ArrayList;
import java.util.List;

import static android.support.design.R.id.image;

public class Globals {
    private static Globals instance;
    // Global variable
    List sites = new ArrayList();
    List controls = new ArrayList();
    // Restrict the constructor from being instantiated
    private Globals(){}

    public static synchronized Globals getInstance(){
        if(instance==null){
            instance=new Globals();
        }
        return instance;
    }

    public void setSite(String name, int img) {
        this.sites.add(new SiteControl(name, img));
    }

    public List getSites() {
        return sites;
    }

    public void setControl(String name, String voice_control, int img, int state, int info) {
        this.controls.add(new Control(name, voice_control, img, state, info));
    }

    public List getControls() {
        return controls;
    }
}
