package Procesamiento;

import org.opencv.core.Point;

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
}
