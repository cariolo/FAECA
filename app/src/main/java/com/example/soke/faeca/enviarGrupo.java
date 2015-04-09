package com.example.soke.faeca;

import android.app.Activity;
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

    public ArrayList<String> destinatarios = new ArrayList<>();

    public void enviarGrupoNotificacion(ArrayList destinatarios, String mensaje) {

        for (int i = 0; i < destinatarios.size(); i++) {
            ParsePush push = new ParsePush();
            ParseQuery query = ParseInstallation.getQuery();
            query.whereEqualTo("user", destinatarios.get(i).toString());

            push.setQuery(query);
            push.setMessage(mensaje);
            push.sendInBackground();
        }
        Toast.makeText(this, "Mensaje enviado a todos los integrantes del grupo con éxito", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_grupo);
        Spinner spinerGrupos = (Spinner) findViewById(R.id.grupos);
        ImageButton enviar = (ImageButton) findViewById(R.id.enviarGrupos);

        setTitle("Notificación grupal");

        String eleccion = (String) spinerGrupos.getSelectedItem();

        if (eleccion.equals("Grupo 1, (Zeon, JoseLuis y Virtual)")) {
            destinatarios.add("Zeon");
            destinatarios.add("JoseLuis");
            destinatarios.add("Virtual");
        }

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarGrupoNotificacion(destinatarios, getIntent().getStringExtra("mensaje"));
            }
        });
    }

    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top);
    }
}
