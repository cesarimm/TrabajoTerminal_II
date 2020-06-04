
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
public class Herramientas {
    
    public static double distanciaEuclidiana(Point a, Point b){
        return Math.sqrt(Math.pow((double)a.x-b.x,2)+Math.pow((double)a.y-b.y,2));
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
    
    
    
//    
//        public static void ordenarPuntos(ArrayList<Point> aux){
//        
//        ArrayList<Point> listaOrdenada = new ArrayList<Point>();
//        
//                  
//        double distanciaAux = Herramientas.distanciaEuclidiana(aux.get(1), aux.get(2));
//        int ref = 2;
//        System.out.println("Distancia temporal: "+distanciaAux);
//        
//        double distanciaTemporal = 0;
//        for(int i=2;i<aux.size()-1;i++){
//              distanciaTemporal = Herramientas.distanciaEuclidiana(aux.get(1), aux.get(i+1));
//              System.out.println("Distancia temporal: "+distanciaTemporal);
//              if(distanciaTemporal<distanciaAux){
//                  ref = i+1;
//                  distanciaAux = distanciaTemporal;
//              }
//        }
//           
//          System.out.println("Distancia menor: "+distanciaAux+" Referencia: "+ref);
//            
//    }
    
 
    
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
        
          System.out.println("TI: "+aux.size());
          
        
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
        System.out.println("TF: "+listaOrdenada.size());
        
          for (int i = 0; i < listaOrdenada.size(); i++) {
              //System.out.println("Punto: "+listaOrdenada.get(i).x+" "+listaOrdenada.get(i).y);
              System.out.println("("+listaOrdenada.get(i).x+", "+listaOrdenada.get(i).y+")");
          }
        
          return listaOrdenada;
    }
      
      
      public static void sintaxisOBJ(ArrayList<Point> aux){
          double xProm=0, yProm=0;
          int arreglo[] = new int[aux.size()+1];
          
        for(int i=0;i<aux.size();i++){
            System.out.println(" v "+aux.get(i).x+" "+aux.get(i).y+" 0");
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
    
    public static void main(String args[]){
        Point p1 = new Point(177,394);
        Point px = new Point(177, 394);
        Point p2 = new Point(179,82);
        Point p3 = new Point(180,437);
        Point p4 = new Point(205,85);
        Point p5 = new Point(216,83);
        
        ArrayList<Point> aux = new ArrayList<Point>();
//        aux.add(px);
//        aux.add(px);
//        aux.add(p1);
//        aux.add(p2);
//        aux.add(p3);
//        aux.add(p4);
//        aux.add(p5);
//Cuadrado
//        aux.add(new Point(179.0,82.0));
//        aux.add(new Point(177.0,394.0));
//        aux.add(new Point(470.0,440.0));
//        aux.add(new Point(533.0,405.0));
//        aux.add(new Point(534.0,163.0));
//        aux.add(new Point(511.0,82.0));
//        aux.add(new Point(216.0,83.0));
//        aux.add(new Point(379.0,82.0));
//        aux.add(new Point(533.0,162.0));
//        aux.add(new Point(532.0,404.0));
//        aux.add(new Point(177.0,394.0));
//        aux.add(new Point(180.0,437.0));
//        aux.add(new Point(296.0,438.0));
//        aux.add(new Point(370.0,437.0));
//        aux.add(new Point(438.0,438.0));
//        aux.add(new Point(525.0,438.0));
//        aux.add(new Point(530.0,320.0));
//        aux.add(new Point(529.0,208.0));
//        aux.add(new Point(482.0,86.0));
//        aux.add(new Point(205.0,85.0));
//        aux.add(new Point(340.0,86.0));
//        aux.add(new Point(530.0,118.0));
//        aux.add(new Point(530.0,261.0));
//        aux.add(new Point(529.0,367.0));
//        aux.add(new Point(465.0,437.0));
//        aux.add(new Point(395.0,438.0));
//        aux.add(new Point(335.0,437.0));
   
//Puntos estrella
//        aux.add(new Point(111.0,141.0));
//        aux.add(new Point(101.0,167.0));
//        aux.add(new Point(92.0,196.0));
//        aux.add(new Point(95.0,186.0));
//        aux.add(new Point(104.0,158.0));
//        aux.add(new Point(126.0,145.0));
//        aux.add(new Point(162.0,154.0));
//        aux.add(new Point(166.0,155.0));
//        aux.add(new Point(130.0,146.0));
//        aux.add(new Point(47.0,143.0));
//        aux.add(new Point(6.0,152.0));
//        aux.add(new Point(33.0,146.0));
//        aux.add(new Point(69.0,148.0));
//        aux.add(new Point(78.0,180.0));
//        aux.add(new Point(83.0,198.0));
//        aux.add(new Point(74.0,166.0));
//        aux.add(new Point(169.0,65.0));
//        aux.add(new Point(179.0,156.0));
//        aux.add(new Point(179.0,56.0));
//        aux.add(new Point(30.0,124.0));
//        aux.add(new Point(40.0,142.0));
//        aux.add(new Point(72.0,147.0));
//        aux.add(new Point(81.0,179.0));
//        aux.add(new Point(91.0,188.0));
//        aux.add(new Point(100.0,160.0));
//        aux.add(new Point(117.0,140.0));
//        aux.add(new Point(153.0,149.0));
//        aux.add(new Point(72.0,147.0));
//        aux.add(new Point(41.0,141.0));
//        aux.add(new Point(30.0,125.0));
//        aux.add(new Point(0.0,50.0));
//        aux.add(new Point(0.0,150.0));
//        aux.add(new Point(5.0,53.0));
//        aux.add(new Point(83.0,34.0));
//        aux.add(new Point(74.0,62.0));
//        aux.add(new Point(42.0,61.0));
//        aux.add(new Point(12.0,53.0));
//        aux.add(new Point(46.0,62.0));
//        aux.add(new Point(75.0,59.0));
//        aux.add(new Point(84.0,31.0));
//        aux.add(new Point(94.0,13.0));
//        aux.add(new Point(103.0,45.0));
//        aux.add(new Point(120.0,69.0));
//        aux.add(new Point(161.0,60.0));
//        aux.add(new Point(148.0,128.0));
//        aux.add(new Point(139.0,117.0));
//        aux.add(new Point(156.0,61.0));
//        aux.add(new Point(115.0,70.0));
//        aux.add(new Point(101.0,40.0));
//        aux.add(new Point(92.0,9.0));
//        aux.add(new Point(102.0,32.0));
//        aux.add(new Point(111.0,64.0));
//        aux.add(new Point(146.0,60.0));
//        aux.add(new Point(177.0,53.0));
//        aux.add(new Point(136.0,62.0));
//        aux.add(new Point(108.0,51.0));
//        aux.add(new Point(99.0,19.0));
//        aux.add(new Point(86.0,13.0));
//        aux.add(new Point(77.0,42.0));
//        aux.add(new Point(60.0,62.0));
//        aux.add(new Point(24.0,53.0));
//        aux.add(new Point(8.0,49.0));
//        aux.add(new Point(44.0,58.0));
//        aux.add(new Point(73.0,54.0));
       
//aux.add(new Point(111.0,141.0));
//aux.add(new Point(107.0,146.0));
//aux.add(new Point(105.0,155.0));
//aux.add(new Point(102.0,162.0));
//aux.add(new Point(100.0,171.0));
//aux.add(new Point(97.0,178.0));
//aux.add(new Point(95.0,186.0));
//aux.add(new Point(92.0,194.0));
//aux.add(new Point(90.0,202.0));
//aux.add(new Point(90.0,200.0));
//aux.add(new Point(93.0,193.0));
//aux.add(new Point(95.0,184.0));
//aux.add(new Point(98.0,177.0));
//aux.add(new Point(100.0,168.0));
//aux.add(new Point(103.0,161.0));
//aux.add(new Point(105.0,153.0));
//aux.add(new Point(108.0,145.0));
//aux.add(new Point(116.0,143.0));
//aux.add(new Point(126.0,145.0));
//aux.add(new Point(135.0,148.0));
//aux.add(new Point(146.0,150.0));
//aux.add(new Point(155.0,153.0));
//aux.add(new Point(166.0,155.0));
//aux.add(new Point(175.0,158.0));
//aux.add(new Point(176.0,158.0));
//aux.add(new Point(167.0,156.0));
//aux.add(new Point(158.0,153.0));
//aux.add(new Point(147.0,151.0));
//aux.add(new Point(138.0,148.0));
//aux.add(new Point(127.0,146.0));
//aux.add(new Point(118.0,143.0));
//aux.add(new Point(64.0,140.0));
//aux.add(new Point(52.0,142.0));
//aux.add(new Point(42.0,145.0));
//aux.add(new Point(29.0,147.0));
//aux.add(new Point(19.0,150.0));
//aux.add(new Point(6.0,152.0));
//aux.add(new Point(5.0,153.0));
//aux.add(new Point(15.0,150.0));
//aux.add(new Point(28.0,148.0));
//aux.add(new Point(38.0,145.0));
//aux.add(new Point(51.0,143.0));
//aux.add(new Point(61.0,140.0));
//aux.add(new Point(69.0,146.0));
//aux.add(new Point(71.0,155.0));
//aux.add(new Point(74.0,164.0));
//aux.add(new Point(76.0,173.0));
//aux.add(new Point(79.0,181.0));
//aux.add(new Point(81.0,191.0));
//aux.add(new Point(84.0,199.0));
//aux.add(new Point(84.0,201.0));
//aux.add(new Point(82.0,192.0));
//aux.add(new Point(79.0,184.0));
//aux.add(new Point(77.0,174.0));
//aux.add(new Point(74.0,166.0));
//aux.add(new Point(72.0,156.0));
//aux.add(new Point(69.0,148.0));
//aux.add(new Point(67.0,140.0));
//aux.add(new Point(147.0,88.0));
//aux.add(new Point(140.0,114.0));
//aux.add(new Point(157.0,134.0));
//aux.add(new Point(177.0,156.0));
//aux.add(new Point(166.0,143.0));
//aux.add(new Point(149.0,123.0));
//aux.add(new Point(147.0,90.0));
//aux.add(new Point(10.0,55.0));
//aux.add(new Point(23.0,69.0));
//aux.add(new Point(40.0,89.0));
//aux.add(new Point(50.0,103.0));
//aux.add(new Point(8.0,149.0));
//aux.add(new Point(17.0,147.0));
//aux.add(new Point(27.0,144.0));
//aux.add(new Point(40.0,142.0));
//aux.add(new Point(50.0,139.0));
//aux.add(new Point(63.0,137.0));
//aux.add(new Point(71.0,141.0));
//aux.add(new Point(73.0,150.0));
//aux.add(new Point(76.0,158.0));
//aux.add(new Point(78.0,168.0));
//aux.add(new Point(81.0,176.0));
//aux.add(new Point(83.0,186.0));
//aux.add(new Point(86.0,194.0));
//aux.add(new Point(89.0,195.0));
//aux.add(new Point(91.0,186.0));
//aux.add(new Point(94.0,179.0));
//aux.add(new Point(96.0,170.0));
//aux.add(new Point(99.0,163.0));
//aux.add(new Point(101.0,154.0));
//aux.add(new Point(104.0,147.0));
//aux.add(new Point(106.0,140.0));
//aux.add(new Point(117.0,140.0));
//aux.add(new Point(128.0,142.0));
//aux.add(new Point(137.0,145.0));
//aux.add(new Point(148.0,147.0));
//aux.add(new Point(157.0,150.0));
//aux.add(new Point(166.0,152.0));
//aux.add(new Point(165.0,152.0));
//aux.add(new Point(156.0,149.0));
//aux.add(new Point(145.0,147.0));
//aux.add(new Point(136.0,144.0));
//aux.add(new Point(125.0,142.0));
//aux.add(new Point(116.0,139.0));
//aux.add(new Point(106.0,141.0));
//aux.add(new Point(103.0,148.0));
//aux.add(new Point(101.0,156.0));
//aux.add(new Point(98.0,164.0));
//aux.add(new Point(96.0,172.0));
//aux.add(new Point(93.0,180.0));
//aux.add(new Point(91.0,188.0));
//aux.add(new Point(88.0,195.0));
//aux.add(new Point(85.0,193.0));
//aux.add(new Point(83.0,183.0));
//aux.add(new Point(80.0,175.0));
//aux.add(new Point(78.0,166.0));
//aux.add(new Point(75.0,157.0));
//aux.add(new Point(73.0,148.0));
//aux.add(new Point(70.0,140.0));
//aux.add(new Point(8.0,148.0));
//aux.add(new Point(50.0,103.0));
//aux.add(new Point(40.0,88.0));
//aux.add(new Point(23.0,68.0));
//aux.add(new Point(0.0,50.0));
//aux.add(new Point(13.0,64.0));
//aux.add(new Point(39.0,92.0));
//aux.add(new Point(32.0,117.0));
//aux.add(new Point(10.0,141.0));
//aux.add(new Point(46.0,101.0));
//aux.add(new Point(30.0,81.0));
//aux.add(new Point(5.0,54.0));
//aux.add(new Point(90.0,11.0));
//aux.add(new Point(88.0,20.0));
//aux.add(new Point(85.0,27.0));
//aux.add(new Point(83.0,36.0));
//aux.add(new Point(80.0,43.0));
//aux.add(new Point(78.0,52.0));
//aux.add(new Point(75.0,59.0));
//aux.add(new Point(73.0,66.0));
//aux.add(new Point(62.0,66.0));
//aux.add(new Point(51.0,64.0));
//aux.add(new Point(42.0,61.0));
//aux.add(new Point(31.0,59.0));
//aux.add(new Point(22.0,56.0));
//aux.add(new Point(13.0,54.0));
//aux.add(new Point(14.0,54.0));
//aux.add(new Point(23.0,57.0));
//aux.add(new Point(34.0,59.0));
//aux.add(new Point(43.0,62.0));
//aux.add(new Point(54.0,64.0));
//aux.add(new Point(63.0,67.0));
//aux.add(new Point(73.0,65.0));
//aux.add(new Point(76.0,58.0));
//aux.add(new Point(78.0,50.0));
//aux.add(new Point(81.0,42.0));
//aux.add(new Point(83.0,34.0));
//aux.add(new Point(86.0,26.0));
//aux.add(new Point(88.0,18.0));
//aux.add(new Point(91.0,11.0));
//aux.add(new Point(94.0,13.0));
//aux.add(new Point(96.0,23.0));
//aux.add(new Point(99.0,31.0));
//aux.add(new Point(101.0,40.0));
//aux.add(new Point(104.0,49.0));
//aux.add(new Point(106.0,58.0));
//aux.add(new Point(109.0,66.0));
//aux.add(new Point(116.0,69.0));
//aux.add(new Point(129.0,67.0));
//aux.add(new Point(139.0,64.0));
//aux.add(new Point(152.0,62.0));
//aux.add(new Point(162.0,59.0));
//aux.add(new Point(171.0,58.0));
//aux.add(new Point(129.0,103.0));
//aux.add(new Point(139.0,118.0));
//aux.add(new Point(156.0,138.0));
//aux.add(new Point(169.0,151.0));
//aux.add(new Point(156.0,137.0));
//aux.add(new Point(139.0,117.0));
//aux.add(new Point(171.0,57.0));
//aux.add(new Point(108.0,65.0));
//aux.add(new Point(106.0,56.0));
//aux.add(new Point(103.0,48.0));
//aux.add(new Point(101.0,38.0));
//aux.add(new Point(98.0,30.0));
//aux.add(new Point(96.0,20.0));
//aux.add(new Point(93.0,12.0));
//aux.add(new Point(95.0,5.0));
//aux.add(new Point(97.0,14.0));
//aux.add(new Point(100.0,22.0));
//aux.add(new Point(102.0,32.0));
//aux.add(new Point(105.0,40.0));
//aux.add(new Point(107.0,50.0));
//aux.add(new Point(110.0,58.0));
//aux.add(new Point(112.0,66.0));
//aux.add(new Point(119.0,65.0));
//aux.add(new Point(132.0,63.0));
//aux.add(new Point(142.0,60.0));
//aux.add(new Point(155.0,58.0));
//aux.add(new Point(165.0,55.0));
//aux.add(new Point(177.0,53.0));
//aux.add(new Point(174.0,53.0));
//aux.add(new Point(164.0,56.0));
//aux.add(new Point(151.0,58.0));
//aux.add(new Point(141.0,61.0));
//aux.add(new Point(128.0,63.0));
//aux.add(new Point(118.0,66.0));
//aux.add(new Point(110.0,60.0));
//aux.add(new Point(108.0,51.0));
//aux.add(new Point(105.0,42.0));
//aux.add(new Point(103.0,33.0));
//aux.add(new Point(100.0,25.0));
//aux.add(new Point(98.0,15.0));
//aux.add(new Point(95.0,7.0));
//aux.add(new Point(89.0,4.0));
//aux.add(new Point(87.0,12.0));
//aux.add(new Point(84.0,20.0));
//aux.add(new Point(82.0,28.0));
//aux.add(new Point(79.0,35.0));
//aux.add(new Point(77.0,44.0));
//aux.add(new Point(74.0,51.0));
//aux.add(new Point(72.0,60.0));
//aux.add(new Point(63.0,63.0));
//aux.add(new Point(53.0,61.0));
//aux.add(new Point(44.0,58.0));
//aux.add(new Point(33.0,56.0));
//aux.add(new Point(24.0,53.0));
//aux.add(new Point(13.0,51.0));
//aux.add(new Point(4.0,48.0));
//aux.add(new Point(3.0,48.0));
//aux.add(new Point(12.0,50.0));
//aux.add(new Point(21.0,53.0));
//aux.add(new Point(32.0,55.0));
//aux.add(new Point(41.0,58.0));
//aux.add(new Point(52.0,60.0));
//aux.add(new Point(61.0,63.0));
//aux.add(new Point(71.0,61.0));
//aux.add(new Point(74.0,53.0));
//aux.add(new Point(76.0,45.0));
//aux.add(new Point(79.0,38.0));
//aux.add(new Point(81.0,29.0));
//aux.add(new Point(84.0,22.0));
//aux.add(new Point(86.0,13.0));


///Trapecio
//aux.add(new Point(85.0,25.0));
//aux.add(new Point(81.0,31.0));
//aux.add(new Point(78.0,40.0));
//aux.add(new Point(76.0,51.0));
//aux.add(new Point(73.0,60.0));
//aux.add(new Point(71.0,71.0));
//aux.add(new Point(68.0,80.0));
//aux.add(new Point(66.0,91.0));
//aux.add(new Point(63.0,100.0));
//aux.add(new Point(61.0,111.0));
//aux.add(new Point(58.0,120.0));
//aux.add(new Point(56.0,131.0));
//aux.add(new Point(53.0,140.0));
//aux.add(new Point(51.0,151.0));
//aux.add(new Point(48.0,160.0));
//aux.add(new Point(46.0,171.0));
//aux.add(new Point(43.0,180.0));
//aux.add(new Point(41.0,191.0));
//aux.add(new Point(38.0,200.0));
//aux.add(new Point(36.0,211.0));
//aux.add(new Point(33.0,220.0));
//aux.add(new Point(31.0,231.0));
//aux.add(new Point(28.0,240.0));
//aux.add(new Point(26.0,251.0));
//aux.add(new Point(23.0,260.0));
//aux.add(new Point(21.0,271.0));
//aux.add(new Point(20.0,280.0));
//aux.add(new Point(332.0,276.0));
//aux.add(new Point(329.0,267.0));
//aux.add(new Point(327.0,256.0));
//aux.add(new Point(324.0,247.0));
//aux.add(new Point(322.0,236.0));
//aux.add(new Point(319.0,227.0));
//aux.add(new Point(317.0,216.0));
//aux.add(new Point(314.0,207.0));
//aux.add(new Point(312.0,196.0));
//aux.add(new Point(309.0,187.0));
//aux.add(new Point(307.0,176.0));
//aux.add(new Point(304.0,167.0));
//aux.add(new Point(302.0,156.0));
//aux.add(new Point(299.0,147.0));
//aux.add(new Point(297.0,136.0));
//aux.add(new Point(294.0,127.0));
//aux.add(new Point(292.0,116.0));
//aux.add(new Point(289.0,107.0));
//aux.add(new Point(287.0,96.0));
//aux.add(new Point(284.0,87.0));
//aux.add(new Point(282.0,76.0));
//aux.add(new Point(279.0,67.0));
//aux.add(new Point(277.0,56.0));
//aux.add(new Point(274.0,47.0));
//aux.add(new Point(272.0,36.0));
//aux.add(new Point(269.0,27.0));
//aux.add(new Point(85.0,25.0));
//aux.add(new Point(270.0,31.0));
//aux.add(new Point(273.0,40.0));
//aux.add(new Point(275.0,51.0));
//aux.add(new Point(278.0,60.0));
//aux.add(new Point(280.0,71.0));
//aux.add(new Point(283.0,80.0));
//aux.add(new Point(285.0,91.0));
//aux.add(new Point(288.0,100.0));
//aux.add(new Point(290.0,111.0));
//aux.add(new Point(293.0,120.0));
//aux.add(new Point(295.0,131.0));
//aux.add(new Point(298.0,140.0));
//aux.add(new Point(300.0,151.0));
//aux.add(new Point(303.0,160.0));
//aux.add(new Point(305.0,171.0));
//aux.add(new Point(308.0,180.0));
//aux.add(new Point(310.0,191.0));
//aux.add(new Point(313.0,200.0));
//aux.add(new Point(315.0,211.0));
//aux.add(new Point(318.0,220.0));
//aux.add(new Point(320.0,231.0));
//aux.add(new Point(323.0,240.0));
//aux.add(new Point(325.0,251.0));
//aux.add(new Point(328.0,260.0));
//aux.add(new Point(330.0,271.0));
//aux.add(new Point(330.0,281.0));
//aux.add(new Point(20.0,272.0));
//aux.add(new Point(23.0,263.0));
//aux.add(new Point(25.0,252.0));
//aux.add(new Point(28.0,243.0));
//aux.add(new Point(30.0,232.0));
//aux.add(new Point(33.0,223.0));
//aux.add(new Point(35.0,212.0));
//aux.add(new Point(38.0,203.0));
//aux.add(new Point(40.0,192.0));
//aux.add(new Point(43.0,183.0));
//aux.add(new Point(45.0,172.0));
//aux.add(new Point(48.0,163.0));
//aux.add(new Point(50.0,152.0));
//aux.add(new Point(53.0,143.0));
//aux.add(new Point(55.0,132.0));
//aux.add(new Point(58.0,123.0));
//aux.add(new Point(60.0,112.0));
//aux.add(new Point(63.0,103.0));
//aux.add(new Point(65.0,92.0));
//aux.add(new Point(68.0,83.0));
//aux.add(new Point(70.0,72.0));
//aux.add(new Point(73.0,63.0));
//aux.add(new Point(75.0,52.0));
//aux.add(new Point(78.0,43.0));
        
//Triangulo

//aux.add(new Point(186.0,52.0));
//aux.add(new Point(186.0,60.0));
//aux.add(new Point(190.0,66.0));
//aux.add(new Point(194.0,73.0));
//aux.add(new Point(198.0,79.0));
//aux.add(new Point(200.0,84.0));
//aux.add(new Point(203.0,89.0));
//aux.add(new Point(206.0,95.0));
//aux.add(new Point(210.0,101.0));
//aux.add(new Point(213.0,107.0));
//aux.add(new Point(217.0,113.0));
//aux.add(new Point(220.0,119.0));
//aux.add(new Point(224.0,125.0));
//aux.add(new Point(226.0,130.0));
//aux.add(new Point(230.0,136.0));
//aux.add(new Point(233.0,142.0));
//aux.add(new Point(236.0,147.0));
//aux.add(new Point(239.0,153.0));
//aux.add(new Point(243.0,159.0));
//aux.add(new Point(246.0,165.0));
//aux.add(new Point(251.0,172.0));
//aux.add(new Point(255.0,179.0));
//aux.add(new Point(259.0,185.0));
//aux.add(new Point(262.0,191.0));
//aux.add(new Point(267.0,198.0));
//aux.add(new Point(271.0,205.0));
//aux.add(new Point(276.0,212.0));
//aux.add(new Point(281.0,209.0));
//aux.add(new Point(286.0,202.0));
//aux.add(new Point(288.0,197.0));
//aux.add(new Point(293.0,190.0));
//aux.add(new Point(296.0,184.0));
//aux.add(new Point(300.0,178.0));
//aux.add(new Point(302.0,173.0));
//aux.add(new Point(306.0,167.0));
//aux.add(new Point(308.0,162.0));
//aux.add(new Point(313.0,155.0));
//aux.add(new Point(316.0,149.0));
//aux.add(new Point(319.0,144.0));
//aux.add(new Point(322.0,138.0));
//aux.add(new Point(325.0,133.0));
//aux.add(new Point(327.0,128.0));
//aux.add(new Point(331.0,122.0));
//aux.add(new Point(334.0,116.0));
//aux.add(new Point(339.0,109.0));
//aux.add(new Point(342.0,103.0));
//aux.add(new Point(346.0,96.0));
//aux.add(new Point(349.0,90.0));
//aux.add(new Point(353.0,84.0));
//aux.add(new Point(356.0,78.0));
//aux.add(new Point(360.0,72.0));
//aux.add(new Point(362.0,67.0));
//aux.add(new Point(367.0,60.0));
//aux.add(new Point(369.0,53.0));
//aux.add(new Point(260.0,53.0));
//aux.add(new Point(246.0,52.0));
//aux.add(new Point(252.0,52.0));
//aux.add(new Point(366.0,52.0));
//aux.add(new Point(368.0,56.0));
//aux.add(new Point(366.0,61.0));
//aux.add(new Point(361.0,68.0));
//aux.add(new Point(359.0,73.0));
//aux.add(new Point(355.0,79.0));
//aux.add(new Point(352.0,85.0));
//aux.add(new Point(347.0,92.0));
//aux.add(new Point(345.0,97.0));
//aux.add(new Point(340.0,105.0));
//aux.add(new Point(337.0,111.0));
//aux.add(new Point(332.0,118.0));
//aux.add(new Point(330.0,123.0));
//aux.add(new Point(326.0,129.0));
//aux.add(new Point(324.0,134.0));
//aux.add(new Point(321.0,139.0));
//aux.add(new Point(318.0,145.0));
//aux.add(new Point(314.0,151.0));
//aux.add(new Point(311.0,157.0));
//aux.add(new Point(307.0,163.0));
//aux.add(new Point(305.0,168.0));
//aux.add(new Point(301.0,174.0));
//aux.add(new Point(299.0,179.0));
//aux.add(new Point(295.0,185.0));
//aux.add(new Point(291.0,192.0));
//aux.add(new Point(287.0,198.0));
//aux.add(new Point(284.0,204.0));
//aux.add(new Point(278.0,212.0));
//aux.add(new Point(272.0,205.0));
//aux.add(new Point(264.0,192.0));
//aux.add(new Point(256.0,179.0));
//aux.add(new Point(248.0,166.0));
//aux.add(new Point(240.0,153.0));
//aux.add(new Point(234.0,142.0));
//aux.add(new Point(228.0,131.0));
//aux.add(new Point(222.0,120.0));
//aux.add(new Point(214.0,107.0));
//aux.add(new Point(207.0,95.0));
//aux.add(new Point(201.0,84.0));
//aux.add(new Point(196.0,74.0));
//aux.add(new Point(190.0,66.0));
//aux.add(new Point(187.0,60.0));
//aux.add(new Point(337.0,55.0));
//aux.add(new Point(189.0,59.0));
//aux.add(new Point(195.0,67.0));
//aux.add(new Point(199.0,74.0));
//aux.add(new Point(202.0,79.0));
//aux.add(new Point(204.0,84.0));
//aux.add(new Point(207.0,89.0));
//aux.add(new Point(210.0,95.0));
//aux.add(new Point(214.0,101.0));
//aux.add(new Point(217.0,107.0));
//aux.add(new Point(221.0,113.0));
//aux.add(new Point(224.0,119.0));
//aux.add(new Point(229.0,127.0));
//aux.add(new Point(231.0,132.0));
//aux.add(new Point(235.0,138.0));
//aux.add(new Point(237.0,143.0));
//aux.add(new Point(242.0,150.0));
//aux.add(new Point(245.0,156.0));
//aux.add(new Point(250.0,163.0));
//aux.add(new Point(253.0,169.0));
//aux.add(new Point(259.0,177.0));
//aux.add(new Point(262.0,183.0));
//aux.add(new Point(266.0,189.0));
//aux.add(new Point(269.0,195.0));
//aux.add(new Point(274.0,202.0));
//aux.add(new Point(277.0,206.0));
//aux.add(new Point(282.0,201.0));
//aux.add(new Point(285.0,195.0));
//aux.add(new Point(289.0,189.0));
//aux.add(new Point(292.0,183.0));
//aux.add(new Point(296.0,177.0));
//aux.add(new Point(299.0,171.0));
//aux.add(new Point(302.0,166.0));
//aux.add(new Point(305.0,160.0));
//aux.add(new Point(309.0,154.0));
//aux.add(new Point(312.0,148.0));
//aux.add(new Point(316.0,142.0));
//aux.add(new Point(318.0,137.0));
//aux.add(new Point(322.0,131.0));
//aux.add(new Point(324.0,126.0));
//aux.add(new Point(328.0,120.0));
//aux.add(new Point(331.0,114.0));
//aux.add(new Point(336.0,107.0));
//aux.add(new Point(339.0,101.0));
//aux.add(new Point(342.0,96.0));
//aux.add(new Point(345.0,90.0));
//aux.add(new Point(349.0,84.0));
//aux.add(new Point(352.0,78.0));
//aux.add(new Point(356.0,72.0));
//aux.add(new Point(358.0,67.0));
//aux.add(new Point(363.0,60.0));
//aux.add(new Point(363.0,59.0));
//aux.add(new Point(358.0,66.0));
//aux.add(new Point(356.0,71.0));
//aux.add(new Point(352.0,77.0));
//aux.add(new Point(349.0,83.0));
//aux.add(new Point(345.0,89.0));
//aux.add(new Point(342.0,95.0));
//aux.add(new Point(339.0,100.0));
//aux.add(new Point(336.0,106.0));
//aux.add(new Point(331.0,113.0));
//aux.add(new Point(328.0,119.0));
//aux.add(new Point(324.0,125.0));
//aux.add(new Point(322.0,130.0));
//aux.add(new Point(318.0,136.0));
//aux.add(new Point(316.0,141.0));
//aux.add(new Point(312.0,147.0));
//aux.add(new Point(309.0,153.0));
//aux.add(new Point(305.0,159.0));
//aux.add(new Point(302.0,165.0));
//aux.add(new Point(299.0,170.0));
//aux.add(new Point(296.0,176.0));
//aux.add(new Point(292.0,182.0));
//aux.add(new Point(289.0,188.0));
//aux.add(new Point(285.0,194.0));
//aux.add(new Point(282.0,200.0));
//aux.add(new Point(275.0,203.0));
//aux.add(new Point(271.0,197.0));
//aux.add(new Point(268.0,191.0));
//aux.add(new Point(263.0,184.0));
//aux.add(new Point(260.0,178.0));
//aux.add(new Point(255.0,171.0));
//aux.add(new Point(251.0,164.0));
//aux.add(new Point(247.0,158.0));
//aux.add(new Point(243.0,151.0));
//aux.add(new Point(238.0,144.0));
//aux.add(new Point(236.0,139.0));
//aux.add(new Point(233.0,134.0));
//aux.add(new Point(230.0,128.0));
//aux.add(new Point(226.0,122.0));
//aux.add(new Point(223.0,115.0));
//aux.add(new Point(218.0,108.0));
//aux.add(new Point(216.0,103.0));
//aux.add(new Point(211.0,96.0));
//aux.add(new Point(209.0,91.0));
//aux.add(new Point(205.0,85.0));
//aux.add(new Point(203.0,80.0));
//aux.add(new Point(200.0,75.0));
//aux.add(new Point(197.0,69.0));

aux.add(new Point(307.0,368.0));
aux.add(new Point(300.0,372.0));
aux.add(new Point(294.0,374.0));
aux.add(new Point(289.0,377.0));
aux.add(new Point(282.0,379.0));
aux.add(new Point(275.0,382.0));
aux.add(new Point(264.0,384.0));
aux.add(new Point(251.0,387.0));
aux.add(new Point(259.0,385.0));
aux.add(new Point(271.0,383.0));
aux.add(new Point(279.0,380.0));
aux.add(new Point(287.0,378.0));
aux.add(new Point(293.0,375.0));
aux.add(new Point(299.0,373.0));
aux.add(new Point(305.0,369.0));
aux.add(new Point(184.0,315.0));
aux.add(new Point(192.0,321.0));
aux.add(new Point(201.0,326.0));
aux.add(new Point(219.0,351.0));
aux.add(new Point(251.0,333.0));
aux.add(new Point(257.0,318.0));
aux.add(new Point(258.0,318.0));
aux.add(new Point(219.0,351.0));
aux.add(new Point(197.0,322.0));
aux.add(new Point(187.0,317.0));
aux.add(new Point(65.0,247.0));
aux.add(new Point(64.0,244.0));
aux.add(new Point(69.0,248.0));
aux.add(new Point(67.0,243.0));
aux.add(new Point(132.0,246.0));
aux.add(new Point(134.0,256.0));
aux.add(new Point(137.0,263.0));
aux.add(new Point(139.0,269.0));
aux.add(new Point(142.0,275.0));
aux.add(new Point(145.0,281.0));
aux.add(new Point(152.0,290.0));
aux.add(new Point(152.0,289.0));
aux.add(new Point(144.0,279.0));
aux.add(new Point(142.0,274.0));
aux.add(new Point(59.0,229.0));
aux.add(new Point(60.0,240.0));
aux.add(new Point(60.0,232.0));
aux.add(new Point(68.0,208.0));
aux.add(new Point(70.0,199.0));
aux.add(new Point(142.0,191.0));
aux.add(new Point(139.0,198.0));
aux.add(new Point(137.0,207.0));
aux.add(new Point(134.0,219.0));
aux.add(new Point(136.0,246.0));
aux.add(new Point(134.0,238.0));
aux.add(new Point(136.0,208.0));
aux.add(new Point(139.0,200.0));
aux.add(new Point(141.0,192.0));
aux.add(new Point(66.0,188.0));
aux.add(new Point(66.0,202.0));
aux.add(new Point(73.0,192.0));
aux.add(new Point(71.0,212.0));
aux.add(new Point(70.0,234.0));
aux.add(new Point(71.0,253.0));
aux.add(new Point(69.0,233.0));
aux.add(new Point(71.0,209.0));
aux.add(new Point(74.0,191.0));
aux.add(new Point(68.0,182.0));
aux.add(new Point(63.0,187.0));
aux.add(new Point(66.0,183.0));
aux.add(new Point(69.0,186.0));
aux.add(new Point(177.0,154.0));
aux.add(new Point(162.0,167.0));
aux.add(new Point(155.0,177.0));
aux.add(new Point(150.0,184.0));
aux.add(new Point(148.0,189.0));
aux.add(new Point(145.0,195.0));
aux.add(new Point(143.0,203.0));
aux.add(new Point(140.0,212.0));
aux.add(new Point(140.0,246.0));
aux.add(new Point(143.0,256.0));
aux.add(new Point(145.0,264.0));
aux.add(new Point(149.0,271.0));
aux.add(new Point(151.0,276.0));
aux.add(new Point(157.0,284.0));
aux.add(new Point(170.0,298.0));
aux.add(new Point(181.0,307.0));
aux.add(new Point(187.0,310.0));
aux.add(new Point(196.0,315.0));
aux.add(new Point(205.0,322.0));
aux.add(new Point(205.0,319.0));
aux.add(new Point(199.0,315.0));
aux.add(new Point(192.0,313.0));
aux.add(new Point(186.0,309.0));
aux.add(new Point(179.0,305.0));
aux.add(new Point(160.0,288.0));
aux.add(new Point(154.0,279.0));
aux.add(new Point(147.0,266.0));
aux.add(new Point(144.0,260.0));
aux.add(new Point(142.0,253.0));
aux.add(new Point(139.0,240.0));
aux.add(new Point(141.0,208.0));
aux.add(new Point(144.0,200.0));
aux.add(new Point(146.0,194.0));
aux.add(new Point(149.0,187.0));
aux.add(new Point(153.0,180.0));
aux.add(new Point(161.0,170.0));
aux.add(new Point(167.0,149.0));
aux.add(new Point(155.0,160.0));
aux.add(new Point(144.0,173.0));
aux.add(new Point(141.0,179.0));
aux.add(new Point(137.0,185.0));
aux.add(new Point(135.0,192.0));
aux.add(new Point(132.0,199.0));
aux.add(new Point(130.0,210.0));
aux.add(new Point(129.0,242.0));
aux.add(new Point(131.0,256.0));
aux.add(new Point(134.0,264.0));
aux.add(new Point(136.0,271.0));
aux.add(new Point(139.0,276.0));
aux.add(new Point(141.0,281.0));
aux.add(new Point(147.0,289.0));
aux.add(new Point(142.0,282.0));
aux.add(new Point(140.0,277.0));
aux.add(new Point(136.0,270.0));
aux.add(new Point(137.0,186.0));
aux.add(new Point(140.0,181.0));
aux.add(new Point(143.0,175.0));
aux.add(new Point(154.0,162.0));
aux.add(new Point(168.0,149.0));
aux.add(new Point(361.0,148.0));
aux.add(new Point(366.0,155.0));
aux.add(new Point(370.0,164.0));
aux.add(new Point(374.0,171.0));
aux.add(new Point(376.0,178.0));
aux.add(new Point(379.0,185.0));
aux.add(new Point(381.0,195.0));
aux.add(new Point(384.0,207.0));
aux.add(new Point(384.0,248.0));
aux.add(new Point(384.0,241.0));
aux.add(new Point(383.0,206.0));
aux.add(new Point(381.0,192.0));
aux.add(new Point(378.0,184.0));
aux.add(new Point(376.0,176.0));
aux.add(new Point(373.0,170.0));
aux.add(new Point(371.0,164.0));
aux.add(new Point(367.0,157.0));
aux.add(new Point(365.0,152.0));
aux.add(new Point(222.0,131.0));
aux.add(new Point(207.0,134.0));
aux.add(new Point(197.0,136.0));
aux.add(new Point(191.0,139.0));
aux.add(new Point(185.0,141.0));
aux.add(new Point(179.0,145.0));
aux.add(new Point(173.0,148.0));
aux.add(new Point(159.0,160.0));
aux.add(new Point(148.0,174.0));
aux.add(new Point(144.0,180.0));
aux.add(new Point(140.0,186.0));
aux.add(new Point(144.0,182.0));
aux.add(new Point(162.0,158.0));
aux.add(new Point(175.0,148.0));
aux.add(new Point(182.0,143.0));
aux.add(new Point(187.0,141.0));
aux.add(new Point(192.0,138.0));
aux.add(new Point(200.0,136.0));
aux.add(new Point(208.0,133.0));
aux.add(new Point(235.0,131.0));
aux.add(new Point(251.0,134.0));
aux.add(new Point(259.0,136.0));
aux.add(new Point(267.0,139.0));
aux.add(new Point(272.0,141.0));
aux.add(new Point(278.0,145.0));
aux.add(new Point(283.0,147.0));
aux.add(new Point(306.0,168.0));
aux.add(new Point(311.0,176.0));
aux.add(new Point(316.0,183.0));
aux.add(new Point(318.0,188.0));
aux.add(new Point(321.0,193.0));
aux.add(new Point(323.0,202.0));
aux.add(new Point(326.0,210.0));
aux.add(new Point(328.0,234.0));
aux.add(new Point(325.0,250.0));
aux.add(new Point(323.0,260.0));
aux.add(new Point(320.0,266.0));
aux.add(new Point(317.0,274.0));
aux.add(new Point(313.0,280.0));
aux.add(new Point(308.0,288.0));
aux.add(new Point(287.0,309.0));
aux.add(new Point(280.0,313.0));
aux.add(new Point(274.0,317.0));
aux.add(new Point(269.0,319.0));
aux.add(new Point(263.0,322.0));
aux.add(new Point(256.0,324.0));
aux.add(new Point(261.0,322.0));
aux.add(new Point(269.0,320.0));
aux.add(new Point(280.0,314.0));
aux.add(new Point(304.0,293.0));
aux.add(new Point(313.0,282.0));
aux.add(new Point(316.0,276.0));
aux.add(new Point(319.0,270.0));
aux.add(new Point(321.0,264.0));
aux.add(new Point(324.0,257.0));
aux.add(new Point(326.0,244.0));
aux.add(new Point(327.0,224.0));
aux.add(new Point(325.0,206.0));
aux.add(new Point(322.0,198.0));
aux.add(new Point(320.0,190.0));
aux.add(new Point(317.0,185.0));
aux.add(new Point(315.0,180.0));
aux.add(new Point(309.0,172.0));
aux.add(new Point(291.0,153.0));
aux.add(new Point(282.0,146.0));
aux.add(new Point(276.0,143.0));
aux.add(new Point(271.0,140.0));
aux.add(new Point(264.0,138.0));
aux.add(new Point(257.0,135.0));
aux.add(new Point(246.0,133.0));
aux.add(new Point(244.0,128.0));
aux.add(new Point(255.0,131.0));
aux.add(new Point(262.0,133.0));
aux.add(new Point(269.0,136.0));
aux.add(new Point(274.0,138.0));
aux.add(new Point(280.0,142.0));
aux.add(new Point(286.0,145.0));
aux.add(new Point(309.0,166.0));
aux.add(new Point(314.0,174.0));
aux.add(new Point(318.0,180.0));
aux.add(new Point(320.0,185.0));
aux.add(new Point(323.0,190.0));
aux.add(new Point(325.0,197.0));
aux.add(new Point(328.0,205.0));
aux.add(new Point(330.0,223.0));
aux.add(new Point(329.0,245.0));
aux.add(new Point(327.0,258.0));
aux.add(new Point(324.0,264.0));
aux.add(new Point(322.0,271.0));
aux.add(new Point(318.0,278.0));
aux.add(new Point(316.0,283.0));
aux.add(new Point(317.0,280.0));
aux.add(new Point(321.0,274.0));
aux.add(new Point(323.0,268.0));
aux.add(new Point(326.0,261.0));
aux.add(new Point(328.0,251.0));
aux.add(new Point(331.0,235.0));
aux.add(new Point(329.0,209.0));
aux.add(new Point(326.0,200.0));
aux.add(new Point(324.0,192.0));
aux.add(new Point(321.0,186.0));
aux.add(new Point(319.0,181.0));
aux.add(new Point(315.0,175.0));
aux.add(new Point(311.0,168.0));
aux.add(new Point(291.0,148.0));
aux.add(new Point(283.0,143.0));
aux.add(new Point(277.0,139.0));
aux.add(new Point(271.0,137.0));
aux.add(new Point(265.0,134.0));
aux.add(new Point(258.0,132.0));
aux.add(new Point(250.0,129.0));
aux.add(new Point(218.0,128.0));
aux.add(new Point(204.0,131.0));
aux.add(new Point(196.0,133.0));
aux.add(new Point(188.0,136.0));
aux.add(new Point(183.0,138.0));
aux.add(new Point(177.0,142.0));
aux.add(new Point(179.0,141.0));
aux.add(new Point(184.0,138.0));
aux.add(new Point(189.0,136.0));
aux.add(new Point(229.0,20.0));
aux.add(new Point(218.0,26.0));
aux.add(new Point(215.0,34.0));
aux.add(new Point(215.0,51.0));
aux.add(new Point(209.0,62.0));
aux.add(new Point(196.0,64.0));
aux.add(new Point(188.0,56.0));
aux.add(new Point(186.0,47.0));
aux.add(new Point(183.0,42.0));
aux.add(new Point(171.0,28.0));
aux.add(new Point(163.0,31.0));
aux.add(new Point(157.0,34.0));
aux.add(new Point(158.0,58.0));
aux.add(new Point(160.0,65.0));
aux.add(new Point(154.0,79.0));
aux.add(new Point(149.0,81.0));
aux.add(new Point(137.0,78.0));
aux.add(new Point(133.0,71.0));
aux.add(new Point(118.0,58.0));
aux.add(new Point(112.0,57.0));
aux.add(new Point(105.0,61.0));
aux.add(new Point(101.0,70.0));
aux.add(new Point(103.0,79.0));
aux.add(new Point(106.0,85.0));
aux.add(new Point(113.0,95.0));
aux.add(new Point(115.0,105.0));
aux.add(new Point(101.0,117.0));
aux.add(new Point(96.0,114.0));
aux.add(new Point(87.0,108.0));
aux.add(new Point(82.0,105.0));
aux.add(new Point(74.0,103.0));
aux.add(new Point(62.0,104.0));
aux.add(new Point(58.0,111.0));
aux.add(new Point(57.0,117.0));
aux.add(new Point(63.0,125.0));
aux.add(new Point(74.0,135.0));
aux.add(new Point(83.0,145.0));
aux.add(new Point(81.0,151.0));
aux.add(new Point(78.0,156.0));
aux.add(new Point(75.0,160.0));
aux.add(new Point(62.0,160.0));
aux.add(new Point(54.0,158.0));
aux.add(new Point(33.0,160.0));
aux.add(new Point(31.0,169.0));
aux.add(new Point(38.0,182.0));
aux.add(new Point(44.0,185.0));
aux.add(new Point(51.0,188.0));
aux.add(new Point(61.0,199.0));
aux.add(new Point(58.0,215.0));
aux.add(new Point(34.0,217.0));
aux.add(new Point(27.0,220.0));
aux.add(new Point(22.0,239.0));
aux.add(new Point(28.0,242.0));
aux.add(new Point(37.0,244.0));
aux.add(new Point(63.0,250.0));
aux.add(new Point(65.0,265.0));
aux.add(new Point(53.0,273.0));
aux.add(new Point(45.0,275.0));
aux.add(new Point(38.0,280.0));
aux.add(new Point(31.0,287.0));
aux.add(new Point(32.0,295.0));
aux.add(new Point(36.0,303.0));
aux.add(new Point(57.0,303.0));
aux.add(new Point(64.0,300.0));
aux.add(new Point(73.0,298.0));
aux.add(new Point(78.0,301.0));
aux.add(new Point(81.0,307.0));
aux.add(new Point(84.0,312.0));
aux.add(new Point(73.0,326.0));
aux.add(new Point(60.0,341.0));
aux.add(new Point(59.0,349.0));
aux.add(new Point(67.0,359.0));
aux.add(new Point(79.0,357.0));
aux.add(new Point(85.0,354.0));
aux.add(new Point(90.0,352.0));
aux.add(new Point(100.0,344.0));
aux.add(new Point(116.0,354.0));
aux.add(new Point(113.0,367.0));
aux.add(new Point(108.0,375.0));
aux.add(new Point(105.0,381.0));
aux.add(new Point(103.0,395.0));
aux.add(new Point(111.0,401.0));
aux.add(new Point(120.0,402.0));
aux.add(new Point(136.0,388.0));
aux.add(new Point(139.0,382.0));
aux.add(new Point(153.0,379.0));
aux.add(new Point(158.0,381.0));
aux.add(new Point(163.0,386.0));
aux.add(new Point(163.0,395.0));
aux.add(new Point(159.0,404.0));
aux.add(new Point(159.0,425.0));
aux.add(new Point(165.0,428.0));
aux.add(new Point(176.0,430.0));
aux.add(new Point(185.0,419.0));
aux.add(new Point(188.0,412.0));
aux.add(new Point(191.0,403.0));
aux.add(new Point(204.0,395.0));
aux.add(new Point(216.0,401.0));
aux.add(new Point(218.0,424.0));
aux.add(new Point(221.0,431.0));
aux.add(new Point(223.0,437.0));
aux.add(new Point(242.0,435.0));
aux.add(new Point(244.0,428.0));
aux.add(new Point(247.0,419.0));
aux.add(new Point(247.0,402.0));
aux.add(new Point(256.0,395.0));
aux.add(new Point(267.0,395.0));
aux.add(new Point(274.0,406.0));
aux.add(new Point(276.0,414.0));
aux.add(new Point(281.0,421.0));
aux.add(new Point(286.0,429.0));
aux.add(new Point(294.0,429.0));
aux.add(new Point(301.0,426.0));
aux.add(new Point(304.0,423.0));
aux.add(new Point(298.0,427.0));
aux.add(new Point(291.0,430.0));
aux.add(new Point(286.0,427.0));
aux.add(new Point(278.0,417.0));
aux.add(new Point(276.0,412.0));
aux.add(new Point(273.0,405.0));
aux.add(new Point(264.0,395.0));
aux.add(new Point(255.0,396.0));
aux.add(new Point(247.0,405.0));
aux.add(new Point(246.0,420.0));
aux.add(new Point(244.0,430.0));
aux.add(new Point(241.0,435.0));
aux.add(new Point(223.0,435.0));
aux.add(new Point(220.0,430.0));
aux.add(new Point(218.0,422.0));
aux.add(new Point(213.0,397.0));
aux.add(new Point(196.0,395.0));
aux.add(new Point(190.0,404.0));
aux.add(new Point(188.0,413.0));
aux.add(new Point(184.0,419.0));
aux.add(new Point(171.0,430.0));
aux.add(new Point(164.0,427.0));
aux.add(new Point(158.0,413.0));
aux.add(new Point(161.0,400.0));
aux.add(new Point(163.0,392.0));
aux.add(new Point(162.0,384.0));
aux.add(new Point(153.0,378.0));
aux.add(new Point(142.0,378.0));
aux.add(new Point(136.0,386.0));
aux.add(new Point(130.0,393.0));
aux.add(new Point(119.0,402.0));
aux.add(new Point(112.0,401.0));
aux.add(new Point(103.0,394.0));
aux.add(new Point(108.0,376.0));
aux.add(new Point(113.0,368.0));
aux.add(new Point(116.0,353.0));
aux.add(new Point(99.0,344.0));
aux.add(new Point(89.0,352.0));
aux.add(new Point(84.0,354.0));
aux.add(new Point(68.0,359.0));
aux.add(new Point(59.0,348.0));
aux.add(new Point(60.0,342.0));
aux.add(new Point(74.0,326.0));
aux.add(new Point(83.0,318.0));
aux.add(new Point(82.0,308.0));
aux.add(new Point(80.0,303.0));
aux.add(new Point(74.0,299.0));
aux.add(new Point(65.0,300.0));
aux.add(new Point(58.0,302.0));
aux.add(new Point(40.0,303.0));
aux.add(new Point(33.0,296.0));
aux.add(new Point(30.0,288.0));
aux.add(new Point(45.0,276.0));
aux.add(new Point(50.0,273.0));
aux.add(new Point(60.0,271.0));
aux.add(new Point(64.0,257.0));
aux.add(new Point(58.0,246.0));
aux.add(new Point(34.0,243.0));
aux.add(new Point(26.0,241.0));
aux.add(new Point(25.0,220.0));
aux.add(new Point(33.0,218.0));
aux.add(new Point(56.0,215.0));
aux.add(new Point(55.0,189.0));
aux.add(new Point(47.0,186.0));
aux.add(new Point(42.0,184.0));
aux.add(new Point(30.0,174.0));
aux.add(new Point(32.0,163.0));
aux.add(new Point(38.0,157.0));
aux.add(new Point(59.0,159.0));
aux.add(new Point(66.0,162.0));
aux.add(new Point(77.0,160.0));
aux.add(new Point(79.0,155.0));
aux.add(new Point(82.0,150.0));
aux.add(new Point(82.0,141.0));
aux.add(new Point(72.0,133.0));
aux.add(new Point(60.0,120.0));
aux.add(new Point(56.0,114.0));
aux.add(new Point(62.0,106.0));
aux.add(new Point(73.0,102.0));
aux.add(new Point(80.0,105.0));
aux.add(new Point(96.0,115.0));
aux.add(new Point(102.0,117.0));
aux.add(new Point(116.0,104.0));
aux.add(new Point(112.0,92.0));
aux.add(new Point(105.0,83.0));
aux.add(new Point(103.0,77.0));
aux.add(new Point(100.0,69.0));
aux.add(new Point(107.0,61.0));
aux.add(new Point(115.0,57.0));
aux.add(new Point(122.0,62.0));
aux.add(new Point(134.0,75.0));
aux.add(new Point(141.0,82.0));
aux.add(new Point(153.0,80.0));
aux.add(new Point(161.0,74.0));
aux.add(new Point(159.0,60.0));
aux.add(new Point(156.0,53.0));
aux.add(new Point(156.0,35.0));
aux.add(new Point(164.0,30.0));
aux.add(new Point(179.0,36.0));
aux.add(new Point(184.0,44.0));
aux.add(new Point(187.0,50.0));
aux.add(new Point(189.0,59.0));
aux.add(new Point(200.0,63.0));
aux.add(new Point(213.0,57.0));
aux.add(new Point(214.0,49.0));
aux.add(new Point(216.0,30.0));
aux.add(new Point(220.0,24.0));
aux.add(new Point(233.0,19.0));
aux.add(new Point(239.0,25.0));
aux.add(new Point(241.0,30.0));
aux.add(new Point(244.0,36.0));
aux.add(new Point(249.0,61.0));
aux.add(new Point(265.0,62.0));
aux.add(new Point(270.0,54.0));
aux.add(new Point(273.0,46.0));
aux.add(new Point(275.0,41.0));
aux.add(new Point(287.0,27.0));
aux.add(new Point(295.0,30.0));
aux.add(new Point(302.0,35.0));
aux.add(new Point(301.0,56.0));
aux.add(new Point(298.0,62.0));
aux.add(new Point(298.0,74.0));
aux.add(new Point(304.0,78.0));
aux.add(new Point(309.0,80.0));
aux.add(new Point(320.0,78.0));
aux.add(new Point(325.0,72.0));
aux.add(new Point(332.0,64.0));
aux.add(new Point(342.0,56.0));
aux.add(new Point(348.0,56.0));
aux.add(new Point(358.0,64.0));
aux.add(new Point(356.0,76.0));
aux.add(new Point(353.0,81.0));
aux.add(new Point(351.0,86.0));
aux.add(new Point(343.0,98.0));
aux.add(new Point(353.0,113.0));
aux.add(new Point(364.0,112.0));
aux.add(new Point(374.0,105.0));
aux.add(new Point(379.0,102.0));
aux.add(new Point(387.0,100.0));
aux.add(new Point(400.0,107.0));
aux.add(new Point(403.0,113.0));
aux.add(new Point(397.0,121.0));
aux.add(new Point(385.0,132.0));
aux.add(new Point(377.0,140.0));
aux.add(new Point(379.0,151.0));
aux.add(new Point(382.0,156.0));
aux.add(new Point(387.0,159.0));
aux.add(new Point(396.0,158.0));
aux.add(new Point(403.0,156.0));
aux.add(new Point(422.0,155.0));
aux.add(new Point(427.0,159.0));
aux.add(new Point(430.0,166.0));
aux.add(new Point(430.0,172.0));
aux.add(new Point(420.0,180.0));
aux.add(new Point(414.0,183.0));
aux.add(new Point(406.0,186.0));
aux.add(new Point(396.0,200.0));
aux.add(new Point(400.0,211.0));
aux.add(new Point(424.0,213.0));
aux.add(new Point(431.0,216.0));
aux.add(new Point(439.0,223.0));
aux.add(new Point(436.0,238.0));
aux.add(new Point(429.0,240.0));
aux.add(new Point(404.0,243.0));
aux.add(new Point(397.0,256.0));
aux.add(new Point(402.0,268.0));
aux.add(new Point(412.0,270.0));
aux.add(new Point(418.0,274.0));
aux.add(new Point(425.0,278.0));
aux.add(new Point(432.0,285.0));
aux.add(new Point(430.0,292.0));
aux.add(new Point(427.0,298.0));
aux.add(new Point(409.0,301.0));
aux.add(new Point(401.0,298.0));
aux.add(new Point(388.0,296.0));
aux.add(new Point(384.0,299.0));
aux.add(new Point(381.0,304.0));
aux.add(new Point(378.0,313.0));
aux.add(new Point(383.0,321.0));
aux.add(new Point(389.0,324.0));
aux.add(new Point(403.0,340.0));
aux.add(new Point(403.0,346.0));
aux.add(new Point(399.0,353.0));
aux.add(new Point(386.0,355.0));
aux.add(new Point(378.0,352.0));
aux.add(new Point(371.0,347.0));
aux.add(new Point(364.0,343.0));
aux.add(new Point(356.0,342.0));
aux.add(new Point(345.0,354.0));
aux.add(new Point(347.0,363.0));
aux.add(new Point(355.0,373.0));
aux.add(new Point(357.0,378.0));
aux.add(new Point(360.0,385.0));
aux.add(new Point(360.0,393.0));
aux.add(new Point(351.0,400.0));
aux.add(new Point(344.0,400.0));
aux.add(new Point(335.0,393.0));
aux.add(new Point(328.0,385.0));
aux.add(new Point(318.0,376.0));
aux.add(new Point(308.0,378.0));
aux.add(new Point(303.0,381.0));
aux.add(new Point(300.0,393.0));
aux.add(new Point(303.0,399.0));
aux.add(new Point(305.0,418.0));
aux.add(new Point(304.0,420.0));
aux.add(new Point(303.0,400.0));
aux.add(new Point(301.0,394.0));
aux.add(new Point(308.0,379.0));
aux.add(new Point(319.0,377.0));
aux.add(new Point(328.0,387.0));
aux.add(new Point(337.0,396.0));
aux.add(new Point(355.0,397.0));
aux.add(new Point(355.0,372.0));
aux.add(new Point(347.0,362.0));
aux.add(new Point(346.0,352.0));
aux.add(new Point(359.0,341.0));
aux.add(new Point(369.0,347.0));
aux.add(new Point(376.0,351.0));
aux.add(new Point(381.0,354.0));
aux.add(new Point(390.0,356.0));
aux.add(new Point(401.0,351.0));
aux.add(new Point(405.0,344.0));
aux.add(new Point(401.0,337.0));
aux.add(new Point(379.0,316.0));
aux.add(new Point(379.0,308.0));
aux.add(new Point(384.0,300.0));
aux.add(new Point(395.0,296.0));
aux.add(new Point(401.0,299.0));
aux.add(new Point(423.0,301.0));
aux.add(new Point(428.0,296.0));
aux.add(new Point(431.0,289.0));
aux.add(new Point(431.0,282.0));
aux.add(new Point(422.0,276.0));
aux.add(new Point(416.0,272.0));
aux.add(new Point(407.0,270.0));
aux.add(new Point(396.0,262.0));
aux.add(new Point(398.0,248.0));
aux.add(new Point(424.0,241.0));
aux.add(new Point(434.0,239.0));
aux.add(new Point(440.0,232.0));
aux.add(new Point(437.0,218.0));
aux.add(new Point(430.0,215.0));
aux.add(new Point(404.0,213.0));
aux.add(new Point(397.0,206.0));
aux.add(new Point(403.0,187.0));
aux.add(new Point(412.0,184.0));
aux.add(new Point(419.0,181.0));
aux.add(new Point(428.0,174.0));
aux.add(new Point(431.0,169.0));
aux.add(new Point(428.0,162.0));
aux.add(new Point(426.0,155.0));
aux.add(new Point(404.0,155.0));
aux.add(new Point(397.0,158.0));
aux.add(new Point(388.0,160.0));
aux.add(new Point(380.0,151.0));
aux.add(new Point(379.0,139.0));
aux.add(new Point(387.0,132.0));
aux.add(new Point(399.0,120.0));
aux.add(new Point(402.0,114.0));
aux.add(new Point(388.0,99.0));
aux.add(new Point(380.0,102.0));
aux.add(new Point(374.0,104.0));
aux.add(new Point(344.0,97.0));
aux.add(new Point(354.0,81.0));
aux.add(new Point(356.0,74.0));
aux.add(new Point(356.0,61));
aux.add(new Point(336.0,60.0));
aux.add(new Point(328.0,67.0));
aux.add(new Point(321.0,76.0));
aux.add(new Point(310.0,81.0));
aux.add(new Point(304.0,77.0));
aux.add(new Point(298.0,72.0));
aux.add(new Point(299.0,61.0));
aux.add(new Point(301.0,53.0));
aux.add(new Point(302.0,34.0));
aux.add(new Point(297.0,30.0));
aux.add(new Point(288.0,28.0));
aux.add(new Point(279.0,35.0));
aux.add(new Point(274.0,42.0));
aux.add(new Point(272.0,49.0));
aux.add(new Point(269.0,57.0));
aux.add(new Point(257.0,63.0));
aux.add(new Point(244.0,55.0));
aux.add(new Point(242.0,31.0));
        
      // Herramientas.ordenarPuntos(aux);  
       
   aux =  Herramientas.ordenarPuntos(aux);
        System.out.println("");
        Herramientas.sintaxisOBJ(aux);
       //Herramientas.sintaxisOpenGL(aux);
       
       // System.out.println(Herramientas.regresarReferencia(p1,  Herramientas.limpiarLista(aux)));
    }
    
}
//
///*private float colors[] = {
//
//               // 0.6f,  0.6f,  0.6f, 0.6f,
//               // 0.6f,  0.6f,  0.6f, 0.6f,
//               // 0.6f,  0.6f,  0.6f, 0.6f,
//               // 0.6f,  0.6f,  0.6f, 0.6f
//
//                0.5f,  0.5f,  0.5f,  0.5f,
//                0.5f,  0.5f,  0.5f,  0.5f,
//                0.5f,  0.5f,  0.5f,  0.5f,
//                0.5f,  0.5f,  0.5f,  0.5f,
//                0.5f,  0.5f,  0.5f,  0.5f,
//                0.5f,  0.5f,  0.5f,  0.5f,
//                0.5f,  0.5f,  0.5f,  0.5f,
//                0.5f,  0.5f,  0.5f,  0.5f,
//                0.5f,  0.5f,  0.5f,  0.5f
//
//               /* 0.0f,  1.0f,  0.0f,  1.0f,
//                0.0f,  1.0f,  0.0f,  1.0f,
//                1.0f,  0.5f,  0.0f,  1.0f,
//                1.0f,  0.5f,  0.0f,  1.0f,
//                1.0f,  0.0f,  0.0f,  1.0f
//               /* 1.0f,  0.0f,  0.0f,  1.0f,
//                0.0f,  0.0f,  1.0f,  1.0f,
//                1.0f,  0.0f,  1.0f,  1.0f*/
//        };/*