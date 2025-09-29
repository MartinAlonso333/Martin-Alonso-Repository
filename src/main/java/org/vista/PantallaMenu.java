package org.vista;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.function.Consumer;

public class PantallaMenu extends Pantalla {

    private final Consumer<Integer> cantidadDeJugadores;
    private final Runnable salir;

    public PantallaMenu(Stage stage, Consumer<Integer> onStartGame, Runnable salir) {
        super(stage);
        this.cantidadDeJugadores = onStartGame;
        this.salir = salir;
    }

    @Override
    public void mostrar() {
        ImageView fondo = new ImageView(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/imagen_tanque.png")))
        );
        fondo.setPreserveRatio(true);
        fondo.setFitWidth(800);
        fondo.setFitHeight(600);

        Button unJugadorBtn = crearBoton("Un Jugador");
        unJugadorBtn.setOnAction(e -> cantidadDeJugadores.accept(1));

        Button dosJugadoresBtn = crearBoton("Dos Jugadores");
        dosJugadoresBtn.setOnAction(e -> cantidadDeJugadores.accept(2));

        Button salirBtn = crearBoton("Salir");
        salirBtn.setOnAction(e -> salir.run());

        VBox menu = new VBox(25, unJugadorBtn, dosJugadoresBtn, salirBtn);
        menu.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(fondo, menu);
        root.setPadding(new Insets(50));

        stage.getScene().setRoot(root);
        stage.setTitle("Menú Principal");
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
                        "-fx-border-radius: 15;" +
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
