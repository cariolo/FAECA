package com.example.soke.faeca;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;

import java.util.Arrays;
import java.util.List;


public class enviarGrupo extends Activity {

    public List<String> destinatarios = null;
    public String eleccion = null;

    public List<String> acdemesa = null;
    public List<String> almazaras = null;
    public List<String> caniaazucar = null;
    public List<String> frutas = null;
    public List<String> frutos = null;
    public List<String> lacteo = null;
    public List<String> olivareras = null;
    public List<String> ovino = null;
    public List<String> suministros = null;
    public List<String> tabaco = null;
    public List<String> vitivinicola = null;

    public Spinner spinerGrupos = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_grupo);

        acdemesa = Arrays.asList(getResources().getStringArray(R.array.aceituna_de_mesa));
        almazaras = Arrays.asList(getResources().getStringArray(R.array.almazaras));
        caniaazucar = Arrays.asList(getResources().getStringArray(R.array.cania_de_azucar));
        frutas = Arrays.asList(getResources().getStringArray(R.array.frutas_y_hortalizas));
        frutos = Arrays.asList(getResources().getStringArray(R.array.frutos_secos));
        lacteo = Arrays.asList(getResources().getStringArray(R.array.lacteo));
        olivareras = Arrays.asList(getResources().getStringArray(R.array.olivareras));
        ovino = Arrays.asList(getResources().getStringArray(R.array.ovino_caprino));
        suministros = Arrays.asList(getResources().getStringArray(R.array.suministros));
        tabaco = Arrays.asList(getResources().getStringArray(R.array.tabaco));
        vitivinicola = Arrays.asList(getResources().getStringArray(R.array.vitivinicola));


        ImageButton crearGrupos = (ImageButton) findViewById(R.id.crearGrupos);

        crearGrupos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent creacionGrupo = new Intent(getApplicationContext(), EnvioGrupalCustomizado.class);
                creacionGrupo.putExtra("mensaje", getIntent().getStringExtra("mensaje"));
                startActivity(creacionGrupo);
                overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
            }
        });

        spinerGrupos = (Spinner) findViewById(R.id.grupos);
        ImageButton enviar = (ImageButton) findViewById(R.id.enviarGrupos);

        setTitle("Notificación grupal");


        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eleccion = spinerGrupos.getSelectedItem().toString();

                switch (eleccion) {
                    case "ACEITE DE OLIVA (ALMAZARAS)":
                        destinatarios = almazaras;
                        break;
                    case "ACEITE DE OLIVA OLIVARERAS (OLIVARERAS)":
                        destinatarios = olivareras;
                        break;
                    case "ACEITUNA DE MESA":
                        destinatarios = acdemesa;
                        break;
                    case "CAÑA DE AZUCAR":
                        destinatarios = caniaazucar;
                        break;
                    case "FRUTAS Y HORTALIZAS":
                        destinatarios = frutas;
                        break;
                    case "FRUTOS SECOS":
                        destinatarios = frutos;
                        break;
                    case "LACTEO":
                        destinatarios = lacteo;
                        break;
                    case "OVINO CAPRINO":
                        destinatarios = ovino;
                        break;
                    case "SUMINISTROS":
                        destinatarios = suministros;
                        break;
                    case "TABACO":
                        destinatarios = tabaco;
                        break;
                    case "VITIVINICOLA":
                        destinatarios = vitivinicola;
                        break;
                }

                try {
                    enviarGrupoNotificacion(destinatarios, getIntent().getStringExtra("mensaje"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void enviarGrupoNotificacion(List destinatarios, String mensaje) throws ParseException {
        if (destinatarios == null) {
            Toast.makeText(getApplicationContext(), "No has seleccionado ningun grupo.", Toast.LENGTH_SHORT).show();
        } else {

            ParsePush push = new ParsePush();
            ParseQuery query = ParseInstallation.getQuery();

            for (int i = 0; i < destinatarios.size(); i++) {
                query.whereEqualTo("user", destinatarios.get(i).toString());

                push.setQuery(query);
                push.setMessage(mensaje);
                push.sendInBackground();
            }
            Toast.makeText(this, "Mensaje enviado a todos los integrantes del grupo con éxito", Toast.LENGTH_SHORT).show();
        }
    }

    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top);
    }
}
