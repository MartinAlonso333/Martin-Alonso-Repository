package org.vista;

import javafx.scene.image.Image;
import org.modelo.entidades.tanques.Tanque;
import org.modelo.utilidades.Direccion;

import java.util.List;
import java.util.Map;

public class TanqueVista extends EnteVista {

    private final Tanque tanque;

    public TanqueVista(Tanque tanque, Map<Direccion, List<Image>> animaciones) {
        super(tanque, animaciones);
        this.tanque = tanque;
    }

    @Override
    public void actualizar(double deltaTime) {
        Direccion nuevaDir = tanque.getDireccion();
        if (nuevaDir != direccionActual) {
            direccionActual = nuevaDir;
            frameActual = 0;
        }
        super.actualizar(deltaTime);
    }
}
