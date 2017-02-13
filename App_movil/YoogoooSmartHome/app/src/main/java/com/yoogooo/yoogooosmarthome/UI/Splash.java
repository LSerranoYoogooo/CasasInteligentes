package com.yoogooo.yoogooosmarthome.UI;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.yoogooo.yoogooosmarthome.Model.Control;
import com.yoogooo.yoogooosmarthome.Model.Enclouser;
import com.yoogooo.yoogooosmarthome.Model.Site;
import com.yoogooo.yoogooosmarthome.R;
import com.yoogooo.yoogooosmarthome.Single.Globals;
import com.yoogooo.yoogooosmarthome.Single.VolleyS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Splash extends Activity {
    private static int SPLASH_TIME_OUT = 4000;
    Globals globals = Globals.getInstance();
    private RequestQueue fRequestQueue;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = findViewById(R.id.activity_splash);
        //comprobacion de session
        String id = globals.getUsr_id();
        if(globals.getUsr_id() != null){
            Intent intent = new Intent(Splash.this, Main.class);
            startActivity(intent);
            finish();
        } else if(globals.getUsr_id() == null){
            //full screen in splash
            try
            {
                BufferedReader fin = new BufferedReader(new InputStreamReader(openFileInput("dataYSH")));
                final String usr_id = fin.readLine();
                fin.close();
                if(!usr_id.equals("0")){
                    VolleyS volley = VolleyS.getInstance(getApplicationContext());
                    fRequestQueue = volley.getRequestQueue();

                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    setContentView(R.layout.splash);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(Splash.this, Main.class);
                            startActivity(intent);
                            finish();
                        }
                    }, SPLASH_TIME_OUT);

                    new Thread(new Runnable() {
                        public void run() {
                            Request(usr_id, view);
                        }
                    }).start();

                } else {
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    setContentView(R.layout.splash);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(Splash.this, Login.class);
                            startActivity(intent);
                            finish();
                        }
                    }, SPLASH_TIME_OUT);
                }

            }
            catch (Exception ex)
            {
                //Log.e("Ficheros", "fichero no encontrado");
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
                setContentView(R.layout.splash);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(Splash.this, Login.class);
                        startActivity(intent);
                        finish();
                    }
                }, SPLASH_TIME_OUT);
            }
        }
    }

    private void  Request (final String user_id,  final View v) {
        //url de consulta
        final String url = "http://www.demomp2015.yoogooo.com/Smart_Home/WB/get_user_id.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Globals global = Globals.getInstance();
                            JSONObject json = new JSONObject(response);
                            String status = json.getString("status");
                            if(status.equals("false")){
                                Snackbar.make(v, "Error al recuperar sus session", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
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
                                //===============================================================
                                try
                                {
                                    OutputStreamWriter fout = new OutputStreamWriter(openFileOutput("dataYSH", Context.MODE_PRIVATE));
                                    fout.write(global.getUsr_id());
                                    fout.close();
                                }
                                catch (Exception ex)
                                {
                                    //Log.e("Ficheros", "Error al escribir fichero a memoria interna");
                                }
                                //=================================================================
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Snackbar.make(v, "Error de conexión", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                        Snackbar.make(v, "Error de conexión", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                params.put("usr_id", user_id);
                return params;
            }
        };
        fRequestQueue.add(postRequest);
    }
}
