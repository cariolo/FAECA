package com.example.soke.faeca;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;


public class MenuPrincipal extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String PREFS_NAME = "MyPrefsFile";

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean("my_first_time", true)) {
            //the app is being launched for first time, do something
            Toast.makeText(this, "Primera vez que se abre la app!!", Toast.LENGTH_LONG).show();
            // first time task.

            // record the fact that the app has been started at least once
            settings.edit().putBoolean("my_first_time", false).commit();
        }
        else{
            Toast.makeText(this, "No es la primera vez", Toast.LENGTH_LONG).show();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuprincipal);


        //Parse.enableLocalDatastore(this); Al habilitar la BD local, no se logea bien al servidor parse (se envian los push, pero no hay constancia de quien)

        Parse.initialize(this,"IGPp8uUXyGziD2kGBcLPhfzk5KqYyliY3gzjH3RR","xb0pfIjxiZgZhMgNt93b51J00HFOQTrUWe4NjJof");
        ParseInstallation.getCurrentInstallation().put("user", "JoseLuis");

        ParsePush.subscribeInBackground("Administradores", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "Conexión exitosa");
                } else {
                    Log.e("com.parse.push", "Conexión fallida", e);
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void enviarATodos(View v){
        EditText mensaje = (EditText) findViewById(R.id.mensajeCaja);
        Spinner spinnerLayout = (Spinner) findViewById((R.id.spinnerTipoPush));
        String valorSpin = String.valueOf(spinnerLayout.getSelectedItem());
        EditText campoTexto = (EditText) findViewById(R.id.mensajeCaja);
        ParsePush pushAtodos = new ParsePush();

        pushAtodos.setMessage("\n" + valorSpin.toUpperCase() + ": " + mensaje.getText().toString());

        pushAtodos.sendInBackground();

        ParseObject push = new ParseObject(valorSpin);
        push.put("Mensaje", campoTexto.getText().toString());
        push.saveEventually();
    }

    public void enviarA(View v){
        EditText edit=(EditText) findViewById(R.id.mensajeCaja);
        Spinner spinnerUsuario = (Spinner) findViewById((R.id.spinnerUsuario));
        List <String> usuarios = new ArrayList<>();
        spinnerUsuario.setVisibility(View.VISIBLE);

       // ParseQuery<ParseObject> consulta = ;

        String usuario_destino = String.valueOf(spinnerUsuario.getSelectedItem());

        ParsePush pruebapush = new ParsePush();
        pruebapush.setMessage(edit.getText().toString());

        pruebapush.sendInBackground();
    }
    public void consulta(View v){
        Spinner spinnerTipoPush = (Spinner) findViewById((R.id.spinnerTipoPush));
        String valorSpin = String.valueOf(spinnerTipoPush.getSelectedItem());


        Intent i = new Intent(this, Consulta.class );
        i.putExtra("ValorSpin", valorSpin);
        startActivity(i);
    }
}
