package com.app.floppysoftware.pmm_p03_mathdice;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Clase que implementa el menú de opciones mediante un fragment.
 *
 * @author  Miguel I. García López
 * @version 1.0
 * @since   11 Nov 2015
 */
public class MenuFragment extends Fragment {

    // Atributos
    private ArrayList<OpcionMenu> opcionesMenu;  // Opciones del menú
    private ListView listViewMenu;               // ListView para el menú
    private MenuAdapter menuAdapter;             // Adapter para el ListView del menú

    // Lístener para interactuar con la activity
    private MenuFragmentListener mListener;

    /**
     * Interfaz que ha de implementar la activity, para comunicarse
     * con este fragment.
     */
    public interface MenuFragmentListener {

        // Método a implementar, para indicar la opción
        // seleccionada.
        public void onOptionSelected(int position);
    }

    /**
     * Constructor público vacío requerido.
     */
    public MenuFragment() {
        //
    }

    /**
     * Método llamado al crear la interfaz de usuario por 1ª vez.
     *
     * @param inflater   layout inflater
     * @param container  contenedor del layout
     * @param savedInstanceState  estado previamente guardado
     * @return  layout del fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflar y devolver el layout del fragment
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    /**
     * Método llamado cuando el fragment es enlazado a la activity.
     *
     * @param activity  activity
     */
    @Override
    public void onAttach(Activity activity) {

        // Llamar a la superclase
        super.onAttach(activity);

        // Comprobar que la activity ha implementado el lístener para comunicarse
        try {
            mListener = (MenuFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " ha de implementar MenuFragmentListener");
        }
    }

    /**
     * Método llamado cuando el fragment es desenlazado de la activity.
     */
    @Override
    public void onDetach() {

        // Llamar a la superclase
        super.onDetach();

        // Invalidar el lístener
        mListener = null;
    }

    /**
     * Método llamado cuando la activity ha sido creada.
     *
     * @param savedInstanceState  estado previamente guardado
     */
    @Override
    public void onActivityCreated (Bundle savedInstanceState) {

        // Llamar a la superclase
        super.onActivityCreated(savedInstanceState);

        // Recuperar referencia del ListView del menú
        listViewMenu = (ListView) getActivity().findViewById(R.id.listViewMenu);

        // Crear ArrayList para las opciones del menú principal
        opcionesMenu = new ArrayList<OpcionMenu>();

        // Crear las opciones y añadirlas al ArrayList
        opcionesMenu.add(new OpcionMenu(R.drawable.ic_perfil, Color.parseColor("#b71c1c"), R.string.menu_perfil, Color.parseColor("#ffcdd2")));
        opcionesMenu.add(new OpcionMenu(R.drawable.ic_juego, Color.parseColor("#004d40"), R.string.menu_juego, Color.parseColor("#b2dfdb")));
        opcionesMenu.add(new OpcionMenu(R.drawable.ic_instrucciones, Color.parseColor("#e65100"), R.string.menu_instrucciones, Color.parseColor("#ffe0b2")));
        opcionesMenu.add(new OpcionMenu(R.drawable.ic_informacion, Color.parseColor("#3e2723"), R.string.menu_informacion, Color.parseColor("#d7ccc8")));

        // Crear el adapter para el ListView del menú
        menuAdapter = new MenuAdapter(getActivity(), opcionesMenu);

        // Conectar el adapter al ListView del menú
        listViewMenu.setAdapter(menuAdapter);

        // Añadir el lístener de selección de las opciones del menú
        listViewMenu.setOnItemClickListener(new ListenerListViewMenu());
    }

    /**
     * Inner class. Listener para la selección de opciones del menú principal.
     */
    private class ListenerListViewMenu implements AdapterView.OnItemClickListener {

        /**
         * Método que se llama al seleccionar una opción del menú.
         *
         * @param parent    ListView que contiene el menú
         * @param view      View del elemento seleccionado
         * @param position  Posición del item
         * @param id        Id de fila del item seleccionado
         */
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            // Recuperar opción seleccionada
            OpcionMenu opcion = (OpcionMenu) parent.getItemAtPosition(position);

            // Recuperar el texto de la opción seleccionada
            int idTextoOpcion = opcion.getTextoId();

            // También podríamos haber recuperado el texto de esta otra forma:
            // int idTextoOpcion = opcionesMenu.get(position).getTextoId();

            // O de esta otra:
            // TextView tv = (TextView) view.findViewById(R.id.textViewOpcionMenu);
            // CharSequence idTextoOpcion = tv.getText();

            // Mostrar mensaje con el texto de la opción seleccionada
            //Toast.makeText(getActivity(), idTextoOpcion, Toast.LENGTH_SHORT).show();

            // Pasarle los datos a la activity
            mListener.onOptionSelected(position);
        }
    }
}
