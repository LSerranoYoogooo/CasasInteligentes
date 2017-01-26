package com.yoogooo.yoogooosmarthome.UI;

import android.content.Intent;
import android.support.design.widget.Snackbar;
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

public class Add_enclouser extends AppCompatActivity {
    TextView name;
    private RequestQueue fRequestQueue;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_enclouser);
        view = findViewById(R.id.activity_login);

        VolleyS volley = VolleyS.getInstance(getApplicationContext());
        fRequestQueue = volley.getRequestQueue();

        name = (TextView) findViewById(R.id.enclouser_name);
        //boton agregar sitio
        Button btnAddSite = (Button) findViewById(R.id.btnAddEnclouser);
        btnAddSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Globals global = Globals.getInstance();
                //Strings provicionales
                Request(global.getId_st(), name.getText().toString(), "homecontrol_ejm", view);
            }
        });
    }

    private void  Request (final String site_id, final String enclouser_name, final String img, final View v) {
        final String url = "http://www.demomp2015.yoogooo.com/Smart_Home/WB/set_enclouser.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json = new JSONObject(response);
                            String status = json.getString("status");
                            if (status.equals("true")){
                                Snackbar.make(v, "Sala agregada con exito", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                Intent intent = new Intent(Add_enclouser.this, Main.class);
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
                params.put("site_id", site_id);
                params.put("name", enclouser_name);
                params.put("img", img);
                return params;
            }
        };
        fRequestQueue.add(postRequest);
    }

}
