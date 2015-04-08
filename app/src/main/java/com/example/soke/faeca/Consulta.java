package com.example.soke.faeca;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class Consulta extends Activity implements AdapterView.OnItemClickListener{

    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_push);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                swipeContainer.setRefreshing(false);
                finish();
                startActivity(getIntent());
            }
        });

        Bundle elPrimero = getIntent().getExtras();
        String valorSpin = elPrimero.getString("ValorSpin");
        ListView vistaResultadosConsulta = (ListView) findViewById(R.id.listaResultados);

        ParseQuery<ParseObject> query = ParseQuery.getQuery(valorSpin);

        setTitle("Consulta de " + valorSpin);


        try {
            List<ParseObject> lista=query.find();
            ArrayList<String> listaResultado = new ArrayList<>();
            for(int i = 0; i < lista.size(); i++){

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
