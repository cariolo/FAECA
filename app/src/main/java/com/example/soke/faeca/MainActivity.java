package com.example.soke.faeca;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //Parse.enableLocalDatastore(this);
        //Parse.initialize(this,"IGPp8uUXyGziD2kGBcLPhfzk5KqYyliY3gzjH3RR","xb0pfIjxiZgZhMgNt93b51J00HFOQTrUWe4NjJof");

        ParsePush.subscribeInBackground("", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void enviarPushManual(View v){
        EditText edit=(EditText) findViewById(R.id.editText);
        final Spinner spin = (Spinner) findViewById((R.id.spinner));
        final String spinVal = String.valueOf(spin.getSelectedItem());

        ParseObject push = new ParseObject(spinVal);
        push.put("Mensaje", edit.getText().toString());
        push.pinInBackground();
        push.saveEventually();
        Toast.makeText(this, "Enviando...", Toast.LENGTH_LONG).show();
        this.enviarATodos(v);
    }

    public void recuperarObjeto(View v) throws ParseException {
        ParseObject a;
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Urgencia");
        Toast.makeText(this, "Consultando...", Toast.LENGTH_LONG).show();
        List<ParseObject> lista=query.find();


    }

    public void enviarATodos(View v){
        EditText edit=(EditText) findViewById(R.id.editText);

        ParsePush pruebapush=new ParsePush();
        pruebapush.setMessage(edit.getText().toString());
        pruebapush.sendInBackground();
    }
    public void reuniones(View v){
        Intent i = new Intent(this, Reuniones.class );
        startActivity(i);

    }
}
