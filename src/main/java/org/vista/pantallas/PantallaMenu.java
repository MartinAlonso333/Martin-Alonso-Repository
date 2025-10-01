package org.vista.pantallas;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import org.controlador.EstadoMenu;

public class PantallaMenu {

    private final Pane root;
    private EstadoMenu estadoMenu; // Referencia al estado actual del menú

    public PantallaMenu(Pane root, EstadoMenu estadoMenu) {
        this.root = root;
        this.estadoMenu = estadoMenu;
    }

    public void mostrar() {
        root.getChildren().clear();

        Button btn1 = new Button("1 Jugador");
        btn1.setLayoutX(300);
        btn1.setLayoutY(200);
        btn1.setFont(Font.font(18));
        btn1.setOnAction(e -> estadoMenu.manejarInput("UN_JUGADOR", true));

        Button btn2 = new Button("2 Jugadores");
        btn2.setLayoutX(300);
        btn2.setLayoutY(250);
        btn2.setFont(Font.font(18));
        btn2.setOnAction(e -> estadoMenu.manejarInput("DOS_JUGADORES", true));

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
