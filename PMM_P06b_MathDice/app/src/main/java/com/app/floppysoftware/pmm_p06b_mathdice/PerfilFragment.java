package com.app.floppysoftware.pmm_p06b_mathdice;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * Clase que implementa la opción del menú PERFIL, mediante un fragment. En pantallas
 * grandes, se visualiza un mapa; en pantallas pequeñas, hay un botón que lanza el mapa.
 *
 * @author  Miguel I. García López
 * @version 1.3
 * @since   05 Feb 2016
 */
public class PerfilFragment extends Fragment implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener
        {

    // Tag para el log
    private static final String TAG = "PerfilFragment";

    // ID del extra de perfil
    public static final String ARG_PERFIL = "ARG_PERFIL";

    // Datos ficticios de localización para depuración en emuladores: -- FIXME
    // Sidney, Australia.
    private static final boolean FAKE_LOCALIZACION = true;
    private static final double FAKE_LATITUD = -34;
    private static final double FAKE_LONGITUD = 151;

    // Request code para el Intent de la cámara
    private static final int REQUEST_CODE_FOTO =  100;

    // Nombre del directorio para almacenar la foto del perfil
    public static final String DIRECTORIO_FOTO_PERFIL = "Perfil";

    // Nombre del fichero de la foto de perfil
    public static final String FICHERO_FOTO_PERFIL = "perfil.jpg";

    // Nombre del fichero de la foto de la cámara
    private static final String FICHERO_FOTO_CAMARA = "foto.jpg";

    // View de la foto
    private ImageView imageViewFoto;

    // Botón para tomar una foto con la cámara
    private ImageButton imageButtonCamara;

    // Views de entrada de datos
    private EditText editTextNombre;         // Nombre
    private EditText editTextApellidos;      // Apellidos
    private EditText editTextEdad;           // Edad
    private EditText editTextLocalizacion;   // Localización

    // Botón para guardar el perfil
    private Button buttonGuardar;

    // Botón para lanzar el mapa en pantallas pequeñas
    private Button buttonMapa;

    // Datos del perfil
    private Perfil perfil;

    // Variable que indica si el usuario ha hecho una foto
    private boolean hayFotoNueva = false;

    // File de la foto hecha con la cámara
    private File fileFotoNueva;

    // Cliente de Google Play services
    private GoogleApiClient mGoogleApiClient;

    // Última localización conocida
    private Location mLastLocation;

    // Indicador de tamaño de pantalla
    private boolean pantallaGrande;

    // Lístener para interactuar con la activity
    private PerfilFragmentListener mListener;

    /**
     * Interfaz que ha de implementar la activity, para comunicarse
     * con este fragment.
     */
    public interface PerfilFragmentListener {

        // Método a implementar, para indicar los datos del perfil
        public void onPerfilSelected(Perfil perfil);
    }

    /**
     * Método para crear una nueva instancia del fragment.
     *
     * @param perfil   datos del perfil
     * @return  fragment instanciado
     */
    public static PerfilFragment newInstance(Perfil perfil) {

        // Instanciar el fragment
        PerfilFragment fragment = new PerfilFragment();

        // Crear bundle para los argumentos
        Bundle args = new Bundle();

        // Poner perfil como argumento
        args.putSerializable(ARG_PERFIL, perfil);

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
     * Método llamado al crear el fragment.
     *
     * @param savedInstanceState  Estado previamente guardado
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        // Llamar siempre y en primer lugar al método de la super clase
        super.onCreate(savedInstanceState);

        // Terminar, si la inicialización ya fue realizada
        if(savedInstanceState != null) {
            return;
        }

        // Tomar argumentos enviados al fragment
        Bundle bundle = getArguments();

        // Tomar datos del perfil
        perfil = (Perfil) bundle.getSerializable(ARG_PERFIL);
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
        View v = inflater.inflate(R.layout.fragment_perfil, container, false);

        // Averiguar tipo de pantalla, según esté presente o no el fragment del mapa
        // en el layout.
        pantallaGrande = v.findViewById(R.id.fragmentMapa) != null;

        //
        Log.i(TAG, "pantallaGrande = " + pantallaGrande);

        // Devolver layout inflado
        return v;
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

        // Terminar, si la inicialización ya fue realizada
        if(savedInstanceState != null) {
            return;
        }

        // Tomar la referencia de la imagen de la foto
        imageViewFoto = (ImageView) getActivity().findViewById(R.id.imageViewFoto);

        // Tomar la referencia de los campos de entrada
        editTextNombre = (EditText) getActivity().findViewById(R.id.editTextNombre);
        editTextApellidos = (EditText) getActivity().findViewById(R.id.editTextApellidos);
        editTextEdad = (EditText) getActivity().findViewById(R.id.editTextEdad);
        editTextLocalizacion = (EditText) getActivity().findViewById(R.id.editTextLocalizacion);

        // Tomar la referencia del botón de la cámara
        imageButtonCamara = (ImageButton) getActivity().findViewById(R.id.imageButtonCamara);

        // Si el dispositivo tiene cámara, fijar el lístener del botón
        if(hayCamara()) {

            // Fijar lístener del botón
            imageButtonCamara.setOnClickListener(new View.OnClickListener() {

                /**
                 * Método llamado al hacer click en el botón. Tomar una foto.
                 *
                 * @param view botón clickado
                 */
                @Override
                public void onClick(View view) {

                    // Tomar una foto
                    tomarFoto();
                }
            });
        }
        else {

            // El dispositivo no tiene cámara, inhabilitar el botón
            imageButtonCamara.setEnabled(false);

            // Log
            Log.i(TAG, "El dispositivo no tiene cámara");
        }

        // Configuración dependiendo del tipo de pantalla
        if(!pantallaGrande) {

            // Pantalla pequeña

            // Tomar la referencia del botón para visualizar el mapa
            buttonMapa = (Button) getActivity().findViewById(R.id.buttonMaps);

            // Fijar el lístener del botón
            buttonMapa.setOnClickListener(new View.OnClickListener() {

                /**
                 * Método llamado al hacer click en el botón. Mostrar el mapa.
                 *
                 * @param view  botón clickado
                 */
                @Override
                public void onClick(View view) {

                    // Iniciar transacción
                    FragmentTransaction ft = getFragmentManager().beginTransaction();

                    // Reemplazar el fragment actual (éste), por el del mapa, creando
                    // un fragment de mapa y pasándole los datos de la localización.
                    ft.replace(R.id.fragmentPerfil, MapaFragment.newInstance(perfil.getLatitud(), perfil.getLongitud()));

                    // Añadir transacción al Back Stack, de manera que se pueda volver al
                    // fragment actual (éste).
                    ft.addToBackStack(null);

                    // Solicitar commit de la transacción
                    ft.commit();
                }
            });
        }

        // Tomar la referencia del botón para guardar las preferencias
        buttonGuardar = (Button) getActivity().findViewById(R.id.buttonGuardar);

        // Fijar el lístener del botón
        buttonGuardar.setOnClickListener(new View.OnClickListener() {

            /**
             * Método llamado al hacer click en el botón. Guardar las preferencias
             * e indicárselo a la activity.
             *
             * @param view  botón clickado
             */
            @Override
            public void onClick(View view) {

                // Modificar el perfil, según los datos introducidos
                savePerfil();

                // Comunicarlo a la activity
                mListener.onPerfilSelected(perfil);
            }
        });

        // Crear una instancia de Google API Client
        if (mGoogleApiClient == null) {

            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)          // Callbacks
                    .addOnConnectionFailedListener(this)   // Lístener de conexión fallida
                    .addApi(LocationServices.API)          // Solicitar servicios de localización
                    .build();
        }

        // Refrescar perfil en pantalla
        refreshPerfil();
    }

    /**
     * Método llamado tras onActivityCreated(), y tras onStop() > onRestart().
     */
    @Override
    public void onStart() {

        // Llamar a la superclase
        super.onStart();

        // Iniciar la conexión con Google Play services
        mGoogleApiClient.connect();
    }

    /**
     * Método llamado tras onPause() (al reanudar el fragment) u onStart().
     */
    @Override
    public void onResume() {

        // Llamar a la superclase
        super.onResume();

        // Nada de momento
    }

    /**
     * Método llamado al pausar el fragment.
     */
    @Override
    public void onPause() {

        // Llamar a la superclase
        super.onPause();

        // Nada de momento
    }

    /**
     * Método llamado al detener el fragment.
     */
    @Override
    public void onStop() {

        // Llamar a la superclase
        super.onStop();

        // Desconectar Google Play services
        if(mGoogleApiClient.isConnected()) {

            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Método llamado cuando se realice la conexión con Google Play services.
     *
     * @param bundle  datos adicionales
     */
    @Override
    public void onConnected(Bundle bundle) {

        // Log
        Log.i(TAG, "Conectado a Google Play services");

        // Solicitar datos de la última localización
        try {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
        } catch(SecurityException e ) {
            // Log
            Log.i(TAG, "Localización denegada");
        }

        // Comprobar si hay datos nuevos de localización
        if (mLastLocation != null) {

            // Sí, refrescar
            refreshLocalizacion();
        }
    }

    /**
     * Método llamado si se suspende la conexión con Google Play services.
     *
     * @param cause  código de la suspensión
     */
    @Override
    public void onConnectionSuspended(int cause) {

        // Log
        Log.i(TAG, "Suspendida conexión a Google Play services por: " + cause);
    }

    /**
     * Método llamado si la conexión no se pudo realizar con éxito.
     *
     * @param connectionResult  detalle
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        // Log
        Log.i(TAG, "Error en la conexión a Google Play services: " + connectionResult.getErrorMessage());
    }

    /**
     * Actualizar perfil en pantalla.
     */
    private void refreshPerfil() {

        // Fotografía
        refreshFoto();

        // Datos
        editTextNombre.setText(perfil.getNombre());
        editTextApellidos.setText(perfil.getApellidos());

        // Edad
        if(perfil.getEdad() != Perfil.EDAD_DESCONOCIDA) {
            editTextEdad.setText(String.valueOf(perfil.getEdad()));
        }

        // Localización
        refreshLocalizacion();

        //
        Log.i(TAG, "Perfil actualizado");
    }

    /**
     * Actualizar localización en pantalla y en el perfil.
     */
    private void refreshLocalizacion() {

        // Tomar localización nueva, si la hay
        if(mLastLocation != null) {

            perfil.setLatitud(mLastLocation.getLatitude());
            perfil.setLongitud(mLastLocation.getLongitude());

            // Log
            Log.i(TAG, "Nueva localización - latitud:  " + String.valueOf(perfil.getLatitud()));
            Log.i(TAG, "Nueva localización - longitud: " + String.valueOf(perfil.getLongitud()));

            // Invalidar datos
            mLastLocation = null;

        } else if(FAKE_LOCALIZACION) { // FIXME

            // Falsear localización
            perfil.setLatitud(FAKE_LATITUD);
            perfil.setLongitud(FAKE_LONGITUD);

            // Log
            Log.i(TAG, "Localización falseada");
        }

        // Actualizar en pantalla
        if(perfil.getLatitud() != Perfil.LOCALIZACION_DESCONOCIDA && perfil.getLongitud() != Perfil.LOCALIZACION_DESCONOCIDA) {

            // Hay datos de localización

            // Texto con los datos de localización
            String localizacion = getString(R.string.latitud_abreviada) + ":"
                + String.valueOf(perfil.getLatitud()) + ", "
                + getString(R.string.longitud_abreviada) + ":"
                + String.valueOf(perfil.getLongitud());

            // Mostrar localización
            editTextLocalizacion.setText(localizacion);

            // Actuar según tipo de pantalla
            if(pantallaGrande) {

                // Pantalla grande

                // Actualizar mapa
                refreshMapa();

            } else {

                // Pantalla pequeña

                // Habilitar botón del mapa
                if(!buttonMapa.isEnabled()) {
                    buttonMapa.setEnabled(true);
                }
            }

            // Log
            Log.i(TAG, "Localización actualizada");
        } else  {

            // No hay datos de localización

            // Actuar según tipo de pantalla
            if(!pantallaGrande) {

                // Pantalla pequeña

                // Deshabilitar botón del mapa
                if(buttonMapa.isEnabled()) {
                    buttonMapa.setEnabled(false);
                }
            }

            // Log
            Log.i(TAG, "No hay datos nuevos de localización");
        }
    }

    /**
     * Actualizar datos del perfil.
     */
    private void savePerfil() {

        // Si hay foto nueva, convertirla en la del perfil
        if(hayFotoNueva) {

            // Para comprobación de éxito
            boolean fotoGrabada = false;

            // Fichero de la foto del perfil
            File fotoPerfil = getImageFile(FICHERO_FOTO_PERFIL);

            // Proceder si no hubo errores, y podemos escribir
            // en el almacenamiento externo.
            if(fotoPerfil != null && isExternalStorageWritable()) {

                // Log
                Log.i(TAG, "Grabando foto de perfil");

                // Para comprobación de éxito de borrado
                boolean borrada = true;

                // Si ya hay una foto de perfil, eliminarla
                if(fotoPerfil.exists()) {
                    if(!fotoPerfil.delete()) {

                        // Log
                        Log.i(TAG, "No se pudo borrar la foto del perfil");

                        // Fracaso
                        borrada = false;
                    }
                }

                // Si no hay foto de perfil, convertir la foto
                // nueva en la de perfil.
                if(borrada) {

                    // Renombrar la foto nueva
                    if(fileFotoNueva.renameTo(fotoPerfil)) {

                        // Ya no hay foto nueva
                        hayFotoNueva = false;

                        // Fijar path de la foto de perfil
                        perfil.setFotografia(fotoPerfil.getPath());

                        // Éxito
                        fotoGrabada = true;
                    } else {

                        // Log
                        Log.i(TAG, "No se pudo renombrar la foto");
                    }
                }
            }

            // Comprobación de éxito o fracaso
            if(!fotoGrabada) {

                // Mensaje de error
                dialogoError(R.string.error_grabando_foto);
            }
        }

        // Fijar resto de datos del perfil
        perfil.setNombre(editTextNombre.getText().toString());
        perfil.setApellidos(editTextApellidos.getText().toString());

        // Edad
        String edadString = editTextEdad.getText().toString();
        perfil.setEdad(edadString.length() == 0 ? Perfil.EDAD_DESCONOCIDA : Integer.parseInt(editTextEdad.getText().toString()));

        // El perfil ya tiene actualizados los datos de localización,
        // por lo que no es necesario hacer nada aquí.
    }

    /**
     * Llamar a la cámara mediante un intent, para tomar una foto.
     */
    private void tomarFoto() {

        // Fichero de la foto
        fileFotoNueva = getImageFile(FICHERO_FOTO_CAMARA);

        // Comprobar que podemos seguir
        if(fileFotoNueva == null || !isExternalStorageWritable()) {

            // Log
            Log.i(TAG, "Error al acceder al almacenamiento externo");

            // Error
            dialogoError(R.string.error_almacenamiento);

            // Abortar
            return;
        }

        // Log
        Log.i(TAG, "Lanzando intent para la cámara");

        // Intent para llamar a la cámara y tomar una foto
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Fijar URI del fichero como argumento
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileFotoNueva));

        // Lanzar el intent
        startActivityForResult(intent, REQUEST_CODE_FOTO);
    }

    /**
     * Construir un fichero de foto en el almacenamiento externo, pero
     * que no se muestre en la galería, y que se elimine al desinstalar la app.
     *
     * @param nombreFichero  nombre del fichero
     * @return   fichero o null en caso de error
     */
    private File getImageFile(String nombreFichero){

        // Comprobar que tenemos acceso al almacenamiento externo
        if(!isExternalStorageWritable()) {

            // Abortar
            return null;
        }

        // Crear un File para el directorio
        File directorioFotos = new File(getActivity().getExternalFilesDir(
                Environment.DIRECTORY_PICTURES), DIRECTORIO_FOTO_PERFIL);

        // Crear el directorio (y directorio/s padre/s) si no existe
        if(!directorioFotos.exists()){
            if(!directorioFotos.mkdirs()){

                // Log
                Log.d(TAG, "No se pudo crear el directorio");

                // Fracaso
                return null;
            }
        }

        // Crear y devolver File para el fichero de la foto
        return new File(directorioFotos.getPath() + File.separator + nombreFichero);
    }

    /**
     * Método llamado tras finalizar un intent con devolución de resultados.
     *
     * @param requestCode   código de petición
     * @param resultCode    código de resultado
     * @param data          datos adicionales
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Comprobar si es el intent de la cámara
        if(requestCode == REQUEST_CODE_FOTO) {

            // Log
            Log.i(TAG, "Retorno del Intent de la cámara");

            // Comprobar si hubo éxito
            if(resultCode == Activity.RESULT_OK) {

                // Log
                Log.i(TAG, "Foto realizada");

                // Éxito
                hayFotoNueva = true;

                // Refrescar foto en pantalla
                refreshFoto();

            } else if (resultCode == Activity.RESULT_CANCELED) {

                // El usuario canceló la foto

                // Log
                Log.i(TAG, "Foto cancelada");

            } else {

                // Log
                Log.i(TAG, "Hubo algún error al realizar la foto");

                // Error
                dialogoError(R.string.error_haciendo_foto);
            }
        }
    }

    /**
     * Refrescar la foto en pantalla.
     */
    public void refreshFoto() {

        // Inner class, para tratar y cargar la foto en background
        class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {

            // ImageView de la foto
            private final WeakReference<ImageView> imageViewRef;

            // Path del fichero de la imagen
            private String path;

            /**
             * Constructor.
             *
             * @param imageView  imageView de la foto
             */
            public BitmapWorkerTask(ImageView imageView) {

                // Utilizar WeakReference para asegurar que el imageView puede
                // ser recolectado como basura.
                imageViewRef = new WeakReference<ImageView>(imageView);
            }

            /**
             * Tratar la imagen en background.
             *
             * @param params  parámetros de entrada
             * @return        bitmap resultante, o null
             */
            @Override
            protected Bitmap doInBackground(String... params) {

                // Tomar path de la imagen
                path = params[0];

                // Comprobar acceso al almacenamiento externo
                if(!isExternalStorageReadable()) {
                    return null;
                }

                // Generar una imagen reducida a partir de la foto, minimizando problemas
                // de memoria y rendimiento.

                // Bitmap de la foto
                Bitmap bmpFoto = null;

                // Log
                Log.i(TAG, "Redimensionando foto");

                // Opciones para BitmapFactory
                BitmapFactory.Options opts = new BitmapFactory.Options();

                // Obtener sólo dimensiones (no se carga en memoria, ni decodifica)
                opts.inJustDecodeBounds = true;

                // Solicitud
                BitmapFactory.decodeResource(getResources(), R.drawable.perfil_vacio, opts);

                // Averiguar dimensiones de la imagen del perfil vacío
                int perfAncho = opts.outWidth;
                int perfAlto = opts.outHeight;

                // Log
                Log.i(TAG, "Ancho perfil: " + perfAncho);
                Log.i(TAG, "Alto  perfil: " + perfAlto);

                // Solicitud
                BitmapFactory.decodeFile(path, opts);

                // Obtener dimensiones de la foto
                int fotoAncho = opts.outWidth;
                int fotoAlto = opts.outHeight;

                // Log
                Log.i(TAG, "Ancho foto: " + fotoAncho);
                Log.i(TAG, "Alto  foto: " + fotoAlto);

                // Calcular factor de reducción
                int factorAncho = fotoAncho / perfAncho;
                int factorAlto = fotoAlto / perfAlto;
                int factor = factorAncho > factorAlto ? factorAlto : factorAncho;

                // Log
                Log.i(TAG, "Factor ancho: " + factorAncho);
                Log.i(TAG, "Factor alto:  " + factorAlto);
                Log.i(TAG, "Factor calc.: " + factor);

                // Fijar opciones de decodificación
                opts.inJustDecodeBounds = false;  // Decodificar
                opts.inSampleSize = factor;       // Factor de reducción

                // Nota: El factor de reducción será redondeado por Android a la baja, como
                //       una potencia de 2.

                // Log
                Log.i(TAG, "Decodificando la imagen");

                // Decodificar imagen
                Bitmap bmpReducido = BitmapFactory.decodeFile(path, opts);

                // Log
                Log.i(TAG, "Ancho dec.: " + bmpReducido.getWidth());
                Log.i(TAG, "Alto  dec.: " + bmpReducido.getHeight());

                // Log
                Log.i(TAG, "Creando thumbnail");

                // Crear un thumbnail del tamaño de la imagen del perfil vacío
                bmpFoto = ThumbnailUtils.extractThumbnail(bmpReducido, perfAncho, perfAlto);

                // Log
                Log.i(TAG, "Thumbnail generado");

                Log.i(TAG, "Ancho thu.: " + bmpFoto.getWidth());
                Log.i(TAG, "Alto  thu.: " + bmpFoto.getHeight());

                // Devolver bitmap del thumbnail
                return bmpFoto;
            }

            /**
             * Una vez finalizado, cambiar la imagen del imageView de la foto.
             *
             * @param bmpFoto   bitmap
             */
            @Override
            protected void onPostExecute(Bitmap bmpFoto) {

                // Mostrar foto
                if(imageViewRef != null && bmpFoto != null) {

                    // Log
                    Log.i(TAG, "Cambiando foto");

                    // Hay foto, mostrarla
                    imageViewFoto.setImageBitmap(bmpFoto);

                } else {

                    // Log
                    Log.i(TAG, "Hubo algún error al tratar la foto");

                    // No hay foto, mostrar foto en blanco
                    imageViewFoto.setImageResource(R.drawable.perfil_vacio);
                }
            }
        }

        // Mostrar foto en pantalla, según la disponibilidad, por este orden:
        //
        // 1. Foto hecha con la cámara
        // 2. Foto actual del perfil
        // 3. Foto en blanco (en este caso, no es necesario hacer nada)

        // Log
        Log.i(TAG, "Refrescando foto");

        // Path de la foto
        String path;

        // Tomar path del fichero de la foto
        path = hayFotoNueva ? fileFotoNueva.getPath() : perfil.getFotografia();

        // Cambiar la foto
        if(path != null) {

            // Realizar el cambio en background
            BitmapWorkerTask task = new BitmapWorkerTask(imageViewFoto);
            task.execute(path);
        }
    }

    /**
     * Comprobar que tenemos acceso de lectura / escritura al almacenamiento externo
     *
     * @return  true en caso afirmativo; false en caso contrario
     */
    private boolean isExternalStorageWritable() {

        // Leer estado
        String state = Environment.getExternalStorageState();

        // Comprobar que está montado y se puede leer y escribir
        if (Environment.MEDIA_MOUNTED.equals(state)) {

            // Sí
            return true;
        }

        // Log
        Log.i(TAG, "Almacenamiento externo no disponible para escritura");

        // No
        return false;
    }

    /**
     * Comprobar que tenemos acceso de lectura al almacenamiento externo
     *
     * @return  true en caso afirmativo; false en caso contrario
     */
    private boolean isExternalStorageReadable() {

        // Leer estado
        String state = Environment.getExternalStorageState();

        // Comprobar que está montado y se puede leer
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {

            // Sí
            return true;
        }

        // Log
        Log.i(TAG, "Almacenamiento externo no disponible para lectura");

        // No
        return false;
    }

    /**
     * Comprobar que el dispositivo tiene una cámara.
     *
     * @return  true en caso afirmativo; false en caso contrario
     */
    private boolean hayCamara() {

        // Interrogar al sistema y devolver resultado
        return getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    /**
     * Refrescar mapa con los datos de localización actuales.
     */
    private void refreshMapa() {

        // Tomar referencia del fragment del mapa
        MapaFragment fragmentMapa = (MapaFragment) getChildFragmentManager().findFragmentById(R.id.fragmentMapa);

        // Indicarle los datos de localización
        fragmentMapa.localiza(perfil.getLatitud(), perfil.getLongitud());
    }

    /**
     * Mostrar un mensaje de error.
     *
     * @param texto   descripción del error
     */
    private void dialogoError(int texto) {

        // Crear y mostrar diálogo
        new AlertDialog.Builder(getActivity())
            .setTitle(R.string.dialogo_error_titulo)
            .setMessage(texto)
            .setCancelable(false)
            .setPositiveButton(R.string.dialogo_boton_ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Nada de nada
                }
            }).create().show();
    }
}
