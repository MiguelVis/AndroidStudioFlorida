package com.app.floppysoftware.pmm_p02_menu;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Clase que implementa el menú principal.
 *
 * Muestra una imagen y las opciones del menú principal.
 *
 * @author  Miguel I. García López
 * @version 1.1
 * @since   14 Oct 2015
 */
public class MenuActivity extends AppCompatActivity {

    // Tag para el log
    private static final String TAG = "MenuActivity";

    // Atributos
    private ArrayList<OpcionMenu> opcionesMenu;  // Opciones del menú
    private ListView listViewMenu;               // ListView para el menú
    private MenuAdapter menuAdapter;             // Adapter para el ListView del menú

    /**
     * Método llamado al crear la activity. También es llamado si se cambia
     * la orientación de la pantalla del dispositivo, pues realmente la
     * activity es destruida y vuelta a recrear.
     *
     * @param savedInstanceState  Estado anterior de la activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Llamar siempre y en primer lugar al método de la super clase
        super.onCreate(savedInstanceState);

        // Establecer layout
        setContentView(R.layout.activity_menu);

        // Log
        Log.i(TAG, "onCreate");

        // Recuperar referencia del ListView del menú
        listViewMenu = (ListView) findViewById(R.id.listViewMenu);

        // Crear ArrayList para las opciones del menú principal
        opcionesMenu = new ArrayList<OpcionMenu>();

        // Crear las opciones y añadirlas al ArrayList
        opcionesMenu.add(new OpcionMenu(R.drawable.ic_perfil, Color.parseColor("#b71c1c"), R.string.menu_perfil, Color.parseColor("#ffcdd2")));
        opcionesMenu.add(new OpcionMenu(R.drawable.ic_juego, Color.parseColor("#004d40"), R.string.menu_juego, Color.parseColor("#b2dfdb")));
        opcionesMenu.add(new OpcionMenu(R.drawable.ic_instrucciones, Color.parseColor("#e65100"), R.string.menu_instrucciones, Color.parseColor("#ffe0b2")));
        opcionesMenu.add(new OpcionMenu(R.drawable.ic_informacion, Color.parseColor("#3e2723"), R.string.menu_informacion, Color.parseColor("#d7ccc8")));

        // Crear el adapter para el ListView del menú
        menuAdapter = new MenuAdapter(this, opcionesMenu);

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
            Toast.makeText(MenuActivity.this, idTextoOpcion, Toast.LENGTH_SHORT).show();
        }
    }
}
