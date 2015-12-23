package com.app.floppysoftware.pmm_p04_mathdice;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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

    // Request Codes para Intents
    private static final int REQUEST_CODE_PERFIL = 1;  // Perfil

    // Fragment activo en el área de trabajo
    private Fragment fragmentEnArea = null;

    // Fragments para las opciones
    private PerfilFragment perfilFragment = null;
    private JuegoFragment juegoFragment = null;
    private InstruccionesFragment instruccionesFragment = null;
    private InformacionFragment informacionFragment = null;

    // Datos del perfil
    private String perfilNombre = "Elvis";  // Nombre
    private int perfilEdad = 43;            // Edad

    // Tamaño de pantalla del dispositivo
    private boolean pantallaGrande = false;

    /**
     * Este método será llamado a través del fragment del menú,
     * cuando se seleccione una opción.
     *
     * @param position  posición de la opción en el menú (0...X)
     */
    public void onOptionSelected(int position) {

        //Toast.makeText(this, "Opción: " + position, Toast.LENGTH_SHORT).show();

        // Cambiar el fragment del área de trabajo,
        // según la opción seleccionada
        switch(position) {
            case OPCION_PERFIL : // PERFIL

                // Ejecutar una activity en el caso de pantallas normales,
                // o cambiar el fragment en el caso de tablets.
                if(pantallaGrande) {

                    // Crear fragment si no está creado ya
                    if(perfilFragment == null) {
                        perfilFragment = PerfilFragment.newInstance(perfilNombre, perfilEdad);
                    }

                    // Cambiar el fragment
                    fijaFragmentArea(perfilFragment);
                } else {

                    // Crear intent
                    Intent intent = new Intent(this, PerfilActivity.class);

                    // Enviarle los datos actuales del perfil
                    intent.putExtra(PerfilActivity.ARG_PERFIL_NOMBRE, perfilNombre);  // Nombre
                    intent.putExtra(PerfilActivity.ARG_PERFIL_EDAD, perfilEdad);      // Edad

                    // Ejecutar activity que devolverá el perfil introducido
                    startActivityForResult(intent, REQUEST_CODE_PERFIL);
                }
                break;
            case OPCION_JUEGO : // JUEGO

                // Ejecutar una activity en el caso de pantallas normales,
                // o cambiar el fragment en el caso de tablets.
                if(pantallaGrande) {

                    // Crear fragment si no está creado ya
                    if (juegoFragment == null) {
                        juegoFragment = new JuegoFragment();
                    }

                    // Cambiar el fragment
                    fijaFragmentArea(juegoFragment);
                } else {

                    // Crear intent
                    Intent intent = new Intent(this, JuegoActivity.class);

                    // Ejecutar activity
                    startActivity(intent);
                }

                break;
            case OPCION_INSTRUCCIONES : // INSTRUCCIONES

                // Ejecutar una activity en el caso de pantallas normales,
                // o cambiar el fragment en el caso de tablets.
                if(pantallaGrande) {

                    // Crear fragment si no está creado ya
                    if (instruccionesFragment == null) {
                        instruccionesFragment = new InstruccionesFragment();
                    }

                    // Cambiar el fragment
                    fijaFragmentArea(instruccionesFragment);
                } else {

                    // Crear intent
                    Intent intent = new Intent(this, InstruccionesActivity.class);

                    // Ejecutar activity
                    startActivity(intent);
                }
                break;
            case OPCION_INFORMACION : // INFORMACIÓN

                // Ejecutar una activity en el caso de pantallas normales,
                // o cambiar el fragment en el caso de tablets.
                if(pantallaGrande) {

                    // Crear fragment si no está creado ya
                    if (informacionFragment == null) {
                        informacionFragment = new InformacionFragment();
                    }

                    // Cambiar el fragment
                    fijaFragmentArea(informacionFragment);
                } else {

                    // Crear intent
                    Intent intent = new Intent(this, InformacionActivity.class);

                    // Ejecutar activity
                    startActivity(intent);
                }
                break;
            default :
                break;
        }
    }

    /**
     * Este método será llamado desde el fragment de solicitud del perfil,
     * con los datos introducidos por el usuario.
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
     * Método llamado cuando una activity ejecutada
     * mediante startActivityForResult() finaliza.
     *
     * @param requestCode  Código que identifica la petición
     * @param resultCode   Código del resultado
     * @param data         Intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Comprobar si es la activity del perfil
        if (requestCode == REQUEST_CODE_PERFIL && resultCode == RESULT_OK && data != null) {

            // Tomar datos
            perfilNombre = data.getStringExtra(PerfilActivity.ARG_PERFIL_NOMBRE);
            perfilEdad = data.getIntExtra(PerfilActivity.ARG_PERFIL_EDAD, 0);
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

        // Comprobar si la activity está utilizando el layout
        // para tablets.
        if (findViewById(R.id.fragmentArea) != null) {

            // El dispositivo es una tablet
            pantallaGrande = true;

            // Orientación apaisada
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

            // Si se está restaurando el estado, no hacer nada con
            // el fragment (ya está presente).
            if (savedInstanceState == null) {

                // Fijar el fragment inicial
                fijaFragmentArea(InicialFragment.newInstance(R.drawable.logo_floppy_software, R.string.alumno));
            }

        } else {

            // El dispositivo no es una tablet
            pantallaGrande = false;

            // Orientación vertical
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
