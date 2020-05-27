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
public class ConvexHull {
 private Mat srcGray = new Mat();
    private JFrame frame;
    private JLabel imgSrcLabel;
    private JLabel imgContoursLabel;
    private static final int MAX_THRESHOLD = 255;
    private int threshold = 100;
    private Random rng = new Random(12345);
    public ConvexHull(String[] args) {
       // String filename = "D:\\Documents\\Trabajo_Terminal_Dos\\Imagenes\\cuadrado.jpg";
        String filename = "D:\\Documents\\Trabajo_Terminal_Dos\\Imagenes\\engrane2.jpg";
       //String filename = "D:\\Documents\\Trabajo_Terminal_Dos\\Imagenes\\trapecio.jpg";
       // String filename = "D:\\Documents\\Trabajo_Terminal_Dos\\Imagenes\\trinagulo.jpg";
       // String filename = "D:\\Documents\\Trabajo_Terminal_Dos\\Imagenes\\estrella.png";
        Mat src = Imgcodecs.imread(filename);
        if (src.empty()) {
            System.err.println("Cannot read image: " + filename);
            System.exit(0);
        }
        Imgproc.cvtColor(src, srcGray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.blur(srcGray, srcGray, new Size(3, 3));
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
        update();
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
                update();
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
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(cannyOutput, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
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
        
        
        int mod = 5;
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
         
         this.ordenarPuntos(aux);
         System.out.println("");
         //this.ordenar(aux);
        //Calcular el punto medio de la cara
        double sumX=0, sumY=0;
        int puntosSuma=0;
      for (int i = 0; i < aux.size(); i++) {
               Imgproc.circle(drawing, new Point(aux.get(i).x, aux.get(i).y), 5, new Scalar(255,0,0), 2, 8, 0);
               sumX+=aux.get(i).x;
               sumY+=aux.get(i).y;
               puntosSuma++;
              // System.out.println("X: "+aux.get(i).x+" Y:"+aux.get(i).y);
                
        }
                 //aux.add(new Point(sumX/puntosSuma, sumY/puntosSuma));
               //  System.out.println(sumX/puntosSuma+" "+sumY/puntosSuma);
//                   for(int i=0;i<aux.size()-1;i++){
//            System.out.println("v "+aux.get(i).x+" "+aux.get(i).y+" 0");
//        }
                   
         for(int i=0;i<aux.size()-1;i++){
            System.out.println("aux.add(new Point("+aux.get(i).x+","+aux.get(i).y+"));");
        } 
         
         System.out.println("v "+Math.ceil(sumX/puntosSuma)+" "+Math.ceil(sumY/puntosSuma)+"  0");
         
         System.out.println("usemtl Default");
         
       
       for(int i=0;i<aux.size()-2;i++){
            System.out.println("f "+(i+1)+" "+(i+2)+" "+(aux.size()));
        }
        
       
        Imgproc.circle(drawing, new Point(sumX/puntosSuma, sumY/puntosSuma), 5, new Scalar(0,0,255), 2, 8, 0);
        imgContoursLabel.setIcon(new ImageIcon(HighGui.toBufferedImage(drawing)));
        frame.repaint();
        System.out.println("");
    }
    
    
     
     
        
     
    private void ordenarPuntos(ArrayList<Point> aux){
        
        ArrayList<Point> listaOrdenada = new ArrayList<Point>();
        
        double distanciaAux = Herramientas.distanciaEuclidiana(aux.get(0), aux.get(1));
        int ref = 1;
        
        for(int j=1;j<aux.size();j++){
             double distanciaTemporal = 0;
            for(int i=j;i<aux.size()-1;i++){
              ///Calculando con todos los puntos 
               distanciaTemporal = Herramientas.distanciaEuclidiana(aux.get(j), aux.get(i+1));
              if(distanciaTemporal<distanciaAux){
                  System.out.println("distanciaMenor: "+distanciaAux);
                   distanciaAux = distanciaTemporal;
                   ref = i;
              }else if(distanciaTemporal==0){
                  System.out.println("Son iguales");
                  aux.remove(i+1);
              }
           }     
        }       
    }
    
    
    
      public static void main(String[] args) {
        // Load the native OpenCV library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // Schedule a job for the event dispatch thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ConvexHull(args);
            }
        });
    }
    
}
