
import java.util.ArrayList;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author CESAR IVAN MTZ
 */
public class Divisiones {
    
    public static Mat printMat(int x,int xMax, int y, int yMax, Mat mat){
        System.out.println("");    
         Mat aux = Mat.zeros(mat.size(), CvType.CV_8U);
        //Mat aux = mat;
        for (int i = y; i <=yMax; i++) {
            for (int j = x; j < xMax; j++) {
                try{
                   for (int k = 0; k < mat.get(j, i).length; k++) {
                    if(mat.get(j, i)[k]!=0){
                        System.out.println("x:"+j+" y:"+i+" "+mat.get(j, i)[k]);
                        //Imgproc.circle(aux, new Point(i,j), 5, new Scalar(255,0,0), 2, 8, 0);
                        if(i==142){
                          Imgproc.circle(aux, new Point(142,j), 5, new Scalar(255, 0, 0), 2, 8, 0);  
                        }else if(i==255){
                            Imgproc.circle(aux, new Point(255,j), 5, new Scalar(255, 0, 0), 2, 8, 0);
                        }else if(i==31){
                            Imgproc.circle(aux, new Point(29,j), 5, new Scalar(255, 0, 0), 2, 8, 0);
                        }else if(i==368){
                            Imgproc.circle(aux, new Point(255,j), 5, new Scalar(255, 0, 0), 2, 8, 0);
                        }
                    }
                    
                } 
                }catch(Exception e){
                    
                    }
                
            }
        }
        
       // Imgproc.circle(aux, new Point(142,50), 5, new Scalar(0,255,10), 2, 8, 0);
        
        return aux;
        
    }

   public static Mat generarPuntos(int x,int xMax, int y, int yMax, double longitud, int divisiones, Mat mat){
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
        
        nuevaSintaxisOBJ(listaPuntos, 100.0);
      
        return aux;       
    }
   
   
   public static void nuevaSintaxisOBJ(ArrayList<Point> aux){
       
       for(int i=0;i<aux.size();i++){
            System.out.println("v "+aux.get(i).x+" "+aux.get(i).y+" 0");
        }
       
       
        System.out.println("usemtl Default");
        
        
          for(int i=0;i<aux.size()-2;i++){
            System.out.println("f "+(i+1)+" "+(i+2)+" "+(i+3));     
        }
       
       
       
   }
   
   
    public static void nuevaSintaxisOBJ(ArrayList<Point> aux, double distancia){
       
       for(int i=0;i<aux.size();i++){
            System.out.println("v "+aux.get(i).x+" "+aux.get(i).y+" 0");
        }
       
       
        for(int i=0;i<aux.size();i++){
            System.out.println("v "+aux.get(i).x+" "+aux.get(i).y+" "+distancia);
        }
       
       
        System.out.println("usemtl Default");
        
        ///Primera cara
          for(int i=0;i<(aux.size()-2);i++){
            System.out.println("f "+(i+1)+" "+(i+2)+" "+(i+3));       
        }
          
        ///Segunda cara  
          for(int i=aux.size();i<(aux.size()-1)*2;i++){
            System.out.println("f "+(i+1)+" "+(i+2)+" "+(i+3));       
        }
          
          
        //Union entrre las caras laterales
         for(int i=0;i<aux.size()-2;i++){
            System.out.println("f "+(i+1)+" "+(i+3)+" "+(aux.size()+(i+1)));
            System.out.println("f "+(aux.size()+(i+1))+" "+(aux.size()+(i+3))+" "+(i+3));    
        }
                        
//        //Las ultimas dos caras
         System.out.println("f "+(1)+" "+(2)+" "+(aux.size()+1));
         System.out.println("f "+(aux.size()+1)+" "+(aux.size()+2)+" "+2);
         
          System.out.println("f "+(aux.size()-2)+" "+(aux.size()-1)+" "+((2*aux.size())-2));
         System.out.println("f "+((2*aux.size())-2)+" "+((2*aux.size())-1)+" "+(aux.size()-1));
          
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
        
        nuevaSintaxisOBJ(listaPuntos, 100.0);
      
        return aux;         
   }
   
  
//   public static Mat generarPuntos(int x,int xMax, int y, int yMax, double longitud, int divisiones, Mat mat){
//       Mat aux = Mat.zeros(mat.size(), CvType.CV_8U);
//       
//        double lon=longitud/divisiones;
//        double xRef[] = new double[divisiones+1];
//               xRef[0]=y+5;
//               xRef[divisiones]=yMax-12;
//               
//               
//       for(int i=1;i<divisiones;i++){
//           xRef[i]=(lon * i)+y+5;;
//       }
//        
//     
//        for (int i = y; i <=yMax; i++) {
//            for (int j = x; j < xMax; j++) {
//                for(int p=0;p<xRef.length;p++){
//                    if((int)xRef[p]==i){
//                      
//                        //System.out.println("xRef: "+xRef[p]);  
//                        try{
//                            for (int k = 0; k < mat.get(j, i).length; k++) {
//                             if(mat.get(j, i)[k]!=0){
//                          //       System.out.println("x:"+j+" y:"+i+" "+mat.get(j, i)[k]);
//                                 Imgproc.circle(aux, new Point(i,j), 1, new Scalar(255,0,0), 2, 8, 0);                         
//                             }
//
//                         } 
//                   }catch(Exception e){
//
//                       }
//                        
//                        
//                    }
//                }
//                   
//            }
//        }
//     
//      
//        return aux;       
//    }
    
    
    
     public static Mat buscarHuecos(int x,int xMax, int y, int yMax, double longitud, int divisiones, Mat mat){
       Mat aux = Mat.zeros(mat.size(), CvType.CV_8U);
       ArrayList<Point> listaPuntos = new ArrayList<Point>();
       
       Point a, b;
       
       
       longitud=Herramientas.yMax - Herramientas.yMin;
       
        double lon=longitud/divisiones;
        double xRef[] = new double[divisiones+1];
               xRef[0]=x+7;
               xRef[divisiones]=xMax-12;               
               
       for(int i=1;i<divisiones;i++){
           xRef[i]=(lon * i)+y+5;;
       }
        
     
        for (int i = y; i <=yMax; i++) {
             a=b=null;
            for (int j = x; j < xMax; j++) { 
                for(int p=0;p<xRef.length;p++){
                    if((int)xRef[p]==j){
                      
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
        
        ArrayList<Point> puntitos = new ArrayList<Point>();
        ///Limpiar puntos de los extremos
         for (int i = 0; i <listaPuntos.size(); i++) {
             try{
               
               double xL = listaPuntos.get(i).x;
                if(xL<Herramientas.max-10&&xL>Herramientas.min+10){
                    System.out.println("x"+listaPuntos.get(i).x+"min "+Herramientas.min+10+" max:"+Herramientas.max);
                    puntitos.add(listaPuntos.get(i));
                }
              
             }catch(Exception e){
                 
             }
         }
        

         listaPuntos = puntitos;
         //puntitos.clear();
     
        System.out.println("Size: "+listaPuntos.size());
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
