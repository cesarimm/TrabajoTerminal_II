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

public class Procesamiento {

    public static ArrayList<Point> convexHull(Mat mat){
        Random rng = new Random(12345);
        ////Convex Hull
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(mat, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
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


        Mat drawing = Mat.zeros(mat.size(), CvType.CV_8UC3);

        for (int i = 0; i < contours.size(); i++) {
            Scalar color = new Scalar(rng.nextInt(256), rng.nextInt(256), rng.nextInt(256));
            Imgproc.drawContours(drawing, contours, i, color);
            //Imgproc.drawContours(drawing, hullList, i, color );
        }

        int mod = 2;
        //Obtener todos los puntos de los diferentes contornos
        int k = 0;
        for (int i = 0; i < contours.size(); i++) {
            List<Point> puntos = contours.get(i).toList();
            //Obtener los puntos de cada parte del contorno
            for (int j = 0; j < puntos.size(); j++) {
                if (k % mod == 0) {
                    aux.add(puntos.get(j));

                }
                k++;
            }
        }

       return aux;
    }
}
