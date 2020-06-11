
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author CESAR IVAN MTZ
 */
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author PC-PUBG
 */
public class HerramientasProcesamiento {
    
    
    private Mat srcGray = new Mat();
    private Mat matCanny = new Mat();
    private JFrame frame;
    private JLabel imgSrcLabel;
    private JLabel imgContoursLabel;
    private static final int MAX_THRESHOLD = 255;
    private int threshold = 100;
    private Random rng = new Random(12345);
    ///CornerHarris
    private Mat dst = new Mat();
    private Mat dstNorm = new Mat();
    private Mat dstNormScaled = new Mat();
    
    
    public HerramientasProcesamiento(String[] args) {
        String filename = "D:\\Documents\\Trabajo_Terminal_Dos\\Imagenes\\cuadrado.jpg";
        //String filename = "D:\\Documents\\Trabajo_Terminal_Dos\\Imagenes\\engrane2.jpg";
       //String filename = "D:\\Documents\\Trabajo_Terminal_Dos\\Imagenes\\trapecio.jpg";
       //String filename = "D:\\Documents\\Trabajo_Terminal_Dos\\Imagenes\\trinagulo.jpg";
        //String filename = "D:\\Documents\\Trabajo_Terminal_Dos\\Imagenes\\estrella.png";
      // String filename = "D:\\Documents\\Trabajo_Terminal_Dos\\Imagenes\\ele.jpg";
       // String filename = "D:\\Documents\\Trabajo_Terminal_Dos\\Imagenes\\trapecio.jpg";
        //String filename = "D:\\Documents\\Trabajo_Terminal_Dos\\Imagenes\\flecha.jpg";
       //String filename = "D:\\Documents\\Trabajo_Terminal_Dos\\Imagenes\\esquinas.jpg";
       //String filename = "D:\\Documents\\Trabajo_Terminal_Dos\\Imagenes\\comido.jpg";
       //String filename = "D:\\Documents\\Trabajo_Terminal_Dos\\Imagenes\\frasco2.jpg";
        Mat src = Imgcodecs.imread(filename);
        if (src.empty()) {
            System.err.println("Cannot read image: " + filename);
            System.exit(0);
        }
        
        ///Convertir imagen a color gris
        Imgproc.cvtColor(src, srcGray, Imgproc.COLOR_BGR2GRAY);
        
        
        ///Aplicar filtro blur "Desenfoque"
        Imgproc.blur(srcGray, srcGray, new Size(3, 3));
        
        ///Aplicar filtro canny "Resalte de bordes"
        Imgproc.Canny(srcGray, matCanny, 50, 150);
       // Imgproc.medianBlur(srcGray, srcGray, 5);
        // Create and set up the window.
        frame = new JFrame("Convex Hull demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Set up the content pane.
        Image img = HighGui.toBufferedImage(src);
        addComponentsToPane(frame.getContentPane(), img);
        // Use the content pane's default BorderLayout. No need for
        // setLayout(new BorderLayout());
        // Display the window.
        frame.pack();
        frame.setVisible(true);
        update2();
    }
    
    private void addComponentsToPane(Container pane, Image img) {
        if (!(pane.getLayout() instanceof BorderLayout)) {
            pane.add(new JLabel("Container doesn't use BorderLayout!"));
            return;
        }
        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.PAGE_AXIS));
        sliderPanel.add(new JLabel("Canny threshold: "));
        JSlider slider = new JSlider(0, MAX_THRESHOLD, threshold);
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                threshold = source.getValue();
                update2();
            }
        });
        sliderPanel.add(slider);
        pane.add(sliderPanel, BorderLayout.PAGE_START);
        JPanel imgPanel = new JPanel();
        imgSrcLabel = new JLabel(new ImageIcon(img));
        imgPanel.add(imgSrcLabel);
        Mat blackImg = Mat.zeros(srcGray.size(), CvType.CV_8U);
        imgContoursLabel = new JLabel(new ImageIcon(HighGui.toBufferedImage(blackImg)));
        imgPanel.add(imgContoursLabel);
        pane.add(imgPanel, BorderLayout.CENTER);
    }
    
    
    private void update() {
        Mat cannyOutput = new Mat();
        Imgproc.Canny(srcGray, cannyOutput, threshold, threshold * 2);
        System.out.println("");
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        
        Imgproc.findContours(matCanny, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        
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
        
         Mat drawing = Mat.zeros(cannyOutput.size(), CvType.CV_8UC3);
        
        
       
        for (int i = 0; i < contours.size(); i++) {
            Scalar color = new Scalar(rng.nextInt(256), rng.nextInt(256), rng.nextInt(256));
            Imgproc.drawContours(drawing, contours, i, color);
            //Imgproc.drawContours(drawing, hullList, i, color );
        }
        
        
        int mod = 2;
        //Obtener todos los puntos de los diferentes contornos
        int k=0;
         for (int i = 0; i < contours.size(); i++) {
            List<Point> puntos = contours.get(i).toList();
             //Obtener los puntos de cada parte del contorno
             for (int j=0;j<puntos.size();j++){
               if(k%mod==0){
                      aux.add(puntos.get(j));
                   
                 }
                    k++;
             }             
        }
         

         
        double sumX=0, sumY=0;
        int puntosSuma=0;
      for (int i = 0; i < aux.size(); i++) {
               Imgproc.circle(drawing, new Point(aux.get(i).x, aux.get(i).y), 5, new Scalar(255,0,0), 2, 8, 0);
               sumX+=aux.get(i).x;
               sumY+=aux.get(i).y;
               puntosSuma++;                       
        }


      
         aux = Herramientas.ordenarPuntos(aux);
         Herramientas.sintaxisOBJ(aux);
         Herramientas.sintaxisOpenGL(aux);
        Imgproc.circle(drawing, new Point(sumX/puntosSuma, sumY/puntosSuma), 5, new Scalar(0,0,255), 2, 8, 0);
        imgContoursLabel.setIcon(new ImageIcon(HighGui.toBufferedImage(drawing)));
        frame.repaint();
        System.out.println("");
    }
    
    
     
     private void update2() {
         
      ArrayList<Point> listaPuntos = new ArrayList<Point>();
       dst = Mat.zeros(matCanny.size(), CvType.CV_32F);

        /// Detector parameters
        int blockSize = 2;
        int apertureSize = 3;
        double k = 0.0419;

        /// Detecting corners
        Imgproc.cornerHarris(matCanny, dst, blockSize, apertureSize, k);

        /// Normalizing
        Core.normalize(dst, dstNorm, 0, 255, Core.NORM_MINMAX);
        Core.convertScaleAbs(dstNorm, dstNormScaled);

        /// Drawing a circle around corners
        float[] dstNormData = new float[(int) (dstNorm.total() * dstNorm.channels())];
        dstNorm.get(0, 0, dstNormData);
        // System.out.println("Hola");
        
        
         for (int r = 10; r <= 200; r++) {
          
            for (int i = 0; i < dstNorm.rows(); i++) {
               for (int j = 0; j < dstNorm.cols(); j++) {
                   if ((int) dstNormData[i * dstNorm.cols() + j] > r) {
                       // System.out.println("Row: "+(int) dstNormData[i * dstNorm.cols() + j]);
                       //System.out.println(this.threshold);
                       //Imgproc.circle(dstNormScaled, new Point(j, i), 5, new Scalar(0), 2, 8, 0);
                       ///System.out.println("i: "+i+" j: "+j);
                       listaPuntos.add(new Point(j,i)); 
                                           
                   }                          
               }
           }
            
            
              System.out.println("Tamaño: "+listaPuntos.size());
                       
                        if(listaPuntos.size()>127){                
                           listaPuntos.clear();
                          
                       }else{
                           System.out.println(r);
                           r=201;
                           break;
                       }
             
        }
        
//         for (int i = 0; i <listaPuntos.size(); i++) {
//             Imgproc.circle(dstNormScaled, listaPuntos.get(i), 5, new Scalar(0), 2, 8, 0);
//         }
         
        
        
        
        
        
        ///Limpiar los puntos
        //Con este array tenemos que generar otro que tenga solo los puntos limpias jeje saludos
        int array[] = new int[listaPuntos.size()];
        
        
            for(int i=0;i<listaPuntos.size();i++){ 
                for(int j=i;j<listaPuntos.size();j++){
                    if(array[j]==0)
                    if(j!=i){
                       if(Herramientas.distanciaEuclidiana(listaPuntos.get(i), listaPuntos.get(j))<=9){     
                              array[j]=1;                                        
                       }
                    }
                }           
          }



        
        //Imprimir puntos limpios
        ArrayList<Point> ptoLimpio = new ArrayList<>();
        
        int cont=0;
          for(int i=0;i<listaPuntos.size();i++){ 
            if(array[i]==0){
                Imgproc.circle(dstNormScaled, listaPuntos.get(i), 5, new Scalar(0), 2, 8, 0);
                //System.out.println("v "+listaPuntos.get(i).getX()+" "+listaPuntos.get(i).getY()+" 0");
                  //System.out.println("("+listaPuntos.get(i).getX()+","+listaPuntos.get(i).getY()+")");
                  ptoLimpio.add(listaPuntos.get(i));
                  cont++;
            } 
          }
          
          listaPuntos.clear();
          listaPuntos = ptoLimpio;
          ptoLimpio = null;
          
           System.out.println("Puntos: Iniciales:"+listaPuntos.size()+" Finales: "+cont);
           System.out.println("Umbral: "+this.threshold);
            
           
           
           ///Obtener Obj
           Herramientas.sintaxisOBJ(listaPuntos);

      Imgproc.circle(dstNormScaled, new Point(72, 190), 5, new Scalar(255,0,0), 2, 8, 0);
      Imgproc.circle(dstNormScaled, new Point(69, 244), 5, new Scalar(255,0,0), 2, 8, 0);
       
       
      // this.obtenerPixeles();
       

        imgContoursLabel.setIcon(new ImageIcon(HighGui.toBufferedImage(dstNormScaled)));
        frame.repaint();
    }
        
     
    
    
    
    
      public static void main(String[] args) {
        // Load the native OpenCV library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // Schedule a job for the event dispatch thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HerramientasProcesamiento(args);
            }
        });
    }
    
}
