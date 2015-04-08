package com.example.soke.faeca;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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


public class Consulta extends Activity implements AdapterView.OnItemClickListener{
    static String valorSpin = null;
    private String valorSpinARefrescar=null;
    private SwipeRefreshLayout swipeContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_push);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);


        if(getIntent().hasExtra("ValorSpin")) {
            Bundle elPrimero = getIntent().getExtras();
            valorSpin = elPrimero.getString("ValorSpin");
            valorSpinARefrescar=elPrimero.getString("ValorSpin");
        }
        else{
            LinkedList <String> lista = new LinkedList<String>();
            final Spinner spiner_oculto = (Spinner) findViewById(R.id.consulta_oculta);
            lista.add("Reunion");
            lista.add("Urgencia");
            lista.add("Conferencia");
            lista.add("Recordatorio");
            ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lista);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spiner_oculto.setAdapter(adapter);
            spiner_oculto.setVisibility(View.VISIBLE);
            spiner_oculto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    public void onItemSelected (AdapterView < ? > parentView, View selectedItemView,
                    int position, long id){

                        if(valorSpin == null) {
                            valorSpin = String.valueOf(spiner_oculto.getSelectedItem());
                            valorSpinARefrescar=String.valueOf(spiner_oculto.getSelectedItem());
                        }

                        if (valorSpin != null)
                            poblarSpinner(valorSpin);

                        valorSpin = null;
                    }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
                }
        if(valorSpin != null)
            poblarSpinner(valorSpin);

            valorSpin = null;
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                swipeContainer.setRefreshing(false);
                poblarSpinner(valorSpinARefrescar);
            }
        });
        }




    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        Intent intent = new Intent(this, NotificacionCompleta.class);
        intent.putExtra("notificacion", l.getItemAtPosition(position).toString());
        startActivity(intent);
    }

    public void poblarSpinner(String valorSpin){
        ListView vistaResultadosConsulta = (ListView) findViewById(R.id.listaResultados);

        ParseQuery<ParseObject> query = ParseQuery.getQuery(valorSpin);

        setTitle("Consulta de " + valorSpin.toLowerCase());


        try {
            List<ParseObject> lista=query.find();
            ArrayList<String> listaResultado = new ArrayList<>();
            for(int i = lista.size()-1; i >=0; i--){

                listaResultado.add("Mensaje: "+lista.get(i).getString("Mensaje") + "\nFecha: " + lista.get(i).getCreatedAt().toString() + "\nEnviado por: " + lista.get(i).getString("Sender"));
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_expandable_list_item_1, listaResultado);
            vistaResultadosConsulta.setAdapter(arrayAdapter);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ListView listview = (ListView) findViewById(R.id.listaResultados);
        listview.setOnItemClickListener(this);
    }

    @Override
    public void onBackPressed(){
        finish();
        overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top);
    }
}
