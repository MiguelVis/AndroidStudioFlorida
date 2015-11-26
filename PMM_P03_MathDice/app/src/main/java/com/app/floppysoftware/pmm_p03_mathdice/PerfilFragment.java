package com.app.floppysoftware.pmm_p03_mathdice;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Clase que implementa la opción del menú PERFIL, mediante un fragment.
 *
 * @author  Miguel I. García López
 * @version 1.1
 * @since   25 Nov 2015
 */
public class PerfilFragment extends Fragment {

    // Views de entrada de datos
    private EditText editTextNombre;  // Nombre
    private EditText editTextEdad;    // Edad

    // Botón para iniciar el juego
    private Button buttonJugar;

    // Lístener para interactuar con la activity
    private PerfilFragmentListener mListener;

    /**
     * Interfaz que ha de implementar la activity, para comunicarse
     * con este fragment.
     */
    public interface PerfilFragmentListener {

        // Método a implementar, para indicar los datos del perfil
        public void onPerfilSelected(String nombre, int edad);
    }

    /**
     * Método para crear una nueva instancia del fragment.
     *
     * @param nombre   nombre del perfil
     * @param edad     edad del perfil
     * @return  fragment instanciado
     */
    public static PerfilFragment newInstance(String nombre, int edad) {

        // Instanciar el fragment
        PerfilFragment fragment = new PerfilFragment();

        // Crear bundle para los argumentos
        Bundle args = new Bundle();

        // Poner argumento: nombre
        args.putString(PerfilActivity.ARG_PERFIL_NOMBRE, nombre);

        // Poner argumento: edad
        args.putInt(PerfilActivity.ARG_PERFIL_EDAD, edad);

        // Poner los argumentos
        fragment.setArguments(args);

        // Devolver fragment instanciado
        return fragment;
    }

    /**
     * Constructor público vacío requerido
     */
    public PerfilFragment() {
        //
    }

    /**
     * Método llamado al crear la interfaz de usuario por 1ª vez.
     *
     * @param inflater   layout inflater
     * @param container  contenedor del layout
     * @param savedInstanceState  estado previamente guardado
     * @return  layout del fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflar el layout del fragment
        return inflater.inflate(R.layout.fragment_perfil, container, false);
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

        // Comprobar que la activity ha implementado el lístener para comunicarse
        try {
            mListener = (PerfilFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " ha de implementar PerfilFragmentListener");
        }
    }

    /**
     * Método llamado cuando el fragment es desenlazado de la activity.
     */
    @Override
    public void onDetach() {

        // Llamar a la superclase
        super.onDetach();

        // Invalidar el lístener
        mListener = null;
    }

    /**
     * Método llamado cuando la activity ha sido creada.
     *
     * @param savedInstanceState  estado grabado previamente
     */
    @Override
    public void onActivityCreated (Bundle savedInstanceState) {

        // Llamar a la superclase
        super.onActivityCreated(savedInstanceState);

        // Tomar la referencia del campo de entrada del nombre
        editTextNombre = (EditText) getActivity().findViewById(R.id.editTextNombre);

        // Tomar la referencia del campo de entrada de la edad
        editTextEdad = (EditText) getActivity().findViewById(R.id.editTextEdad);

        // Tomar valores iniciales del perfil, si los hay (cuando se ejecuta
        // inicialmente después de la Splash Screen, no los hay).
        Bundle bundle = getArguments();

        if(bundle != null) {
            // Fijar el valor inicial del nombre
            editTextNombre.setText(getArguments().getString(PerfilActivity.ARG_PERFIL_NOMBRE));

            // Fijar el valor inicial de la edad
            editTextEdad.setText(String.valueOf(getArguments().getInt(PerfilActivity.ARG_PERFIL_EDAD)));
        }

        // Tomar la referencia del botón de inicio del juego
        buttonJugar = (Button) getActivity().findViewById(R.id.buttonJugar);

        // Fijar lístener del botón
        buttonJugar.setOnClickListener(new View.OnClickListener() {

            /**
             * Método llamado al hacer click en el botón.
             *
             * @param view  botón clickado
             */
            @Override
            public void onClick(View view) {

                // Tomar datos del perfil

                // Nombre
                String nombre = editTextNombre.getText().toString();

                // Edad
                int edad;

                // Tomar la edad, comprobando posibles excepciones en la conversión
                try {

                    // Tomar la edad
                    edad = Integer.parseInt(editTextEdad.getText().toString());

                } catch(NumberFormatException ex) {

                    // Valor erróneo, tomar valor por defecto
                    edad = 0;
                }

                // Enviar datos del perfil a la activity
                mListener.onPerfilSelected(nombre, edad);
            }
        });
    }
}
