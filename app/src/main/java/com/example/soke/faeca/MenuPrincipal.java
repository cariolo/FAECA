package com.example.soke.faeca;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;


public class MenuPrincipal extends ActionBarActivity {

    public static String usuario=null;
    public int USUARIO=-1;
    public SharedPreferences shared;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        final int USUARIO_REQUEST_CODE = 1;
        USUARIO=USUARIO_REQUEST_CODE;
        final String PREFS_NAME = "MyPrefsFile";


        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        shared=settings;
        if (settings.getBoolean("my_first_time", true)) {

            String usuario=null;
            Bundle extras=new Bundle();
            extras.putString("usuario", usuario);

            Intent i=new Intent(this, Identificacion.class);
            i.putExtras(extras);
            startActivityForResult(i, USUARIO_REQUEST_CODE);
        }
        else{
            Toast.makeText(this, "Bienvenido/a "+shared.getString("usuario","1"), Toast.LENGTH_LONG).show();
        }
        setContentView(R.layout.activity_menuprincipal);


        //Parse.enableLocalDatastore(this); Al habilitar la BD local, no se logea bien al servidor parse (se envian los push, pero no hay constancia de quien)


        Parse.initialize(this,"IGPp8uUXyGziD2kGBcLPhfzk5KqYyliY3gzjH3RR","xb0pfIjxiZgZhMgNt93b51J00HFOQTrUWe4NjJof");
        ParseInstallation.getCurrentInstallation().put("user", "JoseLuis");

        ParsePush.subscribeInBackground("Administradores", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                } else {
                    Log.d("ERROR AL SUSCRIBIR", "Ha habido un error al suscribirte");
                    e.printStackTrace();
                }
            }
        });
        ParsePush.subscribeInBackground("Todos", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                } else {
                    Log.d("ERROR AL SUSCRIBIR", "Ha habido un error al suscribirte");
                    e.printStackTrace();

                }
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){

        if (requestCode == USUARIO) {
            if (resultCode == RESULT_OK) {
                usuario=data.getStringExtra("usuario");
                Toast.makeText(this, "Hola "+usuario, Toast.LENGTH_LONG).show();
                if(usuario!=null) {
                    shared.edit().putBoolean("my_first_time", false).commit();
                    shared.edit().putString("usuario", usuario).commit();
                    ParseUser user=new ParseUser();
                    user.setUsername(usuario);
                    user.setPassword("no_pass");
                    user.signUpInBackground(new SignUpCallback() {
                        public void done(ParseException e) {
                            if (e == null) {
                                // Hooray! Let them use the app now.

                            } else {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Lo sentimos, sin identificacion no puedes continuar", Toast.LENGTH_LONG).show();
                super.finish();
                finish();
            }
        }
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
        if (id == R.id.Opciones) {
            Toast.makeText(this, "Opciones seleccionado", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.Salir) {
            this.finish();
            super.finish();
        }
        if (id == R.id.Acercade___) {
            Toast.makeText(this, "App desarrollada por Carlos Martinez y Jose Luis Martinez", Toast.LENGTH_SHORT).show();
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
        EditText campoTexto = (EditText) findViewById(R.id.mensajeCaja);
        Intent i=new Intent(this, EnviarA.class);
        i.putExtra("mensaje", campoTexto.getText().toString());
        startActivity(i);
    }
    public void consulta(View v){
        Spinner spinnerTipoPush = (Spinner) findViewById((R.id.spinnerTipoPush));
        String valorSpin = String.valueOf(spinnerTipoPush.getSelectedItem());

        Intent i = new Intent(this, Consulta.class );
        i.putExtra("ValorSpin", valorSpin);
        startActivity(i);
    }

}
