package com.app.floppysoftware.r01_rava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Random;

/**
 * Clase que implementa el juego.
 */
public class JuegoActivity extends AppCompatActivity implements BotonesFragment.OnBotonesFragmentInteractionListener {

    // Número de colores
    private static final int NUM_COLORES = 4;

    // Puntos a acumular en los aciertos
    private static final int PUNTOS_ADICIONALES = 5;

    // Número de mezclas a realizar con los botones
    private static final int NUM_MEZCLAS = 8;

    // Número de color a averiguar
    private int numColor = 0;

    // Número de color asignado a cada botón
    private int [] colorBotones = new int [] {0, 1, 2, 3};

    // Imágenes asignadas a cada color
    private int coloresDrwId [] = new int [] {R.drawable.boton_rojo, R.drawable.boton_amarillo,
            R.drawable.boton_verde, R.drawable.boton_azul};

    // Nombres asignados a cada color
    private int coloresStrId [] = new int [] {R.string.color_rojo, R.string.color_amarillo,
            R.string.color_verde, R.string.color_azul};

    // Número de puntos acumulados
    private int puntos = 0;

    // Generador de números aleatorios
    private Random genRandom = new Random();

    // Fragment del texto del color
    ColorFragment colorFragment;

    // Fragment de los botones
    BotonesFragment botonesFragment;

    // Fragment del texto de los puntos
    PuntosFragment puntosFragment;

    /**
     * Método llamado por el fragment, para indicar el
     * número de botón pulsado.
     *
     * @param boton  Botón (0 ... 3)
     */
    public void onBotonPulsado(int boton) {

        // Comprobar la jugada
        compruebaJugada(boton);
    }

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
        setContentView(R.layout.activity_juego);

        // Tomar referencias de los fragments
        colorFragment = (ColorFragment) getFragmentManager().findFragmentById(R.id.fragmentColor);
        botonesFragment = (BotonesFragment) getFragmentManager().findFragmentById(R.id.fragmentBotones);
        puntosFragment = (PuntosFragment) getFragmentManager().findFragmentById(R.id.fragmentPuntos);

        // Comenzar partida nueva
        nuevaPartida();
    }

    /**
     * Comenzar una nueva partida
     */
    private void nuevaPartida() {

        // Nuevo número de color
        int nuevoColor;

        // Asegurarse de que el nuevo número de color es
        // distinto al actual, para no repetir.
        do {

            // Color (0 ... NUM_COLORES - 1)
            nuevoColor = genRandom.nextInt(NUM_COLORES);

        } while(nuevoColor == numColor);

        // Tomar nuevo número de color
        numColor = nuevoColor;

        // Mezclar los colores de los botones
        for(int i = 0; i < NUM_MEZCLAS; ++i) {

            // Obtener 2 botones
            int botonUno = genRandom.nextInt(NUM_COLORES);
            int botonDos = genRandom.nextInt(NUM_COLORES);

            // Intercambiar sus colores
            int temp = colorBotones[botonUno];
            colorBotones[botonUno] = colorBotones[botonDos];
            colorBotones[botonDos] = temp;
        }

        // Actualizar nombre del color
        actualizarColor();

        // Actualizar los botones del fragment
        actualizarBotones();

        // Actualizar puntos
        actualizarPuntos();
    }

    /**
     * Actualizar el nombre del color en el fragment.
     */
    private void actualizarColor() {

        // Actualizar el nombre del color
        colorFragment.setNombreColor(coloresStrId[numColor]);
    }

    /**
     * Actualizar los colores de los botones en el fragment.
     */
    private void actualizarBotones() {

        // Actualizar los colores de los botones
        botonesFragment.setImagenBotones(coloresDrwId[colorBotones[0]], coloresDrwId[colorBotones[1]],
                coloresDrwId[colorBotones[2]], coloresDrwId[colorBotones[3]]);
    }

    /**
     * Actualizar los puntos en el fragment
     */
    private void actualizarPuntos() {

        // Actualizar los puntos en el fragment
        puntosFragment.setPuntos(puntos);
    }

    /**
     * Comprobar la jugada, y sumar puntos en caso de acierto.
     *
     * @param boton
     */
    private void compruebaJugada(int boton) {

        // Comprobar si hubo acierto
        if(numColor == colorBotones[boton]) {

            // Sumar puntos
            puntos += PUNTOS_ADICIONALES;
        }

        // Otra partida
        nuevaPartida();
    }
}
