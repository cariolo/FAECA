package com.example.soke.faeca;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class NotificacionCompleta extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacion_completa);

        TextView texto=(TextView) findViewById(R.id.textView4);
        texto.setText(getIntent().getStringExtra("notificacion"));
    }

}
