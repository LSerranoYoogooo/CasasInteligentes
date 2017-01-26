package com.yoogooo.yoogooosmarthome.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yoogooo.yoogooosmarthome.Model.Enclouser;
import com.yoogooo.yoogooosmarthome.R;
import com.yoogooo.yoogooosmarthome.Single.Globals;
import com.yoogooo.yoogooosmarthome.UI.Main;

import java.util.List;

public class EnclouserAdapter extends RecyclerView.Adapter<EnclouserAdapter.EnclouserViewHolder> {
    private List<Enclouser> items;

    public static class EnclouserViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos para cada site
        public ImageView image;
        public TextView st_id;
        public TextView title;
        private Globals globals = Globals.getInstance();

        public EnclouserViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.image);
            title = (TextView) v.findViewById(R.id.site_name);
            st_id = (TextView) v.findViewById(R.id.id_site);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    globals.setId_st(st_id.getText().toString());
                    v.getContext().startActivity(new Intent(v.getContext(),Main.class));
                }
            });
        }
    }

    public EnclouserAdapter(List<Enclouser> items){
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //carga de datos a cada card view y posterior mente al layout correspondiente
    @Override
    public EnclouserViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.enclouser_card, viewGroup, false);
        return new EnclouserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(EnclouserViewHolder viewHolder, int i) {
        viewHolder.image.setImageResource(items.get(i).getImage());
        viewHolder.title.setText(items.get(i).getTitle());
        viewHolder.st_id.setText(items.get(i).getId_site());
    }
}
