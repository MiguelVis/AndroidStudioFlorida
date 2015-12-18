package com.app.floppysoftware.pmm_p04_mathdice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Clase que implementa el adaptador para el ListView del menú principal.
 *
 * @author  Miguel I. García López
 * @version 1.1
 * @since   29 Oct 2015
 */
public class MenuAdapter extends ArrayAdapter<OpcionMenu> {

    // Atributos
    private Context context;                // Contexto
    private ArrayList<OpcionMenu> opciones; // Opciones del menú

    /**
     * Constructor.
     *
     * @param context   Contexto
     * @param opciones  Opciones
     */
    public MenuAdapter(Context context, ArrayList<OpcionMenu> opciones) {

        // Llamar a la superclase, siempre
        super(context, 0, opciones);

        // Guardar atributos
        this.context = context;   // Contexto
        this.opciones = opciones; // Opciones
    }

    /**
     * Transformar y devolver el view que corresponde a una opción del menú. En este
     * caso, se trata de un layout con una imagen y un texto.
     *
     * @param position     Posición en el ListView
     * @param convertView  View (layout) a transformar y devolver
     * @param parent       ListView
     * @return             View (layout) transformado
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Inflar el layout si no ha sido inflado todavía
        if(convertView == null) {

            // Tomar inflater
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // Inflar el layout
            convertView = inflater.inflate(R.layout.layout_opcion_menu, parent, false);
        }

        // Tomar el objeto que referencia la opción de menú
        OpcionMenu opcion = opciones.get(position);

        // Tomar los views del layout
        ImageView imagen = (ImageView) convertView.findViewById(R.id.imageViewOpcionMenu);
        TextView texto = (TextView) convertView.findViewById(R.id.textViewOpcionMenu);

        // Cambiar la imagen y su color de fondo
        imagen.setImageResource(opcion.getImagenId());
        imagen.setBackgroundColor(opcion.getImagenBkgClr());

        // Cambiar el texto y su color de fondo
        texto.setText(opcion.getTextoId());
        texto.setBackgroundColor(opcion.getTextoBkgClr());

        // Devolver el view (layout) transformado
        return convertView;
    }
}
