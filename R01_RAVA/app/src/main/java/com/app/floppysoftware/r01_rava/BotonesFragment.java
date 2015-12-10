package com.app.floppysoftware.r01_rava;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Clase que implementa el fragment de los botones de color.
 */
public class BotonesFragment extends Fragment {

    // ImageViews de los botones de color
    private ImageView botonUno;
    private ImageView botonDos;
    private ImageView botonTres;
    private ImageView botonCuatro;

    // Lístener a implementar por la activity
    private OnBotonesFragmentInteractionListener mListener;

    /**
     * Lístener a implementar por la activity, para comunicarse
     * con el fragment.
     */
    public interface OnBotonesFragmentInteractionListener {

        /**
         * Método a llamar, cuando se pulse un botón de color.
         *
         * @param numero  Numero del botón pulsado
         */
        public void onBotonPulsado(int numero);
    }

    /**
     * Constructor público vacío requerido.
     */
    public BotonesFragment() {
        //
    }

    /**
     * Método utilizado por la activity, para cambiar la imagen de los botones.
     *
     * @param resIdUno
     * @param resIdDos
     * @param resIdTres
     * @param resIdCuatro
     */
    public void setImagenBotones(int resIdUno, int resIdDos, int resIdTres, int resIdCuatro) {

        // Fijar las imágenes de cada botón
        botonUno.setImageResource(resIdUno);
        botonDos.setImageResource(resIdDos);
        botonTres.setImageResource(resIdTres);
        botonCuatro.setImageResource(resIdCuatro);
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
        View v = inflater.inflate(R.layout.fragment_botones, container, false);

        // Tomar las referencias de los ImageView de los botones
        botonUno = (ImageView) v.findViewById(R.id.imageViewBoton1);
        botonDos = (ImageView) v.findViewById(R.id.imageViewBoton2);
        botonTres = (ImageView) v.findViewById(R.id.imageViewBoton3);
        botonCuatro = (ImageView) v.findViewById(R.id.imageViewBoton4);

        // Crear lístener para los botones
        OnClickBotonListener onClickBotonListener = new OnClickBotonListener();

        // Enlazar botones con el lístener
        botonUno.setOnClickListener(onClickBotonListener);
        botonDos.setOnClickListener(onClickBotonListener);
        botonTres.setOnClickListener(onClickBotonListener);
        botonCuatro.setOnClickListener(onClickBotonListener);

        // Devolver layout inflado
        return v;
    }

    /**
     * Método llamado al enlazar el fragment con la activity.
     *
     * @param activity  Activity
     */
    @Override
    public void onAttach(Activity activity) {

        // Llamar a la superclase
        super.onAttach(activity);

        // Comprobar que la activity ha implementado el callback
        try {
            mListener = (OnBotonesFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnBotonesFragmentInteractionListener");
        }
    }

    /**
     * Método llamado al desenlazar el fragment de la actitivy.
     */
    @Override
    public void onDetach() {

        // Llamar a la superclase
        super.onDetach();

        // Invalidar el callback
        mListener = null;
    }

    /**
     * Clase que implementa el lístener de los botones.
     */
    private class OnClickBotonListener implements Button.OnClickListener {

        /**
         * Método llamado al hacer click en un botón. Indica a la
         * activity el botón pulsado.
         *
         * @param v  View del botón
         */
        public void onClick(View v) {

            // Indicar a la activity el botón pulsado
            if(v == botonUno)
                mListener.onBotonPulsado(0);
            else if(v == botonDos)
                mListener.onBotonPulsado(1);
            else if(v == botonTres)
                mListener.onBotonPulsado(2);
            else
                mListener.onBotonPulsado(3);
        }
    }
}
