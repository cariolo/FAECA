package com.example.soke.faeca;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.ArrayList;


public class MenuPrincipal extends Activity {

    public static String usuario = null;
    public static boolean terminado = false;
    public int USUARIO = -1;
    public SharedPreferences shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);

        super.onCreate(savedInstanceState);
        final int USUARIO_REQUEST_CODE = 1;
        USUARIO = USUARIO_REQUEST_CODE;
        final String PREFS_NAME = "MyPrefsFile";

        setTitle("Menú principal");

        Parse.setLogLevel(Parse.LOG_LEVEL_VERBOSE);


        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        shared = settings;
        if (settings.getBoolean("my_first_time", true)) {

            String usuario = null;
            Bundle extras = new Bundle();
            extras.putString("usuario", usuario);

            Intent i = new Intent(this, Identificacion.class);
            i.putExtras(extras);
            startActivityForResult(i, USUARIO_REQUEST_CODE);
        } else {
            Toast.makeText(this, "Bienvenido/a " + shared.getString("usuario", "1"), Toast.LENGTH_LONG).show();
        }
        setContentView(R.layout.activity_menuprincipal);


        ParseInstallation.getCurrentInstallation().put("user", shared.getString("usuario", "1"));


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
        ParseInstallation.getCurrentInstallation().saveInBackground();

        ImageButton env = (ImageButton) findViewById(R.id.enviarA);
        registerForContextMenu(env);
    }

    public void dummyClick(View v) {
        ImageButton env = (ImageButton) findViewById(R.id.enviarA);

        openContextMenu(env);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.menu_enviara, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.enviarausuario:
                try {
                    enviarA();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.enviaratodos:
                enviarATodos();
                return true;
            case R.id.enviaragrupo:
                try {
                    enviarGrupo();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == USUARIO) {
            if (resultCode == RESULT_OK) {
                usuario = data.getStringExtra("usuario");
                Toast.makeText(this, "Bienvenido " + usuario, Toast.LENGTH_LONG).show();
                if (usuario != null) {
                    shared.edit().putBoolean("my_first_time", false).commit();
                    shared.edit().putString("usuario", usuario).commit();
                    ParseUser user = new ParseUser();
                    user.setUsername(usuario);
                    user.setPassword("no_pass");
                    user.signUpInBackground(new SignUpCallback() {
                        public void done(ParseException e) {
                            if (e == null) {

                            } else {
                                e.printStackTrace();
                            }
                        }
                    });
                    Toast.makeText(getApplicationContext(), "La aplicacion se ha reiniciado para realizar los cambios", Toast.LENGTH_LONG).show();

                    super.finish();
                    this.finish();
                    startActivity(getIntent());

                }
            }
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Lo sentimos, sin identificacion no puedes continuar", Toast.LENGTH_LONG).show();
                super.finish();
                finish();
            }
        }
        overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
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
        // automatically handle clicks on the Home/Up boton_enviar_a, so long
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

    public void enviarATodos() {
        EditText mensaje = (EditText) findViewById(R.id.mensajeCaja);
        Spinner spinnerLayout = (Spinner) findViewById((R.id.spinnerTipoPush));
        String valorSpin = String.valueOf(spinnerLayout.getSelectedItem());
        EditText campoTexto = (EditText) findViewById(R.id.mensajeCaja);
        ParsePush pushAtodos = new ParsePush();

        if (campoTexto.getText().toString().length() == 0) {
            Toast.makeText(this, "Campo de texto vacío, rellénelo e inténtelo de nuevo", Toast.LENGTH_SHORT).show();
        } else {
            Intent i = new Intent(this, Consulta.class);
            i.putExtra("tipo", valorSpin);

            pushAtodos.setMessage("\n" + valorSpin.toUpperCase() + ": " + mensaje.getText().toString());

            pushAtodos.sendInBackground();

            ParseObject push = new ParseObject(valorSpin);

            push.put("Mensaje", campoTexto.getText().toString());
            push.put("Sender", shared.getString("usuario", "1"));
            push.saveEventually();
        }
    }

    public void enviarA() throws ParseException {
        EditText campoTexto = (EditText) findViewById(R.id.mensajeCaja);
        ImageButton enviarA = (ImageButton) findViewById(R.id.enviarA);
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        Spinner spinnerLayout = (Spinner) findViewById((R.id.spinnerTipoPush));
        String valorSpin = String.valueOf(spinnerLayout.getSelectedItem());

        ArrayList<String> usuarios = new ArrayList<>();
        ArrayList<ParseUser> users = new ArrayList<>();
        users = (ArrayList<ParseUser>) query.find();
        if (campoTexto.getText().toString().length() == 0) {
            Toast.makeText(this, "Campo de texto vacío, rellénelo e inténtelo de nuevo", Toast.LENGTH_SHORT).show();
        } else {
            for (int i = 0; i < users.size(); i++) {
                usuarios.add(users.get(i).getUsername());
            }

            Intent i = new Intent(this, EnviarA.class);
            i.putExtra("mensaje", campoTexto.getText().toString());
            i.putExtra("usuarios", usuarios);
            i.putExtra("tipo", valorSpin);
            i.putExtra("yo", shared.getString("usuario", "1"));
            startActivity(i);
            overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
        }
    }

    public void consulta(View v) {
        Spinner spinnerTipoPush = (Spinner) findViewById((R.id.spinnerTipoPush));
        String valorSpin = String.valueOf(spinnerTipoPush.getSelectedItem());

        Intent i = new Intent(this, Consulta.class);
        i.putExtra("ValorSpin", valorSpin);
        i.putExtra("usuario", shared.getString("usuario", "1"));
        startActivity(i);
        overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
    }

    public void enviarGrupo() throws ParseException {
        EditText campoTexto = (EditText) findViewById(R.id.mensajeCaja);
        Spinner spinnerTipoPush = (Spinner) findViewById((R.id.spinnerTipoPush));


        if (campoTexto.getText().toString().length() == 0) {
            Toast.makeText(this, "Campo de texto vacío, rellénelo e inténtelo de nuevo", Toast.LENGTH_SHORT).show();
        } else {
            Intent i = new Intent(this, enviarGrupo.class);
            i.putExtra("tipoMensaje", spinnerTipoPush.getSelectedItem().toString());
            i.putExtra("mensaje", campoTexto.getText().toString());
            i.putExtra("usuario", shared.getString("usuario", "1").toString());
            startActivity(i);
            overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
        }
    }

    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top);
    }
}
