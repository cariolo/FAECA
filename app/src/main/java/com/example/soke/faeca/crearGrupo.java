package com.example.soke.faeca;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;


public class crearGrupo extends Activity {
    public ArrayList<String> grupoCooperativas = null;
    ListView lv = null;
    ImageButton creacionGrupo = null;

    public void crearGrupo(ArrayList grupo) {


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_grupo);

        creacionGrupo = (ImageButton) findViewById(R.id.crearGrupo);
        lv = (ListView) findViewById(R.id.Cooperativas);
        grupoCooperativas = new ArrayList<>();
        String[] coops = getResources().getStringArray(R.array.cooperativas);
        Arrays.sort(coops);
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, coops);

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView check = (CheckedTextView) view;
                if (check.isChecked()) {
                    grupoCooperativas.add(lv.getItemAtPosition(position).toString());
                    Toast.makeText(getApplicationContext(), "Cooperativa: " + grupoCooperativas.get(grupoCooperativas.size() - 1) + "añadida a la lista preliminar correctamente", Toast.LENGTH_SHORT).show();
                }
                if (!check.isChecked()) {
                    Toast.makeText(getApplicationContext(), "Cooperativa: " + grupoCooperativas.remove(lv.getItemAtPosition(position).toString()) + "eliminada de la lista preliminar correctamente", Toast.LENGTH_SHORT).show();
                    grupoCooperativas.remove(lv.getItemAtPosition(position).toString());

                }
            }
        });

        creacionGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearGrupo(grupoCooperativas);
            }
        });
    }
}
