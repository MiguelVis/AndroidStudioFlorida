package com.app.floppysoftware.pmm_p06_mathdice;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Clase que gestiona la persistencia de las preferencias.
 *
 * @author  Miguel I. García López
 * @version 1.0
 * @since   03 Feb 2016
 */
public final class Preferencias {

    // TAG para el log
    private static final String TAG = "Preferencias";

    // Fichero de preferencias
    private static final String FICHERO_PREFERENCIAS = "mathdice";

    // Claves para preferencias
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_APELLIDOS = "apellidos";
    private static final String KEY_EDAD = "edad";
    private static final String KEY_FOTOGRAFIA = "fotografia";
    private static final String KEY_LATITUD = "latitud";
    private static final String KEY_LONGITUD = "longitud";

    /**
     * Constructor que impide crear objetos con esta clase.
     */
    private Preferencias() {

        //
    }

    /**
     * Leer los datos del perfil desde el fichero de preferencias.
     *
     * @return  perfil
     */
    public static Perfil loadPerfil(Context context) {

        // Tomar handle del fichero de preferencias
        SharedPreferences prefs = context.getSharedPreferences(FICHERO_PREFERENCIAS, Activity.MODE_PRIVATE);

        // Crear objeto perfil a partir de las preferencias guardadas
        Perfil perf = new Perfil(prefs.getString(KEY_NOMBRE, null),
                prefs.getString(KEY_APELLIDOS, null),
                prefs.getInt(KEY_EDAD, Perfil.EDAD_DESCONOCIDA),
                prefs.getString(KEY_FOTOGRAFIA, null),
                Double.longBitsToDouble(prefs.getLong(KEY_LATITUD, Double.doubleToRawLongBits(Perfil.LOCALIZACION_DESCONOCIDA))),
                Double.longBitsToDouble(prefs.getLong(KEY_LONGITUD, Double.doubleToRawLongBits(Perfil.LOCALIZACION_DESCONOCIDA))));

        // Log
        Log.i(TAG, "Perfil cargado - Nombre:    " + perf.getNombre());
        Log.i(TAG, "Perfil cargado - Apellidos: " + perf.getApellidos());
        Log.i(TAG, "Perfil cargado - Edad:      " + perf.getEdad());
        Log.i(TAG, "Perfil cargado - Fotograf.: " + perf.getFotografia());
        Log.i(TAG, "Perfil cargado - Latitud:   " + perf.getLatitud());
        Log.i(TAG, "Perfil cargado - Longitud:  " + perf.getLongitud());

        // Devolver perfil
        return perf;
    }

    /**
     * Escribir los datos del perfil en el fichero de preferencias.
     *
     * @param perf  perfil
     */
    public static void savePerfil(Context context, Perfil perf) {

        // Tomar handle del fichero de preferencias
        SharedPreferences prefs = context.getSharedPreferences(FICHERO_PREFERENCIAS, Activity.MODE_PRIVATE);

        // Tomar editor
        SharedPreferences.Editor editor = prefs.edit();

        // Escribir los datos del perfil
        editor.putString(KEY_NOMBRE, perf.getNombre());
        editor.putString(KEY_APELLIDOS, perf.getApellidos());
        editor.putInt(KEY_EDAD, perf.getEdad());
        editor.putString(KEY_FOTOGRAFIA, perf.getFotografia());
        editor.putLong(KEY_LONGITUD, Double.doubleToRawLongBits(perf.getLongitud()));
        editor.putLong(KEY_LATITUD, Double.doubleToRawLongBits(perf.getLatitud()));

        // Persistir los cambios
        editor.commit();

        // Log
        Log.i(TAG, "Perfil guardado - Nombre:    " + perf.getNombre());
        Log.i(TAG, "Perfil guardado - Apellidos: " + perf.getApellidos());
        Log.i(TAG, "Perfil guardado - Edad:      " + perf.getEdad());
        Log.i(TAG, "Perfil guardado - Fotograf.: " + perf.getFotografia());
        Log.i(TAG, "Perfil guardado - Latitud:   " + perf.getLatitud());
        Log.i(TAG, "Perfil guardado - Longitud:  " + perf.getLongitud());
    }
}
