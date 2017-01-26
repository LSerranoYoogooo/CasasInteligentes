package com.yoogooo.yoogooosmarthome.UI;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.yoogooo.yoogooosmarthome.Helper.InternetStatus;
import com.yoogooo.yoogooosmarthome.R;
import com.yoogooo.yoogooosmarthome.Single.Globals;
import com.yoogooo.yoogooosmarthome.Single.VolleyS;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    private TextView usr;
    private TextView pass;
    private RequestQueue fRequestQueue;
    private View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = findViewById(R.id.activity_login);
        //ocultar Action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.login);

        //boton fotante para agregar nuevos usuarios
        /*FloatingActionButton addUsr = (FloatingActionButton) findViewById(R.id.addUsr);
        addUsr.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });*/

        //boton de inicio de sesion
        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Dialog dialog = new Dialog(getApplicationContext());
                VolleyS volley = VolleyS.getInstance(getApplicationContext());
                fRequestQueue = volley.getRequestQueue();
                usr = (TextView) findViewById(R.id.txt_user);
                pass = (TextView) findViewById(R.id.txt_pass);
                if(InternetStatus.isOnline(getApplicationContext())){
                    //dialog.init();
                    Request(usr.getText().toString(), pass.getText().toString(), view);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Sin coneccion a internet", Toast.LENGTH_SHORT);
                    toast.show();
                }
                //dialog.close();
            }
        });
    }

    private void  Request (final String user, final String pass, final View v) {
        final String url = "http://www.demomp2015.yoogooo.com/Smart_Home/WB/get_user.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json = new JSONObject(response);
                            String status = json.getString("status");
                            if (status.equals("false")){
                                Snackbar.make(v, "Email o Contraseña incorrectos", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                            Globals global = Globals.getInstance();
                            global.setUsr(json.getString("id"), json.getString("name"), json.getString("email"), json.getString("service_type"), json.getString("site_id"));
                            Intent intent = new Intent(Login.this, Main.class);
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
                params.put("email", user);
                params.put("pass", pass);
                return params;
            }
        };
        fRequestQueue.add(postRequest);
    }


}
