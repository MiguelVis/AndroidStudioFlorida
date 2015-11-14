package com.app.floppysoftware.pmm_p03_mathdice;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Clase que implementa el fragment inicial. Su único propósito es mostrar una pantalla
 * de bienvenida.
 *
 * @author  Miguel I. García López
 * @version 1.0
 * @since   11 Nov 2015
 */
public class InicialFragment extends Fragment {

    // Argumentos para inicializar el fragment
    private static final String ARG_LOGO_ID = "ARG_LOGO_ID";
    private static final String ARG_TEXTO_ID = "ARG_TEXTO_ID";

    /**
     * Método para crear una nueva instancia del fragment.
     *
     * @param logoId   imagen a mostrar
     * @param textoId  texto a mostrar
     * @return  fragment instanciado
     */
    public static InicialFragment newInstance(int logoId, int textoId) {

        // Instanciar el fragment
        InicialFragment fragment = new InicialFragment();

        // Crear bundle para los argumentos
        Bundle args = new Bundle();

        // Poner argumento: imagen
        args.putInt(ARG_LOGO_ID, logoId);

        // Poner argumento: texto
        args.putInt(ARG_TEXTO_ID, textoId);

        // Poner los argumentos
        fragment.setArguments(args);

        // Devolver fragment instanciado
        return fragment;
    }

    /**
     * Constructor público vacío requerido
     */
    public InicialFragment() {
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
       return inflater.inflate(R.layout.fragment_inicial, container, false);
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

        // Fijar imagen del logo, según argumentos de entrada
        ImageView logo = (ImageView) getActivity().findViewById(R.id.imageViewLogoFloppySoftware);
        logo.setImageResource(getArguments().getInt(ARG_LOGO_ID));

        // Fijar texto del logo, según argumentos de entrada
        TextView texto = (TextView) getActivity().findViewById(R.id.textViewNombreAlumno);
        texto.setText(getArguments().getInt(ARG_TEXTO_ID));
    }
}
