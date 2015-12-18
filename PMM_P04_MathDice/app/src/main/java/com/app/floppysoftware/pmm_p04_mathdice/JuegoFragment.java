package com.app.floppysoftware.pmm_p04_mathdice;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


/**
 * Clase que implementa el juego MathDice mediante un fragment.
 *
 * @author  Miguel I. García López
 * @version 1.0
 * @since   11 Nov 2015
 */
public class JuegoFragment extends Fragment {

    // ImageView de los dados
    private ImageView [] imageViewDadosTres  = new ImageView [2];
    private ImageView [] imageViewDadosSeis  = new ImageView [3];
    private ImageView imageViewDodecaedro;

    // ImageView de los botones de suma y resta
    private ImageView imageViewSuma;
    private ImageView imageViewResta;

    // TextView de la operación y resultado
    private TextView textViewOperacion;
    private TextView textViewResultado;

    // Id. de los recursos de las imágenes para los dados
    private int [] recursoIdDadosTres = new int [] {R.drawable.dado_3_1, R.drawable.dado_3_2, R.drawable.dado_3_3};
    private int [] recursoIdDadosSeis = new int [] {R.drawable.dado_6_1, R.drawable.dado_6_2, R.drawable.dado_6_3,
            R.drawable.dado_6_4, R.drawable.dado_6_5, R.drawable.dado_6_6 };
    private int [] recursoIdDodecaedro = new int [] {R.drawable.dode_1, R.drawable.dode_2, R.drawable.dode_3, R.drawable.dode_4,
            R.drawable.dode_5, R.drawable.dode_6, R.drawable.dode_7, R.drawable.dode_8, R.drawable.dode_9,
            R.drawable.dode_10, R.drawable.dode_11, R.drawable.dode_12};

    // Valores de los dados
    private int [] valorDadosTres = new int [2];
    private int [] valorDadosSeis = new int [3];
    private int valorDodecaedro;

    // Generador de números aleatorios
    private Random random = new Random();

    // Operandos
    private final int OPERANDO_INDEFINIDO = -9999;
    private int valorOperando1;
    private int valorOperando2;

    // Operación
    private final int OPERACION_INDEFINIDA = -9999;
    private final int OPERACION_SUMA = 0;
    private final int OPERACION_RESTA = 1;
    private String [] stringOperacion = new String [] {"+", "-"};
    private int valorOperacion;

    // Resultado
    private final int RESULTADO_INDEFINIDO = -9999;
    private int valorResultado;

    // Flag que indica si se ha mostrado el nuevo resultado acumulado
    private Boolean flagResultadoVisto;

    /* No implementado *****************************
    // Lístener para interactuar con la activity
    private OnFragmentInteractionListener mListener;

    // Lístener a implementar por la activity
    public interface OnFragmentInteractionListener {

        // Método a implementar
        public void onFragmentInteraction(Uri uri);
    }
    ***********************************************/

    /**
     * Método para crear una nueva instancia del fragment.
     *
     * @return  nueva instancia del fragment
     */
    public static JuegoFragment newInstance() {

        // Crear fragment
        JuegoFragment fragment = new JuegoFragment();

        /* No implementado *****************************
        // Bundle para argumentos de entrada al fragment
        Bundle args = new Bundle();

        // Poner los parámetros de entrada
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);

        // Poner los argumentos
        fragment.setArguments(args);
        ***********************************************/

        // Devolver nueva instancia del fragment
        return fragment;
    }

    /**
     * Constructor público vacío requerido
     */
    public JuegoFragment() {
        //
    }

