package com.example.rediseno3d;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;

public class OBJAdapter extends ArrayAdapter {

    private List<OBJ> items;

    public OBJAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @Override
    public int getCount() {
        return items != null ? items.size() : 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater =(LayoutInflater) LayoutInflater.from(parent.getContext());

        // Referencia del view procesado
        View listItemView;

        //Comprobando si el View no existe
        listItemView = null == convertView ? layoutInflater.inflate(
                R.layout.dis_obj,
                parent,
                false) : convertView;


        TextView tvTitulo = (TextView) listItemView.findViewById(R.id.lvTitulo);
        TextView tvFecha = (TextView) listItemView.findViewById(R.id.lvFecha);


        // Obtener el item actual
        OBJ item = items.get(position);

        // Obtener Views

        // Actualizar los Views
        tvTitulo.setText(item.getNombre());
        tvFecha.setText(item.getFecha());

        return listItemView;
    }




}
