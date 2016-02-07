package com.app.floppysoftware.pmm_p06b_mathdice;

/**
 * Clase que implementa una opción del menú principal.
 *
 * @author  Miguel I. García López
 * @version 1.0
 * @since   29 Oct 2015
 */
public class OpcionMenu {

    // Atributos
    private int imagenId;      // Imagen
    private int imagenBkgClr;  // Color de fondo de la imagen
    private int textoId;       // Texto
    private int textoBkgClr;   // Color de fondo del texto

    /**
     * Constructor.
     *
     * @param imagenId       Imagen
     * @param imagenBkgClr   Color de fondo de la imagen
     * @param textoId        Texto
     * @param textBkgClr     Color de fondo del texto
     */
    public OpcionMenu(int imagenId, int imagenBkgClr, int textoId, int textBkgClr) {

        // Fijar todos los atributos según parámetros de entrada
        this.imagenId = imagenId;
        this.imagenBkgClr = imagenBkgClr;
        this.textoId = textoId;
        this.textoBkgClr = textBkgClr;
    }

    /**
     * Devolver imagen.
     *
     * @return  Id de la imagen
     */
    public int getImagenId() {

        return this.imagenId;
    }

    /**
     * Devolver color de fondo de la imagen.
     *
     * @return  Color
     */
    public int getImagenBkgClr() {

        return this.imagenBkgClr;
    }

    /**
     * Devolver texto.
     *
     * @return  Id del texto
     */
    public int getTextoId() {

        return this.textoId;
    }

    /**
     * Devolver color de fondo del texto.
     *
     * @return  Color
     */
    public int getTextoBkgClr() {

        return this.textoBkgClr;
    }
}
