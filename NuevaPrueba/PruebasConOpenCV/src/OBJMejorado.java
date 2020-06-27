
import java.util.ArrayList;
import org.opencv.core.Point;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author CESAR IVAN MTZ
 */
public class OBJMejorado {
    
    public static void objXYZ(ArrayList<Point> xy, ArrayList<Point> yz){ 
        ///Obtener minimos y maximos en los diferentes planos
        double xMin=yz.get(0).x, xMax=yz.get(xy.size()-1).x;
        double yMinimo = xy.get(0).y, yMaximo = xy.get(0).y;
        
        
        ///Generar los vertices de la primera cara del plano xy
          for(int i=0;i<xy.size();i++){
            System.out.println("v "+xy.get(i).x+" "+xy.get(i).y+" 0");
            
              //Obtener los valores para Y
           if(xy.get(i).y<yMinimo){
               yMinimo=xy.get(i).y;
           }
           
           if(xy.get(i).y>yMaximo){
               yMaximo=xy.get(i).y;
           }
        }
            
         
        ///Generarlos vertices de la primera cara del plano yz
          for(int i=0;i<yz.size();i++){
            System.out.println("v 0 "+yz.get(i).y+" "+yz.get(i).x);
            
            //Obtener los valores minimos y maximos en el eje de las x
            if(yz.get(i).x<xMin){
               xMin=yz.get(i).x;
           }
           
           if(yz.get(i).x>xMax){
               xMax=yz.get(i).x;
           }        
        }
          
         
        
         
         
             ///Generar los verices de la segunda cara del plano xy
              double longitud = xMax - xMin;          
           for(int i=0;i<xy.size();i++){
                System.out.println("v "+xy.get(i).x+" "+xy.get(i).y+" "+longitud);             
           }
           
           
          ///Encontrar el primer cambio radical en el eje x
          double cambio = xy.get(0).x;
                
          for (int i = 1; i <xy.size(); i++) {
           if((xy.get(i).x-cambio)>=longitud/3){
               cambio = xy.get(i).x;
               break;
           }
        }
           
          //Imprimir el cambio
          System.out.println("Cambio: "+cambio);
           
           ///Generar los verices de la segunda cara del plano yz  
            longitud = yMaximo - yMinimo;
          for(int i=0;i<yz.size();i++){
            System.out.println("v "+cambio+" "+yz.get(i).y+" "+yz.get(i).x);        
           } 
          
          ///GENERAR LAS CARAS
        int referencia =0, auxReferencia;
        //Primera cara del plano xy
          for(int i=0;i<(xy.size()-2);i++){
            System.out.println("f "+(i+1)+" "+(i+2)+" "+(i+3));
            referencia++;
        }
         
         
        //Primera cara del plano yz
         // System.out.println("Aqui");
          auxReferencia = referencia;
          for(int i=auxReferencia+2;i<auxReferencia+yz.size();i++){
            System.out.println("f "+(i+1)+" "+(i+2)+" "+(i+3));
            referencia++;
        }
          
         
         
            //Segunda  cara del plano xy
             //System.out.println("Aqui");            
             auxReferencia = referencia;
         for(int i=auxReferencia+4;i<auxReferencia+xy.size()+2;i++){
            System.out.println("f "+(i+1)+" "+(i+2)+" "+(i+3));
            referencia++;
        }
         
         
         //Segunda cara del plano xy
           //System.out.println("Aqui");
          auxReferencia = referencia;
          for(int i=auxReferencia+6;i<auxReferencia+yz.size()+4;i++){
            System.out.println("f "+(i+1)+" "+(i+2)+" "+(i+3));
            referencia++;
        }       
    }
    
    
    public static void main(String args[]){
        
        ArrayList<Point> xy = new ArrayList<Point>(), yz = new ArrayList<Point>();
        
        //cuando es cuadrado
//        xy.add(new Point(600,0));
//        xy.add(new Point(0,0));
//        xy.add(new Point(600,600));
//        xy.add(new Point(0,600));

        //Para la ele  barrido en y
        xy.add(new Point(0,600));
        xy.add(new Point(0,0));
        xy.add(new Point(300,600));
        xy.add(new Point(300,0));
        xy.add(new Point(310,200));
        xy.add(new Point(310,0));
        xy.add(new Point(600,200));
        xy.add(new Point(600,0));


//        //Paral la ele barrido en x
//        xy.add(new Point(0,0));
//        xy.add(new Point(600,0));
//        xy.add(new Point(0,200));
//        xy.add(new Point(600,200));
//        xy.add(new Point(210,0));
//        xy.add(new Point(210,300));
//        xy.add(new Point(0,600));
//        xy.add(new Point(300, 600));
        
        ///Cauadro mordido
        yz.add(new Point(0,600));
        yz.add(new Point(0,0));
        yz.add(new Point(190,600));
        yz.add(new Point(190,0));
        yz.add(new Point(200,400));
        yz.add(new Point(200,0));
        yz.add(new Point(400,400));
        yz.add(new Point(400,0));
        yz.add(new Point(410,600));
        yz.add(new Point(410,0));
        yz.add(new Point(600,600));
        yz.add(new Point(600,0));
        
       // xy =(ArrayList<Point>)yz.clone();
        
        OBJMejorado.objXYZ(xy, yz);
    }
    
}
