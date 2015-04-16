package com.example.soke.faeca;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;

import java.util.ArrayList;


public class enviarGrupo extends Activity {

    public ArrayList<String[]> almazaras=null;
    public ArrayList<String[]> olivareras=null;
    public ArrayList<String[]> ac_de_mesa=null;
    public ArrayList<String[]> cania_de_azucar=null;
    public ArrayList<String[]> frutas_hortalizas=null;
    public ArrayList<String[]> frutos_secos=null;
    public ArrayList<String[]> lacteo=null;
    public ArrayList<String[]> suministros=null;
    public ArrayList<String[]> tabaco=null;
    public ArrayList<String[]> vitivinicola=null;

    public ArrayList<ArrayList<String[]>> grupos=null;


    public ArrayList<String[]> destinatarios = null;

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

        String[] almres=getResources().getStringArray(R.array.almazaras);



        Resources res = getResources();
        String[] almazarasItems  = res.getStringArray(R.array.almazaras);
        String[] olivarerasItems=res.getStringArray(R.array.olivareras);
        String[] ac_De_mesaItems=res.getStringArray(R.array.aceituna_de_mesa);
        String[] caniaItems=res.getStringArray(R.array.cania_de_azucar);
        String[] frutasItems=res.getStringArray(R.array.frutas_y_hortalizas);
        String[] frutosItems=res.getStringArray(R.array.frutos_secos);
        String[] lacteoItems=res.getStringArray(R.array.lacteo);
        String[] suministrosItems=res.getStringArray(R.array.suministros);
        String[] tabacoItems=res.getStringArray(R.array.tabaco);
        String[] vitiItems=res.getStringArray(R.array.vitivinicola);

        almazaras=new ArrayList<>();
        almazaras.add(almazarasItems);

        olivareras=new ArrayList<>();
        olivareras.add(olivarerasItems);

        ac_de_mesa=new ArrayList<>();
        ac_de_mesa.add(ac_De_mesaItems);

        cania_de_azucar=new ArrayList<>();
        cania_de_azucar.add(caniaItems);

        frutas_hortalizas=new ArrayList<>();
        frutas_hortalizas.add(frutasItems);

        frutos_secos=new ArrayList<>();
        frutos_secos.add(frutosItems);

        lacteo=new ArrayList<>();
        lacteo.add(lacteoItems);

        suministros=new ArrayList<>();
        suministros.add(suministrosItems);

        tabaco=new ArrayList<>();
        tabaco.add(tabacoItems);

        vitivinicola=new ArrayList<>();
        vitivinicola.add(vitiItems);

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


        Spinner spinerGrupos = (Spinner) findViewById(R.id.grupos);
        ImageButton enviar = (ImageButton) findViewById(R.id.enviarGrupos);

        setTitle("Notificación grupal");

        eleccion=spinerGrupos.getSelectedItemPosition();

        switch (eleccion){

            case 0:
                for (int i=0; i<ac_de_mesa.size();i++)
                    destinatarios.add(ac_de_mesa.get(i));
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
