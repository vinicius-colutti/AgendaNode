package br.com.agendanode.app;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sn1041520 on 25/07/2017.
 */

public class AgendaAdapter extends ArrayAdapter<ItemAgenda> {


    int res;
    public AgendaAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ItemAgenda> objects) {
        super(context, resource, objects);
        this.res = resource;
    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        View v = convertView;


        if(v == null){
            v = LayoutInflater.from(getContext()).inflate(res, null);
        }

        ItemAgenda item = getItem(position);

        TextView txt_data = (TextView) v.findViewById(R.id.txt_data_item);
        TextView txt_titulo = (TextView) v.findViewById(R.id.txt_titulo_item);
        TextView txt_detalhes = (TextView) v.findViewById(R.id.txt_detalhes_item);


        txt_data.setText(item.getData());
        txt_titulo.setText(item.getTitulo());
        txt_detalhes.setText(item.getDetalhes());


        return v;
    }
}
