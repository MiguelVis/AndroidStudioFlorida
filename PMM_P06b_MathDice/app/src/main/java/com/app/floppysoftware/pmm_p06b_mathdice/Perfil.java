package com.app.floppysoftware.pmm_p06b_mathdice;

import java.io.Serializable;

/**
 * Clase que implementa el perfil del usuario.
 *
 * @author  Miguel I. García López
 * @version 1.0
 * @since   28 Jan 2016
 */
public class Perfil implements Serializable {

    // Constantes
    public static final int EDAD_DESCONOCIDA = -1;
    public static final double LOCALIZACION_DESCONOCIDA = 999;

    // Datos del perfil del usuario
    private String nombre = null;
    private String apellidos = null;
    private int edad = EDAD_DESCONOCIDA;
    private String fotografia = null;
    private double latitud = LOCALIZACION_DESCONOCIDA;
    private double longitud = LOCALIZACION_DESCONOCIDA;

    /**
     * Constructor.
     */
    public Perfil() {
        //
    }

    /**
     * Constructor.
     *
     * @param nombre       nombre
     * @param apellidos    apellidos
     * @param edad         edad
     * @param fotografia   fotografía
     * @param latitud      latitud
     * @param longitud     longitud
     */
    public Perfil(String nombre, String apellidos, int edad, String fotografia, double latitud, double longitud) {

        // Fijar datos según parámetros de entrada
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.fotografia = fotografia;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    /**
     * Fijar nombre del usuario.
     *
     * @param nombre   nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Fijar apellidos del usuario.
     *
     * @param apellidos   apellidos
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Fijar edad del usuario.
     *
     * @param edad   edad
     */
    public void setEdad(int edad) {
        this.edad = edad;
    }

    /**
     * Fijar fotografía del usuario.
     *
     * @param fotografia   fotografía
     */
    public void setFotografia(String fotografia) {
        this.fotografia = fotografia;
    }

    /**
     * Fijar longitud de la localización.
     *
     * @param longitud   longitud
     */
    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    /**
     * Fijar latitud de la localización.
     *
     * @param latitud   latitud
     */
    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    /**
     * Devolver nombre del usuario.
     *
     * @return  nombre
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * Devolver apellidos del usuario.
     *
     * @return   apellidos
     */
    public String getApellidos() {
        return this.apellidos;
    }

    /**
     * Devolver edad del usuario.
     *
     * @return   edad
     */
    public int getEdad() {
        return this.edad;
    }

    /**
     * Devolver fotografía del perfil.
     *
     * @return   fotografía
     */
    public String getFotografia() {
        return this.fotografia;
    }

    /**
     * Devolver longitud de la localización.
     *
     * @return   longitud
     */
    public double getLongitud() {
        return this.longitud;
    }

    /**
     * Devolver latitud de la localización.
     *
     * @return   latitud
     */
    public double getLatitud() {
        return this.latitud;
    }
}
