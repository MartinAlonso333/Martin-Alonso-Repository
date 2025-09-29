package org.vista;


import javafx.scene.canvas.GraphicsContext;
import org.modelo.entidades.Ente;
import org.modelo.entidades.tanques.Tanque;
import org.modelo.utilidades.Direccion;

import javafx.scene.image.Image;
import java.util.List;
import java.util.Map;

public class EnteVista {
    private static final double ANCHO = 20;
    private static final double ALTO = 20;

    private final Ente ente;
    private final Map<Direccion, List<Image>> animaciones;
    private Direccion direccionActual = Direccion.ARRIBA;
    private int indiceFrame = 0;
    private double tiempoAcumulado = 0;
    private final double tiempoPorFrame = 0.2; // segundos por frame

    public EnteVista(Ente ente, Map<Direccion, List<Image>> animaciones) {
        this.ente = ente;
        this.animaciones = animaciones;
    }

    public void dibujar(GraphicsContext gc) {
        List<Image> frames = animaciones.get(direccionActual);
        if (frames == null || frames.isEmpty()) {
            return;
        }
        Image img = frames.get(indiceFrame);

        // Usar coordenadas de celda para calcular posición en píxeles
        int celdaX = ente.getPosicion().getX();
        int celdaY = ente.getPosicion().getY();

        final int TAM_CELDA = 20;
        double x = celdaX * TAM_CELDA + TAM_CELDA / 2.0;
        double y = celdaY * TAM_CELDA + TAM_CELDA / 2.0;

        System.out.println("Dibujando ente en celda: (" + celdaX + "," + celdaY + ") -> píxeles: (" + x + "," + y + ")");

        double pivotX = ANCHO / 2.0;
        double pivotY = ALTO / 2.0;

        gc.save();
        gc.translate(x, y);

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
        boolean activo = ente.estaActivo();
        System.out.println("Entidad " + ente + " está activa? " + activo);
        return activo;
    }
}