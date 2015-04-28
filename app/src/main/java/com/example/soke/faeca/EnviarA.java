package com.example.soke.faeca;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Collections;


public class EnviarA extends Activity {

    public ListView selectorUsuarios = null;
    public boolean terminado = false;
    public String mensaje, tipo, usuario, usuario_destino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar);

        selectorUsuarios = (ListView) findViewById(R.id.usuarios);

        mensaje = getIntent().getStringExtra("mensaje");
        tipo = getIntent().getStringExtra("tipo");
        usuario = getIntent().getStringExtra("yo");
        ArrayList<String> usuarios = getIntent().getStringArrayListExtra("usuarios");

        Collections.sort(usuarios);

        setTitle("Notificación individual");


        ArrayAdapter<String> adapterUsuarios = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, usuarios);
        selectorUsuarios = (ListView) findViewById(R.id.usuarios);
        selectorUsuarios.setAdapter(adapterUsuarios);
        terminado = false;
        selectorUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                usuario_destino = selectorUsuarios.getItemAtPosition(position).toString();
            }
        });
    }


    public void enviar(View v) {

        ParsePush push = new ParsePush();
        ParseObject push_respaldo = new ParseObject(tipo);
        ParseQuery query = ParseInstallation.getQuery();
        query.whereEqualTo("user", usuario_destino);

        String loc=getIntent().getStringExtra("loc")!=null ? getIntent().getStringExtra("loc") : null;

        push_respaldo.put("Mensaje", mensaje);
        push_respaldo.put("receiver", usuario_destino);
        push_respaldo.put("Sender", usuario);
        if (loc!=null)
            push_respaldo.put("localizacion",loc);

        push_respaldo.saveEventually();

        push.setQuery(query);
        push.setMessage(mensaje);
        push.sendInBackground();
        Toast.makeText(this, "Enviando a " + usuario_destino, Toast.LENGTH_LONG).show();


    }

    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top);
    }
}
