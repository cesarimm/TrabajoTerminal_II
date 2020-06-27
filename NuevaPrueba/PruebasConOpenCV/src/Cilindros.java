/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.opencv.core.Point;
/**
 *
 * @author CESAR IVAN MTZ
 */
public class Cilindros {
    public static void main(String args[]){
        Point a = new Point(3,4);
        Point b = new Point(5,6);
        
        ///Rotarlso 90 grados
        double x = 3*Math.cos(Math.PI/2)-4*Math.sin(Math.PI/2);
        double y = 3*Math.sin(Math.PI/2)+4*Math.cos(Math.PI/2);
        
        System.out.println("X: "+x+" Y: "+y);
        
        for (double i = 0; i <= 2; i+=.5) {
            System.out.println("("+i+",4,"+(2-i)+")");
             System.out.println("("+i+",0,"+(2-i)+")");
              System.out.println("("+(2-i)+",4,"+i+")");
                System.out.println("("+(2-i)+",0,"+i+")");
        }
    }
}
