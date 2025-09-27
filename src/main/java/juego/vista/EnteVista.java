package juego.vista;


import javafx.scene.canvas.GraphicsContext;
import juego.entidades.Ente;
import juego.entidades.tanques.Tanque;
import juego.utilidades.Direccion;

import javafx.scene.image.Image;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class EnteVista {
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
                    .map(ruta -> new Image(Objects.requireNonNull(getClass().getResourceAsStream(ruta))))
                    .toList();
            animaciones.put(dir, frames);
        }
    }

    public void dibujar(GraphicsContext gc) {
        List<Image> frames = animaciones.get(direccionActual);
        if (frames == null || frames.isEmpty()) return;
        Image img = frames.get(indiceFrame);
        gc.drawImage(
                img,
                ente.getPosicion().getX(),
                ente.getPosicion().getY(),
                ente.getDimensiones().getAncho(),
                ente.getDimensiones().getAlto()
        );
    }

    public void actualizar(double deltaTime) {
        // Actualizar dirección si el ente tiene un getter de dirección
        if (ente instanceof Tanque t) {
            direccionActual = t.getDireccion();
        }

        // Animación
        tiempoAcumulado += deltaTime;
        if (tiempoAcumulado >= tiempoPorFrame) {
            tiempoAcumulado = 0;
            indiceFrame = (indiceFrame + 1) % animaciones.get(direccionActual).size();
        }
    }

    public boolean estaVisible() {
        return ente.estaActivo();
    }
}
