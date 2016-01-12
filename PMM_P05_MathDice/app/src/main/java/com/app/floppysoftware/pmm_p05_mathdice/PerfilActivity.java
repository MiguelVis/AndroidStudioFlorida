package com.app.floppysoftware.pmm_p05_mathdice;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

/**
 * Clase que implementa la activity de solicitud de perfil,
 * en dispositivos que no sean tablets.
 *
 * @author  Miguel I. García López
 * @version 1.1
 * @since   23 Dec 2015
 */
public class PerfilActivity extends Activity implements PerfilFragment.PerfilFragmentListener {

    // IDs de los extras
    public static final String ARG_PERFIL_NOMBRE = "ARG_PERFIL_NOMBRE";  // Nombre
    public static final String ARG_PERFIL_EDAD = "ARG_PERFIL_EDAD";      // Edad

    // Datos del perfil
    private String perfilNombre;  // Nombre
    private int perfilEdad;       // Edad

    /**
     * Este método será llamado desde el fragment de solicitud del perfil,
     * con los datos introducidos por el usuario. Esta activity devolverá
     * esos mismos datos a la activity que la llamó.
     *
     * @param nombre  nombre del perfil
     * @param edad    edad del perfil
     */
    public void onPerfilSelected(String nombre, int edad) {

        // Intent para enviar el resultado
        Intent intent = new Intent();

        // Poner los datos del perfil
        intent.putExtra(ARG_PERFIL_NOMBRE, nombre);  // Nombre
        intent.putExtra(ARG_PERFIL_EDAD, edad);      // Edad

        // Fijar resultado OK
        setResult(RESULT_OK, intent);

        // Finalizar esta activity
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

        // Tomar datos actuales del perfil, y establecer fragment
        if(savedInstanceState == null) {

            // Tomar extras
            Bundle extras = getIntent().getExtras();

            // Tomar nombre del perfil
            perfilNombre = extras.getString(PerfilActivity.ARG_PERFIL_NOMBRE);

            // Tomar edad del perfil
            perfilEdad = extras.getInt(PerfilActivity.ARG_PERFIL_EDAD);

            // Comenzar transacción para establecer el fragment
            FragmentTransaction ft = getFragmentManager().beginTransaction();

            // Crear y fijar el fragment
            ft.add(R.id.fragmentPerfil, PerfilFragment.newInstance(perfilNombre, perfilEdad));

            // Finalizar transacción
            ft.commit();
        }
    }
}
