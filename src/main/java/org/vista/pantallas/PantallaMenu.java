package org.vista.pantallas;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class PantallaMenu {

    private final Pane root;
    private Runnable onJugar1Jugador;
    private Runnable onJugar2Jugadores;

    public PantallaMenu(Pane root) {
        this.root = root;
    }

    public void setOnJugar1Jugador(Runnable callback) { this.onJugar1Jugador = callback; }
    public void setOnJugar2Jugadores(Runnable callback) { this.onJugar2Jugadores = callback; }

    public void mostrar() {
        root.getChildren().clear();

        Button btn1Jugador = new Button("1 Jugador");
        btn1Jugador.setLayoutX(300);
        btn1Jugador.setLayoutY(200);
        btn1Jugador.setFont(Font.font(18));
        btn1Jugador.setOnAction(e -> { if(onJugar1Jugador != null) onJugar1Jugador.run(); });

        Button btn2Jugadores = new Button("2 Jugadores");
        btn2Jugadores.setLayoutX(300);
        btn2Jugadores.setLayoutY(250);
        btn2Jugadores.setFont(Font.font(18));
        btn2Jugadores.setOnAction(e -> { if(onJugar2Jugadores != null) onJugar2Jugadores.run(); });

        Button btnSalir = new Button("Salir");
        btnSalir.setLayoutX(300);
        btnSalir.setLayoutY(300);
        btnSalir.setFont(Font.font(18));
        btnSalir.setOnAction(e -> System.exit(0));

        root.getChildren().addAll(btn1Jugador, btn2Jugadores, btnSalir);
    }
}
