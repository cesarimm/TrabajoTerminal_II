package Procesamiento;

import org.opencv.core.Point;

import java.util.ArrayList;

public class Rotaciones {

    public static ArrayList<Point> obtenerCirculos(Point p, double radio, double divisiones){
        ArrayList<Point> puntos = new ArrayList<Point>();

        int cont=0;
        double cx,cy;

        double angulo=0;
        double xm=p.x;
        double ym=p.y;

        do
        {

            cx= xm + radio * (float) Math.cos(angulo);
            cy= ym + radio * (float) Math.sin(angulo);

            // g.setColor(Color.blue);
            //g.drawRect((int) cx , (int) cy, 0,0);

            //System.out.println("x:"+cx+" y:3 "+" z:"+cy);
            puntos.add(new Point(cx,cy));
            cont++;
            angulo = angulo + divisiones;
        }while(angulo < 6.8);

        //System.out.println("cont: "+cont);
        return puntos;

    }

}
