package org.modelo.entidades.powerups;

public class PowerUpFabrica {

    public static PowerUpEfecto crearEfecto(TipoPowerUp tipo) {
        return switch (tipo) {
            case CASCO -> new PowerUpCasco();
            case ESTRELLA -> new PowerUpEstrella();
            case GRANADA -> new PowerUpGranada();
            default -> throw new IllegalArgumentException("Tipo de PowerUp no soportado: " + tipo);
        };
    }
}