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
            case UP -> "J1_UP";
            case DOWN -> "J1_DOWN";
            case LEFT -> "J1_LEFT";
            case RIGHT -> "J1_RIGHT";
            case ENTER -> "J1_FIRE";

            // Jugador 2
            case W -> "J2_UP";
            case S -> "J2_DOWN";
            case A -> "J2_LEFT";
            case D -> "J2_RIGHT";
            case SPACE -> "J2_FIRE";

            default -> null;
        };
    }
}
