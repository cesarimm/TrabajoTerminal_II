package com.example.rediseno3d;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import org.opencv.android.OpenCVLoader;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    final int REQUEST_ACCESS_FINE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();


        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_ACCESS_FINE);
        }



        /// Crear las carpetas de la aplicacion
        this.crearCarpeta();
        if(OpenCVLoader.initDebug()){
           // Toast.makeText(getApplicationContext(), "Funcionamiento  correcto", Toast.LENGTH_SHORT).show();
        }else{
           // Toast.makeText(getApplicationContext(), "No funciona ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==REQUEST_ACCESS_FINE){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //Toast.makeText(this, "Permiso otorgado", Toast.LENGTH_SHORT).show();
                this.crearCarpeta();

                ///Permisos de la aplicación
                if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_ACCESS_FINE);
                }

            }else{
                Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void abrirVentanaCamara(View view){
        finish();
        Intent intent = new Intent(this, VistasMenuActivity .class);
        startActivity(intent);
    }

    public void abrirVentanaListView(View view){
        finish();
        Intent intent = new Intent(this, ObjListActivity.class);
        startActivity(intent);
    }

    public void abrirVentanaComoUtilizar(View view){
        finish();
        Intent intent = new Intent(this, CargarImg.class);
        startActivity(intent);
    }


    //Creacion de la carpeta que será utilizada por la aplicación
    public void crearCarpeta(){
        File file = new File(Environment.getExternalStorageDirectory()+"/R3D/");
        if(!file.exists()){
            file.mkdirs();
            //Toast.makeText(getApplicationContext(), "Creado: ", Toast.LENGTH_SHORT).show();
        }else{
            //Toast.makeText(getApplicationContext(), "Ya fue creado: "+file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        }



        //Creacion de la carpeta para archivos OBJ
        File fileOBJ = new File(Environment.getExternalStorageDirectory()+"/R3D/obj/");
        if(!fileOBJ.exists()){
            fileOBJ.mkdirs();
            //Toast.makeText(getApplicationContext(), "Creado: ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
