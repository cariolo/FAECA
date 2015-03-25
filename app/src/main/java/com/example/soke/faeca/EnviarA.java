package com.example.soke.faeca;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class EnviarA extends ActionBarActivity {

    public LinkedList<String> usuarios=new LinkedList<>();
    public Spinner selectorUsuarios;
    public Spinner selectorGrupos;
    String mensaje;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar);
        selectorUsuarios=(Spinner) findViewById(R.id.usuarios);
        selectorGrupos=(Spinner) findViewById(R.id.grupos);
        mensaje=getIntent().getStringExtra("mensaje");

        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");

        //Busco los canales a los que estoy suscrito
        List<String> subscribedChannels = ParseInstallation.getCurrentInstallation().getList("channels");
        LinkedList<String> canales=new LinkedList<>();
        for(int i=0; i<subscribedChannels.size(); i++)
            canales.add(subscribedChannels.get(i));

        //Lleno el Spinner con los canales a los que estoy suscrito
        ArrayAdapter<String> grupos=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, canales);
        grupos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectorGrupos=(Spinner) findViewById(R.id.grupos);
        selectorGrupos.setAdapter(grupos);


        //Busco todos los usuarios de la aplicacion para poder mandarles push
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> users, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < users.size(); i++) {
                        usuarios.add(users.get(i).get("username").toString());
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });

        //Lleno el Spinner con los usuarios registrados
        ArrayAdapter<String> adapterUsuarios=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, usuarios);
        adapterUsuarios.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectorUsuarios=(Spinner) findViewById(R.id.usuarios);
        selectorUsuarios.setAdapter(adapterUsuarios);

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
        ParseQuery query=ParseInstallation.getQuery();
        query.whereEqualTo("_User", selectorUsuarios.getSelectedItem().toString());

        Toast.makeText(this, selectorUsuarios.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
        ParsePush push=new ParsePush();
        push.setQuery(query);
        push.setMessage(mensaje);
        push.sendInBackground();
    }
}
