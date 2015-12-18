package com.app.floppysoftware.pmm_p04_mathdice;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Clase que implementa la activity de instrucciones, para dispositivos
 * con pantallas de tamaño normal (no tabletas).
 *
 * @author  Miguel I. García López
 * @version 1.0
 * @since   18 Dec 2015
 */
public class InstruccionesActivity extends Activity {

    /**
     * Método llamado al crear la activity. También es llamado si se cambia
     * la orientación de la pantalla del dispositivo, pues realmente la
     * activity es destruida y vuelta a recrear.
     *
     * @param savedInstanceState  Estado anterior de la activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Llamar a la superclase
        super.onCreate(savedInstanceState);

        // Fijar layout
        setContentView(R.layout.activity_instrucciones);
    }
}
