package com.yoogooo.yoogooosmarthome;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.yoogooo.yoogooosmarthome.Adapter.SiteAdapter;
import com.yoogooo.yoogooosmarthome.Single.Globals;

import java.util.ArrayList;
import java.util.List;

public class Main extends AppCompatActivity {
    TextView res;
    // Declarar instancias globales para acceder al Recicler
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    //variable requerida para los controles de voz
    private static final int RECOGNIZE_SPEECH_ACTIVITY = 1;
    // instancia global con datos de session
    Globals globals = Globals.getInstance();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loaditems(globals.getItems());
    }
    //encargado de cargar los elementos al reclicler
    private void loaditems(List items){
        List itemss = items;
        // Obtener el Recycler
        recycler = (RecyclerView) findViewById(R.id.recicler);
        recycler.setHasFixedSize(true);
        // Usar un administrador para LinearLayout
        lManager = new GridLayoutManager(this, 2);
        recycler.setLayoutManager(lManager);
        // Crear un nuevo adaptador
        adapter = new SiteAdapter(items);
        recycler.setAdapter(adapter);
    }
    //onclic pendiente del float button btnMic
    public void onClickBtnMic(View v) {
        Intent intentActionRecognizeSpeech = new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        // Configura el Lenguaje (Español-México)
        intentActionRecognizeSpeech.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL, "es-MX");
        try {
            startActivityForResult(intentActionRecognizeSpeech, RECOGNIZE_SPEECH_ACTIVITY);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(), "Tú dispositivo no soporta el reconocimiento por voz", Toast.LENGTH_SHORT).show();
        }
    }
    //receptor de onClickBtnMic
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RECOGNIZE_SPEECH_ACTIVITY:
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> speech = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String strSpeech2Text = speech.get(0);
                    /* comando de vos
                    res.setText(strSpeech2Text);
                    globals.setItems(strSpeech2Text, R.drawable.homecontrol, true);
                    loaditems(globals.getItems());
                    */
                }
                break;
            default:
                break;
        }
    }
    //acciones de botones en action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_site:
                Intent intent = new Intent(Main.this, AddSite.class);
                startActivity(intent);
                finish();
                break;
            case R.id.logout:
                Intent intent2 = new Intent(Main.this, Login.class);
                startActivity(intent2);
                finish();
                break;
            default:
                break;
        }

        return true;
    }
}
