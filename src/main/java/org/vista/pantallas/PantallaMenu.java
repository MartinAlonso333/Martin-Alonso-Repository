package org.vista.pantallas;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.controlador.EstadoMenu;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;

public class PantallaMenu extends Pantalla {

    private final Pane root;
    private final EstadoMenu estadoMenu;

    public PantallaMenu(Stage stage, Pane root, EstadoMenu estadoMenu) {
        super(stage);
        this.root = root;
        this.estadoMenu = estadoMenu;
    }

    @Override
    public void mostrar() {
        root.getChildren().clear();

        ImageView fondo = new ImageView(new Image("/sprites/logo.png"));
        fondo.setFitWidth(root.getPrefWidth());
        fondo.setFitHeight(root.getPrefHeight());
        fondo.setPreserveRatio(false);
        fondo.setMouseTransparent(true);

        Button btn1 = crearBoton("1 Jugador");
        btn1.setLayoutX(300);
        btn1.setLayoutY(200);
        btn1.setOnAction(e -> estadoMenu.manejarInput("UN_JUGADOR", true));

        Button btn2 = crearBoton("2 Jugadores");
        btn2.setLayoutX(300);
        btn2.setLayoutY(270);
        btn2.setOnAction(e -> estadoMenu.manejarInput("DOS_JUGADORES", true));

        Button btnSalir = crearBoton("Salir");
        btnSalir.setLayoutX(300);
        btnSalir.setLayoutY(340);
        btnSalir.setOnAction(e -> System.exit(0));

        root.getChildren().addAll(fondo, btn1, btn2, btnSalir);
        root.setVisible(true);
        root.toFront();
    }

    private Button crearBoton(String texto) {
        Button btn = new Button(texto);
        btn.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        btn.setTextFill(Color.WHITE);
        btn.setStyle(
                "-fx-background-color: linear-gradient(#4CAF50, #2E7D32);" +
                        "-fx-background-radius: 15;" +
                        "-fx-padding: 10 25;" +
                        "-fx-border-color: white;" +
                        "-fx-border-radius: 15" +
                        ";" +
                        "-fx-border-width: 2;"
        );
        btn.setEffect(new DropShadow(8, Color.BLACK));

        btn.setOnMouseEntered(e -> btn.setStyle(
                "-fx-background-color: linear-gradient(#66BB6A, #388E3C);" +
                        "-fx-background-radius: 15;" +
                        "-fx-padding: 10 25;" +
                        "-fx-border-color: yellow;" +
                        "-fx-border-radius: 15;" +
                        "-fx-border-width: 2;"
        ));
        btn.setOnMouseExited(e -> btn.setStyle(
                "-fx-background-color: linear-gradient(#4CAF50, #2E7D32);" +
                        "-fx-background-radius: 15;" +
                        "-fx-padding: 10 25;" +
                        "-fx-border-color: white;" +
                        "-fx-border-radius: 15;" +
                        "-fx-border-width: 2;"
        ));
        return btn;
    }
}
