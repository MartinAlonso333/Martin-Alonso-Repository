package org.vista;


import javafx.scene.canvas.GraphicsContext;
import org.modelo.entidades.Ente;
import org.modelo.entidades.tanques.Tanque;
import org.modelo.utilidades.Direccion;

import javafx.scene.image.Image;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
public class EnteVista {
    private static final double ANCHO = 20;
    private static final double ALTO = 20;

    private final Ente ente;
    private final Map<Direccion, List<Image>> animaciones = new HashMap<>();
    private Direccion direccionActual = Direccion.ARRIBA;
    private int indiceFrame = 0;
    private double tiempoAcumulado = 0;
    private final double tiempoPorFrame = 0.2; // segundos por frame

    public EnteVista(Ente ente, Map<Direccion, List<String>> rutasFrames) {
        this.ente = ente;
        cargarSprites(rutasFrames);
    }

    private void cargarSprites(Map<Direccion, List<String>> rutasFrames) {
        for (Direccion dir : rutasFrames.keySet()) {
            List<Image> frames = rutasFrames.get(dir).stream()
                    .map(GestorSprites::obtenerSprite)
                    .filter(Objects::nonNull)
                    .toList();
            animaciones.put(dir, frames);
        }
    }

    public void dibujar(GraphicsContext gc) {
        List<Image> frames = animaciones.get(direccionActual);
        if (frames == null || frames.isEmpty()) {
            return;
        }
        Image img = frames.get(indiceFrame);

        double x = ente.getPosicion().getX();
        double y = ente.getPosicion().getY();
        double pivotX = ANCHO / 2.0;
        double pivotY = ALTO / 2.0;

        gc.save();
        gc.translate(x + pivotX, y + pivotY);

        double rotacion = switch (direccionActual) {
            case ARRIBA -> 0;
            case DERECHA -> 90;
            case ABAJO -> 180;
            case IZQUIERDA -> 270;
        };
        gc.rotate(rotacion);

        gc.drawImage(img, -pivotX, -pivotY, ANCHO, ALTO);
        gc.restore();
    }

    public void actualizar(double deltaTime) {
        if (ente instanceof Tanque t) {
            direccionActual = t.getDireccion();
        }

        tiempoAcumulado += deltaTime;
        if (tiempoAcumulado >= tiempoPorFrame) {
            tiempoAcumulado = 0;
            List<Image> framesActuales = animaciones.get(direccionActual);
            if (framesActuales != null && !framesActuales.isEmpty()) {
                indiceFrame = (indiceFrame + 1) % framesActuales.size();
            }
        }
    }

    public boolean estaVisible() {
        return ente.estaActivo();
    }
}
