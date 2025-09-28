package org.modelo.input;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import org.modelo.estados.GestorEstados;
public class GestorInput {

    private final GestorEstados gestor;

    public GestorInput(Scene scene, GestorEstados gestor) {
        this.gestor = gestor;

        scene.setOnKeyPressed(e -> {
            String input = traducirTecla(e.getCode());
            if (input != null) {
                gestor.manejarInput(input);
            }
        });

        scene.setOnKeyReleased(e -> {
            String input = traducirTecla(e.getCode());
            if (input != null) {
                // Llama a método para detener movimiento si es una tecla de movimiento
                if (input.startsWith("J1_") && esTeclaMovimiento(input)) {
                    gestor.detenerMovimientoJugador(1);
                } else if (input.startsWith("J2_") && esTeclaMovimiento(input)) {
                    gestor.detenerMovimientoJugador(2);
                }
            }
        });
    }

    private boolean esTeclaMovimiento(String input) {
        return input.endsWith("ARRIBA") || input.endsWith("ABAJO") || input.endsWith("IZQUIERDA") || input.endsWith("DERECHA");
    }

    private String traducirTecla(KeyCode code) {
        return switch (code) {
            // Jugador 1 (WASD + ESPACIO)
            case W -> "J1_ARRIBA";
            case A -> "J1_IZQUIERDA";
            case S -> "J1_ABAJO";
            case D -> "J1_DERECHA";
            case SPACE -> "J1_DISPARO";
            // Jugador 2 (Flechas + ENTER)
            case UP -> "J2_ARRIBA";
            case LEFT -> "J2_IZQUIERDA";
            case DOWN -> "J2_ABAJO";
            case RIGHT -> "J2_DERECHA";
            case ENTER -> "J2_DISPARO";
            default -> null;
        };
    }
}