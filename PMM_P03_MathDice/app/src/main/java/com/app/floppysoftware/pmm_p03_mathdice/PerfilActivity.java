package com.app.floppysoftware.pmm_p03_mathdice;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Clase que implementa la activity de solicitud de perfil.
 *
 * @author  Miguel I. García López
 * @version 1.0
 * @since   25 Nov 2015
 */
public class PerfilActivity extends Activity implements PerfilFragment.PerfilFragmentListener {

    // IDs de los extras
    public static final String ARG_PERFIL_NOMBRE = "ARG_PERFIL_NOMBRE";  // Nombre
    public static final String ARG_PERFIL_EDAD = "ARG_PERFIL_EDAD";      // Edad

    /**
     * Este método será llamado desde el fragment de solicitud del perfil,
     * con los datos introducidos. Ejecutará la activity del juego, y le
     * pasará estos mismos datos.
     *
     * @param nombre  nombre del perfil
     * @param edad    edad del perfil
     */
    public void onPerfilSelected(String nombre, int edad) {

        // Intent para ejecutar la otra activity
        Intent intentPrincipalActivity = new Intent(getApplicationContext(), PrincipalActivity.class);

        // Enviarle los datos del perfil
        intentPrincipalActivity.putExtra(ARG_PERFIL_NOMBRE, nombre);  // Nombre
        intentPrincipalActivity.putExtra(ARG_PERFIL_EDAD, edad);      // Edad

        // Comenzar la otra activity
        startActivity(intentPrincipalActivity);

        // Finalizar esta activity, de forma que no se pueda volver a ella
        finish();
    }

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
        setContentView(R.layout.activity_perfil);
    }
}
