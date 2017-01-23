package com.yoogooo.yoogooosmarthome.Adapter;

import android.support.v7.widget.RecyclerView;
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
import java.util.List;


public class ControlsAdapter extends RecyclerView.Adapter<ControlsAdapter.ControlViewHolder> {
    private List<Control> items;

    public static class ControlViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos para cada site
        public ImageView image;
        public TextView title;
        public TextView voice_comand;
        public ImageView state;
        public  ImageView detail;
        private RequestQueue fRequestQueue;

        public ControlViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.img_control);
            state = (ImageView) v.findViewById(R.id.img_state);
            detail = (ImageView) v.findViewById(R.id.img_detail);
            title = (TextView) v.findViewById(R.id.name_control);
            voice_comand = (TextView) v.findViewById(R.id.comand_voice);
            //action en al card view para ejecutar cosas cobre ese control
            /*v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Request("Salida8");
                }
            });*/
        }

       /* private void  Request (String valor) {
            final String url = "http://186.4.46.122" + valor;
            StringRequest postRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }
            ) {

            };
            fRequestQueue.add(postRequest);
        }*/
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
        viewHolder.title.setText(items.get(i).getName());
        viewHolder.voice_comand.setText(items.get(i).getVoice_control());
        viewHolder.image.setImageResource(items.get(i).getLogo());
        viewHolder.state.setImageResource(items.get(i).getState());
        viewHolder.detail.setImageResource(items.get(i).getInfo());
    }
}
