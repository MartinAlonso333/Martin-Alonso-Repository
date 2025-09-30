package org.modelo.input;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import org.controlador.GestorEstados;

import java.util.HashSet;
import java.util.Set;

public class GestorInput {

    private final GestorEstados gestor;
    private final Set<String> teclasPresionadas = new HashSet<>();

    public GestorInput(Scene scene, GestorEstados gestor) {
        this.gestor = gestor;

        scene.setOnKeyPressed(e -> {
            String input = traducirTecla(e.getCode());
            if (input != null) teclasPresionadas.add(input);
        });

        scene.setOnKeyReleased(e -> {
            String input = traducirTecla(e.getCode());
            if (input != null) {
                teclasPresionadas.remove(input);
                gestor.manejarInput(input, false); // detener movimiento al soltar
            }
        });
    }

    public void procesarInput() {
        // Llamar cada frame para mover jugadores
        for (String input : teclasPresionadas) {
            gestor.manejarInput(input, true);
        }
    }

    private String traducirTecla(KeyCode code) {
        return switch (code) {
            case W -> "J1_ARRIBA";
            case A -> "J1_IZQUIERDA";
            case S -> "J1_ABAJO";
            case D -> "J1_DERECHA";
            case SPACE -> "J1_DISPARO";
            case UP -> "J2_ARRIBA";
            case LEFT -> "J2_IZQUIERDA";
            case DOWN -> "J2_ABAJO";
            case RIGHT -> "J2_DERECHA";
            case ENTER -> "J2_DISPARO";
            default -> null;
        };
    }
}
