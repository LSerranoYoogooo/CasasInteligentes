package com.yoogooo.yoogooosmarthome.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.List;
import java.util.Map;


public class ControlsAdapter extends RecyclerView.Adapter<ControlsAdapter.ControlViewHolder> {
    private List<Control> items;


    public static class ControlViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos para cada site
        public ImageView image;
        public TextView title;
        public TextView id;
        public ImageView state;
        // variable para las consultas al server
        private RequestQueue fRequestQueue;

        public ControlViewHolder(View v) {
            super(v);
            id = (TextView) v.findViewById(R.id.ctrl_id);
            title = (TextView) v.findViewById(R.id.ctrl_name);
            image = (ImageView) v.findViewById(R.id.ctrl_img);
            state = (ImageView) v.findViewById(R.id.ctrl_state);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    VolleyS volley = VolleyS.getInstance(v.getContext());
                    fRequestQueue = volley.getRequestQueue();
                    //obtencion del id control
                    Globals globals = Globals.getInstance();
                    String idCtrl = id.getText().toString();
                    String channel = globals.getChannelCtrl(idCtrl);
                    String comando = "http://"+globals.getIp()+":"+globals.getPort()+"/Salida"+channel;
                    //envio de peticion al server
                    RequestControl(comando);
                }

                //Consulta al servidor obtener las salas
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
                            //params.put("site_id", site_id);
                            return params;
                        }
                    };
                    fRequestQueue.add(postRequest);
                }
            });//cierre on clic listener
        }
    }

    public ControlsAdapter(List<Control> items){
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //carga de datos a cada card view y posterior mente al layout correspondiente
    @Override
    public ControlsAdapter.ControlViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.control_card, viewGroup, false);
        return new ControlsAdapter.ControlViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ControlsAdapter.ControlViewHolder viewHolder, int i) {
        ImgDefault imgID = new ImgDefault();
        viewHolder.id.setText(items.get(i).getId());
        viewHolder.title.setText(items.get(i).getName());
        viewHolder.image.setImageResource(imgID.getImageId(items.get(i).getImg()));
        if(items.get(i).getState().equals("1")){
            viewHolder.state.setImageResource(R.drawable.on);
        } else {
            viewHolder.state.setImageResource(R.drawable.off);
        }


    }
}
