package com.yoogooo.yoogooosmarthome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.yoogooo.yoogooosmarthome.Model.Enclouser;
import com.yoogooo.yoogooosmarthome.Single.Globals;

public class Update_enclouser extends AppCompatActivity {
    private Globals globals = Globals.getInstance();
    private Enclouser enclouser = globals.getEnclouser();
    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updat_enclouser);

        name = (TextView) findViewById(R.id.up_enclouser_name);
        name.setText(enclouser.getTitle());
    }

    //codigo para el boton de atras
    @Override
    public void onBackPressed() {
        finish();
    }
}
