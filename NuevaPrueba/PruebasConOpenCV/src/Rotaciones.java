
import java.text.DecimalFormat;
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
public class Rotaciones {
    
    
  public static ArrayList<Point> obtenerCirculos( Point p, double radio, double divisiones){
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
  
  
  
  public static void generarSintaxisCilindrosOBJ(ArrayList<Point> aux){
      
      ArrayList<Point> pCir;
      Point a, b;
      
      DecimalFormat df = new DecimalFormat("#.00");
      String texto = "";
      int cont=0, longitud=0;
      
      System.out.println("Tamañito: "+aux.size());
      
      for(int i=0;i<aux.size()-2;i+=2){
          try{
            a = new Point(aux.get(i).x, 0);
          b = new Point(aux.get(i+1).x, 0);
          
          Point medio = Herramientas.puntoMedio(a, b);
          Double radio = Herramientas.distanciaEuclidiana(medio, a);
          pCir = Rotaciones.obtenerCirculos(medio, radio, .227);
          
          for (int j = 0; j<pCir.size(); j++) {
              texto+="v "+df.format(pCir.get(j).x)+" "+aux.get(i).y+" "+df.format(pCir.get(j).y)+"\n";
          }
          
          cont++;
          longitud = pCir.size();  
          }catch(Exception e){
              
          }
          
      }
      
      texto+="usemtl Default\n";
      
      
      
      int contAux=0;
      int limite =  longitud*(cont-1);
      for(int i=1;i<cont;i++){
          for (int j = contAux; j < longitud*i; j++) {
              if(limite-1!=contAux){
                  texto+="f "+(j+1)+" "+(j+2)+" "+(longitud+(j+1))+"\n";
              texto+="f "+(longitud+(j+1))+" "+(longitud+(j+2))+" "+(j+2)+"\n";
              contAux++;
              }
              
            //System.out.println("f "+(j+1)+" "+(j+2)+" "+(longitud+(j+1)));
            //System.out.println("f "+(longitud+(j+1))+" "+(longitud+(j+2))+" "+(j+2));
            
          }
          System.out.println("");
      }
          
      
      System.out.println(texto);
      //System.out.println("Cont: "+contAux+" Lim: "+limite);
      
  }
 
  
  
  
    
    
    public static void main(String Args[]){
       Rotaciones.obtenerCirculos(new Point(2,0), 2, 0.227);
    }
    
}
