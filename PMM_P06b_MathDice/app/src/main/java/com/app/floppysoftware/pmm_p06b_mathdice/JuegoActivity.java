package com.app.floppysoftware.pmm_p06b_mathdice;

import android.app.Activity;
import android.os.Bundle;

/**
 * Clase que implementa la activity del juego, para dispositivos
 * que no sean tablets.
 *
 * @author  Miguel I. García López
 * @version 1.0
 * @since   16 Dec 2015
 */
public class JuegoActivity extends Activity {

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
        setContentView(R.layout.activity_juego);
    }
}