    /**
     * Método llamado al crear el fragment.
     *
     * @param savedInstanceState  estado previamente guardado
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        // Llamar a la superclase
        super.onCreate(savedInstanceState);

        /* No implementado ********************************
        // Obtener argumentos de entrada
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        **************************************************/
    }

    /**
     * Método llamado al crear la interfaz de usuario por 1ª vez.
     *
     * @param inflater   layout inflater
     * @param container  contenedor del layout
     * @param savedInstanceState  estado previamente guardado
     *
     * @return  layout del fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflar y devolver el layout del fragment
        return  inflater.inflate(R.layout.fragment_juego, container, false);
    }

    /**
     * Método llamado cuando el fragment es enlazado a la activity.
     *
     * @param activity  activity
     */
    @Override
    public void onAttach(Activity activity) {

        // Llamar a la superclase
        super.onAttach(activity);

        /* No implementado ******************************************************
        // Comprobar que la activity ha implementado el lístener para comunicarse
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
           throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        *************************************************************************/
    }

    /**
     * Método llamado cuando el fragment es desenlazado de la activity.
     */
    @Override
    public void onDetach() {

        // Llamar a la superclase
        super.onDetach();

        /* No implementado *******
        // Invalidar el lístener
        mListener = null;
        *************************/
    }

    /**
     * Método llamado cuando la activity ha sido creada.
     *
     * @param savedInstanceState  estado previamente guardado
     */
    @Override
    public void onActivityCreated (Bundle savedInstanceState) {

        // Llamar a la superclase
        super.onActivityCreated(savedInstanceState);

        // Tomar las referencias de los ImageView de los dados (valores del 1 al 3)
        imageViewDadosTres[0] = (ImageView) getActivity().findViewById(R.id.imageViewDadoTres1);
        imageViewDadosTres[1] = (ImageView) getActivity().findViewById(R.id.imageViewDadoTres2);

        // Fijar el lístener de los dados
        for(int i = 0; i < imageViewDadosTres.length; ++i) {
            imageViewDadosTres[i].setOnClickListener(onClickDadoTres);
        }

        // Tomar las referencias de los ImageView de los dados (valores del 1 al 6)
        imageViewDadosSeis[0] = (ImageView) getActivity().findViewById(R.id.imageViewDadoSeis1);
        imageViewDadosSeis[1] = (ImageView) getActivity().findViewById(R.id.imageViewDadoSeis2);
        imageViewDadosSeis[2] = (ImageView) getActivity().findViewById(R.id.imageViewDadoSeis3);

        // Fijar el lístener de los dados
        for(int i = 0; i < imageViewDadosSeis.length; ++i) {
            imageViewDadosSeis[i].setOnClickListener(onClickDadoSeis);
        }

        // Tomar la referencia del ImageView del dodecaedro
        imageViewDodecaedro = (ImageView) getActivity().findViewById(R.id.imageViewDodecaedro);

        // Fijar el lístener del dodecaedro
        imageViewDodecaedro.setOnClickListener(onClickDodecaedro);

        // Tomar la referencia del ImageView del botón de suma
        imageViewSuma = (ImageView) getActivity().findViewById(R.id.imageViewBotonSuma);

        // Fijar el lístener del botón de suma
        imageViewSuma.setOnClickListener(onClickSuma);

        // Tomar la referencia del ImageView del botón de resta
        imageViewResta = (ImageView) getActivity().findViewById(R.id.imageViewBotonResta);

        // Fijar el lístener del botón de resta
        imageViewResta.setOnClickListener(onClickResta);

        // Tomar la referencia del TextView de la operación a realizar
        textViewOperacion = (TextView) getActivity().findViewById(R.id.textViewOperacion);

        // Tomar la referencia del TextView del resultado a mostrar
        textViewResultado = (TextView) getActivity().findViewById(R.id.textViewResultado);

        // Inicializar todos los datos del juego
        resetPartida();

        // Mostrar los cambios
        mostrarOperacion();
    }

    /**
     * Inicializar todos los datos de la partida.
     */
    private void resetPartida() {

        // Generar nuevos valores para los dados del 1 al 3
        for(int i = 0; i < valorDadosTres.length; ++i) {

            // Generar valor (1 ... 3) y asignarlo
            valorDadosTres[i] = random.nextInt(3) + 1; // random.nextInt(3) devuelve un nº de 0 a 2

            // Modificar imagen, de acuerdo al valor del dado
            imageViewDadosTres[i].setImageResource(recursoIdDadosTres[valorDadosTres[i] - 1]);
        }

        // Generar nuevos valores para los dados del 1 al 6
        for(int i = 0; i < valorDadosSeis.length; ++i) {

            // Tomar nuevo valor
            valorDadosSeis[i] = random.nextInt(6) + 1; // random.nextInt(6) devuelve un nº de 0 a 5

            // Modificar imagen, de acuerdo al valor del dado
            imageViewDadosSeis[i].setImageResource(recursoIdDadosSeis[valorDadosSeis[i] - 1]);
        }

        // Generar nuevo valor para el dodecaedro del 1 al 12
        valorDodecaedro = random.nextInt(12) + 1; // random.nextInt(12) devuelve un nº de 0 a 11

        // Modificar imagen del dodecaedro, de acuerdo a su valor
        imageViewDodecaedro.setImageResource(recursoIdDodecaedro[valorDodecaedro - 1]);

        // Inicializar operación a realizar
        resetOperacion();

        // Inicializar resultado
        valorResultado = RESULTADO_INDEFINIDO;
    }

    /**
     * Inicializar la operación a realizar. No inicializa el
     * resultado, puesto que puede estar siendo acumulado con
     * operaciones previas.
     */
    private void resetOperacion() {
        // Operandos
        valorOperando1 = OPERANDO_INDEFINIDO;
        valorOperando2 = OPERANDO_INDEFINIDO;

        // Operación
        valorOperacion = OPERACION_INDEFINIDA;

        // Inicializar el flag de operación realizada
        flagResultadoVisto = false;
    }

    /**
     * Mostrar la operación a realizar (que puede estar incompleta),
     * y el nuevo resultado acumulado (si es posible).
     *
     */
    private void mostrarOperacion() {

        // Texto de la operación
        String operacion = "";

        // Flag que valdrá true en caso de que la operación esté completa, o
        // false en caso contrario.
        Boolean hayResultado = true;

        // Primer operando: generar texto a mostrar
        if(valorOperando1 != OPERANDO_INDEFINIDO) {
            // Hay operando: mostrar su valor
            operacion = String.valueOf(valorOperando1);
        } else {
            // No hay operando: indicarlo
            operacion = "_";
            // Operación incompleta
            hayResultado = false;
        }

        // Operador: generar texto a mostrar
        if(valorOperacion != OPERACION_INDEFINIDA) {
            // Hay operador: mostrarlo
            operacion = operacion.concat(stringOperacion[valorOperacion]);
        } else {
            // No hay operador: indicarlo
            operacion = operacion.concat("[]");
            // Operación incompleta
            hayResultado = false;
        }

        // Segundo operando: texto a mostrar
        if(valorOperando2 != OPERANDO_INDEFINIDO) {
            // Hay operando: mostrar su valor
            operacion = operacion.concat(String.valueOf(valorOperando2));
        } else {
            // No hay operando: indicarlo
            operacion = operacion.concat("_");
            // Operación incompleta
            hayResultado = false;
        }

        // Mostrar el texto de la operación a realizar
        textViewOperacion.setText(operacion);

        // Si la operación está completa, calcular el nuevo resultado acumulado,
        // si no se ha hecho ya.
        if(hayResultado && !flagResultadoVisto) {

            // Inicializar el resultado, si no hay resultado previo
            if(valorResultado == RESULTADO_INDEFINIDO) {
                valorResultado = 0;
            }

            // Realizar operación, y acumular el resultado
            if(valorOperacion == OPERACION_SUMA) {
                // Suma
                valorResultado += valorOperando1 + valorOperando2;
            } else {
                // Resta
                valorResultado += valorOperando1 - valorOperando2;
            }

            // Indicar que hay nuevo resultado acumulado
            flagResultadoVisto = true;
        }

        // Generar texto con el resultado
        String resultado = valorResultado == RESULTADO_INDEFINIDO ? "_" : String.valueOf(valorResultado);

        // Mostrar el resultado
        textViewResultado.setText(resultado);

        // Mostrar mensaje si el resultado coincide con el valor del dodecaedro
        if(valorResultado == valorDodecaedro) {

            Toast.makeText(getActivity(), R.string.partida_math_dice, Toast.LENGTH_SHORT).show();  // FIXME
        }

        // FIXME: La partida debería terminar y comenzar otra.
    }

    /**
     * Pone un operando en la operación a realizar. Si el primer operando está disponible,
     * lo pone ahí, de lo contrario, lo pone en el segundo operando, si está disponible.
     *
     * @param valor  valor del operando
     *
     * @return true si se pudo poner el operando, false en caso contrario
     */
    private boolean ponOperando(int valor) {

        // Valor a devolver (por defecto: fracaso)
        boolean exito = false;

        // Si hay una operación completa previa,
        // inicializar la operación a realizar
        if(flagResultadoVisto) {

            resetOperacion();
        }

        // Buscar un operando libre
        if(valorOperando1 == OPERANDO_INDEFINIDO) {

            // El primer operando está libre, poner el valor aquí
            valorOperando1 = valor;

            // Mostrar los cambios
            mostrarOperacion();

            // Éxito
            exito = true;

        } else if(valorOperando2 == OPERANDO_INDEFINIDO) {

            // El segundo operando está libre, poner el valor aquí
            valorOperando2 = valor;

            // Mostrar los cambios
            mostrarOperacion();

            // Éxito
            exito = true;
        }

        // Devolver éxito o fracaso
        return exito;
    }

    /**
     * Poner la operación.
     *
     * @param operacion  operación
     */
    private void ponOperacion(int operacion) {

        // Si hay una operación previa completa, inicializar la operación
        if(flagResultadoVisto) {

            resetOperacion();
        }

        // Cambiar la operación
        valorOperacion = operacion;

        // Mostrar los cambios
        mostrarOperacion();
    }

    /**
     * Lístener para los dados que generan valores del 1 al 3.
     */
    private View.OnClickListener onClickDadoTres = new View.OnClickListener() {

        /**
         * Método llamado al hacer click en el dado.
         *
         * @param v  view del dado
         */
        @Override
        public void onClick(View v) {

            // Valor a devolver
            int valor = -1;

            // Recuperar valor del dado
            for (int i = 0; i < valorDadosTres.length; ++i) {

                // Tomar valor del dado, y salir del bucle
                if (imageViewDadosTres[i] == v) {
                    valor = valorDadosTres[i];
                    break;
                }
            }

            // Mostrar el valor del dado
            // Toast.makeText(getActivity(), "Dado rojo: " + valor, Toast.LENGTH_SHORT).show();

            // Poner el operando
            ponOperando(valor);

            // FIXME: ponOperando devuelve true si se pudo poner el operando
            //
            // Se puede utilizar para deshabilitar el dado clickado
        }
    };

    /**
     * Lístener para los dados que generan valores del 1 al 6.
     */
    private View.OnClickListener onClickDadoSeis = new View.OnClickListener() {

        /**
         * Método llamado al hacer click en el dado.
         *
         * @param v  view del dado
         */
        @Override
        public void onClick(View v) {

            // Valor a devolver
            int valor = -1;

            // Recuperar valor del dado
            for (int i = 0; i < valorDadosSeis.length; ++i) {

                // Tomar valor del dado, y salir del bucle
                if (imageViewDadosSeis[i] == v) {
                    valor = valorDadosSeis[i];
                    break;
                }
            }

            // Mostrar el valor del dado
            // Toast.makeText(getActivity(), "Dado azul: " + valor, Toast.LENGTH_SHORT).show();

            // Poner el operando
            ponOperando(valor);

            // FIXME: ponOperando devuelve true si se pudo poner el operando
            //
            // Se puede utilizar para deshabilitar el dado clickado
        }
    };

    /**
     * Lístener para el dodecaedro.
     */
    private View.OnClickListener onClickDodecaedro = new View.OnClickListener() {

        /**
         * Método llamado al hacer click en el dodecaedro.
         *
         * @param v  view del dodecaedro
         */
        @Override
        public void onClick(View v) {

            // Toast.makeText(getActivity(), "Dodecaedro: " + valorDodecaedro, Toast.LENGTH_SHORT).show();

            // Inicializar todos los datos del juego
            resetPartida();

            // Mostrar los cambios
            mostrarOperacion();
        }
    };

    /**
     * Lístener para el botón de suma.
     */
    private View.OnClickListener onClickSuma = new View.OnClickListener() {

        /**
         * Método llamado al hacer click en el botón de suma.
         *
         * @param v  view del botón
         */
        @Override
        public void onClick(View v) {

            // Toast.makeText(getActivity(), "Suma", Toast.LENGTH_SHORT).show();

            // Poner la operación
            ponOperacion(OPERACION_SUMA);
        }
    };

    /**
     * Lístener para el botón de resta.
     */
    private View.OnClickListener onClickResta = new View.OnClickListener() {

        /**
         * Método llamado al hacer click en el botón de resta.
         *
         * @param v  view del botón
         */
        @Override
        public void onClick(View v) {

            // Toast.makeText(getActivity(), "Resta", Toast.LENGTH_SHORT).show();

            // Poner la operación
            ponOperacion(OPERACION_RESTA);
        }
    };
}


