package com.yoogooo.yoogooosmarthome.UI;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.yoogooo.yoogooosmarthome.Adapter.EnclouserAdapter;
import com.yoogooo.yoogooosmarthome.Helper.VoiceControl;
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
    //doble click para cerrar
    boolean doubleBackToExitPressedOnce = false;
    //
    private VoiceControl voiceC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //inicializador de voley para consultar al server
        VolleyS volley = VolleyS.getInstance(getApplicationContext());
        fRequestQueue = volley.getRequestQueue();
        voiceC = new VoiceControl(getApplicationContext());

        //action floating button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentActionRecognizeSpeech = new Intent(
                        RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                // Configura el Lenguaje (Español-México)
                intentActionRecognizeSpeech.putExtra(
                        RecognizerIntent.EXTRA_LANGUAGE_MODEL, "es-MX");
                try {
                    startActivityForResult(intentActionRecognizeSpeech,
                            RECOGNIZE_SPEECH_ACTIVITY);
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getApplicationContext(),
                            "Tú dispositivo no soporta el reconocimiento por voz",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //menu lateral izquierdo
        //carga de sitios al menu segun el estado actual al hacer login
        loadSites();
        //carga del recinto predeterminado segun info del login
        loadEnclouser(globals.getId_st());
    }

    @Override
    public void onBackPressed() {
        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }*/

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "doble click atras para salir", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 1000);
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
            finish();
        } else if (id == R.id.add_control){
            Intent intent = new Intent(Main.this, Add_control.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.add_enclouser){
            Intent intent = new Intent(Main.this, Add_enclouser.class);
            startActivity(intent);
            finish();
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
    private void loadEnclouser(String siteId){
        ArrayList<Site> listSite = globals.getListSite();
        for (int i = 0; i < listSite.size(); i++) {
            Site site = listSite.get(i);
            if(site.getId().equals(siteId)){
                globals.setIp(site.getIp());
                globals.setPort(site.getPort());
            }
        }
        List enclousersTmp = globals.getListEnclouser();
        ArrayList<Enclouser> listEnclouser = new ArrayList<>();
        for (int i = 0; i < enclousersTmp.size(); i++) {
            Enclouser enclouser = (Enclouser) enclousersTmp.get(i);
            if(enclouser.getId_site().equals(siteId)){
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RECOGNIZE_SPEECH_ACTIVITY:
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> speech = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String strSpeech2Text = speech.get(0);
                    strSpeech2Text = strSpeech2Text.toUpperCase();
                    voiceC.sendComand(strSpeech2Text);
                }
                break;
            default:
                break;
        }
    }

}
