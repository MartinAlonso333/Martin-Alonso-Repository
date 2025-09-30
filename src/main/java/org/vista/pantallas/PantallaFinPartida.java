package org.vista.pantallas;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class PantallaFinPartida {

    private final Pane root;
    private Runnable onVolverAlMenu;

    public PantallaFinPartida(Pane root) {
        this.root = root;
    }

    public void setOnVolverAlMenu(Runnable callback) {
        this.onVolverAlMenu = callback;
    }

    public void mostrar(boolean victoria) {
        root.getChildren().clear();

        Label lblResultado = new Label(victoria ? "¡Ganaste!" : "Perdiste");
        lblResultado.setLayoutX(350);
        lblResultado.setLayoutY(200);

        Button btnMenu = new Button("Volver al menú");
        btnMenu.setLayoutX(350);
        btnMenu.setLayoutY(300);
        btnMenu.setOnAction(e -> {
            if (onVolverAlMenu != null) onVolverAlMenu.run();
        });

        root.getChildren().addAll(lblResultado, btnMenu);
    }
}
