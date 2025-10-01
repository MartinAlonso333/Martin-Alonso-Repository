package org.vista.pantallas;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class PantallaMenu {

    private final Pane root;

    public PantallaMenu(Pane root) {
        this.root = root;
    }

    public void mostrar() {
        root.getChildren().clear();

        Button btn1 = new Button("1 Jugador");
        btn1.setLayoutX(300);
        btn1.setLayoutY(200);
        btn1.setFont(Font.font(18));
        btn1.setOnAction(e -> root.fireEvent(new javafx.event.Event(javafx.event.Event.ANY) {
            public String getEventTypeName() { return "UN_JUGADOR"; }
        }));

        Button btn2 = new Button("2 Jugadores");
        btn2.setLayoutX(300);
        btn2.setLayoutY(250);
        btn2.setFont(Font.font(18));
        btn2.setOnAction(e -> root.fireEvent(new javafx.event.Event(javafx.event.Event.ANY) {
            public String getEventTypeName() { return "DOS_JUGADORES"; }
        }));

        Button btnSalir = new Button("Salir");
        btnSalir.setLayoutX(300);
        btnSalir.setLayoutY(300);
        btnSalir.setFont(Font.font(18));
        btnSalir.setOnAction(e -> System.exit(0));

        root.getChildren().addAll(btn1, btn2, btnSalir);
        root.setVisible(true);
        root.toFront();
    }
}
