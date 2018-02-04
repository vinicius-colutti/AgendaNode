package br.com.agendanode.app;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;


import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class MainActivity extends AppCompatActivity {


    ListView list_view;
    Context context;

    AgendaAdapter adapter;

    Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;


        //Floating Button para abrir tela de novo item
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(context , NovoItemActivity.class));
            }
        });

         list_view = (ListView)findViewById(R.id.list_view);

        //Inicializando o adapter vazio
        adapter = new AgendaAdapter(context, R.layout.list_view_item, new ArrayList<ItemAgenda>());
        list_view.setAdapter(adapter);


        carregarItensDaInternet();

        try {
            socket = IO.socket("http://10.0.2.2:8888");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }



        socket.on("novo_usuario", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                if(args.length > 0){
                    Log.d("novo_usuario", args[0].toString());
                    String json = args[0].toString();
                    final ItemAgenda item = new Gson().fromJson(json, ItemAgenda.class);


                    //Executar a thread principal
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.add(item);
                            enviarNotificacao(item);

                        }
                    });




                }

            }
        });

        socket.connect();

    }

    private void carregarItensDaInternet() {


            new Carrega().execute();

    }

    class Carrega extends AsyncTask<Void, Void, Void>{



        String retornoJson;


        @Override
        protected Void doInBackground(Void... voids) {
            String caminho = getResources().getString(R.string.caminho);
            retornoJson = HttpConnection.get(caminho);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            ItemAgenda[] itens = new Gson().fromJson(retornoJson, ItemAgenda[].class);

            adapter.clear();
            adapter.addAll(Arrays.asList(itens));
        }
    }

    int nofication_id = 1;
    public void enviarNotificacao(ItemAgenda item){

        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        builder.setSmallIcon(R.drawable.ic_sms_white_24dp).setContentTitle(item.getTitulo()).setContentText(item.getDetalhes()).setDefaults(Notification.DEFAULT_ALL).setPriority(Notification.PRIORITY_HIGH).setContentIntent(contentIntent).setAutoCancel(true);

        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);



        manager.notify(nofication_id, builder.build());

        builder.setContentIntent(contentIntent);




    }


}
