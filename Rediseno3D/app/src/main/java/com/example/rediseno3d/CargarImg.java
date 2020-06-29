package com.example.rediseno3d;

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
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;

public class CargarImg extends AppCompatActivity {

    ImageView imageView;
    Bitmap grayBitmap, imagenBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargar_img);

        imageView = (ImageView) findViewById(R.id.imagenCargar);
        OpenCVLoader.initDebug();
        crearCarpeta();
    }

    public void crearCarpeta() {
        // File file = new File(Environment.getExternalStorageDirectory()+"/archivos/MyDesign3D/IMG_20200304_110721.jpg");
        //File file = new File(Environment.getExternalStorageDirectory() + "/R3D/imagenes/IMG_20200613_181957.jpg");
        //File file = new File(Environment.getExternalStorageDirectory() + "/R3D/imagenes/IMG_20200627_232017.jpg");
        File file = new File(Environment.getExternalStorageDirectory() + "/R3D/imagenes/IMG_20200628_184049.jpg");
        if (!file.exists()) {
            Toast.makeText(getApplicationContext(), "No existe... ", Toast.LENGTH_SHORT).show();
        } else {
            Uri uri = Uri.parse(file.getAbsolutePath());


            try{
                imagenBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            }catch (Exception e){
                e.printStackTrace();
            }

            imageView.setImageBitmap(imagenBitmap);

            grayBitmap = Bitmap.createBitmap(imagenBitmap.getWidth(), imagenBitmap.getHeight(), Bitmap.Config.RGB_565);


            Mat rgba = new Mat();
            Mat grayMat = new Mat();
            Mat nuevo = new Mat();

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inDither = false;
            options.inSampleSize = 4;


            //BITMAP TO MAP
            Utils.bitmapToMat(imagenBitmap, nuevo);


            ///Setear el tamaño de la imagen


            Size sz = new Size(800, 600);
            Imgproc.resize(nuevo, rgba, sz);
            ///De esta o de la otra manera funcionan
            rotacion(rgba, 270);


            Imgproc.cvtColor(rgba,grayMat,Imgproc.COLOR_RGB2BGRA);
            Imgproc.Canny(grayMat, rgba, 100, 200);
            //Imgproc.circle(rgba, new Point(600,600), 50, new Scalar(255, 0, 0), 2, 8, 0);
            grayBitmap = Bitmap.createBitmap(rgba.width(), rgba.height(), Bitmap.Config.RGB_565);

            Utils.matToBitmap(rgba, this.grayBitmap);
            imageView.setImageBitmap(this.grayBitmap);

        }
        file = null;
    }


    public  void rotacion(Mat imagen, double angulo){
        double radianes = Math.toRadians(angulo);
        double sin = Math.abs(Math.sin(radianes));
        double cos = Math.abs(Math.cos(radianes));
            //Definir los nuevo tamaños de la imagen
        int newWidth = (int) (imagen.width() * cos + imagen.height() * sin);
        int newHeight = (int) (imagen.width() * sin + imagen.height() * cos);
          //Realiar la rotacion de la imagen
        Point center = new Point(newWidth / 2, newHeight / 2);

        Toast.makeText(getApplicationContext(), "w:"+newWidth / 2+" h:"+newHeight / 2, Toast.LENGTH_LONG).show();

        Mat rotMatrix = Imgproc.getRotationMatrix2D(center, angulo, 1.0); //1.0 means 100 % scale

        Size size = new Size(750, 950);
        Imgproc.warpAffine(imagen, imagen, rotMatrix, size);

    }

}
