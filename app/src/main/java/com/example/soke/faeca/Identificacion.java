package com.example.soke.faeca;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Identificacion extends Activity {

    public static String usuario = null;
    public ListView lv = null;
    public EditText buscador = null;
    public ArrayAdapter<String> adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identificacion);
        buscador = (EditText) findViewById(R.id.CampoNombre);

        String[] coops = getResources().getStringArray(R.array.cooperativas);
        Arrays.sort(coops);
        List coopsList = new ArrayList();

        for (int i = 0; i < coops.length; i++) {
            coopsList.add(coops[i]);
        }

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        ArrayList<String> usuarios = new ArrayList<>();
        ArrayList<ParseUser> users;
        try {

            users = (ArrayList<ParseUser>) query.find();
            for (int i = 0; i < users.size(); i++) {
                usuarios.add(users.get(i).getUsername());
            }

            for (int i = 0; i < coopsList.size(); i++) {
                for (int k = 0; k < usuarios.size(); k++) {
                    if (coopsList.get(i).equals(usuarios.get(k).toString())) {
                        coopsList.remove(i);
                    }
                }
            }
            Collections.sort(coopsList);
            coops = new String[coopsList.size()];
            for (int i = 0; i < coopsList.size(); i++) {
                coops[i] = coopsList.get(i).toString();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, coops);

        lv = (ListView) findViewById(R.id.Cooperativas);
        lv.setAdapter(adapter);
        lv.setTextFilterEnabled(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                buscador.setText(lv.getItemAtPosition(position).toString());
                usuario = String.valueOf(lv.getItemAtPosition(position));
            }
        });


        buscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Identificacion.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        buscador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscador.setText("");
            }
        });

        setTitle("Registro del usuario");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_identificacion, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up boton_enviar_a, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void guardarNombre(View v) {

        if (usuario.equals(null)) {
            Toast.makeText(this, "Seleccione su cooperativa con la que darse de alta", Toast.LENGTH_LONG).show();
        } else {
            Intent i = new Intent();
            i.putExtra("usuario", usuario);
            setResult(RESULT_OK, i);
            finish();
        }
    }
}
