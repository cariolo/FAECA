package com.example.soke.faeca;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class Consulta extends Activity implements AdapterView.OnItemClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_push);
        Bundle elPrimero = getIntent().getExtras();
        String valorSpin = elPrimero.getString("ValorSpin");
        ListView vistaResultadosConsulta = (ListView) findViewById(R.id.listaResultados);

        ParseQuery<ParseObject> query = ParseQuery.getQuery(valorSpin);
        Toast.makeText(this, "Consultando " + valorSpin + "...", Toast.LENGTH_LONG).show();


        setTitle("Consulta de " + valorSpin);


        try {
            List<ParseObject> lista=query.find();
            ArrayList<String> listaResultado = new ArrayList<>();
            for(int i = 0; i < lista.size(); i++){
                listaResultado.add("Mensaje: "+lista.get(i).getString("Mensaje") + "\nFecha: " + lista.get(i).getCreatedAt().toString());
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_expandable_list_item_1, listaResultado);
            vistaResultadosConsulta.setAdapter(arrayAdapter);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ListView listview = (ListView) findViewById(R.id.listaResultados);
        listview.setOnItemClickListener(this);
    }

    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        Intent intent = new Intent(this, NotificacionCompleta.class);
        intent.putExtra("notificacion", l.getItemAtPosition(position).toString());
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        finish();
        overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top);
    }
}
