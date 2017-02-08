package com.yoogooo.yoogooosmarthome.Helper;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.yoogooo.yoogooosmarthome.Model.Control;
import com.yoogooo.yoogooosmarthome.Single.Globals;
import com.yoogooo.yoogooosmarthome.Single.VolleyS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VoiceControl {
    private static Globals instance;
    ArrayList<Control> controls;
    Globals globals = Globals.getInstance();
    private RequestQueue fRequestQueue;

    public VoiceControl(Context context) {
        controls = globals.getListControl();
        VolleyS volley = VolleyS.getInstance(context);
        fRequestQueue = volley.getRequestQueue();
    }

    public boolean sendComand(String comando){
        boolean res = false;
        for (int i = 0; i < controls.size(); i++) {
            Control ctrl = controls.get(i);
            if(ctrl.getVoice_on().equals(comando)){
                String channel = ctrl.getChannel();
                String comand = "http://"+globals.getIp()+":"+globals.getPort()+"/Salida"+channel+"on";
                RequestControl(comand);
                res = true;
            } else if(ctrl.getVoice_off().equals(comando)) {
                String channel = ctrl.getChannel();
                String comand = "http://"+globals.getIp()+":"+globals.getPort()+"/Salida"+channel+"off";
                RequestControl(comand);
                res = true;
            }
        }
        return res;
    }

    private void  RequestControl (final String comando) {
        final String url = comando;
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error.Response", error.toString());
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        fRequestQueue.add(postRequest);
    }

}
