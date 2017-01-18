package com.yoogooo.yoogooosmarthome.Single;

import com.yoogooo.yoogooosmarthome.Model.SiteControl;
import java.util.ArrayList;
import java.util.List;

public class Globals {
    private static Globals instance;
    // Global variable
    List items = new ArrayList();
    // Restrict the constructor from being instantiated
    private Globals(){}

    public static synchronized Globals getInstance(){
        if(instance==null){
            instance=new Globals();
        }
        return instance;
    }

    public void setItems(String name, int img, boolean state) {
        this.items.add(new SiteControl(name, img, state));
    }

    public List getItems() {
        return items;
    }
}
