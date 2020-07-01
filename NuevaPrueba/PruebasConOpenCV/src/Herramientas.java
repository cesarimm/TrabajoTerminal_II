
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class Herramientas {
    
    public static double  min = 0, max =0, yMin=0, yMax=0;
    
    public static double distanciaEuclidiana(Point a, Point b){
        return Math.sqrt(Math.pow((double)a.x-b.x,2)+Math.pow((double)a.y-b.y,2));
    }
    
    
    public static Point puntoMedio(Point a, Point b){
        return new Point(Math.round((a.x+b.x)/2), Math.round((a.y+b.y)/2));
    }
    
    private  static ArrayList<Point> ordenar(ArrayList<Point> a){ 
         for (int i=0; i < a.size()-1;i++){
              for(int j=0; j < a.size()-1;j++){
                  if (a.get(j).x>a.get(j+1).x){
                    Point aux = a.get(j);
                    a.set(j, a.get(j+1));
                    a.set(j+1, aux);
                  }
              }
         }
         return a;
    }
    
    
    

    
    public static ArrayList<Point> limpiarLista(ArrayList<Point> aux){        
        int contador=0;   
        for (int i = 0; i < aux.size(); i++) {
            for (int j = 0; j <aux.size(); j++) {
                if(j!=i&&aux.get(i).equals(aux.get(j))){
                   aux.remove(j);
                   contador++;
                }
            }
        }         
        return aux;
    }
    
    public static int regresarReferencia(Point aux, ArrayList<Point> lista){
        int referencia = 0, j = 1;
                
        double distanciaAux = Herramientas.distanciaEuclidiana(aux, lista.get(0)); 
        
//        if(distanciaAux==0){        
//            do{
//             distanciaAux = Herramientas.distanciaEuclidiana(aux, lista.get(j));
//             j++;
//            }while(distanciaAux==0);
//        }
        
        double distanciaTemporal = 0;
        for(int i=j;i<lista.size();i++){   
            distanciaTemporal = Herramientas.distanciaEuclidiana(aux, lista.get(i));       
             if(distanciaTemporal<distanciaAux){
                  referencia = i;
                  distanciaAux = distanciaTemporal;
              }            
        }
          
        return referencia;
    }
    
    
      public static ArrayList<Point> ordenarPuntos(ArrayList<Point> aux){  
          
        int referencia = 0; 
        ArrayList<Point> listaOrdenada = new ArrayList<Point>(); 
        
        //Eliminar los puntos repetidos de la lista
        aux = Herramientas.limpiarLista(aux);
        
         // System.out.println("TI: "+aux.size());
          
        
            ///Elegimos un punto y lo eliminamos de la lista ahora buscamos el punto más cercano.
            Point puntoAux = aux.get(0);
            aux.remove(0);

            //Este punto lo agregamos a la lista ordenada
            listaOrdenada.add(puntoAux);

            ///Ejecutamos la funcion de buscar para obtener la referencia
             referencia = Herramientas.regresarReferencia(puntoAux, aux);

             do{
             puntoAux = aux.get(referencia);
             aux.remove(referencia);
             listaOrdenada.add(puntoAux);
               
              referencia = Herramientas.regresarReferencia(puntoAux, aux);
             }while(aux.size()!=1);
             
       
        
       
        listaOrdenada.add(aux.get(0));
       // System.out.println("TF: "+listaOrdenada.size());
        
//          for (int i = 0; i < listaOrdenada.size(); i++) {
//              //System.out.println("Punto: "+listaOrdenada.get(i).x+" "+listaOrdenada.get(i).y);
//              System.out.println("("+listaOrdenada.get(i).x+", "+listaOrdenada.get(i).y+")");
//          }
        
          return listaOrdenada;
    }
      
      
    public static double getLongitud(ArrayList<Point> aux){      
        double xMin=aux.get(0).x, xMax=aux.get(aux.size()-1).x;
        double yMinimo = aux.get(0).y, yMaximo = aux.get(0).y;
        double longitud=0;
         
        for (int i = 1; i <aux.size()-1; i++) {
           
            ///Para x
           if(aux.get(i).x<xMin){
               xMin=aux.get(i).x;
           }
           
           if(aux.get(i).x>xMax){
               xMax=aux.get(i).x;
           }
           
           //Para y
           if(aux.get(i).y<yMinimo){
               yMinimo=aux.get(i).y;
           }
           
           if(aux.get(i).y>yMaximo){
               yMaximo=aux.get(i).y;
           }
        }
        
       
        Herramientas.min = xMin; Herramientas.max = xMax;
        Herramientas.yMin = yMinimo; Herramientas.yMax = yMaximo;
        System.out.println("Min: "+xMin+" Max: "+xMax);
        System.out.println("YMin: "+yMinimo+" yMax: "+yMaximo);
        longitud = xMax - xMin;
        System.out.println("Longitud: "+longitud);
    
        return longitud;
    }
    
   
    
   
      
    
     public static void sintaxisOBJ(ArrayList<Point> aux, int indice){
         double xProm=0, yProm=0;
          
        for(int i=0;i<aux.size();i++){
            System.out.println("v "+aux.get(i).x+" "+aux.get(i).y+" 0");
            xProm+=aux.get(i).x;
            yProm+=aux.get(i).y;
        }
        
        System.out.println("v "+Math.ceil(xProm/aux.size())+" "+Math.ceil(yProm/aux.size())+" 0");
        
        System.out.println("usemtl Default");
         
       
       for(int i=0;i<aux.size()-1;i++){
            System.out.println("f "+(i+1+indice)+" "+(i+2+indice)+" "+(aux.size()+indice+1));
        }
       
        System.out.println("f "+(1+indice)+" "+(aux.size()+indice)+" "+(aux.size()+indice+1));
           
      }
      
      public static void sintaxisOBJ(ArrayList<Point> aux){
          double xProm=0, yProm=0;
          int arreglo[] = new int[aux.size()+1];
          
        for(int i=0;i<aux.size();i++){
            System.out.println("v "+aux.get(i).x+" "+aux.get(i).y+" 0");
            xProm+=aux.get(i).x;
            yProm+=aux.get(i).y;
        }
        
        System.out.println("v "+Math.ceil(xProm/aux.size())+" "+Math.ceil(yProm/aux.size())+" 0");
        
        System.out.println("usemtl Default");
         
       
       for(int i=0;i<aux.size()-1;i++){
            System.out.println("f "+(i+1)+" "+(i+2)+" "+(aux.size()+1));
            arreglo[i+1]++;
            arreglo[i+2]++;
        }
       
        System.out.println("f "+1+" "+aux.size()+" "+(aux.size()+1));
        
        for(int i=0;i<aux.size()+1;i++){
            if(arreglo[i]==1){
                System.out.println(i+1);
            }
        }
      }
      
      
      
         public static void sintaxisOBJV2(ArrayList<Point> aux, double largo){
          double xProm=0, yProm=0;
          int arreglo[] = new int[aux.size()+1];
          
        for(int i=0;i<aux.size();i++){
            System.out.println("v "+aux.get(i).x+" "+aux.get(i).y+" 0");
            xProm+=aux.get(i).x;
            yProm+=aux.get(i).y;
        }
        
        System.out.println("v "+Math.ceil(xProm/aux.size())+" "+Math.ceil(yProm/aux.size())+" 0");
        
        ///Para duplicar los puntos
        for(int i=0;i<aux.size();i++){
            System.out.println("v "+aux.get(i).x+" "+aux.get(i).y+" "+largo);
            //xProm+=aux.get(i).x;
            //yProm+=aux.get(i).y;
        }
        
        System.out.println("v "+Math.ceil(xProm/aux.size())+" "+Math.ceil(yProm/aux.size())+" "+largo);
        
        System.out.println("usemtl Default");
         
       
        ///Imprimir las caras laterales
        
       for(int i=0;i<aux.size()-1;i++){
            ///Primer triangulo
            System.out.println("f "+(i+1)+" "+(i+2)+" "+(aux.size()+1));
                   
        }
       
       System.out.println("f "+1+" "+aux.size()+" "+(aux.size()+1));
       
       for(int i=0;i<aux.size()-1;i++){
            ///Primer triangulo
            System.out.println("f "+(aux.size()+(i+2))+" "+(aux.size()+(i+3))+" "+((aux.size()*2)+2));
            
            
        }
       
       
        System.out.println("f "+(aux.size()+2)+" "+((aux.size()*2)+1)+" "+((aux.size()*2)+2));
        
        //Union entrre las caras laterales
         for(int i=0;i<aux.size()-1;i++){
            System.out.println("f "+(i+1)+" "+(i+2)+" "+(aux.size()+(i+2)));
            System.out.println("f "+(aux.size()+(i+2))+" "+(aux.size()+(i+3))+" "+(i+2));    
        }
         
        //Las ultimas dos caras
        System.out.println("f "+1+" "+(aux.size()-1)+" "+((aux.size()*2)+1));
        System.out.println("f "+1+" "+((aux.size()*2)+1)+" "+(aux.size()+2));   
        
      }
      
      
       public static void sintaxisOpenGL(ArrayList<Point> aux){
          double xProm=0, yProm=0;
          
        for(int i=0;i<aux.size();i++){
            System.out.println(+aux.get(i).x/200+"f,"+aux.get(i).y/200+"f, 0f,");
            xProm+=aux.get(i).x;
            yProm+=aux.get(i).y;
        }
        
        System.out.println(Math.ceil(xProm/aux.size())/200+"f,"+Math.ceil(yProm/aux.size())/200+"f,0f");
        
        System.out.println("usemtl Default");
         
       
//       for(int i=0;i<aux.size()-1;i++){
//            System.out.println("(byte)"+(i)+",(byte)"+(i+1)+",(byte)"+(aux.size())+",");
//        }
//       
//        System.out.println(0+",(byte)"+(aux.size()-1)+",(byte)"+(aux.size()));

         for(int i=0;i<aux.size()-1;i++){
            System.out.println(""+(i)+","+(i+1)+","+(aux.size())+",");
        }
       
        System.out.println(0+","+(aux.size()-1)+","+(aux.size()));
      }
      
    
//    public static void ordenarPuntos(ArrayList<Point> aux){
//        
//        ArrayList<Point> listaOrdenada = new ArrayList<Point>();
//        
//        for(int j=0;j<aux.size()-1;j++){
//            
//        double distanciaAux = Herramientas.distanciaEuclidiana(aux.get(j), aux.get(j+1));
//        int ref = j+1;
//        
//        System.out.println("Pares Ordenados: "+j+" "+(j+1));
//        
//        double distanciaTemporal = 0;
//        for(int i=j+1;i<aux.size()-1;i++){
//              distanciaTemporal = Herramientas.distanciaEuclidiana(aux.get(j), aux.get(i+1));
//              System.out.println("Pares Ordenados: "+j+" "+(i+1));
//             // System.out.println("Distancia temporal: "+distanciaTemporal);
//              if(distanciaTemporal<distanciaAux){
//                  ref = i+1;
//                  distanciaAux = distanciaTemporal;
//              }
//        }
//         
//             System.out.println("Distancia menor: "+distanciaAux+" Referencia: "+ref);
//            System.out.println("Nuevo");
//       
//            
//        }          
//    }
       
       
      
       
       public static void obetenerDatos(String archivo){
           String cadena="";
            ArrayList<String> vertices = new ArrayList<String>();
            ArrayList<String> caras = new ArrayList<String>();
            double[] v; 
            byte[] faces;
        try {
            FileReader f = new FileReader(archivo);
            BufferedReader b = new BufferedReader(f);
            
               try {
                   while((cadena = b.readLine())!=null) {
                       
                     //  System.out.println(cadena);
                    
                       
                       if(cadena.length()>=1){
                           if(cadena.substring(0, 1).equals("v")){
                           vertices.add(cadena.replace("v ", ""));
                            }else if(cadena.substring(0, 1).equals("f")){
                                caras.add(cadena.replace("f ",""));
                            }   
                       }
                                          
                       
                    }      
                    b.close();
               } catch (IOException ex) {
                   Logger.getLogger(Herramientas.class.getName()).log(Level.SEVERE, null, ex);
               }
               
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Herramientas.class.getName()).log(Level.SEVERE, null, ex);
        }
        
          v=new double[vertices.size()*3];
          faces= new byte[caras.size()*3];
        
          int cont=0;
          for (int i = 0; i < vertices.size(); i++) {
               System.out.println(vertices.get(i));
                String[] palabras = vertices.get(i).split(" ");
                for(int j=0;j<palabras.length;j++){
                  //  System.out.println(palabras[j]);
                 v[cont]=Double.parseDouble(palabras[j]);
                 cont++;
                }            
          }
          
          
          
            cont=0;
          for (int i = 0; i < caras.size(); i++) {
               System.out.println(caras.get(i));
                String[] palabras = caras.get(i).split(" ");
                 for(int j=0;j<palabras.length;j++){
                     faces[cont]=(byte)Byte.parseByte(palabras[j]);               
                  cont++;
                } 
          }

          System.out.println("");
       }
       
       
       
     
    
    public static void main(String args[]){
        
        Herramientas.obetenerDatos("D:\\Documents\\Trabajo_Terminal_Dos\\Modelos3D\\cuadroRaro.obj");
//        Point p1 = new Point(177,394);
//        Point px = new Point(177, 394);
//        Point p2 = new Point(179,82);
//        Point p3 = new Point(180,437);
//        Point p4 = new Point(205,85);
//        Point p5 = new Point(216,83);
//        
//        ArrayList<Point> aux = new ArrayList<Point>();
//
//        
//      // Herramientas.ordenarPuntos(aux);  
//       
//   aux =  Herramientas.ordenarPuntos(aux);
//        System.out.println("");
//        Herramientas.sintaxisOBJ(aux);
//       //Herramientas.sintaxisOpenGL(aux);
//       
//       // System.out.println(Herramientas.regresarReferencia(p1,  Herramientas.limpiarLista(aux)));
    }
    
}
