package Procesamiento;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.Toast;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.File;

public class Preprocesamiento {

    public static Mat preProceamiento(String nameFile){
        //File file = new File(Environment.getExternalStorageDirectory() + "/R3D/imagenes/IMG_20200629_195356.jpg");
        File file = new File(Environment.getExternalStorageDirectory() + "/R3D/imagenes/"+nameFile);
        ImageView imageView;
        Bitmap  imagenBitmap=null;

        if (!file.exists()) {
            //Toast.makeText(getApplicationContext(), "No existe... ", Toast.LENGTH_SHORT).show();
            return null;
        } else {
            Uri uri = Uri.parse(file.getAbsolutePath());


            try{
                imagenBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            }catch (Exception e){
                e.printStackTrace();
            }

            //imageView.setImageBitmap(imagenBitmap);

            //grayBitmap = Bitmap.createBitmap(imagenBitmap.getWidth(), imagenBitmap.getHeight(), Bitmap.Config.RGB_565);


            Mat rgba = new Mat();
            Mat grayMat = new Mat();
            Mat nuevo = new Mat();

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inDither = false;
            options.inSampleSize = 4;


            //BITMAP TO MAP
            Utils.bitmapToMat(imagenBitmap, nuevo);


            ///Setear el tamaño de la imagen


            //Size sz = new Size(800, 400);
            Size sz = new Size(800, 400);
            Imgproc.resize(nuevo, rgba, sz);
            ///De esta o de la otra manera funcionan
            //rotacion(rgba, 270);


            Imgproc.cvtColor(rgba,grayMat,Imgproc.COLOR_BGR2GRAY);

            Imgproc.blur(grayMat, rgba, new Size(3, 3));

            Imgproc.Canny(rgba, grayMat, 100, 200);

            file = null;
            return grayMat;

        }
    }


    public static void rotacion(Mat imagen, double angulo){
        double radianes = Math.toRadians(angulo);
        double sin = Math.abs(Math.sin(radianes));
        double cos = Math.abs(Math.cos(radianes));
        //Definir los nuevo tamaños de la imagen
        int newWidth = (int) (imagen.width() * cos + imagen.height() * sin);
        int newHeight = (int) (imagen.width() * sin + imagen.height() * cos);
        //Realiar la rotacion de la imagen
        Point center = new Point(newWidth / 2, newHeight / 2);

        //Toast.makeText(getApplicationContext(), "w:"+newWidth / 2+" h:"+newHeight / 2, Toast.LENGTH_LONG).show();

        Mat rotMatrix = Imgproc.getRotationMatrix2D(center, angulo, 1.0); //1.0 means 100 % scale

        Size size = new Size(750, 950);
        Imgproc.warpAffine(imagen, imagen, rotMatrix, size);
    }



}


