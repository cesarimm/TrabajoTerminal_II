package Manual;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rediseno3d.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class AdapterInstruccion extends ArrayAdapter {
     private List<Instruccion> items = this.llenarLista();



    public AdapterInstruccion(@NonNull Context context) {
        super(context, 0);
    }

    @Override
    public int getCount() {
        return items != null ? items.size() : 0;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) LayoutInflater.from(parent.getContext());

        ///Referencia del view
        View listItemView;


        listItemView = null == convertView ? layoutInflater.inflate(R.layout.dis_instruccion, parent, false) : convertView;

        TextView tvTitulo = (TextView) listItemView.findViewById(R.id.IstTitulo);
        ImageView imagen = (ImageView) listItemView.findViewById(R.id.IstImagen);
        TextView tvContenido = (TextView) listItemView.findViewById(R.id.IstContenido);

        Instruccion instruccion= items.get(position);

        tvTitulo.setText(instruccion.getTitulo());
        tvContenido.setText(instruccion.getContenido());
        imagen.setImageResource(instruccion.getImagen());

        return listItemView;
    }

    private List<Instruccion> llenarLista() {
        List<Instruccion> listaInstrucciones = new ArrayList<Instruccion>();

        ////AGREGAR LA INSTRUCCION NUMERO 1
        Instruccion i1 =new Instruccion("Menu principal",
                "Se encuentran tres opciones: \n" +
                        "REDISEÑO DE OBJETO\nLISTA DE OBJ\n¿CÓMO UTILIZAR?(Ventana actual)\n"+
                        "A continuación se describe cada uno de ellos",
                R.drawable.menuprincipal);
        listaInstrucciones.add(i1);
        ////AGREGAR LA INSTRUCCION NUMERO 2
        i1 =new Instruccion("1. REDISEÑO DE OBJETO",
                 "Después de dar clic en el botón de “REDISEÑO DE OBJETO 3D” se abrirá la siguiente ventana, en la cual debemos de seleccionar una opción dependiendo del tipo de objeto debemos " +
                         "seleccionar la opción indicada.",
                R.drawable.seleccionobjeto);
        listaInstrucciones.add(i1);
        ///Solidos
        i1 =new Instruccion("Solidos",
                "Pueden ser figuras geometricas, letras, bases o soportes entro otros.",
                R.drawable.solido);
        listaInstrucciones.add(i1);
        //Cilindros
        i1 =new Instruccion("Cilindros",
                "Objetos como vasos, tornillos, conos de boliche, tubos, entre otros",
                R.drawable.cilindros);
        listaInstrucciones.add(i1);
        //Huecos
        i1 =new Instruccion("Huecos o donas",
                "Objetos como lo son tuercas, engranes, llantas, entre otros.",
                R.drawable.hueco);
        listaInstrucciones.add(i1);
        ///Complejos
        i1 =new Instruccion("Complejos",
                "Solidos con dos caras que tengan atributos caracteristicos",
                R.drawable.complejo);
        listaInstrucciones.add(i1);
        ////AGREGAR LA INSTRUCCION NUMERO 3
        i1 =new Instruccion("Captura de imagenes",
                ">Mantener la misma dstancia al capturar las diversas vistas del objeto.\n" +
                          ">Fondo debe de realizar un constraste con el objeto,  puede ser blanco o negro.",
                R.drawable.camarita);
        listaInstrucciones.add(i1);
         ///Numero de imagenes por el tipo de objeto
        i1 =new Instruccion("Número de imagenes",
                "Las capturas deben ser tomadas en orden a partir de la cara mas representativa del objeto." +
                        "Observe la siguiente tabla:",
                R.drawable.numcapturas);
        listaInstrucciones.add(i1);

        //Botones capturar imagen
        i1 =new Instruccion("Botones de capturar Imagen",
                ">Botón Rojo. Se utiliza para capturar la imagen, como resultado muestra un mensaje de 'Capturado', lo cual quiere decir que la imagen ha sido tomada exitosamente.\n"+
                ">Botón Verde. Para dar inicio al proceso de creación de archivos OBJ. Se habilita cuando se han tomado las fotografías necesarias.",
                R.drawable.botononescaptura);
        listaInstrucciones.add(i1);

        ///Pantalla de procesamiento
        i1 =new Instruccion("Pantalla de procesamiento",
                "Al visualizarse la siguiente pantalla significa que nuestro proceso de ingeniería inversa a terminado con éxito. Presionamos el botón “CONTINUAR”, el cual nos llevara a la ventana de “LISTA DE ARCHIVOS OBJ” de la cual se habla en el siguiente punto.",
                R.drawable.vistaprogreso);
        listaInstrucciones.add(i1);
        ///Error de procesamiento
        i1 =new Instruccion("Error de procesamiento",
                "Al mostrarse la siguiente alerta quiere decir que algo ha fallado en el proceso y no se han seguido las indicaciones correctas. " +
                         "\n>Posibles errores: \n>Demasiado brillo en las imagenes capturadas \n>Fondo incorrecto \n>No se mantuvo la distancia  \n>Imagenes mal capturadas",
                R.drawable.errorprogreso);
        listaInstrucciones.add(i1);

        ////AGREGAR LA INSTRUCCION NUMERO 4
        i1 =new Instruccion("LISTA OBJ",
                "Se encuentran por orden de creación los archivos el primero es el más reciente y así sucesivamente.  En cada archivo tenemos la opción de realizar tres acciones que a continuación se describen.\nSe realizan dos archivos por cada objeto que se rediseñó exitosamente, un archivo de calidad baja y otro archivo de calidad alta, así el usuario tiene más opciones de cuál es el que desea utilizar según sea lo que requiera.",
                R.drawable.objitem);
        listaInstrucciones.add(i1);

        ///Visualizador
        i1 =new Instruccion("Visualizador",
                 "Muestra el resultado del objeto rediseñado y lo gira alrededor de un eje. \n"
                         + "En algunos casos el archivo OBJ no se puede visualizar, esto es debido a que el visualizador" +
                         "no soporta la nube de puntos que en ocasiones es muy extensa.  \n",
                R.drawable.visualizador);
        listaInstrucciones.add(i1);

        ///Compartir
        i1 =new Instruccion("Compartir OBJ",
                "Facilitar pasar el archivo OBJ a otro dispositivo móvil",
                R.drawable.shareitem);
        listaInstrucciones.add(i1);

        ///Eliminar
        i1 =new Instruccion("Eliminar OBJ",
                "Eliminar el archivo OBJ en caso de ya nor requerido por el usuario",
                R.drawable.deleteitem);
        listaInstrucciones.add(i1);


        return listaInstrucciones;
    }

}
