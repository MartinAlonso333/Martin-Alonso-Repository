package juego.vista;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import juego.entidades.Ente;
import juego.entidades.Direccion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                    .map(ruta -> new Image(getClass().getResourceAsStream(ruta)))
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
                ente.getDimensiones().ancho(),
                ente.getDimensiones().alto()
        );
    }

    public void actualizar(double deltaTime) {
        // Actualizar dirección si el ente tiene un getter de dirección
        if (ente instanceof juego.entidades.Tanque t) {
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
