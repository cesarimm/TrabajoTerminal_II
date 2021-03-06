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
    Point central;

    public ConvexHull(String[] args) {
        //String filename = "D:\\Documents\\Trabajo_Terminal_Dos\\Imagenes\\cuadrado.jpg";
        //String filename = "D:\\Documents\\Trabajo_Terminal_Dos\\Imagenes\\engrane2.jpg";
        //String filename = "D:\\Documents\\Trabajo_Terminal_Dos\\Imagenes\\llanta.jpg";
        //String filename = "D:\\Documents\\Trabajo_Terminal_Dos\\Imagenes\\trapecio.jpg";
        //String filename = "D:\\Documents\\Trabajo_Terminal_Dos\\Imagenes\\trinagulo.jpg";
        //String filename = "D:\\Documents\\Trabajo_Terminal_Dos\\Imagenes\\estrella.png";
        //String filename = "D:\\Documents\\Trabajo_Terminal_Dos\\Imagenes\\ele.jpg";
        // String filename = "D:\\Documents\\Trabajo_Terminal_Dos\\Imagenes\\trapecio.jpg";
        String filename = "D:\\Documents\\Trabajo_Terminal_Dos\\Imagenes\\flecha.jpg";
        //String filename = "D:\\Documents\\Trabajo_Terminal_Dos\\Imagenes\\esquinas.jpg";
        //String filename = "D:\\Documents\\Trabajo_Terminal_Dos\\Imagenes\\comido.jpg";
        //String filename = "D:\\Documents\\Trabajo_Terminal_Dos\\Imagenes\\frasco2.jpg";
        //String filename = "D:\\Documents\\Trabajo_Terminal_Dos\\Imagenes\\ci1.jpg";
        //String filename = "D:\\Documents\\Trabajo_Terminal_Dos\\Imagenes\\gas.jpg";
        //String filename = "D:\\Documents\\Trabajo_Terminal_Dos\\Imagenes\\recorte2.jpg";
        //String filename = "D:\\Documents\\Trabajo_Terminal_Dos\\Imagenes\\escalera1.jpg";
        //String filename = "D:\\Documents\\Trabajo_Terminal_Dos\\Imagenes\\Achicada.jpg";
        //String filename = "D:\\Documents\\Trabajo_Terminal_Dos\\Imagenes\\circulo.jpg";
        Mat src = Imgcodecs.imread(filename);
        if (src.empty()) {
            System.err.println("Cannot read image: " + filename);
            System.exit(0);
        }
        Imgproc.cvtColor(src, srcGray, Imgproc.COLOR_BGR2GRAY);
        //Imgproc.blur(srcGray, srcGray, new Size(3, 3));
        Imgproc.medianBlur(srcGray, srcGray, 5);

        ///
        //Para buscar y dibujar circulos 
//        Mat circleOut = new Mat();
//        Imgproc.HoughCircles(srcGray, circleOut, Imgproc.HOUGH_GRADIENT, (double) srcGray.rows() / 16, 100.0, 30.0, 1, 30);
//
//        for (int x = 0; x < 1; x++) {
//            try {
//                double[] c = circleOut.get(0, x);
//
//                //Centro del circulo
//                Point centro = new Point(Math.round(c[0]), Math.round(c[1]));
//                Imgproc.circle(src, centro, 1, new Scalar(0, 100, 100), 3, 8, 0);
//
//                System.out.println("Centro x:" + Math.round(c[0]) + " y:" + Math.round(c[1]));
//                ///
//
//                central = centro;
//                int radio = (int) Math.round(c[2]);
//                Imgproc.circle(src, centro, radio, new Scalar(255, 0, 255), 3, 8, 0);
//            } catch (Exception e) {
//
//            }
//
//        }

        //Linea extra
        //Imgproc.Canny(srcGray, srcGray, 50, 150);
        // Create and set up the window.
        frame = new JFrame("Convex Hull demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Set up the content pane.
        Image img = HighGui.toBufferedImage(srcGray);
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
        System.out.println("Threshold: " + threshold);
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

//        ///CONTORNOS
//        double yProm=0, xProm=0;
//        int totalPuntos=0,  rango=5, mod = 1, refMod=0;
//         for (int i = 0; i < contours.size(); i++) {
//            List<Point> puntos = contours.get(i).toList();  
//            //Obtener los puntos de cada parte del contorno
//            for (int j = 0; j < puntos.size(); j++) {
//               xProm+=puntos.get(j).x;
//               yProm+=puntos.get(j).y;
//               totalPuntos++;
//            }
//            
//        }
//         
//        yProm=yProm/totalPuntos;
//        xProm=xProm/totalPuntos;
//        
//        ////Crear el punto promedio entre el centro del circulo y el de todos los puntos
//        xProm=(xProm+this.central.x)/2;
//        yProm=(yProm+this.central.y)/2;
//        
//        ///Recorrer nuevamente cada contorno y ver si su centro de masa Por controno se encuentra
//        //dentro del reango aceptado para eliminar interferencias
//        
//        double yAux=0, xAux=0;
//        int divisiones=0;
//           for (int i = 0; i < contours.size(); i++) {
//            List<Point> puntos = contours.get(i).toList();  
//            //Obtener los puntos de cada parte del contorno
//            for (int j = 0; j < puntos.size(); j++) {
//               xAux+=puntos.get(j).x;
//               yAux+=puntos.get(j).y;
//               divisiones++;
//            }
//            
//            xAux=xAux/divisiones;
//            yAux=yAux/divisiones;
//            
//               
//            if(!((yAux<yProm-rango||yAux>yProm+rango)&&(xAux<xProm-rango||xAux>xProm+rango))){
//                System.out.println("General x:"+xProm+" y:"+yProm);
//               System.out.println("Contorno x:"+xAux+" y:"+yAux);
//               for (int j = 0; j < puntos.size(); j++) {
//                   if(refMod%mod==0){
//                       Imgproc.circle(drawing, puntos.get(j), 5, new Scalar(255, 0, 0), 2, 8, 0);
//                       aux.add(puntos.get(j));
//                   }
//                   refMod++;
//                } 
//            }
//            
//            
//            divisiones=0;
//            yAux=0;
//            xAux=0;
//        }
        /////////
        int contAux = 0;
        //contours.size()
        int mod = 2;
        //Obtener todos los puntos de los diferentes contornos
        int k = 0;
        for (int i = 0; i < contours.size(); i++) {
            List<Point> puntos = contours.get(i).toList();
            contAux += puntos.size();
            //Obtener los puntos de cada parte del contorno
            for (int j = 0; j < puntos.size(); j++) {
                if (k % mod == 0) {
                    aux.add(puntos.get(j));
                    //Imgproc.circle(drawing, new Point(aux.get(i).x, aux.get(i).y), 5, new Scalar(255, 0, 0), 2, 8, 0);
                }
                k++;
            }
//            if(contAux>=1000){
//                break;
//            }
        }

        //Calcular el punto medio de la cara solo es para dibujar
        double sumX = 0, sumY = 0;
        int puntosSuma = 0;
        for (int i = 0; i < aux.size(); i++) {
            // Imgproc.circle(drawing, new Point(aux.get(i).x, aux.get(i).y), 5, new Scalar(255, 0, 0), 2, 8, 0);
            sumX += aux.get(i).x;
            sumY += aux.get(i).y;
            puntosSuma++;
            // System.out.println("X: "+aux.get(i).x+" Y:"+aux.get(i).y);             
        }

        ////Obtiene la lista de puntos
        aux = Herramientas.ordenarPuntos(aux);
        ///Obtener la longitud de extremos de la figura    
        double longitud = Herramientas.getLongitud(aux);
        //Herramientas.sintaxisOBJV2(aux, 100);
        //Herramientas.sintaxisDonasOBJ(aux);
        //Division
        // cannyOutput = Divisiones.printMat((int)Herramientas.yMin-5, (int)Herramientas.yMax+5, (int)Herramientas.min-5, (int)Herramientas.max+5, cannyOutput);

       cannyOutput = Divisiones.generarPuntos((int) Herramientas.yMin - 5, (int) Herramientas.yMax + 5, (int) Herramientas.min - 5, (int) Herramientas.max + 5,
                longitud, 50, cannyOutput);
    //    cannyOutput = Divisiones.generarPuntosY((int) Herramientas.yMin - 5, (int) Herramientas.yMax + 5, (int) Herramientas.min - 5, (int) Herramientas.max + 5,
       //         longitud, 20, cannyOutput);

//          cannyOutput = Divisiones.printMat((int) Herramientas.yMin - 5, (int) Herramientas.yMax + 5, (int) Herramientas.min - 5, (int) Herramientas.max + 5,
//                cannyOutput);
//        Mat resizeImg = new Mat();
//        Size sz = new Size(100,150);
//        Imgproc.resize(cannyOutput, resizeImg, sz );
        //ConvexHull.rotate(resizeImg, 90);
        //cannyOutput = Divisiones.printMat(33,195,29,368, cannyOutput);
        //Herramientas.sintaxisOBJ(aux, Herramientas.dividir(Herramientas.getLongitud(aux), aux, 3));
        // Herramientas.sintaxisOBJ(aux);
        // Herramientas.sintaxisOpenGL(aux);
        //Herramientas.sintaxisOBJV2(aux, 185);
        Imgproc.circle(drawing, new Point(sumX / puntosSuma, sumY / puntosSuma), 5, new Scalar(0, 0, 255), 2, 8, 0);

        //this.rotate(cannyOutput, 90);
        ///Aqui 
        imgContoursLabel.setIcon(new ImageIcon(HighGui.toBufferedImage(cannyOutput)));
        frame.repaint();

        ///Este es el original
//         imgContoursLabel.setIcon(new ImageIcon(HighGui.toBufferedImage(drawing)));
//         frame.repaint();
        System.out.println("");
    }

    public void buscarLimitesY(double inferior, double superior, int x, Mat imagenProcesada) {
        for (double i = inferior; i <= superior; i++) {
            double colores[] = imagenProcesada.get(x, (int) i);

            for (int j = 0; j < colores.length; j++) {
                System.out.println(colores[j]);
            }

            System.out.println("");
        }
    }

    public void rotate(Mat image, double angle) {
        //Calculate size of new matrix
        double radians = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(radians));
        double cos = Math.abs(Math.cos(radians));

        int newWidth = (int) (image.width() * cos + image.height() * sin);
        int newHeight = (int) (image.width() * sin + image.height() * cos);

        // rotating image
        Point center = new Point(newWidth / 2, newHeight / 2);
        Mat rotMatrix = Imgproc.getRotationMatrix2D(center, angle, 1.0); //1.0 means 100 % scale

        Size size = new Size(newWidth, newHeight * 1.5);
        Imgproc.warpAffine(image, image, rotMatrix, size);

        imgContoursLabel.setIcon(new ImageIcon(HighGui.toBufferedImage(image)));
        frame.repaint();

    }

    private void ordenarPuntos(ArrayList<Point> aux) {

        ArrayList<Point> listaOrdenada = new ArrayList<Point>();

        double distanciaAux = Herramientas.distanciaEuclidiana(aux.get(0), aux.get(1));
        int ref = 1;

        for (int j = 1; j < aux.size(); j++) {
            double distanciaTemporal = 0;
            for (int i = j; i < aux.size() - 1; i++) {
                ///Calculando con todos los puntos 
                distanciaTemporal = Herramientas.distanciaEuclidiana(aux.get(j), aux.get(i + 1));
                if (distanciaTemporal < distanciaAux) {
                    System.out.println("distanciaMenor: " + distanciaAux);
                    distanciaAux = distanciaTemporal;
                    ref = i;
                } else if (distanciaTemporal == 0) {
                    System.out.println("Son iguales");
                    aux.remove(i + 1);
                }
            }
        }
    }

    ///Obtener contornos centrales
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
