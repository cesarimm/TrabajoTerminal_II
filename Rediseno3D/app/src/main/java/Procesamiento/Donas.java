package Procesamiento;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Donas {

    public static ArrayList<Point> puntosGenerarDonas(Mat original){

        ///Otros variables auxiliares
         Random rng = new Random(12345);

            ////Auxiliar para encontrar el centro del circulo
            Imgproc.cvtColor(original, original, Imgproc.COLOR_BGR2GRAY);
            Imgproc.medianBlur(original, original, 5);


        //Para buscar y dibujar circulos
        Mat circleOut = new Mat();
        Point central=new Point();
        Imgproc.HoughCircles(original, circleOut, Imgproc.HOUGH_GRADIENT, (double)original.rows()/16, 100.0, 30.0, 1, 30);

        for(int x=0;x<1;x++){
            try{
                double[] c = circleOut.get(0, x);

                //Centro del circulo

                //Imgproc.circle(src, centro, 1, new Scalar(0,100,100),3,8,0);

                central=new Point(Math.round(c[0]), Math.round(c[1]));
               // int radio = (int) Math.round(c[2]);
                //Imgproc.circle(src, centro, radio, new Scalar(255,0,255),3,8,0);
            }catch(Exception e){

            }

        }


        ////Se va a mandar a procesar
        Mat cannyOutput = new Mat();
        Imgproc.Canny(original, cannyOutput, 100, 200);

        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(cannyOutput, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        List<MatOfPoint> hullList = new ArrayList<>();
        for (MatOfPoint contour : contours) {
            MatOfInt hull = new MatOfInt();
            Imgproc.convexHull(contour, hull);
            Point[] contourArray = contour.toArray();
            Point[] hullPoints = new Point[hull.rows()];
            List<Integer> hullContourIdxList = hull.toList();
            for (int i = 0; i < hullContourIdxList.size(); i++) {
                hullPoints[i] = contourArray[hullContourIdxList.get(i)];
            }
            hullList.add(new MatOfPoint(hullPoints));
        }

        ArrayList<Point> aux = new ArrayList<Point>();

       /* Mat drawing = Mat.zeros(cannyOutput.size(), CvType.CV_8UC3);

        for (int i = 0; i < contours.size(); i++) {
            Scalar color = new Scalar(rng.nextInt(256), rng.nextInt(256), rng.nextInt(256));
            Imgproc.drawContours(drawing, contours, i, color);
            //Imgproc.drawContours(drawing, hullList, i, color );
        }*/


        ///CONTORNOS
        double yProm=0, xProm=0;
        int totalPuntos=0,  rango=5, mod = 1, refMod=0;
        for (int i = 0; i < contours.size(); i++) {
            List<Point> puntos = contours.get(i).toList();
            //Obtener los puntos de cada parte del contorno
            for (int j = 0; j < puntos.size(); j++) {
                xProm+=puntos.get(j).x;
                yProm+=puntos.get(j).y;
                totalPuntos++;
            }

        }

        yProm=yProm/totalPuntos;
        xProm=xProm/totalPuntos;

        ////Crear el punto promedio entre el centro del circulo y el de todos los puntos
        xProm=(xProm+central.x)/2;
        yProm=(yProm+central.y)/2;

        ///Recorrer nuevamente cada contorno y ver si su centro de masa Por controno se encuentra
        //dentro del reango aceptado para eliminar interferencias

        double yAux=0, xAux=0;
        int divisiones=0;
        for (int i = 0; i < contours.size(); i++) {
            List<Point> puntos = contours.get(i).toList();
            //Obtener los puntos de cada parte del contorno
            for (int j = 0; j < puntos.size(); j++) {
                xAux+=puntos.get(j).x;
                yAux+=puntos.get(j).y;
                divisiones++;
            }

            xAux=xAux/divisiones;
            yAux=yAux/divisiones;


            if(!((yAux<yProm-rango||yAux>yProm+rango)&&(xAux<xProm-rango||xAux>xProm+rango))){
                System.out.println("General x:"+xProm+" y:"+yProm);
                System.out.println("Contorno x:"+xAux+" y:"+yAux);
                for (int j = 0; j < puntos.size(); j++) {
                    if(refMod%mod==0){
                        //Imgproc.circle(drawing, puntos.get(j), 5, new Scalar(255, 0, 0), 2, 8, 0);
                        aux.add(puntos.get(j));
                    }
                    refMod++;
                }
            }


            divisiones=0;
            yAux=0;
            xAux=0;
        }

         ///Se debe de mandar a ordenar, posteiormente a generar Sintaxis
        return aux;
    }

}
