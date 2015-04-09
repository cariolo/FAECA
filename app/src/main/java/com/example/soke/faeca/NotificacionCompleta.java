package com.example.soke.faeca;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class NotificacionCompleta extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacion_completa);

        setTitle("Detalles de Notificación");


        TextView texto = (TextView) findViewById(R.id.detalles_notificacion);
        texto.setText(getIntent().getStringExtra("notificacion"));
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top);
    }
}
