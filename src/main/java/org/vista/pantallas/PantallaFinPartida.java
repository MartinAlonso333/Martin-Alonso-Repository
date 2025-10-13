package org.vista.pantallas;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.modelo.eventos.EventoManager;
import org.modelo.eventos.GestorEventos;
import org.modelo.eventos.TipoEvento;

public class PantallaFinPartida extends Pantalla {

    private final Pane root;
    GestorEventos em;
    public PantallaFinPartida(Stage stage, Pane root, GestorEventos gestorEventos) {
        super(stage);
        this.root = root;
        this.em = gestorEventos;
    }

    @Override
    public void mostrar() {
        mostrar(false);
    }

    public void mostrar(boolean victoria) {
        root.getChildren().clear();

        ImageView fondo = new ImageView(new Image("/sprites/logo.png"));
        fondo.setFitWidth(root.getPrefWidth());
        fondo.setFitHeight(root.getPrefHeight());
        fondo.setPreserveRatio(false);
        fondo.setMouseTransparent(true);

        // Label grande y centrado
        Label lbl = new Label(victoria ? "¡Ganaste!" : "Perdiste");
        lbl.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        lbl.setTextFill(Color.WHITE);

        // Centrar el label
        lbl.setLayoutX((root.getPrefWidth() - lbl.getWidth()) / 2);
        lbl.setLayoutY(150);
        lbl.layoutXProperty().bind(root.widthProperty().subtract(lbl.widthProperty()).divide(2));

        Button btnMenu = crearBoton("Volver al Menú");
        btnMenu.setLayoutX(300);
        btnMenu.setLayoutY(300);
        btnMenu.setOnAction(e -> em.notificar(TipoEvento.MOSTRAR_MENU, null));

        Button btnSalir = crearBoton("Salir");
        btnSalir.setLayoutX(300);
        btnSalir.setLayoutY(400);
        btnSalir.setOnAction(e -> System.exit(0));

        // Agregar todos los elementos al root, el label primero para que quede arriba del fondo
        root.getChildren().addAll(fondo, lbl, btnMenu, btnSalir);

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

    public void ocultar() {
        root.getChildren().clear();
        root.setVisible(false);
    }
}
