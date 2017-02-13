package com.yoogooo.yoogooosmarthome.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.yoogooo.yoogooosmarthome.Helper.ImgDefault;
import com.yoogooo.yoogooosmarthome.Model.Control;
import com.yoogooo.yoogooosmarthome.Model.Enclouser;
import com.yoogooo.yoogooosmarthome.R;
import com.yoogooo.yoogooosmarthome.Single.Globals;
import java.util.ArrayList;
import java.util.List;

public class EnclouserAdapter extends RecyclerView.Adapter<EnclouserAdapter.EnclouserViewHolder> {
    private List<Enclouser> items;

    public static class EnclouserViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos para cada site
        public ImageView image;
        public TextView enc_id, title;
        private Globals globals = Globals.getInstance();

        public EnclouserViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.image);
            title = (TextView) v.findViewById(R.id.site_name);
            enc_id = (TextView) v.findViewById(R.id.id_enc);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    globals.setId_enc(enc_id.getText().toString());
                    ArrayList<Control> controlsTmp = globals.getListControl();
                    ArrayList<Control> listControls = new ArrayList<>();
                    for (int i = 0; i < controlsTmp.size(); i++) {
                        Control control = (Control) controlsTmp.get(i);
                        if(control.getEnc_id().equals(globals.getId_enc())){
                            listControls.add(control);
                        }
                    }
                    // Obtener el Recycler
                    Context ctx = v.getContext();
                    View rootView = ((Activity)ctx).getWindow().getDecorView().findViewById(android.R.id.content);
                    RecyclerView recycler = (RecyclerView) rootView.findViewById(R.id.principal_recycler);
                    recycler.setHasFixedSize(true);
                    // Usar un administrador para LinearLayout
                    RecyclerView.LayoutManager lManager = new GridLayoutManager(v.getContext(), 2);
                    recycler.setLayoutManager(lManager);
                    // Crear un nuevo adaptador
                    RecyclerView.Adapter adapter = new ControlsAdapter(listControls);
                    recycler.setAdapter(adapter);
                    Menu menu = globals.getMenu();
                    MenuItem addCtrl = menu.findItem(R.id.add_control);
                    MenuItem addEnc = menu.findItem(R.id.add_enclouser);
                    addCtrl.setVisible(true);
                    addEnc.setVisible(false);
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

    //carga de datos a cada card view y posteriormente al layout correspondiente
    @Override
    public EnclouserViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.enclouser_card, viewGroup, false);
        return new EnclouserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(EnclouserViewHolder viewHolder, int i) {
        viewHolder.image.setImageResource(ImgDefault.getImageId(items.get(i).getImage()));
        viewHolder.title.setText(items.get(i).getTitle());
        viewHolder.enc_id.setText(items.get(i).getId());
        viewHolder.enc_id.setInputType(InputType.TYPE_NULL);
        viewHolder.title.setInputType(InputType.TYPE_NULL);
    }
}
