package com.yoogooo.yoogooosmarthome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import com.yoogooo.yoogooosmarthome.Single.Globals;

public class AddControl extends AppCompatActivity {
    //variable requerida para los controles de voz
    private static final int RECOGNIZE_SPEECH_ACTIVITY = 1;
    private String[] spinner;
    private Spinner tipo;
    private TextView ctrl_name;
    private TextView voice_on;
    private TextView voice_off;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        MenuItem site = menu.findItem(R.id.add_site);
        site.setVisible(false);
        MenuItem control = menu.findItem(R.id.add_control);
        control.setVisible(false);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_control);

        this.spinner = new String[] {
                "Luces", "Toma Corriente", "Tuberias", "Portones", "Puertas", "Piscina", "Aire Condicionado"
        };
        tipo = (Spinner) findViewById(R.id.spn_tipe_control);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinner);
        tipo.setAdapter(adapter);

        ctrl_name  =(TextView) findViewById(R.id.txt_name_control);
        voice_on = (TextView) findViewById(R.id.txt_voice_on);
        voice_off = (TextView) findViewById(R.id.txt_voice_off);

        //boton add control
        Button btnAddCtlr = (Button) findViewById(R.id.btn_add_control);
        btnAddCtlr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Globals global = Globals.getInstance();
                String txt_name_control = ctrl_name.getText().toString();
                String txt_voice_on = voice_on.getText().toString();
                String txt_voice_off = voice_off.getText().toString();

                global.setControl(txt_name_control, txt_voice_on, R.drawable.luz, R.drawable.on, R.drawable.info);

                Intent intent = new Intent(AddControl.this, ControlList.class);
                startActivity(intent);
                finish();
            }
        });
    }
    //acciones de botones en action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                Intent intent2 = new Intent(this, Login.class);
                startActivity(intent2);
                finish();
                break;
            default:
                break;
        }
        return true;
    }


}
