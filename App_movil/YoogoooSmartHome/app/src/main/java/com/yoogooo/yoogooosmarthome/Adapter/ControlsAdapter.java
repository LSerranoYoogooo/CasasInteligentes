package com.yoogooo.yoogooosmarthome.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.yoogooo.yoogooosmarthome.Helper.ImgDefault;
import com.yoogooo.yoogooosmarthome.Model.Control;
import com.yoogooo.yoogooosmarthome.R;
import com.yoogooo.yoogooosmarthome.Single.Globals;
import com.yoogooo.yoogooosmarthome.UI.Control_details;
import java.util.List;

public class ControlsAdapter extends RecyclerView.Adapter<ControlsAdapter.ControlViewHolder> {
    private List<Control> items;

    public static class ControlViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos para cada site
        public ImageView image, state;
        public TextView title, id;

        public ControlViewHolder(View v) {
            super(v);
            id = (TextView) v.findViewById(R.id.ctrl_id);
            title = (TextView) v.findViewById(R.id.ctrl_name);
            image = (ImageView) v.findViewById(R.id.ctrl_img);
            state = (ImageView) v.findViewById(R.id.ctrl_state);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //obtencion del id control
                    Globals globals = Globals.getInstance();
                    Control control = globals.getCtrl(id.getText().toString());
                    globals.setControl(control);

                    Intent intent = new Intent(v.getContext().getApplicationContext(), Control_details.class);
                    v.getContext().startActivity(intent);
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
