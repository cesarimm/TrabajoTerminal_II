package com.example.rediseno3d;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;

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
    public View getView(final int position, View convertView, ViewGroup parent) {

        final int  aux = position;

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
        final String nombre = item.getNombre();
        //Trabajar con la imagen de cada uno
        /*File file = new File(Environment.getExternalStorageDirectory() + "/R3D/imagenes/IMG_20200603_175302.jpg");

        try{
          Bitmap imagenBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
          tvImageView.setImageBitmap(imagenBitmap);
        }catch (Exception e){

        }*/

        tvImageView.setImageResource(R.drawable.cubito);

        ////Para los botones
        Button compartir = (Button) listItemView.findViewById(R.id.btnCompartir);
        final Button visualizar = (Button) listItemView.findViewById(R.id.btnVisualizar);
        Button eliminar = (Button) listItemView.findViewById(R.id.btnEliminar);

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(getContext(), "eliminar"+aux, Toast.LENGTH_SHORT).show();
                eliminarArchivo(getContext(), nombre, aux);
            }
        });

        visualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(getContext(), "visualizar"+aux, Toast.LENGTH_SHORT).show();
                abrirVisualizador(getContext(), nombre);
            }
        });

        compartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri path = FileProvider.getUriForFile(getContext(), "com.example.rediseno3d", new File(Environment.getExternalStorageDirectory()+"/R3D/obj/"+nombre));
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "R3D: ");
                shareIntent.putExtra(Intent.EXTRA_STREAM, path);
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                shareIntent.setType("text/*");
                getContext().startActivity(Intent.createChooser(shareIntent, "Share..."));
            }
        });

        return listItemView;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private List<OBJ> obtenerLista(){

        List<OBJ> archivos = new ArrayList<OBJ>();
         ///Obtener los archivos del adapter y poder utilizarlos en el visualizador
            File f = new File(Environment.getExternalStorageDirectory() + "/R3D/obj/");
            File[] files = f.listFiles();


            BasicFileAttributes atributos;
            for (int i = files.length -1; i >=0 ; i--) {
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

    private void eliminarArchivo(Context contexto, final String nombreArchvo, final int aux){
        AlertDialog.Builder build = new AlertDialog.Builder(contexto);
        build.setMessage("¿Desea eliminar el archivo?");
        build.setTitle("Eliminar OBJ");
        build.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                items.remove(aux);
                notifyDataSetChanged();
                File f = new File(Environment.getExternalStorageDirectory() + "/R3D/obj/"+nombreArchvo);
                f.delete();
            }
        });

        build.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dialog = build.create();
        dialog.show();
    }

    private void abrirVisualizador(Context contexto, String nombreArchvo){
        try{
            ///Obtener los datos para la libreria de OPENGL a partir del OBJ
            float vertices[]  = Figura.getVertices(nombreArchvo);
            byte indices[]  = Figura.getIndices(nombreArchvo);

            float colors[]= new float[(vertices.length/3)*4];

            for(int x=0;x<(vertices.length/3)*4;x++){
                colors[x] = 0.5f;
            }
            // Toast.makeText(getApplicationContext(), "Item: "+textView.getText().toString(), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(contexto, Visualizador.class);
            intent.putExtra("Vertices", vertices);
            intent.putExtra("Indices", indices);
            intent.putExtra("Colores", colors);
            contexto.startActivity(intent);
        }catch(Exception e){
            ///NJo es posible abrir
            AlertDialog.Builder build = new AlertDialog.Builder(contexto);
            build.setMessage("Calidad no soportada por el visualizador");
            build.setTitle("Visualizador");
            build.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            AlertDialog dialog = build.create();
            dialog.show();
        }
    }

}
