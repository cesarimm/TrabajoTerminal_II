package Procesamiento;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;

public class SubRectangulos {

    public static ArrayList<Point> generarPuntosX(int x, int xMax, int y, int yMax, double longitud, int divisiones, Mat mat){
        Mat aux = Mat.zeros(mat.size(), CvType.CV_8U);
        ArrayList<Point> listaPuntos = new ArrayList<Point>();

        Point a, b;

        double lon=longitud/divisiones;
        double xRef[] = new double[divisiones+1];
        xRef[0]=y+7;
        xRef[divisiones]=yMax-12;


        for(int i=1;i<divisiones;i++){
            xRef[i]=(lon * i)+y+5;;
        }


        for (int i = y; i <=yMax; i++) {
            a=b=null;
            for (int j = x; j < xMax; j++) {
                for(int p=0;p<xRef.length;p++){
                    if((int)xRef[p]==i){

                        //System.out.println("xRef: "+xRef[p]);
                        try{
                            for (int k = 0; k < mat.get(j, i).length; k++) {
                                if(mat.get(j, i)[k]!=0){
                                    //       System.out.println("x:"+j+" y:"+i+" "+mat.get(j, i)[k]);
                                    //Imgproc.circle(aux, new Point(i,j), 1, new Scalar(255,0,0), 2, 8, 0);
                                    ///Guardar los puntos
                                    if(a==null){
                                        a = new Point(i, j);
                                    }else{
                                        b = new Point(i, j);
                                    }
                                }

                            }
                        }catch(Exception e){

                        }

                    }
                }
            }

            if(a!=null){
                listaPuntos.add(a);
                listaPuntos.add(b);
            }


        }

        System.out.println("Size: "+listaPuntos.size());
        for (int i = 0; i < listaPuntos.size(); i++) {
            try{
                Imgproc.circle(aux, listaPuntos.get(i), 1, new Scalar(255,0,0), 2, 8, 0);
            }catch(Exception e){
                // System.out.println("niPex");
            }
        }

        //nuevaSintaxisOBJ(listaPuntos, 100.0);

        return listaPuntos;
    }


    public static Mat generarPuntosY(int x,int xMax, int y, int yMax, double longitud, int divisiones, Mat mat){
        Mat aux = Mat.zeros(mat.size(), CvType.CV_8U);
        ArrayList<Point> listaPuntos = new ArrayList<Point>();

        Point a, b;

        longitud=Herramientas.yMax - Herramientas.yMin;

        double lon=longitud/divisiones;
        double xRef[] = new double[divisiones+1];
        xRef[0]=x;
        xRef[divisiones]=xMax+12;

        for(int i=1;i<divisiones;i++){
            xRef[i]=(lon * i)+y;;
        }


        for (int i = x; i <=xMax; i++) {
            a=b=null;
            for (int j = y; j < yMax; j++) {
                for(int p=0;p<xRef.length;p++){
                    if((int)xRef[p]==i){

                        //System.out.println("xRef: "+xRef[p]);
                        try{
                            for (int k = 0; k < mat.get(i, j).length; k++) {
                                if(mat.get(i, j)[k]!=0){
                                    //       System.out.println("x:"+j+" y:"+i+" "+mat.get(j, i)[k]);
                                    //Imgproc.circle(aux, new Point(i,j), 1, new Scalar(255,0,0), 2, 8, 0);
                                    ///Guardar los puntos
                                    if(a==null){
                                        a = new Point(j, i);
                                    }else{
                                        b = new Point(j, i);
                                    }
                                }

                            }
                        }catch(Exception e){

                        }

                    }
                }
            }

            if(a!=null){
                listaPuntos.add(a);
                listaPuntos.add(b);
            }


        }


        for (int i = 0; i < listaPuntos.size(); i++) {
            try{
                Imgproc.circle(aux, listaPuntos.get(i), 1, new Scalar(255,0,0), 2, 8, 0);
            }catch(Exception e){
                // System.out.println("niPex");
            }
        }

        //nuevaSintaxisOBJ(listaPuntos, 100.0);

        return aux;
    }

}
