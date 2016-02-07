package com.app.floppysoftware.pmm_p06b_mathdice;

import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Implementación de una Splash Screen.
 *
 * Ésta será la activity de inicio de la aplicación, que dará paso
 * a la activity principal.
 *
 * Durante 3 segundos, se mostrará una barra de progreso, que indicará
 * el tiempo transcurrido desde el inicio de la aplicación / tiempo
 * restante para que se inicie la otra activity.
 *
 * Se ha desarrollado de manera que si la activity entra en pausa o es parada,
 * y luego es restablecida, continúe contando a partir del tiempo que ya había
 * transcurrido. Es decir, que no vuelva a comenzar a contar desde cero.
 *
 * @author  Miguel I. García López
 * @version 1.3
 * @since   18 Dec 2015
 */
public class SplashActivity extends Activity {

    // Tag para el log
    private static final String TAG = "SplashActivity";

    // Clave para recuperar el tiempo transcurrido si la aplicación es parada y restablecida
    private static final String KEY_TIEMPO_TRANSCURRIDO = "TiempoTranscurrido";

    // Tiempo total en milisegundos, durante el cual se mostrará la Splash Screen
    private static final long DURACION_TOTAL = 3000;

    // Duración en milisegundos de cada paso, para actualizar la barra de progreso
    private static final long DURACION_PASO = 125;

    // Contador de tiempo transcurrido
    private long tiempoTranscurrido;

    // Barra de progreso, para mostrar tiempo transcurrido / restante
    private ProgressBar barraProgreso;

    // Timer utilizado para medir el tiempo transcurrido
    private Timer timer;

    /**
     * Método llamado al crear la activity. También es llamado si se cambia
     * la orientación de la pantalla del dispositivo, pues realmente la
     * activity es destruida y vuelta a recrear.
     *
     * @param savedInstanceState  Estado anterior de la activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Llamada al método de la super clase (es lo primero que se ha de hacer siempre)
        super.onCreate(savedInstanceState);

        /* =============================================================================================
           El siguiente código da error en tiempo de ejecución:

           requestWindowFeature(Window.FEATURE_NO_TITLE);

           android.util.AndroidRuntimeException: requestFeature() must be called before adding content

           No entiendo por qué, ya que -en principio- no se da la causa que indica el mensaje de error,
           puesto que el código original era:

           super.onCreate(savedInstanceState);
           requestWindowFeature(Window.FEATURE_NO_TITLE);
           setContentView(R.layout.activity_splash);

           Quizás sea un problema derivado del uso de la librería de compatibilidad.

           Como alternativa, se ha creado un tema adicional para uso exclusivo de esta activity,
           y se ha indicado en el manifiesto.
           ============================================================================================ */

        // Establecer layout
        setContentView(R.layout.activity_splash);

        // Log
        Log.i(TAG, "onCreate");

        // Si la activity se está recreando, recuperar los valores previamente guardados
        if (savedInstanceState != null) {
            // Recuperar el tiempo ya transcurrido
            tiempoTranscurrido = savedInstanceState.getLong(KEY_TIEMPO_TRANSCURRIDO);
        }
        // Si la activity se crea por primera vez, inicializar algunas cosas
        else {
            // Establecer el tiempo ya transcurrido a cero
            tiempoTranscurrido = 0;
        }

        // Tomar la referencia del objeto de la barra de progreso
        barraProgreso = (ProgressBar) findViewById(R.id.progressBar);
    }

    /**
     * Método que se llama al crear la actividad por primera vez tras onStart(),
     * o después de salir de una pausa tras onPause().
     *
     * Establecer el timer que gestiona el tiempo transcurrido y la barra
     * de progreso.
     */
    @Override
    protected void onResume() {

        // Llamar siempre al método de la super clase
        super.onResume();

        // Log
        Log.i(TAG, "onResume");

        // Timer utilizado para contar el tiempo y actualizar la barra de progreso
        timer = new Timer();

        // Iniciar la tarea asociada que se ejecutará por primera vez
        // tras DURACION_PASO milisegundos, y se repetirá cada DURACION_PASO milisegundos.
        timer.schedule(new EjecutaPaso(), DURACION_PASO, DURACION_PASO);
    }

    /**
     * Método que se llama al entrar la activity en pausa.
     *
     * Cancelar el timer y la tarea asociada.
     */
    @Override
    protected void onPause() {

        // Llamar siempre al método de la super clase
        super.onPause();

        // Log
        Log.i(TAG, "onPause");

        // Cancelar el timer y la tarea asociada
        cancelarTimer();
    }

    /**
     * Grabar el estado de la activity.
     *
     * @param savedInstanceState  Bundle para grabar los datos
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        // Log
        Log.i(TAG, "onSaveInstanceState");

        // Grabar tiempo transcurrido
        savedInstanceState.putLong(KEY_TIEMPO_TRANSCURRIDO, tiempoTranscurrido);

        // Llamar siempre al método de la super clase
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * Cancelar el timer y la tarea asociada.
     */
    private void cancelarTimer() {

        // Log
        Log.i(TAG, "cancelarTimer");

        // Cancelar el timer y la tarea asociada
        timer.cancel();

        // Purgar tareas del timer
        timer.purge();
    }

    /**
     * Clase que implementa una tarea del timer.
     *
     * Dicha tarea gestiona el tiempo transcurrido, actualiza la barra de progreso,
     * y ejecuta la otra activity (y finaliza ésta) si se ha llegado al final del tiempo total.
     */
    private class EjecutaPaso extends TimerTask {

        /**
         * Método llamado en cada paso de tiempo.
         *
         * Actualiza la barra de progreso.
         *
         * Cuando haya transcurrido el tiempo total, se iniciará la otra activity,
         * y finalizará ésta.
         */
        @Override
        public void run() {

            // Log
            Log.i(TAG, "EjecutaPaso = " + tiempoTranscurrido);

            // Incrementar el tiempo transcurrido
            tiempoTranscurrido += DURACION_PASO;

            // Ajustar al valor máximo si hay desbordamiento
            if(tiempoTranscurrido > DURACION_TOTAL) {
                tiempoTranscurrido = DURACION_TOTAL;
            }

            // Actualizar la barra de progreso
            barraProgreso.setProgress((int) (tiempoTranscurrido * 100 / DURACION_TOTAL));

            // Si ya ha transcurrido el tiempo total, iniciar la otra activity,
            // y finalizar ésta
            if(tiempoTranscurrido == DURACION_TOTAL) {
                // Log
                Log.i(TAG, "EjecutaPaso = iniciando la otra activity y finalizando ésta");

                // Cancelar el timer y purgar las tareas pendientes
                cancelarTimer();

                // Intent para ejecutar la otra activity
                Intent intent = new Intent(getApplicationContext(), PrincipalActivity.class);

                // Comenzar la otra activity
                startActivity(intent);

                // Finalizar esta activity, de forma que no se pueda volver a ella
                finish();
            }
        }
    }
}
