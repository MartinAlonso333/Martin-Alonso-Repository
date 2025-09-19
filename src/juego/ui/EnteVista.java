package juego.vista;

import juego.entidades.Ente;
import juego.utilidades.Coordenada;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class EnteVista {

    private final Ente ente;
    private final Image sprite;

    public EnteVista(Ente ente, Image sprite) {
        this.ente = ente;
        this.sprite = sprite;
    }

    public void dibujar(GraphicsContext gc) {
        Coordenada pos = ente.getPosicion();
        gc.drawImage(sprite, pos.getX(), pos.getY());
    }

    // Para actualizaciones dinámicas (por ejemplo animaciones)
    public void actualizar() {

    }

    public boolean estaVisible() {
        return ente.estaActivo();
    }
}
