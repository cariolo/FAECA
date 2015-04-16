package com.example.soke.faeca;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseException;

import java.util.Arrays;
import java.util.List;


public class enviarGrupo extends Activity {

    public List<String> destinatarios=null;
    public String eleccion=null;
    public List<String> acdemesa= Arrays.asList(getResources().getStringArray(R.array.aceituna_de_mesa));
    public List<String> almazaras= Arrays.asList(getResources().getStringArray(R.array.almazaras));
    public List<String> caniaazucar= Arrays.asList(getResources().getStringArray(R.array.cania_de_azucar));
    public List<String> frutas= Arrays.asList(getResources().getStringArray(R.array.frutas_y_hortalizas));
    public List<String> frutos= Arrays.asList(getResources().getStringArray(R.array.frutos_secos));
    public List<String> lacteo= Arrays.asList(getResources().getStringArray(R.array.lacteo));
    public List<String> olivareras= Arrays.asList(getResources().getStringArray(R.array.olivareras));
    public List<String> ovino= Arrays.asList(getResources().getStringArray(R.array.ovino_caprino));
    public List<String> suministros= Arrays.asList(getResources().getStringArray(R.array.suministros));
    public List<String> tabaco= Arrays.asList(getResources().getStringArray(R.array.tabaco));
    public List<String> vitivinicola= Arrays.asList(getResources().getStringArray(R.array.vitivinicola));

    public Spinner spinerGrupos=null;


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

        spinerGrupos = (Spinner) findViewById(R.id.grupos);
        ImageButton enviar = (ImageButton) findViewById(R.id.enviarGrupos);

        setTitle("Notificación grupal");




        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    eleccion=spinerGrupos.getSelectedItem().toString();

                    enviarGrupoNotificacion(destinatarios, getIntent().getStringExtra("mensaje"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void enviarGrupoNotificacion(List destinatarios, String mensaje) throws ParseException {
        if(destinatarios==null){
            Toast.makeText(getApplicationContext(), "No has seleccionado ningun grupo.", Toast.LENGTH_SHORT).show();
        }
        else {

            Toast.makeText(this, "Mensaje enviado a todos los integrantes del grupo con éxito", Toast.LENGTH_SHORT).show();
        }
    }

    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top);
    }
}
