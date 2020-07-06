package Procesamiento;

import org.opencv.core.Point;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SintaxisOBJ {


    public static String sintaxisOBJSolidos(ArrayList<Point> aux, double distancia){

        String texto="";
        for(int i=0;i<aux.size();i++){
            //System.out.println("v "+aux.get(i).x+" "+aux.get(i).y+" 0");
                texto+="v "+aux.get(i).x+" "+aux.get(i).y+" 0\n";
        }

        if(distancia!=0)
        for(int i=0;i<aux.size();i++){
            //System.out.println("v "+aux.get(i).x+" "+aux.get(i).y+" "+distancia);
            texto+="v "+aux.get(i).x+" "+aux.get(i).y+" "+distancia+"\n";
        }


        //System.out.println("usemtl Default");
         texto+="usemtl Default\n";

        ///Primera cara
        for(int i=0;i<(aux.size()-2);i++){
            //System.out.println("f "+(i+1)+" "+(i+2)+" "+(i+3));
            texto+="f "+(i+1)+" "+(i+2)+" "+(i+3)+"\n";
        }

        if(distancia!=0) {

            ///Segunda cara
            for (int i = aux.size(); i < (aux.size() - 1) * 2; i++) {
                //System.out.println("f " + (i + 1) + " " + (i + 2) + " " + (i + 3));
                texto+="f "+(i+1)+" "+(i+2)+" "+(i+3)+"\n";
            }


            //Union entrre las caras laterales
            for (int i = 0; i < aux.size() - 2; i++) {
                //System.out.println("f " + (i + 1) + " " + (i + 3) + " " + (aux.size() + (i + 1)));
                //System.out.println("f " + (aux.size() + (i + 1)) + " " + (aux.size() + (i + 3)) + " " + (i + 3));
                texto+="f " + (i + 1) + " " + (i + 3) + " " + (aux.size() + (i + 1))+"\n";
                texto+="f " + (aux.size() + (i + 1)) + " " + (aux.size() + (i + 3)) + " " + (i + 3)+"\n";
            }

            //Las ultimas dos caras
            //System.out.println("f " + (1) + " " + (2) + " " + (aux.size() + 1));
            //System.out.println("f " + (aux.size() + 1) + " " + (aux.size() + 2) + " " + 2);
            texto+="f " + (1) + " " + (2) + " " + (aux.size() + 1)+"\n";
            texto+="f " + (aux.size() + 1) + " " + (aux.size() + 2) + " " +2+"\n";

           // System.out.println("f " + (aux.size() - 2) + " " + (aux.size() - 1) + " " + ((2 * aux.size()) - 2));
            //System.out.println("f " + ((2 * aux.size()) - 2) + " " + ((2 * aux.size()) - 1) + " " + (aux.size() - 1));
            texto+="f " + (aux.size() - 2) + " " + (aux.size() - 1) + " " + (2 * aux.size())+"\n";
            texto+="f " + ((2 * aux.size()) - 2) + " " + ((2 * aux.size()) - 1) + " " + (aux.size() - 1);

        }

        return texto;
    }


    ////Sintaxis Cilindros
    public static String generarSintaxisCilindrosOBJ(ArrayList<Point> aux){

        ArrayList<Point> pCir;
        Point a, b;

        DecimalFormat df = new DecimalFormat("#.00");
        String texto = "";
        int cont=0, longitud=0;

        for(int i=0;i<aux.size();i+=2){
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
            }
           // System.out.println("");
        }


        //System.out.println(texto);
            return texto;
    }



    ///Circulos Donas
    public static String sintaxisOBJDonas(ArrayList<Point> aux, double largo){
        String texto="";
        double xProm=0, yProm=0;
        int arreglo[] = new int[aux.size()+1];

        for(int i=0;i<aux.size();i++){
            //System.out.println("v "+aux.get(i).x+" "+aux.get(i).y+" 0");
            texto+="v "+aux.get(i).x+" "+aux.get(i).y+" 0"+"\n";
            xProm+=aux.get(i).x;
            yProm+=aux.get(i).y;
        }

        //System.out.println("v "+Math.ceil(xProm/aux.size())+" "+Math.ceil(yProm/aux.size())+" 0");
        texto+="v "+Math.ceil(xProm/aux.size())+" "+Math.ceil(yProm/aux.size())+" 0"+"\n";
        ///Para duplicar los puntos
        for(int i=0;i<aux.size();i++){
            //System.out.println("v "+aux.get(i).x+" "+aux.get(i).y+" "+largo);
            texto+="v "+aux.get(i).x+" "+aux.get(i).y+" "+largo+"\n";
            //xProm+=aux.get(i).x;
            //yProm+=aux.get(i).y;
        }

        //System.out.println("v "+Math.ceil(xProm/aux.size())+" "+Math.ceil(yProm/aux.size())+" "+largo);
        texto+="v "+Math.ceil(xProm/aux.size())+" "+Math.ceil(yProm/aux.size())+" "+largo+"\n";

        //System.out.println("usemtl Default");
        texto+="usemtl Default"+"\n";

        ///Imprimir las caras laterales

        for(int i=0;i<aux.size()-1;i++){
            ///Primer triangulo
            //System.out.println("f "+(i+1)+" "+(i+2)+" "+(aux.size()+1));
            texto+="f "+(i+1)+" "+(i+2)+" "+(aux.size()+1)+"\n";
        }

        //System.out.println("f "+1+" "+aux.size()+" "+(aux.size()+1));
        texto+="f "+1+" "+aux.size()+" "+(aux.size()+1)+"\n";

        for(int i=0;i<aux.size()-1;i++){
            ///Primer triangulo
            //System.out.println("f "+(aux.size()+(i+2))+" "+(aux.size()+(i+3))+" "+((aux.size()*2)+2));
            texto+="f "+(aux.size()+(i+2))+" "+(aux.size()+(i+3))+" "+((aux.size()*2)+2)+"\n";
        }

        //System.out.println("f "+(aux.size()+2)+" "+((aux.size()*2)+1)+" "+((aux.size()*2)+2));
        texto+="f "+(aux.size()+2)+" "+((aux.size()*2)+1)+" "+((aux.size()*2)+2)+"\n";

        //Union entrre las caras laterales
        for(int i=0;i<aux.size()-1;i++){
            //System.out.println("f "+(i+1)+" "+(i+2)+" "+(aux.size()+(i+2)));
            //System.out.println("f "+(aux.size()+(i+2))+" "+(aux.size()+(i+3))+" "+(i+2));
            texto+= "f "+(i+1)+" "+(i+2)+" "+(aux.size()+(i+2))+"\n";
            texto+="f "+(aux.size()+(i+2))+" "+(aux.size()+(i+3))+" "+(i+2)+"\n";
        }

        //Las ultimas dos caras
        //System.out.println("f "+1+" "+(aux.size()-1)+" "+((aux.size()*2)+1));
        //System.out.println("f "+1+" "+((aux.size()*2)+1)+" "+(aux.size()+2));
        texto+="f "+1+" "+(aux.size()-1)+" "+((aux.size()*2)+1)+"\n";
        texto+="f "+1+" "+(aux.size()-1)+" "+((aux.size()*2)+1)+"\n";
        return texto;
    }


    ////Para los solidos complejos
    public static String objXYZ(ArrayList<Point> xy, ArrayList<Point> yz){
        ///Obtener minimos y maximos en los diferentes planos
        double xMin=yz.get(0).x, xMax=yz.get(xy.size()-1).x;
        double yMinimo = xy.get(0).y, yMaximo = xy.get(0).y;

        String planoXy="", planoYz="", relacionCaras="";

        ///Generar los vertices de la primera cara del plano xy
        for(int i=0;i<xy.size();i++){
            //System.out.println("v "+xy.get(i).x+" "+xy.get(i).y+" 0");
            planoXy+="v "+xy.get(i).x+" "+xy.get(i).y+" 0\n";
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
            //System.out.println("v 0 "+yz.get(i).y+" "+yz.get(i).x);
            planoYz+="v 0 "+yz.get(i).y+" "+yz.get(i).x+"\n";
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
            //System.out.println("v "+xy.get(i).x+" "+xy.get(i).y+" "+longitud);
            planoXy+="v "+xy.get(i).x+" "+xy.get(i).y+" "+longitud+"\n";
        }

        ///Encontrar el primer cambio radical en el eje x
        double cambio = xy.get(0).x;

        for (int i = 1; i <xy.size(); i++) {
            if((xy.get(i).x-cambio)>=longitud/3){
                cambio = xy.get(i).x;
                break;
            }
        }

        ///Generar los verices de la segunda cara del plano yz
        longitud = yMaximo - yMinimo;
        for(int i=0;i<yz.size();i++){
            //System.out.println("v "+cambio+" "+yz.get(i).y+" "+yz.get(i).x);
            planoYz+="v "+cambio+" "+yz.get(i).y+" "+yz.get(i).x+"\n";
        }

        //System.out.println(planoXy);
        //System.out.println(planoYz);
        planoYz+="usemtl Default\n";
        ///Generar las caras

        //Caras del plano XY
        for(int i=0;i<(xy.size()-2);i++){
            //System.out.println("f "+(i+1)+" "+(i+2)+" "+(i+3));
            relacionCaras+="f "+(i+1)+" "+(i+2)+" "+(i+3)+"\n";
        }


        for(int i=(xy.size());i<(xy.size()-1)*2;i++){
            //System.out.println("f "+(i+1)+" "+(i+2)+" "+(i+3));
            relacionCaras+="f "+(i+1)+" "+(i+2)+" "+(i+3)+"\n";
        }


        //Caras del plano YZ
        for(int i=xy.size()*2;i<(xy.size()*2)+(yz.size()-2);i++){
            //System.out.println("f "+(i+1)+" "+(i+2)+" "+(i+3));
            relacionCaras+="f "+(i+1)+" "+(i+2)+" "+(i+3)+"\n";

        }

        for(int i=xy.size()*2+yz.size();i<(xy.size()*2)+(yz.size()-1)*2;i++){
            //System.out.println("f "+(i+1)+" "+(i+2)+" "+(i+3));
            relacionCaras+="f "+(i+1)+" "+(i+2)+" "+(i+3)+"\n";
        }



        //Union entrre las caras laterales

        for(int i=0;i<xy.size()-2;i++){
            if(xy.get(i).x>cambio){
                //System.out.println("f "+(i+1)+" "+(i+3)+" "+(xy.size()+(i+1)));
                //System.out.println("f "+(xy.size()+(i+1))+" "+(xy.size()+(i+3))+" "+(i+3));
                relacionCaras+="f "+(i+1)+" "+(i+3)+" "+(xy.size()+(i+1))+"\n";
                relacionCaras+="f "+(xy.size()+(i+1))+" "+(xy.size()+(i+3))+" "+(i+3)+"\n";
            }
        }

        for(int i=xy.size()*2;i<(xy.size()*2)+(yz.size()-2);i++){
            //System.out.println("f "+(i+1)+" "+(i+3)+" "+(yz.size()+(i+1)));
            //System.out.println("f "+(yz.size()+(i+1))+" "+(yz.size()+(i+3))+" "+(i+3));
            relacionCaras+="f "+(i+1)+" "+(i+3)+" "+(yz.size()+(i+1))+"\n";
            relacionCaras+="f "+(yz.size()+(i+1))+" "+(yz.size()+(i+3))+" "+(i+3)+"\n";
        }

        ///Ultima cara
        //System.out.println("f "+(xy.size()-2)+" "+(xy.size()-1)+" "+((2*xy.size())-2));
        //System.out.println("f "+((2*xy.size())-2)+" "+((2*xy.size())-1)+" "+(xy.size()-1));
        relacionCaras+="f "+(xy.size()-2)+" "+(xy.size()-1)+" "+((2*xy.size())-2)+"\n";
        relacionCaras+="f "+((2*xy.size())-2)+" "+((2*xy.size())-1)+" "+(xy.size()-1)+"\n";

        return planoXy+planoYz+relacionCaras;
    }

}
