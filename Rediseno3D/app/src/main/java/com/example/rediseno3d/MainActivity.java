package com.example.rediseno3d;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import org.opencv.android.OpenCVLoader;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        this.crearCarpeta();
        if(OpenCVLoader.initDebug()){
            Toast.makeText(getApplicationContext(), "Funcionamiento  correcto", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "No funciona ", Toast.LENGTH_SHORT).show();
        }
    }

    public void abrirVentanaCamara(View view){
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }

    public void abrirVentanaListView(View view){
        Intent intent = new Intent(this, ObjListActivity.class);
        startActivity(intent);
    }

    public void abrirVentanaComoUtilizar(View view){
        Intent intent = new Intent(this, Visualizador.class);
        startActivity(intent);
    }


    //Creacion de la carpeta que será utilizada por la aplicación
    public void crearCarpeta(){
        File file = new File(Environment.getExternalStorageDirectory()+"/R3D/");
        if(!file.exists()){
            file.mkdirs();
            Toast.makeText(getApplicationContext(), "Creado: ", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "Ya fue creado: "+file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        }



        //Creacion de la carpeta para archivos OBJ
        File fileOBJ = new File(Environment.getExternalStorageDirectory()+"/R3D/obj/");
        if(!fileOBJ.exists()){
            fileOBJ.mkdirs();
            //Toast.makeText(getApplicationContext(), "Creado: ", Toast.LENGTH_SHORT).show();
        }
    }

}
