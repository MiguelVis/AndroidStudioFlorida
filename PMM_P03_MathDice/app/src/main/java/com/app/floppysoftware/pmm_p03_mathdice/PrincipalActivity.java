package com.app.floppysoftware.pmm_p03_mathdice;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * Clase que implementa la pantalla principal mediante fragments.
 *
 * @author  Miguel I. García López
 * @version 1.3
 * @since   10 Nov 2015
 */
public class PrincipalActivity extends Activity implements MenuFragment.MenuFragmentListener {

    // Tag para el log
    private static final String TAG = "PrincipalActivity";

    // Fragment activo en el área de trabajo
    private Fragment fragmentEnArea = null;

    // Fragments para las opciones
    PerfilFragment perfilFragment = null;
    JuegoFragment juegoFragment = null;
    InstruccionesFragment instruccionesFragment = null;
    InformacionFragment informacionFragment = null;

    /**
     * Este método será llamado a través del fragment del menú,
     * cuando se seleccione una opción.
     *
     * @param position  posición de la opción en el menú (0...X)
     */
    public void onOptionSelected(int position) {

        //Toast.makeText(this,"Opción: " + position,Toast.LENGTH_SHORT).show();

        // Cambiar el fragment del área de trabajo,
        // según la opción seleccionada
        switch(position) {
            case 0 : // PERFIL
                if(perfilFragment == null) {
                    perfilFragment = new PerfilFragment();
                }
                fijaFragmentArea(perfilFragment);
                break;
            case 1 : // JUEGO
                // Crear fragment si no está creado ya
                if(juegoFragment == null) {
                    juegoFragment = new JuegoFragment();
                }

                // Cambiar el fragment
                fijaFragmentArea(juegoFragment);
                break;
            case 2 : // INSTRUCCIONES
                if(instruccionesFragment == null) {
                    instruccionesFragment = new InstruccionesFragment();
                }
                fijaFragmentArea(instruccionesFragment);
                break;
            case 3 : // INFORMACIÓN
                if(informacionFragment == null) {
                    informacionFragment = new InformacionFragment();
                }
                fijaFragmentArea(informacionFragment);
                break;
            default :
                break;
        }
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

        // Llamar siempre y en primer lugar al método de la super clase
        super.onCreate(savedInstanceState);

        // Establecer layout
        setContentView(R.layout.activity_principal);

        // Log
        Log.i(TAG, "onCreate");

        // Comprobar que la activity está utilizando el layout
        // con el fragment del área de trabajo.
        if (findViewById(R.id.fragmentArea) != null) {

            // Si se está restaurando el estado, no hacer nada con
            // el fragment (ya está presente).
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            //InicialFragment firstFragment = new InicialFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            //firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            //getFragmentManager().beginTransaction()
                    //.add(R.id.fragmentArea, firstFragment).commit();

            // Fijar el fragment inicial
            fijaFragmentArea(InicialFragment.newInstance(R.drawable.logo_floppy_software, R.string.alumno));
        }
    }

    /**
     * Establecer el fragment en el área de trabajo.
     *
     * @param f  fragment
     */
    private void fijaFragmentArea(Fragment f) {

        // No hacer nada si dicho fragment ya está en el
        // área de trabajo.
        if(fragmentEnArea != f) {

            // Comenzar transacción
            FragmentTransaction ft = getFragmentManager().beginTransaction();

            // Fijar el fragment, según ya haya uno (replace), o no (add).
            if (fragmentEnArea == null) {
                ft.add(R.id.fragmentArea, f);
            } else {
                ft.replace(R.id.fragmentArea, f);
            }

            // Finalizar transacción
            ft.commit();

            // Recordar el fragment actualmente activo
            fragmentEnArea = f;
        }
    }
}
