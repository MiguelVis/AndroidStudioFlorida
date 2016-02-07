package com.app.floppysoftware.pmm_p06b_mathdice;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Fragment que implementa el mapa.
 *
 * @author  Miguel I. García López
 * @version 1.1
 * @since   07 Feb 2016
 */
public class MapaFragment extends Fragment implements OnMapReadyCallback {

    // TAG para el log
    private static final String TAG = "MapaFragment";

    // IDs para los argumentos
    private static final String ARG_LATITUD = "ARG_LATITUD";
    private static final String ARG_LONGITUD = "ARG_LONGITUD";

    // Valor para indicar latitud / longitud desconocida
    private static final double LOCALIZACION_DESCONOCIDA = 9999;

    // Datos de localización
    private double latitud = LOCALIZACION_DESCONOCIDA;
    private double longitud = LOCALIZACION_DESCONOCIDA;

    // Fragment del mapa
    private MapFragment mapFragment;

    // Objeto para gestionar GoogleMap
    private GoogleMap mMap;

    /**
     * Crear instancia del fragment.
     *
     * @param latitud    latitud
     * @param longitud   longitud
     * @return   fragment
     */
    public static MapaFragment newInstance(double latitud, double longitud) {

        // Crear objeto fragment
        MapaFragment fragment = new MapaFragment();

        // Crear bundle para los argumentos
        Bundle args = new Bundle();

        // Fijar argumentos
        args.putDouble(ARG_LATITUD, latitud);
        args.putDouble(ARG_LONGITUD, longitud);

        // Poner los argumentos
        fragment.setArguments(args);

        // Devolver fragment
        return fragment;
    }

    /**
     * Constructor.
     */
    public MapaFragment() {

        // Nada
    }

    /**
     * Método llamado al crear el fragment.
     *
     * @param savedInstanceState  Estado previamente guardado
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        // Llamar al método de la superclase
        super.onCreate(savedInstanceState);

        // Terminar, si la inicialización ya fue realizada
        if(savedInstanceState != null) {
            return;
        }

        // Tomar argumentos enviados al fragment
        Bundle bundle = getArguments();

        // Tomar datos de localización, si los hay
        if (bundle != null) {

            latitud = bundle.getDouble(ARG_LATITUD);
            longitud = bundle.getDouble(ARG_LONGITUD);
        }
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

        // Inflar el layout del fragment
        View v = inflater.inflate(R.layout.fragment_mapa, container, false);

        // Inicializar fragment del mapa, si no lo está ya
        if(mapFragment == null) {

            // Crear objeto fragment de Google Maps
            mapFragment = new MapFragment();

            // Iniciar transacción
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();

            // Añadir
            ft.add(R.id.mapaGoogle, mapFragment).commit();

            // Tomar manejador del mapa, de manera asíncrona (cuando el
            // mapa esté listo, se llamará al método onMapReady().
            mapFragment.getMapAsync(this);
        }

        // Devolver layout inflado
        return v;
    }

    /**
     * Método que será llamado cuando el mapa esté listo. Refrescar
     * el mapa.
     *
     * @param googleMap   mapa
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        // Tomar referencia del mapa
        mMap = googleMap;

        // Refrescar mapa
        refreshMapa();
    }

    /**
     * Señalar localización en el mapa.
     *
     * @param latitud   latitud
     * @param longitud  longitud
     */
    public void localiza(double latitud, double longitud) {

        // Tomar datos
        this.latitud = latitud;
        this.longitud = longitud;

        // Refrescar mapa
        refreshMapa();
    }

    /**
     * Refrescar el mapa, señalando la localización actual.
     */
    private void refreshMapa() {

        // Abortar, si el mapa no está inicializado,
        // o la localización no está disponible.
        if(mMap == null || latitud == LOCALIZACION_DESCONOCIDA || longitud == LOCALIZACION_DESCONOCIDA) {
            return;
        }

        // Log
        Log.i(TAG, "Refrescando mapa");

        // Tomar datos de localización
        LatLng donde = new LatLng(latitud, longitud);

        // Crear título para InfoWindow
        String titulo = getString(R.string.mapa_marcador_titulo);

        // Crear texto adicional para InfoWindow
        String texto = getString(R.string.mapa_marcador_texto);

        // Crear y poner un marcador
        Marker marcador = mMap.addMarker(new MarkerOptions().position(donde).title(titulo).snippet(texto));

        // Visualizar InfoWindow
        marcador.showInfoWindow();

        // Mover la cámara del mapa, con un zoom de nivel 10 (ciudad)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(donde, 10));
    }
}
