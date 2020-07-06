package com.example.rediseno3d;

import Procesamiento.Preprocesamiento;
import Procesamiento.Procesamiento;
import androidx.appcompat.app.AppCompatActivity;
import Procesamiento.*;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Point;

import java.util.ArrayList;

public class ProgresoActivity extends AppCompatActivity {

    TextView tv;
    ArrayList<String> lista;
    int tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progreso);

        getSupportActionBar().hide();
        ///
        Intent in = getIntent();
        Bundle b = in.getExtras();

        if(b!=null){
            lista = (ArrayList<String>) b.get("Lista");
            tipo= (int) b.get("Tipo");
        }

        String aux = "";

        for(int i=0;i<lista.size();i++){
            aux+=lista.get(i)+" \n";
        }

        if(tipo==1){
            procesoTipo1(28);
            procesoTipo1(60);
        }else if(tipo==2){
            procesoTipo2(4);
            procesoTipo2(30);
        }else if(tipo==3){
            procesoTipo3(5);
            procesoTipo3(15);
        }else if(tipo==4){
            procesoTipo4(14);
            procesoTipo4(60);
        }

        ///lista.clear();
        tv =(TextView) findViewById(R.id.textViewArchivos);
        tv.setText(aux);
        CameraActivity.listaImagenes.clear();
        lista.clear();
    }

     ////Objetos solidos
    private void procesoTipo1(int divisiones){
        try{

            String sOBJ="";
            ArrayList<Mat> listaMat = new ArrayList<>();
            ArrayList<ArrayList<Point>> listaPuntos = new ArrayList<>();

            ////ERROR
            ///Proprocesar todos las imagens
            for (int i=0 ; i <lista.size() ; i++) {
                listaMat.add(Preprocesamiento.preProceamiento(lista.get(i)));
            }

            ///Aqui hay error en el convexHull
            ///Agregar el convex hull para el reconocimiento de bordes
            for (int i = 0; i <listaMat.size() ; i++) {
                listaPuntos.add(Procesamiento.convexHull(listaMat.get(i)));
            }

            ///Ordenar las diferentes listas de puntos
            for (int i = 0; i <listaMat.size() ; i++) {
                listaPuntos.set(i, Herramientas.ordenarPuntos(listaPuntos.get(i)));
            }


            ////Obtener los datos de sintaxis
            ///Ordenar los puntos
            ///Actualizan los datos
            double longitud = Herramientas.getLongitud(listaPuntos.get(0));
            ArrayList<Point> listaSub = SubRectangulos.generarPuntosX((int) Herramientas.yMin - 5, (int) Herramientas.yMax + 5,
                    (int) Herramientas.min - 5, (int) Herramientas.max + 5,
                    longitud, divisiones, listaMat.get(0));

            Toast.makeText(getApplicationContext(), ""+listaSub.size(), Toast.LENGTH_SHORT).show();
            if(listaMat.size()==1){
                ///Crear la sintaxis
                sOBJ = SintaxisOBJ.sintaxisOBJSolidos(listaSub, 0);
            }else if(listaMat.size()==2){
                ///Obtener las longitudes
                double distancia = Herramientas.getOnlyLongitud(listaPuntos.get(1));
                sOBJ = SintaxisOBJ.sintaxisOBJSolidos(listaSub, distancia);
            }else if(listaMat.size()==3){
                ///Obtener las longitudes
                double distancia = Herramientas.getOnlyLongitud(listaPuntos.get(1));
                double distancia2 = Herramientas.getOnlyLongitud(listaPuntos.get(2));
                sOBJ = SintaxisOBJ.sintaxisOBJSolidos(listaSub, (distancia+distancia2)/2);
            }

            String nombreArchivo="";

            if(divisiones==28){
                nombreArchivo = generarNombreArchivo("baja");
            }else if(divisiones==60){
                nombreArchivo = generarNombreArchivo("alta");
            }

            Archivos.crearArchvoOBJ(nombreArchivo, sOBJ);

        }catch(Exception e){



        }
    }

    ///Cilindros
    private void procesoTipo2(int divisiones){
        Mat mat = Preprocesamiento.preProceamiento(lista.get(0));

        ArrayList<Point> aux = Procesamiento.convexHull(mat);

        aux = Herramientas.ordenarPuntos(aux);
        double longitud = Herramientas.getLongitud(aux);

        ArrayList<Point> auxMat = SubRectangulos.cilindrosEjeY((int) Herramientas.yMin - 5, (int) Herramientas.yMax + 5,
                (int) Herramientas.min - 5, (int) Herramientas.max + 5,
                mat, divisiones);

        //Toast.makeText(getApplicationContext(), ""+auxMat.size(), Toast.LENGTH_SHORT).show();

        String sObj = SintaxisOBJ.generarSintaxisCilindrosOBJ(auxMat);


        String nombreArchivo="";

        if(divisiones==4){
            nombreArchivo = generarNombreArchivo("baja");
        }else if(divisiones==30){
            nombreArchivo = generarNombreArchivo("alta");
        }

        Archivos.crearArchvoOBJ(nombreArchivo, sObj);
        Toast.makeText(getApplicationContext(), "Archivo creado", Toast.LENGTH_SHORT).show();
    }



    ///Engranes, tuercas, etc
    private void procesoTipo3(int mod){
        String sOBJ="";
        ArrayList<Mat> listaMat = new ArrayList<>();
        ArrayList<ArrayList<Point>> listaPuntos = new ArrayList<>();


        ///Proprocesar todos las imagens
        for (int i=0 ; i <lista.size() ; i++) {
            listaMat.add(Preprocesamiento.preProceamiento(lista.get(i)));
        }

        ///Aqui hay error en el convexHull
        ///Agregar el convex hull para el reconocimiento de bordes
        for (int i = 0; i <listaMat.size() ; i++) {
            listaPuntos.add(Procesamiento.convexHull(listaMat.get(i)));
        }

        ///Ordenar las diferentes listas de puntos
        for (int i = 0; i <listaMat.size() ; i++) {
            listaPuntos.set(i, Herramientas.ordenarPuntos(listaPuntos.get(i)));
        }

        ////Obtener los datos mas importantes
        Herramientas.getLongitud(listaPuntos.get(0));
        ArrayList<Point> puntos= SubRectangulos.generarContornoXY((int) Herramientas.yMin - 5, (int) Herramientas.yMax + 5,
                (int) Herramientas.min - 5, (int) Herramientas.max + 5,
                listaMat.get(0));

        ///Limpiar los puntos
        puntos = Herramientas.ordenarPuntos(puntos);
        puntos = Herramientas.limpiarListaModulo(mod, puntos);

        sOBJ = SintaxisOBJ.sintaxisOBJDonas(puntos, Herramientas.getOnlyLongitud(listaPuntos.get(1)));

        String nombreArchivo="";
        if(mod==5){
            nombreArchivo = generarNombreArchivo("alta");
        }else if(mod==15){
            nombreArchivo = generarNombreArchivo("baja");
        }

       Archivos.crearArchvoOBJ(nombreArchivo, sOBJ);
       Toast.makeText(getApplicationContext(), "Archivo creado", Toast.LENGTH_SHORT).show();
    }


    ////Solidos complejos
    private void procesoTipo4(int divisiones){

        ///Para el lado XY
        Mat mat = Preprocesamiento.preProceamiento(lista.get(0));

        ArrayList<Point> aux = Procesamiento.convexHull(mat);

        aux = Herramientas.ordenarPuntos(aux);
        double longitud = Herramientas.getLongitud(aux);
        ArrayList<Point> listaXY = SubRectangulos.generarPuntosX((int) Herramientas.yMin - 5, (int) Herramientas.yMax + 5,
                (int) Herramientas.min - 5, (int) Herramientas.max + 5,
                longitud, divisiones, mat);

        //Para el lado YZ
        Mat mat2 = Preprocesamiento.preProceamiento(lista.get(1));

        ArrayList<Point> aux2 = Procesamiento.convexHull(mat2);

        aux = Herramientas.ordenarPuntos(aux2);
         longitud = Herramientas.getLongitud(aux2);
        ArrayList<Point> listaYZ = SubRectangulos.generarPuntosX((int) Herramientas.yMin - 5, (int) Herramientas.yMax + 5,
                (int) Herramientas.min - 5, (int) Herramientas.max + 5,
                longitud, divisiones, mat2);


        mat2 = Preprocesamiento.preProceamiento(lista.get(2));
        aux2 = Procesamiento.convexHull(mat2);

        ///Validar la distancia
            ///TODO CORRECTO
            try{
                String sObj = SintaxisOBJ.objXYZ(listaXY, listaYZ);

                String nombreArchivo="";

                if(divisiones==14){
                    nombreArchivo = generarNombreArchivo("baja");
                }else if(divisiones==60){
                    nombreArchivo = generarNombreArchivo("alta");
                }

                Archivos.crearArchvoOBJ(nombreArchivo, sObj);
            }catch(Exception e){
                    e.printStackTrace();
            }

    }


     private String generarNombreArchivo(String calidad){
        String aux = lista.get(0).replace("IMG", "OBJ");
        aux = aux.replace(".jpg", calidad+".obj");
        //aux+=calidad+".obj";
        return aux;
     }

}
