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
import com.parse.ParseObject;
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
    public String mensaje = null, tipoMensaje = null, sender = null, receiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_grupo);

        setTitle("Notificacion grupal");

        tipoMensaje = getIntent().getStringExtra("tipoMensaje");
        mensaje = getIntent().getStringExtra("mensaje");
        sender = getIntent().getStringExtra("usuario");

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


        ImageButton envioPersonalizado = (ImageButton) findViewById(R.id.envioPersonalizado);

        envioPersonalizado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent creacionGrupo = new Intent(getApplicationContext(), EnvioGrupalCustomizado.class);
                creacionGrupo.putExtra("mensaje", mensaje);
                creacionGrupo.putExtra("tipoMensaje", tipoMensaje);
                creacionGrupo.putExtra("usuario", sender);
                startActivity(creacionGrupo);
                overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
            }
        });

        spinerGrupos = (Spinner) findViewById(R.id.grupos);
        ImageButton enviar = (ImageButton) findViewById(R.id.enviarGrupos);


        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eleccion = spinerGrupos.getSelectedItem().toString();

                switch (eleccion) {
                    case "ACEITE DE OLIVA (ALMAZARAS)":
                        destinatarios = almazaras;
                        receiver = "ACEITE DE OLIVA (ALMAZARAS)";
                        break;
                    case "ACEITE DE OLIVA OLIVARERAS (OLIVARERAS)":
                        destinatarios = olivareras;
                        receiver = "ACEITE DE OLIVA OLIVARERAS (OLIVARERAS)";
                        break;
                    case "ACEITUNA DE MESA":
                        destinatarios = acdemesa;
                        receiver = "ACEITUNA DE MESA";
                        break;
                    case "CAÑA DE AZUCAR":
                        destinatarios = caniaazucar;
                        receiver = "CAÑA DE AZUCAR";
                        break;
                    case "FRUTAS Y HORTALIZAS":
                        destinatarios = frutas;
                        receiver = "FRUTAS Y HORTALIZAS";
                        break;
                    case "FRUTOS SECOS":
                        destinatarios = frutos;
                        receiver = "FRUTOS SECOS";
                        break;
                    case "LACTEO":
                        destinatarios = lacteo;
                        receiver = "LACTEO";
                        break;
                    case "OVINO CAPRINO":
                        destinatarios = ovino;
                        receiver = "OVINO CAPRINO";
                        break;
                    case "SUMINISTROS":
                        destinatarios = suministros;
                        receiver = "SUMINISTROS";
                        break;
                    case "TABACO":
                        destinatarios = tabaco;
                        receiver = "TABACO";
                        break;
                    case "VITIVINICOLA":
                        destinatarios = vitivinicola;
                        receiver = "VITIVINICOLA";
                        break;
                }

                try {
                    enviarGrupoNotificacion(destinatarios, mensaje, sender, receiver, tipoMensaje);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void enviarGrupoNotificacion(List destinatarios, String mensaje, String sender, String receiver, String tipoMensaje) throws ParseException {
        if (destinatarios == null) {
            Toast.makeText(getApplicationContext(), "No has seleccionado ningun grupo.", Toast.LENGTH_SHORT).show();
        } else {

            ParsePush push = new ParsePush();
            ParseQuery query = ParseInstallation.getQuery();
            ParseObject push_respaldo = new ParseObject(tipoMensaje);

            String loc=getIntent().getStringExtra("loc")!=null ? getIntent().getStringExtra("loc") : null;

            push_respaldo.put("Mensaje", mensaje);
            push_respaldo.put("receiver", receiver);
            push_respaldo.put("Sender", sender);

            if(loc!=null)
                push_respaldo.put("localizacion", loc);

            push_respaldo.saveEventually();
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
