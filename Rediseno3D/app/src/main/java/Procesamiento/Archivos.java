package Procesamiento;

import android.os.Environment;
import android.security.keystore.KeyGenParameterSpec;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Archivos {

    public static  void crearArchvoOBJ(String nombre, String contenido) {

        String direccion = Environment.getExternalStorageDirectory()+"/R3D/obj/";
        //File mediaStorageDir = new File(Environment.getExternalStorageDirectory()+"/R3D/", "obj");

        try{
            File file = new File(direccion+nombre);

            if(!file.exists()){
                file.createNewFile();
            }


            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(contenido);
            bw.close();

        }catch(Exception e){

        }
    }

}
