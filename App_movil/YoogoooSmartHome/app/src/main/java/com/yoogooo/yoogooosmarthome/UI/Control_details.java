package com.yoogooo.yoogooosmarthome.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.yoogooo.yoogooosmarthome.Helper.ImgDefault;
import com.yoogooo.yoogooosmarthome.Model.Control;
import com.yoogooo.yoogooosmarthome.R;
import com.yoogooo.yoogooosmarthome.Single.Globals;
import com.yoogooo.yoogooosmarthome.Single.VolleyS;
import java.util.HashMap;
import java.util.Map;

public class Control_details extends AppCompatActivity {
    private RequestQueue fRequestQueue;
    private TextView name, on, off, channel;
    private ImageView state, type;
    private Globals globals = Globals.getInstance();
    private Control control;
    private int imgType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.control_details);

        VolleyS volley = VolleyS.getInstance(getApplicationContext());
        fRequestQueue = volley.getRequestQueue();
        control = globals.getControl();
        imgType = ImgDefault.getImageId(control.getImg());

        name = (TextView) findViewById(R.id.txt_ctrl_d_name);
        name.setText(control.getName());
        on = (TextView) findViewById(R.id.txt_ctrl_d_on);
        on.setText(control.getVoice_on());
        off = (TextView) findViewById(R.id.txt_ctrl_d_off);
        off.setText(control.getVoice_off());
        channel = (TextView) findViewById(R.id.txt_ctrl_d_channel);
        channel.setText(control.getChannel());
        state = (ImageView) findViewById(R.id.img_ctrl_d_state);
        if(control.getState().equals("0")){
            state.setImageResource(R.drawable.off);
        } else {
            state.setImageResource(R.drawable.on);
        }
        type = (ImageView) findViewById(R.id.img_ctrl_d_type);
        type.setImageResource(imgType);


        state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comando = "http://"+globals.getIp()+":"+globals.getPort()+"/Salida"+control.getChannel();
                //envio de peticion al server
                RequestControl(comando);
            }
        });
    }

    //codigo para el boton de atras
    @Override
    public void onBackPressed() {
        finish();
    }

    //Consulta al servidor obtener las salas
    private void  RequestControl (final String comando) {
        final String url = comando;
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                int x;
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
                //params.put("site_id", site_id);
                return params;
            }
        };
        fRequestQueue.add(postRequest);
    }
}
