package com.example.soke.faeca;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Consulta extends Activity implements AdapterView.OnItemClickListener {
    static String valorSpin = null;
    private String yo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_push);

        yo = getIntent().getStringExtra("usuario");

        LinkedList<String> lista = new LinkedList<String>();
        final Spinner spiner_oculto = (Spinner) findViewById(R.id.consulta_oculta);

        lista.add("Reunion");
        lista.add("Urgencia");
        lista.add("Conferencia");
        lista.add("Recordatorio");
        lista.add("Privado");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lista);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner_oculto.setAdapter(adapter);
        spiner_oculto.setVisibility(View.VISIBLE);
        spiner_oculto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
                                       int position, long id) {

                valorSpin = (String) spiner_oculto.getSelectedItem();
                if (valorSpin != null) {
                    poblarListView(valorSpin);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                if (valorSpin != null) {
                    poblarListView(valorSpin);
                }
            }
        });
    }

    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        Intent intent = new Intent(this, NotificacionCompleta.class);
        intent.putExtra("notificacion", l.getItemAtPosition(position).toString());
        startActivity(intent);
    }

    public void poblarListView(String valorSpin) {

        ParseQuery<ParseObject> receptor = null;
        //if (valorSpin.equals("Privado")) {
            receptor = ParseQuery.getQuery(valorSpin).whereEqualTo("receiver", yo);
            receptor.whereEqualTo("receiver", yo);
        //}

        ListView vistaResultadosConsulta = (ListView) findViewById(R.id.listaResultados);

        setTitle("Consulta de " + valorSpin.toLowerCase());

        try {

            if (!valorSpin.equals("Privado")) {
                receptor = ParseQuery.getQuery(valorSpin);
            }
            ParseQuery<ParseObject> general = receptor;
            List<ParseObject> lista = general.find();
            ArrayList<String> listaResultado = new ArrayList<>();

            for (int i = lista.size() - 1; i >= 0; i--) {
                listaResultado.add(lista.get(i).getString("Sender") + ":\n\t\t" + lista.get(i).getString("Mensaje") + "\n\nFecha: \n" + lista.get(i).getCreatedAt().toString());
            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, listaResultado);
            vistaResultadosConsulta.setAdapter(arrayAdapter);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        vistaResultadosConsulta.setOnItemClickListener(this);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top);
    }
}
