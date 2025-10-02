package org.vista.pantallas;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.vista.EnteVista;
import org.vista.JuegoVista;

public class PantallaJuego extends Pantalla {

    private final Pane root;
    private JuegoVista juegoVista;

    public PantallaJuego(Stage stage, Pane root) {
        super(stage);
        this.root = root;
    }

    public void setJuego(JuegoVista juegoVista) {
        this.juegoVista = juegoVista;
    }

    public JuegoVista getJuegoVista() {
        return juegoVista;
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

    @Override
    public void mostrar() {
        root.setVisible(true);
        root.toFront();
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
    }

    public void ocultar() {
        root.getChildren().clear();
        root.setVisible(false);
    }

}
