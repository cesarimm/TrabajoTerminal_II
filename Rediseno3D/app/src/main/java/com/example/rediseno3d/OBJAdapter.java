package com.example.rediseno3d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.opencv.android.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.O)

public class OBJAdapter extends ArrayAdapter {

    private List<OBJ> items = this.obtenerLista();

    public  OBJAdapter(Context context){
        super(context, 0);
    }

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
        ImageView tvImageView = (ImageView) listItemView.findViewById(R.id.imageViewInflado);

        // Obtener el item actual
        OBJ item = items.get(position);

        // Obtener Views

        // Actualizar los Views
        tvTitulo.setText(item.getNombre());
        tvFecha.setText(item.getFecha());

        //Trabajar con la imagen de cada uno
        File file = new File(Environment.getExternalStorageDirectory() + "/R3D/imagenes/engrane2.jpg");

        try{
          Bitmap imagenBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
          tvImageView.setImageBitmap(imagenBitmap);
        }catch (Exception e){
            e.printStackTrace();
        }


        return listItemView;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private List<OBJ> obtenerLista(){

        List<OBJ> archivos = new ArrayList<OBJ>();

         ///Obtener los archivos del adapter y poder utilizarlos en el visualizador
            File f = new File(Environment.getExternalStorageDirectory() + "/R3D/obj/");
            File[] files = f.listFiles();


            BasicFileAttributes atributos;
            for (int i = 0; i <files.length ; i++) {
                String nombre, fecha="xx/xx/xx";
                //Obteniendo el nombre
                nombre = files[i].getName();
                //Obtener la fecha
                try{
                    atributos = Files.readAttributes(files[i].toPath(), BasicFileAttributes.class);
                    FileTime tiempo = atributos.creationTime();

                    String patron = "yyyy-MM-dd HH:mm:ss";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patron);

                    fecha = simpleDateFormat.format(new Date(tiempo.toMillis()));
                }catch(IOException e){
                    e.printStackTrace();
                }

                archivos.add(new OBJ(nombre, fecha, ""));
            }

        return archivos;
    }


}
