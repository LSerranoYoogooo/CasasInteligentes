package com.yoogooo.yoogooosmarthome.UI;

import android.app.Activity;
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
import com.yoogooo.yoogooosmarthome.Helper.LoadData;
import com.yoogooo.yoogooosmarthome.R;
import com.yoogooo.yoogooosmarthome.Single.Globals;
import com.yoogooo.yoogooosmarthome.Single.VolleyS;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Splash extends Activity {
    private static int SPLASH_TIME_OUT = 4000;
    private Globals globals = Globals.getInstance();
    private RequestQueue fRequestQueue;
    private View view;

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
                        String res = LoadData.loginData(response);//funcion auxiliar
                        if(res.equals("-1")){
                            Snackbar.make(v, "server error", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        } else if (res.equals("0")){
                            Snackbar.make(v, "Datos incorrectos", Snackbar.LENGTH_LONG)
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
