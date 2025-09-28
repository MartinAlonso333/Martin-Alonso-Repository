package org.modelo.input;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import org.modelo.estados.GestorEstados;

public class GestorInput {

    public GestorInput(Scene scene, GestorEstados gestor) {
        scene.setOnKeyPressed(e -> {
            String input = traducirTecla(e.getCode());
            if (input != null) {
                gestor.manejarInput(input);
            }
        });
    }

    private String traducirTecla(KeyCode code) {
        return switch (code) {
            // Jugador 1
            case UP -> "J1_ARRIBA";
            case DOWN -> "J1_ABAJO";
            case LEFT -> "J1_IZQUIERDA";
            case RIGHT -> "J1_DERECHA";
            case ENTER -> "J1_DISPARO";

            // Jugador 2
            case W -> "J2_ARRIBA";
            case S -> "J2_ABAJO";
            case A -> "J2_IZQUIERDA";
            case D -> "J2_DERECHA";
            case SPACE -> "J2_DISPARO";

            default -> null;
        };
    }
}
