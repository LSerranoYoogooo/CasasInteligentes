package com.yoogooo.yoogooosmarthome.UI;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

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

public class Add_control extends AppCompatActivity {
    private String[] spinner;
    private Spinner tipo;
    private TextView ctrl_name, voice_on, voice_off, channel;
    private RequestQueue fRequestQueue;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_control);
        view = findViewById(R.id.add_control);
        VolleyS volley = VolleyS.getInstance(getApplicationContext());
        fRequestQueue = volley.getRequestQueue();
        //carga de opciones al spinner
        this.spinner = new String[] {
                "Luces", "Toma Corriente", "Tuberias", "Portones", "Puertas", "Piscina", "Aire Condicionado"
        };
        tipo = (Spinner) findViewById(R.id.spn_ctrl_type);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinner);
        tipo.setAdapter(adapter);

        ctrl_name = (TextView) findViewById(R.id.txt_ctrl_name);
        voice_on = (TextView) findViewById(R.id.txt_ctrl_on);
        voice_off = (TextView) findViewById(R.id.txt_ctrl_off);
        channel = (TextView) findViewById(R.id.txt_ctrl_channel);


        //boton agregar sitio
        Button btnAddSite = (Button) findViewById(R.id.btn_add_control);
        btnAddSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Globals global = Globals.getInstance();
                //Strings provicionales
                Request(global.getId_enc(), ctrl_name.getText().toString(), voice_on.getText().toString(), voice_off.getText().toString(), channel.getText().toString(), "1", view);
            }
        });
    }

    private void  Request (final String enc_id, final String name, final String voice_on, final String voice_off, final String channel, final String img, final View v) {
        final String url = "http://www.demomp2015.yoogooo.com/Smart_Home/WB/set_control.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json = new JSONObject(response);
                            String status = json.getString("status");
                            if (status.equals("true")){
                                Snackbar.make(v, "Control agregado con exito", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                Intent intent = new Intent(Add_control.this, Main.class);
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
                params.put("enc_id", enc_id);
                params.put("name", name);
                params.put("voice_on", voice_on);
                params.put("voice_off", voice_off);
                params.put("channel", channel);
                params.put("img", img);
                return params;
            }
        };
        fRequestQueue.add(postRequest);
    }
}
