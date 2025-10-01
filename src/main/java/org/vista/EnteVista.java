package org.vista;

import javafx.scene.image.Image;
import org.modelo.entidades.Ente;
import org.modelo.utilidades.Direccion;

import java.util.List;
import java.util.Map;

/**
 * Clase genérica para representar la vista de cualquier ente.
 * Conoce su animación por dirección y puede actualizar frames.
 */
public class EnteVista {

    protected final Ente ente;
    protected final Map<Direccion, List<Image>> animaciones;
    protected Direccion direccionActual = Direccion.ABAJO;
    protected int frameActual = 0;

    public EnteVista(Ente ente, Map<Direccion, List<Image>> animaciones) {
        this.ente = ente;
        this.animaciones = animaciones;
    }

    /**
     * Actualiza el frame de animación.
     */
    public void actualizar(double deltaTime) {
        frameActual++;
        if (ente.getDireccion() != null) {
            direccionActual = ente.getDireccion();
        }
    }

    /**
     * Devuelve el frame actual de la animación según la dirección.
     */
    public Image getFrameActual() {
        List<Image> frames = animaciones.getOrDefault(direccionActual, List.of());
        if (frames.isEmpty()) return null;
        return frames.get(frameActual % frames.size());
    }

    // Posición y dimensiones según el ente
    public double getX() { return ente.getPosicion().getPixelX(); }
    public double getY() { return ente.getPosicion().getPixelY(); }
    public double getAncho() { return ente.getDimensiones().getAncho(); }
    public double getAlto() { return ente.getDimensiones().getAlto(); }

    public Ente getEnte() { return ente; }
    public Direccion getDireccion() { return direccionActual; }
}
