package com.yoogooo.yoogooosmarthome.Adapter;

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
import com.yoogooo.yoogooosmarthome.Model.Control;
import com.yoogooo.yoogooosmarthome.R;
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
                    Snackbar.make(v, "envio datos -->" + id.getText(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    VolleyS volley = VolleyS.getInstance(v.getContext());
                    fRequestQueue = volley.getRequestQueue();
                    //envio de peticion al server
                    RequestControl("Salida8");
                }

                //Consulta al servidor obtener las salas
                private void  RequestControl (final String channel) {
                    final String url = "http://186.4.46.122:81/" + channel;
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
        viewHolder.id.setText(items.get(i).getId());
        viewHolder.title.setText(items.get(i).getName());
        //viewHolder.image.setImageResource(R.drawable.aire_ejm);
        if(items.get(i).getState().equals("1")){
            viewHolder.state.setImageResource(R.color.state_on);
        } else {
            viewHolder.state.setImageResource(R.color.state_off);
        }

    }
}
