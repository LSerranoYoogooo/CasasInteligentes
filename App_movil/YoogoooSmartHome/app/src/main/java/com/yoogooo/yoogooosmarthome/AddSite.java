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
        MenuItem site = menu.findItem(R.id.add_site);
        site.setVisible(false);
        MenuItem control = menu.findItem(R.id.add_control);
        control.setVisible(false);
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
                global.setSite(site_name, R.drawable.homecontrol);
                Intent intent = new Intent(AddSite.this, Main.class);
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
