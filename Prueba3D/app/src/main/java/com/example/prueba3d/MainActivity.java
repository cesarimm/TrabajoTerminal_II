package com.example.prueba3d;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.Toast;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Bitmap grayBitmap, imagenBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OpenCVLoader.initDebug();
        imageView = (ImageView) findViewById(R.id.imagenCargar);
        crearCarpeta();

     /*  if(OpenCVLoader.initDebug()){
           Toast.makeText(getApplicationContext(), "Si", Toast.LENGTH_LONG).show();

       }else{
           Toast.makeText(getApplicationContext(), "Si", Toast.LENGTH_LONG).show();
       }*/
    }

    public void crearCarpeta() {

        // File file = new File(Environment.getExternalStorageDirectory()+"/archivos/MyDesign3D/IMG_20200304_110721.jpg");
        File file = new File(Environment.getExternalStorageDirectory() + "/archivos/MyDesign3D/engrane.jpg");
        if (!file.exists()) {
            Toast.makeText(getApplicationContext(), "No existe... ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Si existe... ", Toast.LENGTH_SHORT).show();
            Uri uri = Uri.parse(file.getAbsolutePath());


            try{
                imagenBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            }catch (Exception e){
                e.printStackTrace();
            }

            imageView.setImageBitmap(imagenBitmap);

           /* grayBitmap = Bitmap.createBitmap(imagenBitmap.getWidth(), imagenBitmap.getHeight(), Bitmap.Config.RGB_565);


            Mat rgba = new Mat();
            Mat grayMat = new Mat();

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inDither = false;
            options.inSampleSize = 4;


            //BITMAP TO MAP
            Utils.bitmapToMat(imagenBitmap, rgba);
            Imgproc.cvtColor(rgba,grayMat,Imgproc.COLOR_RGB2BGRA);
            Imgproc.Canny(grayMat, rgba, 80, 90);

            Utils.matToBitmap(rgba, this.grayBitmap);
            imageView.setImageBitmap(this.grayBitmap); */

        }
        file = null;
    }
}
