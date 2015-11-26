package com.app.floppysoftware.pmm_p03_mathdice;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * Clase que implementa la pantalla principal mediante fragments.
 *
 * @author  Miguel I. García López
 * @version 1.4
 * @since   25 Nov 2015
 */
public class PrincipalActivity extends Activity implements MenuFragment.MenuFragmentListener, PerfilFragment.PerfilFragmentListener {

    // Tag para el log
    private static final String TAG = "PrincipalActivity";

    // Opciones del menú
    private static final int OPCION_PERFIL = 0;
    private static final int OPCION_JUEGO = 1;
    private static final int OPCION_INSTRUCCIONES = 2;
    private static final int OPCION_INFORMACION = 3;

    // Fragment activo en el área de trabajo
    private Fragment fragmentEnArea = null;

    // Fragments para las opciones
    PerfilFragment perfilFragment = null;
    JuegoFragment juegoFragment = null;
    InstruccionesFragment instruccionesFragment = null;
    InformacionFragment informacionFragment = null;

    // Datos del perfil
    String perfilNombre;  // Nombre
    int perfilEdad;       // Edad

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
            case OPCION_PERFIL : // PERFIL
                // Cambiar el fragment
                fijarPerfil();
                break;
            case OPCION_JUEGO : // JUEGO
                // Crear fragment si no está creado ya
                if(juegoFragment == null) {
                    juegoFragment = new JuegoFragment();
                }

                // Cambiar el fragment
                fijaFragmentArea(juegoFragment);
                break;
            case OPCION_INSTRUCCIONES : // INSTRUCCIONES
                // Crear fragment si no está creado ya
                if(instruccionesFragment == null) {
                    instruccionesFragment = new InstruccionesFragment();
                }

                // Cambiar el fragment
                fijaFragmentArea(instruccionesFragment);
                break;
            case OPCION_INFORMACION : // INFORMACIÓN
                // Crear fragment si no está creado ya
                if(informacionFragment == null) {
                    informacionFragment = new InformacionFragment();
                }

                // Cambiar el fragment
                fijaFragmentArea(informacionFragment);
                break;
            default :
                break;
        }
    }

    /**
     * Este método será llamado desde el fragment de solicitud del perfil,
     * con los datos introducidos.
     *
     * @param nombre  nombre del perfil
     * @param edad    edad del perfil
     */
    public void onPerfilSelected(String nombre, int edad) {

        // Actualización de los datos del perfil
        perfilNombre = nombre;  // Nombre
        perfilEdad = edad;      // Edad

        // Comprobación visual
        Toast.makeText(this, "Perfil: '" + perfilNombre + "' de " + perfilEdad + " años de edad", Toast.LENGTH_SHORT).show();
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

        // Tomar los datos del perfil, enviados por la activity que llama a ésta
        if(savedInstanceState == null) {

            // Tomar extras
            Bundle extras = getIntent().getExtras();

            // Tomar nombre del perfil
            perfilNombre = extras.getString(PerfilActivity.ARG_PERFIL_NOMBRE);

            // Tomar edad del perfil
            perfilEdad = extras.getInt(PerfilActivity.ARG_PERFIL_EDAD);

            // Comprobación visual
            Toast.makeText(this, "Perfil: '" + perfilNombre + "' de " + perfilEdad + " años de edad", Toast.LENGTH_SHORT).show();
        }

        // Comprobar que la activity está utilizando el layout
        // con el fragment del área de trabajo.
        if (findViewById(R.id.fragmentArea) != null) {

            // Si se está restaurando el estado, no hacer nada con
            // el fragment (ya está presente).
            if (savedInstanceState != null) {
                return;
            }

            // Fijar el fragment inicial
            // fijaFragmentArea(InicialFragment.newInstance(R.drawable.logo_floppy_software, R.string.alumno));

            // Fijar el fragment inicial (perfil)
            fijarPerfil();
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

    /**
     * Establece el fragment del perfil en el área de trabajo.
     */
    private void fijarPerfil() {
        // Crear fragment si no está creado ya
        if(perfilFragment == null) {
            perfilFragment = PerfilFragment.newInstance(perfilNombre, perfilEdad);
        }

        // Cambiar el fragment
        fijaFragmentArea(perfilFragment);
    }
}
