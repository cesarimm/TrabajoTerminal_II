package Procesamiento;

import org.opencv.core.Point;

import java.util.ArrayList;

public class Herramientas {

    ///Los 4 extremos en el eje "X" y eje "Y"
    public static double  min = 0, max =0, yMin=0, yMax=0;

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
        longitud = xMax - xMin;

        return longitud;
    }

     ///Eliminar puntos Repetidos
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

    ///Calcular distancia euclidina entre dos puntos
    public static double distanciaEuclidiana(Point a, Point b){
        return Math.sqrt(Math.pow((double)a.x-b.x,2)+Math.pow((double)a.y-b.y,2));
    }

    ///Funcion auxiliar para el ordenamiento de puntos
    public static int regresarReferencia(Point aux, ArrayList<Point> lista){
        int referencia = 0, j = 1;

        double distanciaAux = Herramientas.distanciaEuclidiana(aux, lista.get(0));


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

        return listaOrdenada;
    }

}
