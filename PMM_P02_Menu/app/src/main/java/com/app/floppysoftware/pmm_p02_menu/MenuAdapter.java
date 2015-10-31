package com.app.floppysoftware.pmm_p02_menu;

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
 * @version 1.0
 * @since   29 Oct 2015
 */
public class MenuAdapter extends ArrayAdapter<OpcionMenu> {

    // Atributos
    private Context context;                // Contexto
    private ArrayList<OpcionMenu> opciones; // Opciones
    private LayoutInflater inflater;        // Inflater para el layout de la opción
    private ImageView imagen;               // ImageView para la imagen de la opción
    private TextView texto;                 // TextView para el texto de la opción

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

        // Guardar inflater
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Devolver el view que corresponde a una opción del menú.
     *
     * @param position     Posición en el ListView
     * @param convertView  View (layout) a transformar y devolver
     * @param parent       ListView
     * @return             View correspondiente a la opción del menú
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Inflar el layout si no ha sido inflado todavía
        if(convertView == null) {

            // Inflar el layout
            convertView = inflater.inflate(R.layout.layout_opcion_menu, parent, false);

            // Tomar los views del layout
            this.imagen = (ImageView) convertView.findViewById(R.id.imageViewOpcionMenu);
            this.texto = (TextView) convertView.findViewById(R.id.textViewOpcionMenu);
        }

        // Tomar el objeto que referencia la opción de menú
        OpcionMenu opcion = opciones.get(position);

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
