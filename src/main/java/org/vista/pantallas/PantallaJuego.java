package org.vista.pantallas;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.modelo.Juego;
import org.modelo.entidades.Ente;

public class PantallaJuego {

    private final Pane root;
    private Juego juego;
    private Runnable onJuegoTerminado;

    public PantallaJuego(Pane root) {
        this.root = root;
    }

    public void setJuego(Juego juego) {
        this.juego = juego;
    }

    public void setOnJuegoTerminado(Runnable callback) {
        this.onJuegoTerminado = callback;
    }

    public void mostrar() {
        root.getChildren().clear();
        dibujar();
    }

    public void actualizar(double deltaTime) {
        if (juego != null) {
            juego.actualizar(deltaTime);
            dibujar();
            if (juego.getEntesDeTipo(org.modelo.entidades.tanques.TanqueJugador.class).isEmpty() ||
                    juego.getEntesDeTipo(org.modelo.entidades.tanques.TanqueEnemigo.class).isEmpty()) {
                if (onJuegoTerminado != null) onJuegoTerminado.run();
            }
        }
    }

    private void dibujar() {
        root.getChildren().clear();
        if (juego == null) return;

        for (Ente e : juego.getEntes()) {
            Rectangle r = new Rectangle(
                    e.getPosicion().getPixelX(),
                    e.getPosicion().getPixelY(),
                    e.getDimensiones().getAncho(),
                    e.getDimensiones().getAlto()
            );
            r.setFill(Color.GRAY);
            root.getChildren().add(r);
        }
    }
}
