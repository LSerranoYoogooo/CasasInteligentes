package com.yoogooo.yoogooosmarthome.UI;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.yoogooo.yoogooosmarthome.Helper.InternetStatus;
import com.yoogooo.yoogooosmarthome.Helper.LoadData;
import com.yoogooo.yoogooosmarthome.R;
import com.yoogooo.yoogooosmarthome.Single.VolleyS;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
    private TextView usr;
    private TextView pass;
    private RequestQueue fRequestQueue;
    private View view;
    //validaciones
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = findViewById(R.id.activity_login);
        //ocultar Action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.login);

        //boton de inicio de sesion
        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VolleyS volley = VolleyS.getInstance(getApplicationContext());
                fRequestQueue = volley.getRequestQueue();
                usr = (TextView) findViewById(R.id.txt_user);
                pass = (TextView) findViewById(R.id.txt_pass);

                String email = usr.getText().toString();
                String password = pass.getText().toString();
                if(InternetStatus.isOnline(getApplicationContext())){
                    TextInputLayout tilE = (TextInputLayout) findViewById(R.id.til_email);
                    TextInputLayout tilP = (TextInputLayout) findViewById(R.id.til_password);
                    if (!validateEmail(email)) {
                        tilE.setError("Formato de email no valido");
                    } else if (!validatePassword(password)) {
                        tilP.setError("Minimo 8 caracteres");
                    } else {
                        tilE.setErrorEnabled(false);
                        tilP.setErrorEnabled(false);
                        Request(email, password, view);
                    }
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Sin coneccion a internet", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    private void  Request (final String user, final String pass, final View v) {
        //dialogo de carga de datos
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Conectando a su cuenta...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
        //url de consulta
        final String url = "http://www.demomp2015.yoogooo.com/Smart_Home/WB/login.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String res = LoadData.loginData(response);//funcion auxiliar
                        if(res.equals("-1")){
                            pDialog.dismiss();
                            Snackbar.make(v, "server error", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        } else if (res.equals("0")){
                            pDialog.dismiss();
                            Snackbar.make(v, "Datos incorrectos", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        } else {
                            try
                            {
                                OutputStreamWriter fout =
                                        new OutputStreamWriter(
                                                openFileOutput("dataYSH", Context.MODE_PRIVATE));

                                fout.write(res);
                                fout.close();
                            }
                            catch (Exception ex)
                            {
                                Log.e("Ficheros", "Error al escribir fichero a memoria interna");
                            }
                            pDialog.dismiss();

                            Intent intent = new Intent(Login.this, Main.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                        pDialog.dismiss();
                        Snackbar.make(v, "server error", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                params.put("email", user);
                params.put("pass", pass);
                return params;
            }
        };
        fRequestQueue.add(postRequest);
    }

    //funciones de validaciones
    public boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validatePassword(String password) {
        return password.length() > 2; //CAMBIAR A MINIMO 8
    }
}
