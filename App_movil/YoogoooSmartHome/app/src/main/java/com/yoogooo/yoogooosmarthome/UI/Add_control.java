package com.yoogooo.yoogooosmarthome.UI;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
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
import com.yoogooo.yoogooosmarthome.Model.Control;
import com.yoogooo.yoogooosmarthome.R;
import com.yoogooo.yoogooosmarthome.Single.Globals;
import com.yoogooo.yoogooosmarthome.Single.VolleyS;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
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
        final TextInputLayout tilCN = (TextInputLayout) findViewById(R.id.til_ctrl_name);
        final TextInputLayout tilVN = (TextInputLayout) findViewById(R.id.til_ctrl_v_on);
        final TextInputLayout tilVF = (TextInputLayout) findViewById(R.id.til_ctrl_v_off);
        final TextInputLayout tilCH = (TextInputLayout) findViewById(R.id.til_ctrl_channel);
        final TextInputLayout tilSP = (TextInputLayout) findViewById(R.id.til_spnType);

        //boton agregar sitio
        Button btnAddSite = (Button) findViewById(R.id.btn_add_control);
        btnAddSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validateString(ctrl_name.getText().toString())){
                    tilCN.setError("Nombre requerido");
                } else if(!validateString(voice_on.getText().toString())){
                    tilVN.setError("comando de voz requerido");
                    tilCN.setErrorEnabled(false);
                } else if(!validateString(voice_off.getText().toString())){
                    tilVF.setError("comando de voz requerido");
                    tilCN.setErrorEnabled(false);
                    tilVN.setErrorEnabled(false);
                } else if(!validateString(channel.getText().toString())){
                    tilSP.setError("debe elegir un tipo de control");
                    tilCN.setErrorEnabled(false);
                    tilVN.setErrorEnabled(false);
                    tilVF.setErrorEnabled(false);

                } else {
                    if (!validateString(channel.getText().toString())) {
                        tilCH.setError("Canal de control requerido");
                        tilCN.setErrorEnabled(false);
                        tilVN.setErrorEnabled(false);
                        tilVF.setErrorEnabled(false);
                        tilSP.setErrorEnabled(false);
                    } else {
                        boolean VN = voiceExistente(voice_on.getText().toString());
                        boolean VF = voiceExistente(voice_off.getText().toString());
                        boolean CH = usedChannel(channel.getText().toString());
                        if (!VN && !VF && !CH) {
                            tilCN.setErrorEnabled(false);
                            tilVN.setErrorEnabled(false);
                            tilVF.setErrorEnabled(false);
                            tilCH.setErrorEnabled(false);
                            tilSP.setErrorEnabled(false);
                            String type = tipo.getSelectedItem().toString();
                            Globals global = Globals.getInstance();
                            //Strings provicionales
                            Request(global.getId_enc(), ctrl_name.getText().toString(), voice_on.getText().toString().toUpperCase(), voice_off.getText().toString().toUpperCase(), channel.getText().toString(), type, view);
                        } else {
                            if (CH) {
                                Snackbar.make(view, "Canal en uso", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            } else {
                                Snackbar.make(view, "Comando de vos ya en uso", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        }

                    }
                }
            }
        });
    }
    //codigo para el boton de atras
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Add_control.this, Main.class);
        startActivity(intent);
        finish();
    }

    private void  Request (final String enc_id, final String name, final String voice_on, final String voice_off, final String channel, final String img, final View v) {
        final String url = "http://www.demomp2015.yoogooo.com/Smart_Home/WB/set_control.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Globals globals = Globals.getInstance();
                            JSONObject json = new JSONObject(response);
                            String status = json.getString("status");
                            if (status.equals("true")){
                                Snackbar.make(v, "Control agregado con exito", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                RequestAux(globals.getUsr_id());
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

    private void  RequestAux (final String usr_id) {
        final String url = "http://www.demomp2015.yoogooo.com/Smart_Home/WB/get_control_user.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Globals global = Globals.getInstance();
                            //carga enclousers
                            JSONArray controls = new JSONArray(response);
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
                            Intent intent = new Intent(Add_control.this, Main.class);
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
                params.put("usr_id", usr_id);
                return params;
            }
        };
        fRequestQueue.add(postRequest);
    }

    private boolean validateString(String valor) {
        return valor.length() >= 1;
    }

    private boolean voiceExistente(String voice){
        boolean res = false;
        Globals global = Globals.getInstance();
        ArrayList<Control> listControl = global.getListControl();
        for (int i = 0; i < listControl.size(); i++) {
            Control ctrl = listControl.get(i);
            if(ctrl.getVoice_on().equals(voice)){
                res = true;
            }
        }
        for (int i = 0; i < listControl.size(); i++) {
            Control ctrl = listControl.get(i);
            if(ctrl.getVoice_off().equals(voice)){
                res = true;
            }
        }
        return res;
    }

    private boolean usedChannel(String channel){
        boolean res = false;
        Globals global = Globals.getInstance();
        ArrayList<Control> listControl = global.getListControl();
        for (int i = 0; i < listControl.size(); i++) {
            Control ctrl = listControl.get(i);
            if(ctrl.getChannel().equals(channel)){
                res = true;
            }
        }
        return res;
    }

}
