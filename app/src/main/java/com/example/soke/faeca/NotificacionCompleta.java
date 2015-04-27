package com.example.soke.faeca;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


public class NotificacionCompleta extends Activity {

    boolean localizacion = false;
    String[] latitudLongitud = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacion_completa);

        setTitle("Detalles de Notificación");

        ImageButton verMapa = (ImageButton) findViewById(R.id.verMapa);
        verMapa.setVisibility(View.INVISIBLE);
        TextView texto = (TextView) findViewById(R.id.detalles_notificacion);
        String textoModificar = getIntent().getStringExtra("notificacion");

        String[] partesTexto = textoModificar.split("-");

        if (partesTexto[1] != null || partesTexto[1] != " ") {
            latitudLongitud = partesTexto[1].split("|");
            localizacion = true;
            partesTexto[0] += "\nCoordenadas de la localización:  \n " + latitudLongitud[0].toString() + latitudLongitud[1].toString();
            verMapa.setVisibility(View.VISIBLE);
        }
        texto.setText(partesTexto[0]);
        verMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verEnMapa(latitudLongitud[0], latitudLongitud[1]);
            }
        });
    }

    private void verEnMapa(String latitud, String longitud) {
        Intent mapeo = new Intent(getApplicationContext(), ReunionGPS.class);
        mapeo.putExtra("latitud", latitud);
        mapeo.putExtra("longitud", longitud);
        startActivity(mapeo);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top);
    }
}
