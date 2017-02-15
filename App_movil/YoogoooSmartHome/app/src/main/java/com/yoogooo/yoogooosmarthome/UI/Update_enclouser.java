package com.yoogooo.yoogooosmarthome.UI;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.yoogooo.yoogooosmarthome.Model.Enclouser;
import com.yoogooo.yoogooosmarthome.R;
import com.yoogooo.yoogooosmarthome.Single.Globals;

public class Update_enclouser extends AppCompatActivity {
    private Globals globals = Globals.getInstance();
    private Enclouser enclouser = globals.getEnclouser();
    private TextView name;
    private com.getbase.floatingactionbutton.FloatingActionButton update, delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updat_enclouser);

        name = (TextView) findViewById(R.id.up_enclouser_name);
        name.setText(enclouser.getTitle());

        //action floating button
        update = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.float_up_update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Update", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        delete = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.float_up_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Delete", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
    }
    //codigo para el boton de atras
    @Override
    public void onBackPressed() {
        globals.destroyEnclouser();
        finish();
    }
}
