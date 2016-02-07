package com.app.floppysoftware.pmm_p06b_mathdice;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Clase que implementa la opción INFORMACIÓN mediante un fragment.
 *
 * @author  Miguel I. García López
 * @version 1.0
 * @since   11 Nov 2015
 */
public class InformacionFragment extends Fragment {

    /**
     * Constructor público vacío requerido
     */
    public InformacionFragment() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_informacion, container, false);
    }
}
