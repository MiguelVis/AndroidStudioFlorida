package com.app.floppysoftware.r01_rava;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Clase que implementa el fragment que imprime el nombre del color.
 */
public class ColorFragment extends Fragment {

    // TextView del nombre del color
    private TextView textViewColor;

    /**
     * Constructor público vacío requerido.
     */
    public ColorFragment() {
        //
    }

    /**
     * Método utilizado por la activity principal, para
     * cambiar el texto del nombre del color.
     *
     * @param resId   Id de recurso
     */
    public void setNombreColor(int resId) {

        // Cambiar el texto
        textViewColor.setText(resId);
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
        View v = inflater.inflate(R.layout.fragment_color, container, false);

        // Tomar la referencia del TextView
        textViewColor = (TextView) v.findViewById(R.id.textViewColor);

        // Devolver layout inflado
        return v;
    }
}
