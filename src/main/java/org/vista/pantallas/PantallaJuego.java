package org.vista.pantallas;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.vista.EnteVista;
import org.vista.JuegoVista;
import org.modelo.Juego;

public class PantallaJuego {

    private final Pane root;
    private JuegoVista juegoVista;

    public PantallaJuego(Pane root) {
        this.root = root;
    }

    public void setJuego(Juego juego) {
        this.juegoVista = new JuegoVista(juego);
    }

    public void actualizar(double deltaTime) {
        if (juegoVista == null) return;

        root.getChildren().clear();

        for (EnteVista ev : juegoVista.getEntesVista()) {
            Image img = ev.getFrameActual();
            if (img != null) {
                ImageView iv = new ImageView(img);
                iv.setX(ev.getX());
                iv.setY(ev.getY());
                iv.setFitWidth(ev.getAncho());
                iv.setFitHeight(ev.getAlto());
                root.getChildren().add(iv);

                System.out.println(ev.getEnte() + " x=" + ev.getX() + " y=" + ev.getY());
            }
        }
    }

    public void mostrar() {
        root.setVisible(true);
        root.toFront();
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
    }
}
