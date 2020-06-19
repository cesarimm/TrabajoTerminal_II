
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
       
        double lon=longitud/divisiones;
        double xRef[] = new double[divisiones-1];
        
       for(int i=1;i<divisiones;i++){
           xRef[i-1]=(lon * i)+y+5;;
       }
        
        
        for (int i = y; i <=yMax; i++) {
            for (int j = x; j < xMax; j++) {
                for(int p=0;p<xRef.length;p++){
                    if((int)xRef[p]==i){
                      
                        //System.out.println("xRef: "+xRef[p]);  
                        try{
                            for (int k = 0; k < mat.get(j, i).length; k++) {
                             if(mat.get(j, i)[k]!=0){
                          //       System.out.println("x:"+j+" y:"+i+" "+mat.get(j, i)[k]);
                                 Imgproc.circle(aux, new Point(i,j), 1, new Scalar(255,0,0), 2, 8, 0);                         
                             }

                         } 
                   }catch(Exception e){

                       }
                        
                        
                    }
                }
                   
            }
        }
     
      
        return aux;       
    }
    
}
