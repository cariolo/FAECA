package com.example.soke.faeca;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class Identificacion extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identificacion);
    }


    public void guardarNombre(View v){
        EditText n=(EditText) findViewById(R.id.CampoNombre);
        String usuario=n.getText().toString();

        if(usuario.length()<1) {
            Toast.makeText(this, "Debes introducir tu nombre para identificarte", Toast.LENGTH_LONG).show();
        }
        else{
            Intent i = new Intent();
            i.putExtra("usuario", usuario);
            setResult(RESULT_OK, i);
            finish();
        }
    }
}
