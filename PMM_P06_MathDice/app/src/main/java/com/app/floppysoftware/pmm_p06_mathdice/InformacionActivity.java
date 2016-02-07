package com.app.floppysoftware.pmm_p06_mathdice;

import android.app.Activity;
import android.os.Bundle;

/**
 * Clase que implementa la activity de información para dispositivos
 * que no sean tablets.
 *
 * @author  Miguel I. García López
 * @version 1.0
 * @since   18 Dec 2015
 */
public class InformacionActivity extends Activity {

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
        setContentView(R.layout.activity_informacion);
    }
}
