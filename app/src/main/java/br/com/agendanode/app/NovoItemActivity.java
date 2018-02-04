package br.com.agendanode.app;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NovoItemActivity extends AppCompatActivity {


    EditText edit_titulo, edit_detalhes,edit_data;
    String url = "";
    String parametros = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edit_titulo = (EditText) findViewById(R.id.edit_titulo);
        edit_detalhes = (EditText) findViewById(R.id.edit_detalhes);
        edit_data = (EditText) findViewById(R.id.edit_data);
    }

    public void salvarDados(View view) {
            String data = edit_data.getText().toString();
            String detalhes = edit_detalhes.getText().toString();
            String titulo = edit_titulo.getText().toString();

            if(data.isEmpty() || detalhes.isEmpty() || titulo.isEmpty()){
                Toast.makeText(getApplicationContext(), "Nenhum campo pode estar vazio", Toast.LENGTH_LONG).show();

            }else{
                url = "http://10.107.134.10:8888/inserir?";

                parametros = "data=" + data +"&titulo=" + titulo + "&detalhes=" + detalhes;


                new SolicitaDados().execute(url);


            }


    }

    private class SolicitaDados extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls){


            return HttpConnection.get(urls[0]+ parametros);

        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String resultado){

            //edit_detalhes.setText(resultado);


            if(resultado.contains("inserido")){

                Toast.makeText(getApplicationContext(), "Inserido com sucesso", Toast.LENGTH_SHORT).show();


            }else{

              Toast.makeText(getApplicationContext(), "Impossível inserir", Toast.LENGTH_LONG).show();


            }


        }

    }
}
