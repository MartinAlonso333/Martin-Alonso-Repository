package org.vista;

import javafx.scene.image.Image;
import org.modelo.entidades.Ente;
import org.modelo.utilidades.Direccion;

import java.util.List;
import java.util.Map;

public class EnteVista {

    private final Ente ente;
    private final Map<Direccion, List<Image>> animaciones;
    private Direccion direccionActual = Direccion.ABAJO;
    private int frameActual = 0;

    public EnteVista(Ente ente, Map<Direccion, List<Image>> animaciones) {
        this.ente = ente;
        this.animaciones = animaciones;
    }

    public void actualizar(double deltaTime) {
        frameActual++;
    }

    public Image getFrameActual() {
        List<Image> frames = animaciones.getOrDefault(direccionActual, List.of());
        if (frames.isEmpty()) return null;
        return frames.get(frameActual % frames.size());
    }

    public double getX() {
        return ente.getPosicion().getPixelX();
    }

    public double getY() {
        return ente.getPosicion().getPixelY();
    }

    public double getAncho() {
        return ente.getDimensiones().getAncho();
    }

    public double getAlto() {
        return ente.getDimensiones().getAlto();
    }

    public Ente getEnte() {
        return ente;
    }

    public void setDireccion(Direccion dir) {
        if (dir != null) {
            this.direccionActual = dir;
        }
    }

    public Direccion getDireccion() {
        return direccionActual;
    }
}
