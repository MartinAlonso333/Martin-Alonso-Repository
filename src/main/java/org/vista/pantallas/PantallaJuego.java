package org.vista.pantallas;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.modelo.entidades.TipoEnte;
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
            Image imgTanque = ev.getFrameActual();
            if (imgTanque != null) {

                ImageView ivTanque = new ImageView(imgTanque);
                ivTanque.setX(ev.getX());
                ivTanque.setY(ev.getY());
                ivTanque.setFitWidth(ev.getAncho());
                ivTanque.setFitHeight(ev.getAlto());
                ivTanque.setPreserveRatio(true);

                // Rotación para el tanque
                if (ev.getEnte().getDireccion() != null) {
                    double angulo = switch (ev.getDireccion()) {
                        case ARRIBA -> 0;
                        case DERECHA -> 90;
                        case ABAJO -> 180;
                        case IZQUIERDA -> 270;
                    };
                    ivTanque.setRotate(angulo);
                }

                root.getChildren().add(ivTanque);
            }
        }

        // Segundo paso: Agregar overlays del casco (encima de todos los tanques)
        for (EnteVista ev : juegoVista.getEntesVista()) {
            if (ev.getEnte().getTipoEnte() == TipoEnte.JUGADOR && ev.isTieneCascoOverlay()) {
                Image cascoImg = ev.getCascoImage();
                if (cascoImg != null) {
                    // Calcular posición para centrar el casco en el centro del tanque
                    double centroTanqueX = ev.getX() + (ev.getAncho() / 2);
                    double centroTanqueY = ev.getY() + (ev.getAlto() / 2);
                    double cascoX = centroTanqueX - 16;  // Mitad del ancho del casco (32/2 = 16) para centrado
                    double cascoY = centroTanqueY - 16;  // Mitad del alto del casco (32/2 = 16) para centrado

                    ImageView ivCasco = new ImageView(cascoImg);
                    ivCasco.setX(cascoX);
                    ivCasco.setY(cascoY);
                    ivCasco.setFitWidth(32);  // Mantengo 32 para pruebas, ajusta a 20 cuando esté listo
                    ivCasco.setFitHeight(32);
                    ivCasco.setPreserveRatio(true);

                    // Rotación igual que el tanque (calcular directamente para consistencia)
                    if (ev.getEnte().getDireccion() != null) {
                        double angulo = switch (ev.getDireccion()) {
                            case ARRIBA -> 0;
                            case DERECHA -> 90;
                            case ABAJO -> 180;
                            case IZQUIERDA -> 270;
                        };
                        ivCasco.setRotate(angulo);
                    }

                    // Borde rojo para pruebas de visibilidad
                    ivCasco.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-background-color: transparent;");

                    root.getChildren().add(ivCasco);  // Agregar al final: encima de todo
                } else {
                    System.out.println("CascoImg es NULL - No se renderiza overlay");
                }
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