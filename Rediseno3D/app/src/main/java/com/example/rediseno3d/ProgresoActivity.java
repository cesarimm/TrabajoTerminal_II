package com.example.rediseno3d;

import Procesamiento.Preprocesamiento;
import Procesamiento.Procesamiento;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import Procesamiento.*;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
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
    String errores = "";
    boolean flagError=false;
    Button botonContinuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progreso);

        tv =(TextView) findViewById(R.id.textViewProceso);
        tv.setText("Progreso\n");

        botonContinuar = (Button) findViewById(R.id.btnContinuarProgreso);

        getSupportActionBar().hide();
        ///
        Intent in = getIntent();
        Bundle b = in.getExtras();


        if(b!=null){
            lista = (ArrayList<String>) b.get("Lista");
            tipo= (int) b.get("Tipo");
            this.elegirTipo(tipo);
        }else{
            ///Se va a mandar un error - Fallo de fotografías
            flagError = true;
            errores+="Capturar fotografías";
        }

            ///Mandar a vaciar la lista de imagenes
            limpiarLista();

            ////Mostrar errores
        if(flagError){
            mostrarDialogErrores();
        }else{
            tv.setText(tv.getText()+"Proceso terminado con exito\n");
        }

        botonContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(getApplicationContext(), ObjListActivity.class);
                startActivity(intent);
            }
        });

    }

    private void elegirTipo(int tipo){
        if(tipo==1){
            errores+="Solidos\n";
            procesoTipo1(28);
            procesoTipo1(60);
        }else if(tipo==2){
            errores+="Cilindros\n";
            procesoTipo2(4);
            procesoTipo2(30);
        }else if(tipo==3){
            errores+="Huecos\n";
            procesoTipo3(5);
            procesoTipo3(15);
        }else if(tipo==4){
            errores+="Complejos\n";
            procesoTipo4(14);
            procesoTipo4(60);
        }
    }

     ////Objetos solidos
    private void procesoTipo1(int divisiones){
        try{
            tv.setText(tv.getText()+"Procesando Imagenes\n");

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

            //Toast.makeText(getApplicationContext(), ""+listaSub.size(), Toast.LENGTH_SHORT).show();
            tv.setText(tv.getText()+"Creando nube de Puntos\n");
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

            tv.setText(tv.getText()+"Creando archivo OBJ\n");
            String nombreArchivo="";

            if(divisiones==28){
                nombreArchivo = generarNombreArchivo("baja");
            }else if(divisiones==60){
                nombreArchivo = generarNombreArchivo("alta");
            }

            Archivos.crearArchvoOBJ(nombreArchivo, sOBJ);

        }catch(Exception e){
            flagError = true;
            if(divisiones==28){
                errores+="Creación de OBJ calidad baja\n";
                tv.setText(tv.getText()+"Fallo en calidad baja\n");
            }else if(divisiones==60){
                errores+="Creación de OBJ calidad alta\n";
                tv.setText(tv.getText()+"Fallo en calidad alta\n");
            }
        }
    }

    ///Cilindros
    private void procesoTipo2(int divisiones){
        try{
            tv.setText(tv.getText()+"Procesando Imagenes\n");

            Mat mat = Preprocesamiento.preProceamiento(lista.get(0));

            ArrayList<Point> aux = Procesamiento.convexHull(mat);

            aux = Herramientas.ordenarPuntos(aux);
            double longitud = Herramientas.getLongitud(aux);

            ArrayList<Point> auxMat = SubRectangulos.cilindrosEjeY((int) Herramientas.yMin - 5, (int) Herramientas.yMax + 5,
                    (int) Herramientas.min - 5, (int) Herramientas.max + 5,
                    mat, divisiones);

            //Toast.makeText(getApplicationContext(), ""+auxMat.size(), Toast.LENGTH_SHORT).show();
            tv.setText(tv.getText()+"Creando nube de Puntos\n");
            String sObj = SintaxisOBJ.generarSintaxisCilindrosOBJ(auxMat);

            tv.setText(tv.getText()+"Creando archivo OBJ\n");
            String nombreArchivo="";

            if(divisiones==4){
                nombreArchivo = generarNombreArchivo("baja");
            }else if(divisiones==30){
                nombreArchivo = generarNombreArchivo("alta");
                tv.setText(tv.getText()+"Fallo en calidad baja\n");
            }

            Archivos.crearArchvoOBJ(nombreArchivo, sObj);

        }catch(Exception e){
            flagError = true;
            if(divisiones==4){
                errores+="Creación de OBJ calidad baja\n";
                tv.setText(tv.getText()+"Fallo en calidad baja\n");
            }else if(divisiones==30){
                errores+="Creación de OBJ calidad alta\n";
                tv.setText(tv.getText()+"Fallo en calidad alta\n");
            }
        }
    }



    ///Engranes, tuercas, etc
    private void procesoTipo3(int mod){
        try{
            tv.setText(tv.getText()+"Procesando Imagenes\n");

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

            tv.setText(tv.getText()+"Creando nube de Puntos\n");
            ////Obtener los datos mas importantes
            Herramientas.getLongitud(listaPuntos.get(0));
            ArrayList<Point> puntos= SubRectangulos.generarContornoXY((int) Herramientas.yMin - 5, (int) Herramientas.yMax + 5,
                    (int) Herramientas.min - 5, (int) Herramientas.max + 5,
                    listaMat.get(0));

            ///Limpiar los puntos
            puntos = Herramientas.ordenarPuntos(puntos);
            puntos = Herramientas.limpiarListaModulo(mod, puntos);

            sOBJ = SintaxisOBJ.sintaxisOBJDonas(puntos, Herramientas.getOnlyLongitud(listaPuntos.get(1))/6);

            tv.setText(tv.getText()+"Creando archivo OBJ\n");
            String nombreArchivo="";
            if(mod==5){
                nombreArchivo = generarNombreArchivo("alta");
            }else if(mod==15){
                nombreArchivo = generarNombreArchivo("baja");
                tv.setText(tv.getText()+"Fallo en calidad baja\n");
            }

            Archivos.crearArchvoOBJ(nombreArchivo, sOBJ);
        }catch(Exception e){
            flagError = true;
            if(mod==5){
                errores+="Creación de OBJ calidad alta\n";
                tv.setText(tv.getText()+"Fallo en calidad alta\n");
            }else if(mod==15){
                errores+="Creación de OBJ calidad baja\n";
                tv.setText(tv.getText()+"Fallo en calidad baja\n");
            }
        }

    }


    ////Solidos complejos
    private void procesoTipo4(int divisiones){
        try{
            tv.setText(tv.getText()+"Procesando Imagenes\n");
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
            tv.setText(tv.getText()+"Creando nube de Puntos\n");
            ///Validar la distancia
            ///TODO CORRECTO
            String sObj = SintaxisOBJ.objXYZ(listaXY, listaYZ);

            tv.setText(tv.getText()+"Creando archivo OBJ\n");
            String nombreArchivo="";

            if(divisiones==14){
                nombreArchivo = generarNombreArchivo("baja");
            }else if(divisiones==60){
                nombreArchivo = generarNombreArchivo("alta");
            }

            Archivos.crearArchvoOBJ(nombreArchivo, sObj);

        }catch(Exception e){
            flagError = true;
            if(divisiones==14){
                errores+="Creación de OBJ calidad baja\n";
                tv.setText(tv.getText()+"Fallo en calidad baja\n");
            }else if(divisiones==60){
                errores+="Creación de OBJ calidad alta\n";
                tv.setText(tv.getText()+"Fallo en calidad alta\n");
            }
        }
    }


     private String generarNombreArchivo(String calidad){
        String aux = lista.get(0).replace("IMG", "OBJ");
        aux = aux.replace(".jpg", calidad+".obj");
        //aux+=calidad+".obj";
        return aux;
     }

     private void limpiarLista(){
        ///Eliminar los archivos de la carpeta
         CameraActivity.listaImagenes.clear();
         lista.clear();
     }

     private void mostrarDialogErrores(){

         AlertDialog.Builder build = new AlertDialog.Builder(this);
         build.setMessage(this.errores);
         build.setTitle("ERRORES");
         build.setPositiveButton("OK", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialogInterface, int i) {
                 finish();
                 Intent intent = new Intent(getBaseContext(), MainActivity.class);
                 startActivity(intent);
             }
         });

         AlertDialog dialog = build.create();
         dialog.show();
     }

}
