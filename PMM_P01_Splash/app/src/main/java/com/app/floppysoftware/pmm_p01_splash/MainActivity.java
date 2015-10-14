package com.app.floppysoftware.pmm_p01_splash;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Clase que implementa la activity principal.
 *
 * Muestra un mensaje.
 *
 * @author  Miguel I. García López
 * @version 1.00
 * @since   14 Oct 2015
 */
public class MainActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_main);
    }

    /**
     * Método llamado al crear el menú. Actualmente sólo tiene la funcionalidad
     * por defecto.
     *
     * @param menu  Menú
     * @return  True para mostrar el menú, false en caso contrario
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflar el menú
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Devolver true para que se muestre el menú
        return true;
    }

    /**
     * Método llamado cuando se selecciona una opción del menú.
     *
     * @param item  Opción seleccionada
     * @return  True si se ha gestionado la opción, false en caso contrario
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Tomar la opción seleccionada
        int id = item.getItemId();

        // Gestionar la opción
        switch(id) {
            // Settings
            case R.id.action_settings :
                // De momento, nada
                return true;

            // Opción desconocida
            default :
                break;
        }

        // Opción no gestionada aquí, llamar a la super clase
        return super.onOptionsItemSelected(item);
    }
}
