package com.yoogooo.yoogooosmarthome.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.yoogooo.yoogooosmarthome.R;
import com.yoogooo.yoogooosmarthome.Single.Globals;
import com.yoogooo.yoogooosmarthome.Single.VolleyS;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Add_site extends AppCompatActivity {
    TextView name, ip, port;
    private RequestQueue fRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_site);
        //inicializadores elementos
        VolleyS volley = VolleyS.getInstance(getApplicationContext());
        fRequestQueue = volley.getRequestQueue();

        name = (TextView) findViewById(R.id.site_name);
        ip = (TextView) findViewById(R.id.ip_add);
        port = (TextView) findViewById(R.id.ip_port);
        //boton agregar sitio
        Button btnAddSite = (Button) findViewById(R.id.btnAddSite);
        btnAddSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Globals global = Globals.getInstance();
                //Strings provicionales
                Request(global.getUsr_id(), name.getText().toString(), "0.000.000", "0.000.000", "homecontrol_ejm", ip.getText().toString(), port.getText().toString());
            }
        });

    }
    //envio de datos al servidor
    private void  Request (final String user_id, final String site_name, final String latitud, final String longitud, final String img_name, final String ip, final String port) {
        final String url = "http://www.demomp2015.yoogooo.com/Smart_Home/WB/set_site.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    String status = json.getString("status");
                    if (status.equals("true")){
                        Toast toast = Toast.makeText(getApplicationContext(), "Sitio agregado", Toast.LENGTH_SHORT);
                        toast.show();
                        Intent intent = new Intent(Add_site.this, Main.class);
                        startActivity(intent);
                        finish();
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
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                params.put("user", user_id);
                params.put("name", site_name);
                params.put("lat", latitud);
                params.put("lon", longitud);
                params.put("img", img_name);
                params.put("ip", ip);
                params.put("port", port);
                return params;
            }
        };
        fRequestQueue.add(postRequest);
    }

}
