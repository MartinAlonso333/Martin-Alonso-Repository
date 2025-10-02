package org.vista;

import javafx.scene.image.Image;
import org.modelo.entidades.Ente;
import org.modelo.entidades.TipoEnte;
import org.modelo.entidades.tanques.Tanque;
import org.modelo.utilidades.Direccion;

import java.util.List;
import java.util.Map;

/**
 * Vista de un ente del juego. Maneja animación y rotación según dirección si aplica.
 */

public class EnteVista {

    protected final Ente ente;
    protected final Map<Direccion, List<Image>> animaciones; // animaciones por dirección
    protected Direccion direccionActual = Direccion.ABAJO;
    protected int frameActual = 0;
    private double tiempoAcumulado = 0; // para animación fluida

    public EnteVista(Ente ente, Map<Direccion, List<Image>> animaciones) {
        this.ente = ente;
        this.animaciones = animaciones;
    }

    /**
     * Actualiza la animación.
     * @param deltaTime en segundos
     */
    public void actualizar(double deltaTime) {
        Direccion nuevaDir = ente.getDireccion();
        if (nuevaDir != null && nuevaDir != direccionActual) {
            direccionActual = nuevaDir;
            frameActual = 0;
            tiempoAcumulado = 0;
        }

        // Animación solo si es un tipo de tanque
        if (ente.getTipoEnte() == TipoEnte.ENEMIGO || ente.getTipoEnte() == TipoEnte.JUGADOR) {
            if (ente.estaMoviendo()) {
                List<Image> frames = animaciones.getOrDefault(direccionActual, List.of());
                if (!frames.isEmpty() && frames.size() > 1) {
                    tiempoAcumulado += deltaTime;
                    if (tiempoAcumulado >= 0.2) {
                        frameActual = (frameActual + 1) % frames.size();
                        tiempoAcumulado = 0;
                    }
                }
            } else {
                frameActual = 0;
            }
        }
}
    /**
     * Obtiene el frame actual, rotado si corresponde.
     */
    public Image getFrameActual() {
        List<Image> frames = animaciones.getOrDefault(
                (ente.getDireccion() != null) ? direccionActual : Direccion.ABAJO,
                List.of()
        );
        if (frames.isEmpty()) return null;
        return frames.get(frameActual);
    }

    // Posición y dimensiones
    public double getX() { return ente.getPosicion().getPixelX(); }
    public double getY() { return ente.getPosicion().getPixelY(); }
    public double getAncho() { return ente.getDimensiones().getAncho(); }
    public double getAlto() { return ente.getDimensiones().getAlto(); }

    public Ente getEnte() { return ente; }
    public Direccion getDireccion() { return direccionActual; }
}
