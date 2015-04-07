package com.example.soke.faeca;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;

import java.util.ArrayList;


public class EnviarA extends Activity {

    public Spinner selectorUsuarios;
    public boolean terminado=false;
    String mensaje;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar);
        selectorUsuarios=(Spinner) findViewById(R.id.usuarios);
        mensaje = getIntent().getStringExtra("mensaje");
        ArrayList<String> usuarios = getIntent().getStringArrayListExtra("usuarios");

        setTitle("Push individual");

        //Lleno el Spinner con los usuarios registrados
        ArrayAdapter<String> adapterUsuarios = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, usuarios);
        adapterUsuarios.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectorUsuarios = (Spinner) findViewById(R.id.usuarios);
        selectorUsuarios.setAdapter(adapterUsuarios);
        terminado = false;
    }


    public void enviar(View v){

        ParsePush push = new ParsePush();
        ParseQuery query = ParseInstallation.getQuery();
        query.whereEqualTo("user", selectorUsuarios.getSelectedItem().toString());

        push.setQuery(query);
        push.setMessage(mensaje);
        push.sendInBackground();
        Toast.makeText(this, "Enviando a "+selectorUsuarios.getSelectedItem().toString(), Toast.LENGTH_LONG).show();

    }
    public void onBackPressed(){
        finish();
        overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top);
            }
}
