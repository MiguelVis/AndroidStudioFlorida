package com.app.floppysoftware.pmm_p06_mathdice;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

/**
 * Clase que implementa la activity de solicitud de perfil,
 * en dispositivos que no sean tablets.
 *
 * @author  Miguel I. García López
 * @version 1.2
 * @since   31 Jan 2016
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
