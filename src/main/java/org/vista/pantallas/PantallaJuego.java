package org.vista.pantallas;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.vista.EnteVista;
import org.vista.JuegoVista;

public class PantallaJuego {

    private final Pane root;
    private JuegoVista juegoVista;

    public PantallaJuego(Pane root) {
        this.root = root;
    }

    public void setJuego(JuegoVista juegoVista) {
        this.juegoVista = juegoVista;
    }

    public void actualizar(double deltaTime) {
        if (juegoVista == null) return;

        root.getChildren().clear();

        for (EnteVista ev : juegoVista.getEntesVista()) {
            javafx.scene.image.Image img = ev.getFrameActual();
            if (img != null) {
                ImageView iv = new ImageView(img);
                iv.setX(ev.getX());
                iv.setY(ev.getY());
                iv.setFitWidth(ev.getAncho());
                iv.setFitHeight(ev.getAlto());

                // ROTACIÓN según dirección
                if (ev.getEnte().getDireccion() != null) {
                    double angulo = switch (ev.getDireccion()) {
                        case ARRIBA -> 0;
                        case DERECHA -> 90;
                        case ABAJO -> 180;
                        case IZQUIERDA -> 270;
                    };
                    iv.setRotate(angulo);
                }

                root.getChildren().add(iv);
            }
        }
    }

    public void mostrar() {
        root.setVisible(true);
        root.toFront();
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
    }
}
