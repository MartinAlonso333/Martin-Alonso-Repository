package org.vista.pantallas;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.modelo.Juego;
import org.modelo.entidades.Ente;
import org.vista.EnteVista;
import org.vista.JuegoVista;

public class PantallaJuego {

    private final Pane root;
    private Juego juego;
    private Runnable onJuegoTerminado;

    public PantallaJuego(Pane root) {
        this.root = root;
    }

    private JuegoVista juegoVista; // la inicializas cuando seteás el juego

    public void setJuego(Juego juego) {
        this.juego = juego;
        this.juegoVista = new JuegoVista(juego);
    }

    public void setOnJuegoTerminado(Runnable callback) {
        this.onJuegoTerminado = callback;
    }

    public void mostrar() {
        dibujar();
    }

    public void actualizar(double deltaTime) {
        if (juego != null) {
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

        for (EnteVista ev : juegoVista.getEntesVista()) {
            Image img = ev.getFrameActual();
            if (img != null) {
                ImageView iv = new ImageView(img);
                iv.setX(ev.getX());
                iv.setY(ev.getY());
                iv.setFitWidth(ev.getAncho());
                iv.setFitHeight(ev.getAlto());
                root.getChildren().add(iv);
            }
        }
    }
}
