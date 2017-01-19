package com.yoogooo.yoogooosmarthome.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yoogooo.yoogooosmarthome.Model.SiteControl;
import com.yoogooo.yoogooosmarthome.R;

import java.util.List;

public class SiteAdapter extends RecyclerView.Adapter<SiteAdapter.SiteViewHolder> {
    private List<SiteControl> items;

    public static class SiteViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos para cada site
        public ImageView image;
        public TextView title;

        public SiteViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.image);
            title = (TextView) v.findViewById(R.id.title);
        }
    }

    public SiteAdapter(List<SiteControl> items){
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //carga de datos a cada card view y posterior mente al layout correspondiente
    @Override
    public SiteViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.site_card, viewGroup, false);
        return new SiteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SiteViewHolder viewHolder, int i) {
        viewHolder.image.setImageResource(items.get(i).getImage());
        viewHolder.title.setText(items.get(i).getTitle());
    }
}
