package org.vista;

import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class PantallaMenu extends Pantalla {

    public PantallaMenu(Stage stage) {
        super(stage);
    }

    @Override
    public void mostrar() {
        Button unJugadorBtn = new Button("Un Jugador");
        unJugadorBtn.setOnAction(e -> {
            PantallaJuego juego = new PantallaJuego(stage, 1);
            juego.mostrar();
        });

        Button dosJugadoresBtn = new Button("Dos Jugadores");
        dosJugadoresBtn.setOnAction(e -> {
            PantallaJuego juego = new PantallaJuego(stage, 2);
            juego.mostrar();
        });

        Button salirBtn = new Button("Salir");
        salirBtn.setOnAction(e -> stage.close());

        VBox root = new VBox(20, unJugadorBtn, dosJugadoresBtn, salirBtn);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }
}