package com.yoogooo.yoogooosmarthome.UI;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.yoogooo.yoogooosmarthome.Model.Site;
import com.yoogooo.yoogooosmarthome.R;
import com.yoogooo.yoogooosmarthome.Single.Globals;
import com.yoogooo.yoogooosmarthome.Single.VolleyS;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Add_site extends AppCompatActivity {
    private TextView name, ip, port;
    private RequestQueue fRequestQueue;
    private View view;
    private Globals global;
    private Site site;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_site);
        //inicializadores elementos
        VolleyS volley = VolleyS.getInstance(getApplicationContext());
        fRequestQueue = volley.getRequestQueue();
        //obtencion del view actual
        view = findViewById(R.id.content_main);
        global = Globals.getInstance();

        name = (TextView) findViewById(R.id.site_name);
        ip = (TextView) findViewById(R.id.ip_add);
        port = (TextView) findViewById(R.id.ip_port);
        //boton agregar sitio
        Button btnAddSite = (Button) findViewById(R.id.btnAddSite);
        btnAddSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Strings provicionales
                site = new Site(global.getUsr_id(), name.getText().toString(), "0.000.000", "0.000.000", "homecontrol_ejm", ip.getText().toString(), port.getText().toString());
                Request(site , view);
            }
        });

    }
    //codigo para el boton de atras
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Add_site.this, Main.class);
        startActivity(intent);
        finish();
    }
    //envio de datos al servidor
    private void  Request (final Site site, final View v) {
        final String url = "http://www.demomp2015.yoogooo.com/Smart_Home/WB/set_site.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    String status = json.getString("status");
                    if (status.equals("true")){
                        Snackbar.make(v, "Sitio agregado", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        RequestAux(site.getId());
                    }
                } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                        Snackbar.make(v, "Error al agregar sitio a su cuenta", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                params.put("user", site.getId()); //site.getId retorna el id del sitio, sin embargo en este caso almacena el id del usr al que pertenece sitio
                params.put("name", site.getName());
                params.put("lat", site.getLatitud());
                params.put("lon", site.getLongitud());
                params.put("img", site.getImg());
                params.put("ip", site.getIp());
                params.put("port", site.getPort());
                return params;
            }
        };
        fRequestQueue.add(postRequest);
    }

    private void  RequestAux (final String usr_id) {
        final String url = "http://www.demomp2015.yoogooo.com/Smart_Home/WB/get_site_user.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonA = new JSONArray(response);
                    ArrayList<Site> listSite = new ArrayList<>();
                    for (int i = 0; i < jsonA.length(); i++) {
                        Site site = new Site();
                        JSONObject row = jsonA.getJSONObject(i);
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
                    Intent intent = new Intent(Add_site.this, Main.class);
                    startActivity(intent);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener()
                {
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
                params.put("user", usr_id);
                return params;
            }
        };
        fRequestQueue.add(postRequest);
    }
}
