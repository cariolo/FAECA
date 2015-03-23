package com.example.soke.faeca;

import android.content.Intent;
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
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.SaveCallback;


public class MenuPrincipal extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuprincipal);

        //Parse.enableLocalDatastore(this); Al habilitar la BD local, no se logea bien al servidor parse (se envian los push, pero no hay constancia de quien)

        Parse.initialize(this,"IGPp8uUXyGziD2kGBcLPhfzk5KqYyliY3gzjH3RR","xb0pfIjxiZgZhMgNt93b51J00HFOQTrUWe4NjJof");

        ParsePush.subscribeInBackground("", new SaveCallback() {

            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
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

    public void enviarPushManual(View v){
        EditText campoTexto = (EditText) findViewById(R.id.editText);
        Spinner spinnerLayout = (Spinner) findViewById((R.id.spinner));
        String valorSpin = String.valueOf(spinnerLayout.getSelectedItem());

        ParseObject push = new ParseObject(valorSpin);
        push.put("Mensaje", campoTexto.getText().toString());

        //push.pinInBackground(); solo habilitado para la base de datos local activa

        push.saveEventually(); //igual que saveInBackground pero si no hay conexion, en cuanto la encuentre se hara el push

        Toast.makeText(this, "Enviando...", Toast.LENGTH_LONG).show();
        this.enviarATodos(v);
    }

    public void enviarATodos(View v){
        EditText edit=(EditText) findViewById(R.id.editText);

        ParsePush pruebapush = new ParsePush();
        pruebapush.setMessage(edit.getText().toString());

        pruebapush.sendInBackground();
    }
    public void reuniones(View v){
        Spinner spinnerLayout = (Spinner) findViewById((R.id.spinner));
        String valorSpin = String.valueOf(spinnerLayout.getSelectedItem());


        Intent i = new Intent(this, Consulta.class );
        i.putExtra("ValorSpin", valorSpin);
        startActivity(i);
    }
}
