package com.example.soke.faeca;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class Consulta extends Activity {

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
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_single_choice, listaResultado);
            vistaResultadosConsulta.setAdapter(arrayAdapter);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reuniones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed(){
        finish();
        overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top);
    }
}
