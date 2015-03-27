package com.example.soke.faeca;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class EnviarA extends Activity {

    public Spinner selectorUsuarios;
    public Spinner selectorGrupos;
    public boolean terminado=false;
    String mensaje;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Busco los canales a los que estoy suscrito
        List<String> subscribedChannels = ParseInstallation.getCurrentInstallation().getList("channels");
        LinkedList<String> canales = new LinkedList<>();

        for(int i=0; i<subscribedChannels.size(); i++)
            canales.add(subscribedChannels.get(i));

        setContentView(R.layout.activity_enviar);
        selectorUsuarios=(Spinner) findViewById(R.id.usuarios);
        selectorGrupos=(Spinner) findViewById(R.id.grupos);
        mensaje = getIntent().getStringExtra("mensaje");
        ArrayList<String> usuarios = getIntent().getStringArrayListExtra("usuarios");

        //Lleno el Spinner con los canales a los que estoy suscrito
        ArrayAdapter<String> grupos = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, canales);
        grupos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectorGrupos=(Spinner) findViewById(R.id.grupos);
        selectorGrupos.setAdapter(grupos);

        //Lleno el Spinner con los usuarios registrados
        ArrayAdapter<String> adapterUsuarios = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, usuarios);
        adapterUsuarios.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectorUsuarios = (Spinner) findViewById(R.id.usuarios);
        selectorUsuarios.setAdapter(adapterUsuarios);
        terminado = false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_enviar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void enviar(View v){

        RadioButton grupo=(RadioButton) findViewById(R.id.gruposRB);
        RadioButton usuario=(RadioButton) findViewById(R.id.usuariosRB);


        if(usuario.isChecked()) {
            ParsePush push = new ParsePush();
            ParseQuery query = ParseInstallation.getQuery();
            query.whereEqualTo("user", selectorUsuarios.getSelectedItem().toString());

            push.setQuery(query);
            push.setMessage(mensaje);
            push.sendInBackground();
            Toast.makeText(this, "Enviando a "+selectorUsuarios.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
        }
        else if(grupo.isChecked()){
            ParsePush push = new ParsePush();

            push.setChannel("Administradores");
            push.setMessage(mensaje);
            push.sendInBackground();
            Toast.makeText(this, "Enviando a "+selectorGrupos.getSelectedItem().toString(), Toast.LENGTH_LONG).show();

        }
    }
}
