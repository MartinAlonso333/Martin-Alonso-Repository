package org.vista.pantallas;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.modelo.entidades.TipoEnte;
import org.modelo.utilidades.Direccion;
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

    public void actualizar() {
        if (juegoVista == null) return;

        root.getChildren().clear();

        for (EnteVista ev : juegoVista.getEntesVista()) {
            renderizarEnte(ev);
        }
    }

    private void renderizarEnte(EnteVista ev) {
        Image frame = ev.getFrameActual();
        if (frame == null) return;

        // Render principal
        ImageView ivTanque = crearImageView(ev, frame);
        root.getChildren().add(ivTanque);

        // Overlay del casco (si corresponde)
        if (ev.getEnte().getTipoEnte() == TipoEnte.JUGADOR && ev.isTieneCascoOverlay()) {
            agregarCascoOverlay(ev);
        }
    }

    private ImageView crearImageView(EnteVista ev, Image img) {
        ImageView iv = new ImageView(img);
        iv.setX(ev.getX());
        iv.setY(ev.getY());
        iv.setFitWidth(ev.getAncho());
        iv.setFitHeight(ev.getAlto());
        iv.setPreserveRatio(true);

        Direccion dir = ev.getEnte().getDireccion();
        if (dir != null) {
            iv.setRotate(getAnguloRotacion(dir));
        }

        return iv;
    }

    private void agregarCascoOverlay(EnteVista ev) {
        Image cascoImg = ev.getCascoImage();
        if (cascoImg == null) {
            System.out.println("CascoImg es NULL - No se renderiza overlay");
            return;
        }

        double centroX = ev.getX() + ev.getAncho() / 2.0;
        double centroY = ev.getY() + ev.getAlto() / 2.0;
        double cascoX = centroX - 16;
        double cascoY = centroY - 16;

        ImageView ivCasco = new ImageView(cascoImg);
        ivCasco.setX(cascoX);
        ivCasco.setY(cascoY);
        ivCasco.setFitWidth(32);
        ivCasco.setFitHeight(32);
        ivCasco.setPreserveRatio(true);

        Direccion dir = ev.getEnte().getDireccion();
        if (dir != null) {
            ivCasco.setRotate(getAnguloRotacion(dir));
        }

        root.getChildren().add(ivCasco);
    }

    private double getAnguloRotacion(Direccion dir) {
        return switch (dir) {
            case ARRIBA -> 0;
            case DERECHA -> 90;
            case ABAJO -> 180;
            case IZQUIERDA -> 270;
        };
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