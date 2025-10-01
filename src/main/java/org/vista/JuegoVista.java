package org.vista;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.modelo.Juego;
import org.modelo.entidades.Ente;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Gestiona todas las vistas de los entes de un juego.
 * No conoce la lógica, solo dibuja y actualiza.
 */
public class JuegoVista {

    private final List<EnteVista> entesVista = new ArrayList<>();

    public JuegoVista(Juego juego) {
        inicializarVistas(juego.getEntes());
    }

    private void inicializarVistas(List<Ente> entes) {
        entesVista.clear();
        for (Ente e : entes) {
            Map anims = GestorSprites.getAnimacionesPara(e);
            entesVista.add(new EnteVista(e, anims));
        }
    }

    /**
     * Actualiza todas las vistas.
     */
    public void actualizar(double deltaTime) {
        for (EnteVista ev : entesVista) {
            ev.actualizar(deltaTime);
        }
    }

    /**
     * Dibuja todos los entes en el GraphicsContext.
     * También imprime coordenadas de debug.
     */
    public void dibujar(GraphicsContext gc) {
        for (EnteVista ev : entesVista) {
            Image frame = ev.getFrameActual();
            if (frame != null) {
                gc.drawImage(frame, ev.getX(), ev.getY(), ev.getAncho(), ev.getAlto());
            } else {
                // rectángulo rojo si falta sprite
                gc.setFill(javafx.scene.paint.Color.RED);
                gc.fillRect(ev.getX(), ev.getY(), ev.getAncho(), ev.getAlto());
            }
            System.out.printf("Dibujando %s en (%.1f, %.1f)%n", ev.getEnte(), ev.getX(), ev.getY());
        }
    }

    public List<EnteVista> getEntesVista() {
        return new ArrayList<>(entesVista);
    }
}
