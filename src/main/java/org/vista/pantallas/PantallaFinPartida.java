package org.vista.pantallas;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import org.modelo.eventos.EventoManager;
import org.modelo.eventos.TipoEvento;
public class PantallaFinPartida {

    private final Pane root;

    public PantallaFinPartida(Pane root) {
        this.root = root;
    }

    public void mostrar(boolean victoria) {
        root.getChildren().clear();  // Limpia todo lo que haya antes

        Label lbl = new Label(victoria ? "¡Ganaste!" : "Perdiste");
        lbl.setLayoutX(350);
        lbl.setLayoutY(200);

        Button btnMenu = new Button("Volver al menú");
        btnMenu.setLayoutX(350);
        btnMenu.setLayoutY(300);
        btnMenu.setOnAction(e -> EventoManager.getInstancia().notificar(TipoEvento.MOSTRAR_MENU));

        root.getChildren().addAll(lbl, btnMenu);
        root.setVisible(true);
        root.toFront();
    }

    public void ocultar() {
        root.getChildren().clear();
        root.setVisible(false);
    }
}