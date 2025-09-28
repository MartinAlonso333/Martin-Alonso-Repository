package org.modelo.eventos;

import org.modelo.entidades.powerups.TipoPowerUp;
import org.modelo.entidades.tanques.TanqueJugador;

public class PowerUpActivo {
    private final TanqueJugador jugador;
    private final TipoPowerUp tipo;
    private double tiempoRestante;

    PowerUpActivo(TanqueJugador jugador, TipoPowerUp tipo) {
        this.jugador = jugador;
        this.tipo = tipo;
        this.tiempoRestante = tipo.getDuracionMs();
    }

    void aplicar() { tipo.aplicar(jugador); }
    void remover() { tipo.remover(jugador); }

    boolean actualizar(double deltaTime) {
        tiempoRestante -= deltaTime;
        return tiempoRestante <= 0;
    }
}