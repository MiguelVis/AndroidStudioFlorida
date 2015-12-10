package com.app.floppysoftware.r01_rava;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Clase que implementa la Splash Activity. Dará paso a la activity del juego.
 */
public class SplashActivity extends AppCompatActivity {

    /**
     * Método llamado al crear la activity.
     *
     * @param savedInstanceState   Estado guardado
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Llamada a la superclase
        super.onCreate(savedInstanceState);

        // Fijar layout
        setContentView(R.layout.activity_splash);

        // Crear timer de 3 segundos de duración total, y que haga
        // tick cada 1/2 segundo.
        CountDownTimer timer = new CountDownTimer(3000, 500) {

            /**
             * Método llamado en cada tick.
             *
             * @param millisUntilFinished  Milisegundos restantes
             */
            @Override
            public void onTick(long millisUntilFinished) {
                // Nada de momento
            }

            /**
             * Método llamado al transcurrir el tiempo total, en el que
             * lanzaremos la activity del juego.
             */
            @Override
            public void onFinish() {

                // Crear intent
                Intent intent = new Intent(getApplicationContext(), JuegoActivity.class);

                // Lanzar activity
                startActivity(intent);

                // Finalizar esta activity, para que no se pueda volver a ella
                finish();
            }
        };

        // Poner en marcha el timer. Cuando finalice, se ejecutará
        // la activity del juego.
        timer.start();
    }
}
