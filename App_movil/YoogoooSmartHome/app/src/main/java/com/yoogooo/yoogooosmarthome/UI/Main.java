package com.yoogooo.yoogooosmarthome.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.RequestQueue;
import com.yoogooo.yoogooosmarthome.Adapter.EnclouserAdapter;
import com.yoogooo.yoogooosmarthome.Model.Enclouser;
import com.yoogooo.yoogooosmarthome.Model.Site;
import com.yoogooo.yoogooosmarthome.R;
import com.yoogooo.yoogooosmarthome.Single.Globals;
import com.yoogooo.yoogooosmarthome.Single.VolleyS;
import java.util.ArrayList;
import java.util.List;

import static com.yoogooo.yoogooosmarthome.R.menu.main;

public class Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //variable de acceso al menu
    NavigationView navigationView;
    // Declarar instancias globales para acceder al Recicler
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    //variable requerida para los controles de voz
    private static final int RECOGNIZE_SPEECH_ACTIVITY = 1;
    // instancia global con datos de session
    Globals globals = Globals.getInstance();
    // variable para las consultas al server
    private RequestQueue fRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //inicializador de voley para consultar al server
        VolleyS volley = VolleyS.getInstance(getApplicationContext());
        fRequestQueue = volley.getRequestQueue();

        //action floating button
       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //menu lateral derecho
        //carga de sitios al menu segun el estado actual al hacer login
        loadSites();
        //carga del recinto predeterminado segun info del login
        loadEnclouser(globals.getId_st());
        //RequestSites(globals.getUsr_id(), navigationView);
        //(globals.getId_st(), navigationView );
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(main, menu);
        MenuItem addCtrl = menu.findItem(R.id.add_control);
        MenuItem addEnc = menu.findItem(R.id.add_enclouser);
        addCtrl.setVisible(false);
        addEnc.setVisible(true);
        Globals globals = Globals.getInstance();
        globals.setMenu(menu);
        return true;
    }

    @Override
    //menu lateral derecho
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //elementos del menu lateral derecho
        if (id == R.id.action_logout) {
            Intent intent = new Intent(Main.this, Login.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.action_add_site){
            Intent intent = new Intent(Main.this, Add_site.class);
            startActivity(intent);
        } else if (id == R.id.add_control){
            Intent intent = new Intent(Main.this, Add_control.class);
            startActivity(intent);
        } else if (id == R.id.add_enclouser){
            Intent intent = new Intent(Main.this, Add_enclouser.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    //menu lateral izquierdo
    public boolean onNavigationItemSelected(MenuItem item) {
        globals.setId_st(Integer.toString(item.getItemId()));
        loadEnclouser(globals.getId_st());

        Menu menu = globals.getMenu();
        MenuItem addCtrl = menu.findItem(R.id.add_control);
        MenuItem addEnc = menu.findItem(R.id.add_enclouser);
        addCtrl.setVisible(false);
        addEnc.setVisible(true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        //cargar recintos
        return true;
    }

    private void loadSites(){
        List sites = globals.getListSite();
        Menu menu = navigationView.getMenu();
        for (int i = 0; i < sites.size(); i++) {
            Site site = (Site) sites.get(i);
            menu.add(R.id.drawer_menu, Integer.parseInt(site.getId()), Menu.NONE, site.getName());
        }
    }
    //carga de enclouser al recycler
    private void loadEnclouser(String enclouserid){
        List enclousersTmp = globals.getListEnclouser();
        ArrayList<Enclouser> listEnclouser = new ArrayList<>();
        for (int i = 0; i < enclousersTmp.size(); i++) {
            Enclouser enclouser = (Enclouser) enclousersTmp.get(i);
            if(enclouser.getId_site().equals(enclouserid)){
                listEnclouser.add(enclouser);
            }
        }
        // Obtener el Recycler
        recycler = (RecyclerView) findViewById(R.id.principal_recycler);
        recycler.setHasFixedSize(true);
        // Usar un administrador para LinearLayout
        lManager = new GridLayoutManager(this, 2);
        recycler.setLayoutManager(lManager);
        // Crear un nuevo adaptador
        adapter = new EnclouserAdapter(listEnclouser);
        recycler.setAdapter(adapter);
    }

}
