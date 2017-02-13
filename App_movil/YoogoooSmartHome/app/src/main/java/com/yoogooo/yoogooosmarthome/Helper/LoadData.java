package com.yoogooo.yoogooosmarthome.Helper;

import com.yoogooo.yoogooosmarthome.Model.Control;
import com.yoogooo.yoogooosmarthome.Model.Enclouser;
import com.yoogooo.yoogooosmarthome.Model.Site;
import com.yoogooo.yoogooosmarthome.Single.Globals;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class LoadData {

    public static String loginData(String response){
        Globals global = Globals.getInstance();
        String res = "0";
        try {
            JSONObject json = new JSONObject(response);
            String status = json.getString("status");
            if(status.equals("false")){
                res = "0";
            } else {
                //carga datos basicos de usuario
                global.setUsr(json.getString("usr_id"), json.getString("usr_name"), json.getString("usr_email"), json.getString("usr_service_type"), json.getString("site_id"));
                //carga sitios
                JSONArray sites = new JSONArray(json.getString("sites"));
                ArrayList<Site> listSite = new ArrayList<>();
                for (int i = 0; i < sites.length(); i++) {
                    JSONObject row = sites.getJSONObject(i);
                    Site site = new Site();
                    site.setId(row.getString("id"));
                    site.setName(row.getString("name"));
                    site.setLatitud(row.getString("latitud"));
                    site.setLongitud(row.getString("longitud"));
                    site.setImg(row.getString("img_name"));
                    site.setIp(row.getString("ip"));
                    site.setPort(row.getString("port"));
                    listSite.add(site);
                }
                global.setListSite(listSite);
                //carga enclousers
                JSONArray enclousers = new JSONArray(json.getString("enclousers"));
                ArrayList<Enclouser> listEnclouser = new ArrayList<>();
                for (int i = 0; i < enclousers.length(); i++) {
                    JSONObject row = enclousers.getJSONObject(i);
                    Enclouser enclouser = new Enclouser();
                    enclouser.setTitle(row.getString("name"));
                    enclouser.setImage(row.getString("img"));
                    enclouser.setId_site(row.getString("st_id"));
                    enclouser.setId(row.getString("id"));
                    listEnclouser.add(enclouser);
                }
                global.setListEnclouser(listEnclouser);
                //carga controls
                JSONArray controls = new JSONArray(json.getString("controls"));
                ArrayList<Control> listControl = new ArrayList<>();
                for (int i = 0; i < controls.length(); i++) {
                    JSONObject row = controls.getJSONObject(i);
                    Control control = new Control();
                    control.setId(row.getString("id"));
                    control.setEnc_id(row.getString("enc_id"));
                    control.setName(row.getString("name"));
                    control.setVoice_on(row.getString("voice_on"));
                    control.setVoice_off(row.getString("voice_off"));
                    control.setChannel(row.getString("channel"));
                    control.setState(row.getString("state"));
                    control.setImg(row.getString("img"));
                    listControl.add(control);
                }
                global.setListControl(listControl);
                res = json.getString("usr_id");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            res = "-1";
        }
        return res;
    }
}
