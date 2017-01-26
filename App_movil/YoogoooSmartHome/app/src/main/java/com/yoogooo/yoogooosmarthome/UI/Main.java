package com.yoogooo.yoogooosmarthome.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.yoogooo.yoogooosmarthome.Adapter.EnclouserAdapter;
import com.yoogooo.yoogooosmarthome.Model.Enclouser;
import com.yoogooo.yoogooosmarthome.R;
import com.yoogooo.yoogooosmarthome.Single.Globals;
import com.yoogooo.yoogooosmarthome.Single.VolleyS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        //carga de datos al menu
        RequestSites(globals.getUsr_id(), navigationView);
        RequestEnclouser(globals.getId_st(), navigationView );
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
        getMenuInflater().inflate(R.menu.main, menu);
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

        RequestEnclouser(Integer.toString(item.getItemId()), navigationView );
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        //cargar recintos
        return true;
    }

    //carga de enclouser al recycler
    private void loadEnclouser(List enclouser){
        List enclousers = enclouser;
        // Obtener el Recycler
        recycler = (RecyclerView) findViewById(R.id.principal_recycler);
        recycler.setHasFixedSize(true);
        // Usar un administrador para LinearLayout
        lManager = new GridLayoutManager(this, 2);
        recycler.setLayoutManager(lManager);
        // Crear un nuevo adaptador
        adapter = new EnclouserAdapter(enclousers);
        recycler.setAdapter(adapter);
    }

    //Consulta al servidor obtener los sitios y carga de sitios a la UI
    private void  RequestSites (final String user_id, final NavigationView navigationView) {
        final String url = "http://www.demomp2015.yoogooo.com/Smart_Home/WB/get_site_user.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    Menu menu = navigationView.getMenu();
                    JSONArray jsonA = new JSONArray(response);
                    ArrayList<Enclouser> listSite = new ArrayList<>();
                    for (int i = 0; i < jsonA.length(); i++) {
                        JSONObject row = jsonA.getJSONObject(i);
                        menu.add(R.id.drawer_menu, Integer.parseInt(row.getString("id")), Menu.NONE, row.getString("name"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error.Response", error.toString());
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                params.put("user", user_id);
                return params;
            }
        };
        fRequestQueue.add(postRequest);
    }

    //Consulta al servidor obtener las salas
    private void  RequestEnclouser (final String site_id, final NavigationView navigationView) {
        final String url = "http://www.demomp2015.yoogooo.com/Smart_Home/WB/get_enclouser_site.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonA = new JSONArray(response);
                    ArrayList<Enclouser> listEnclouser = new ArrayList<>();
                    for (int i = 0; i < jsonA.length(); i++) {
                        JSONObject row = jsonA.getJSONObject(i);
                        Enclouser enclouser = new Enclouser();
                        enclouser.setTitle(row.getString("name"));
                        enclouser.setId_site(row.getString("id"));
                        enclouser.setImage(R.drawable.homecontrol_ejm);
                        listEnclouser.add(enclouser);
                    }
                    globals.setListEnclouser(listEnclouser);
                    loadEnclouser(globals.getListEnclouser());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error.Response", error.toString());
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                params.put("site_id", site_id);
                return params;
            }
        };
        fRequestQueue.add(postRequest);
    }
}
