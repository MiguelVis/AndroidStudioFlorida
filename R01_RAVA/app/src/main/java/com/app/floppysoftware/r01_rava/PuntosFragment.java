package com.app.floppysoftware.r01_rava;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Clase que implementa el fragment que imprime el número de puntos.
 */
public class PuntosFragment extends Fragment {

    // TextView del número de puntos
    private TextView textViewPuntos;

    /**
     * Constructor público vacío requerido.
     */
    public PuntosFragment() {
        //
    }

    /**
     * Método utilizado por la activity principal, para
     * cambiar el texto del número de puntos.
     *
     * @param puntos   Puntos
     */
    public void setPuntos(int puntos) {

        // Cambiar el texto
        textViewPuntos.setText(String.valueOf(puntos));
    }

    /**
     * Método llamado al crear el fragment.
     *
     * @param savedInstanceState  Estado guardado
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        // Llamar a la superclase
        super.onCreate(savedInstanceState);
    }

    /**
     * Método llamado al crear el View.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflar el layout del fragment
        View v = inflater.inflate(R.layout.fragment_puntos, container, false);

        // Tomar la referencia del TextView
        textViewPuntos = (TextView) v.findViewById(R.id.textViewPuntos);

        // Devolver layout inflado
        return v;
    }
}
