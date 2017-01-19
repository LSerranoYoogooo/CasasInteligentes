package com.yoogooo.yoogooosmarthome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yoogooo.yoogooosmarthome.Single.Globals;

public class AddSite extends AppCompatActivity {
    TextView name;
    //
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.add_site);
        item.setVisible(false);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_site);

        name = (TextView) findViewById(R.id.site_name);

        //boton de inicio de sesion
        Button btnLogin = (Button) findViewById(R.id.btnAddSite);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String site_name = name.getText().toString();
                Globals global = Globals.getInstance();
                global.setItems(site_name, R.drawable.homecontrol, true);
                Intent intent = new Intent(AddSite.this, Main.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
