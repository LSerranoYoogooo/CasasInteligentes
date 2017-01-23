package com.yoogooo.yoogooosmarthome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.yoogooo.yoogooosmarthome.Adapter.ControlsAdapter;
import com.yoogooo.yoogooosmarthome.Adapter.SiteAdapter;
import com.yoogooo.yoogooosmarthome.Single.Globals;

import java.util.List;

public class ControlList extends AppCompatActivity {
    // Declarar instancias globales para acceder al Recicler
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    // instancia global con datos de session
    Globals globals = Globals.getInstance();

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
        setContentView(R.layout.activity_control_list);

        loadControls(globals.getControls());
    }
    //encargado de cargar los elementos al reclycler
    private void loadControls(List items){
        List itemss = items;
        // Obtener el Recycler
        recycler = (RecyclerView) findViewById(R.id.recycler_control);
        recycler.setHasFixedSize(true);
        // Usar un administrador para LinearLayout
        lManager = new GridLayoutManager(this, 2);
        recycler.setLayoutManager(lManager);
        // Crear un nuevo adaptador
        adapter = new ControlsAdapter(items);
        recycler.setAdapter(adapter);
    }
    //acciones de botones en action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_control:
                Intent intent = new Intent(ControlList.this, AddControl.class);
                startActivity(intent);
                finish();
                break;
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
