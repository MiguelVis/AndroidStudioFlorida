package com.app.floppysoftware.pmm_p06b_mathdice;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

/**
 * Clase que implementa la activity de solicitud de perfil,
 * en dispositivos que no sean tablets.
 *
 * @author  Miguel I. García López
 * @version 1.3
 * @since   07 Feb 2016
 */
public class PerfilActivity extends Activity implements PerfilFragment.PerfilFragmentListener {

    // ID del extra de perfil
    public static final String ARG_PERFIL = "ARG_PERFIL";

    /**
     * Este método será llamado desde el fragment de solicitud del perfil,
     * con los datos introducidos por el usuario. Esta activity devolverá
     * esos mismos datos a la activity que la llamó.
     *
     * @param perfil  nuevo perfil
     */
    public void onPerfilSelected(Perfil perfil) {

        // Intent para enviar el resultado
        Intent intent = new Intent();

        // Poner los datos del perfil
        intent.putExtra(PerfilActivity.ARG_PERFIL, perfil);

        // Fijar resultado OK
        setResult(RESULT_OK, intent);

        // Finalizar esta activity
        finish();
    }

    /**
     * Este método será llamado desde el fragment de perfil, cuando
     * el usuario pulse el botón del mapa. Solo para pantallas
     * pequeñas tipo móvil. Lanza el fragment del mapa.
     *
     * @param latitud   latitud
     * @param longitud  longitud
     */
    public void onClickMapaMovil(double latitud, double longitud) {

        // Iniciar transacción
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        // Crear y añadir un fragment de mapa, pasándole los datos de la localización.
        ft.add(R.id.fragmentPerfilMovil, MapaFragment.newInstance(latitud, longitud));

        // Añadir transacción al Back Stack, de manera que se pueda volver al
        // fragment anterior (el de perfil), al pulsar la tecla de Back.
        ft.addToBackStack(null);

        // Solicitar commit de la transacción
        ft.commit();
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

            // Tomar datos del perfil
            Perfil perfil = (Perfil) extras.getSerializable(PerfilActivity.ARG_PERFIL);

            // Comenzar transacción para establecer el fragment
            FragmentTransaction ft = getFragmentManager().beginTransaction();

            // Crear y fijar el fragment
            ft.add(R.id.fragmentPerfilMovil, PerfilFragment.newInstance(perfil));

            // Finalizar transacción
            ft.commit();
        }
    }
}
