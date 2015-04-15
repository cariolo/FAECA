package com.example.soke.faeca;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Collections;


public class enviarGrupo extends Activity {

    public ArrayList<String> almazaras=null;
    public ArrayList<String> olivareras=null;
    public ArrayList<String> ac_de_mesa=null;
    public ArrayList<String> cania_de_azucar=null;
    public ArrayList<String> frutas_hortalizas=null;
    public ArrayList<String> frutos_secos=null;
    public ArrayList<String> lacteo=null;
    public ArrayList<String> suministros=null;
    public ArrayList<String> tabaco=null;
    public ArrayList<String> vitivinicola=null;

    public ArrayList<ArrayList<String>> grupos=null;


    public ArrayList<String> destinatarios = null;

    int eleccion=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_grupo);

        ImageButton crearGrupos = (ImageButton) findViewById(R.id.crearGrupos);

        crearGrupos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent creacionGrupo = new Intent(getApplicationContext(), crearGrupo.class);
                startActivity(creacionGrupo);
                overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
            }
        });

        almazaras=new ArrayList<>(R.array.almazaras);
        olivareras=new ArrayList<>(R.array.olivareras);
        ac_de_mesa=new ArrayList<>(R.array.aceituna_de_mesa);
        cania_de_azucar=new ArrayList<>(R.array.cania_de_azucar);
        frutas_hortalizas=new ArrayList<>(R.array.frutas_y_hortalizas);
        frutos_secos=new ArrayList<>(R.array.frutos_secos);
        lacteo=new ArrayList<>(R.array.lacteo);
        suministros=new ArrayList<>(R.array.suministros);
        tabaco=new ArrayList<>(R.array.tabaco);
        vitivinicola=new ArrayList<>(R.array.vitivinicola);

        grupos=new ArrayList<>();
        grupos.add(ac_de_mesa);
        grupos.add(almazaras);
        grupos.add(cania_de_azucar);
        grupos.add(frutas_hortalizas);
        grupos.add(frutos_secos);
        grupos.add(lacteo);
        grupos.add(olivareras);
        grupos.add(suministros);
        grupos.add(tabaco);
        grupos.add(vitivinicola);

        for(int i=0; i<grupos.size(); i++)
            Collections.sort(grupos.get(i));

        Spinner spinerGrupos = (Spinner) findViewById(R.id.grupos);
        ImageButton enviar = (ImageButton) findViewById(R.id.enviarGrupos);

        setTitle("Notificación grupal");

        eleccion=spinerGrupos.getSelectedItemPosition();

        switch (eleccion){

            case 0:
                destinatarios=ac_de_mesa;
                break;
            case 1:
                destinatarios=almazaras;
                break;
            case 2:
                destinatarios=cania_de_azucar;
                break;
            case 3:
                destinatarios=frutas_hortalizas;
                break;
            case 4:
                destinatarios=frutos_secos;
                break;
            case 5:
                destinatarios=lacteo;
                break;
            case 6:
                destinatarios=olivareras;
                break;
            case 7:
                destinatarios=suministros;
                break;
            case 8:
                destinatarios=tabaco;
                break;
            case 9:
                destinatarios=vitivinicola;
                break;
            default:
                Toast.makeText(getApplicationContext(), "No has seleccionado ningun grupo.", Toast.LENGTH_SHORT).show();
        }



        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarGrupoNotificacion(destinatarios, getIntent().getStringExtra("mensaje"));
            }
        });
    }

    public void enviarGrupoNotificacion(ArrayList destinatarios, String mensaje) {
        if(destinatarios==null){
            Toast.makeText(getApplicationContext(), "No has seleccionado ningun grupo.", Toast.LENGTH_SHORT).show();
        }
        else {
            ParsePush push = new ParsePush();

            for (int i = 0; i < destinatarios.size(); i++) {
                ParseQuery query = ParseInstallation.getQuery();
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
