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

import java.util.Arrays;


public class Identificacion extends Activity {

    ListView lv=null;
    EditText buscador=null;
    ArrayAdapter<String> adapter=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identificacion);
        buscador=(EditText) findViewById(R.id.CampoNombre);

        buscador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscador.setText("");
            }
        });
        String[] coops=getResources().getStringArray(R.array.cooperativas);
        Arrays.sort(coops);
        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, coops);

        lv=(ListView) findViewById(R.id.Cooperativas);
        lv.setAdapter(adapter);
        lv.setTextFilterEnabled(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                buscador.setText(lv.getItemAtPosition(position).toString());
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

    public void guardarNombre(View v){
        String usuario=null;

        if(lv.getSelectedItemPosition()==-1) {
            Toast.makeText(this, "Debes introducir tu nombre para identificarte", Toast.LENGTH_LONG).show();
        }
        else{
            usuario=lv.getSelectedItem().toString();

            Intent i = new Intent();
            i.putExtra("usuario", usuario);
            setResult(RESULT_OK, i);
            finish();
        }
    }
}
