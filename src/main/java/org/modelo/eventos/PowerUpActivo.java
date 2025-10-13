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
        this.tiempoRestante = tipo.getDuracion();
    }

    void aplicar(GestorEventos em) { tipo.aplicar(jugador, em); }
    void remover(GestorEventos em) { tipo.remover(jugador, em); }

    boolean actualizar(double deltaTime) {
        tiempoRestante -= deltaTime;
        return tiempoRestante <= 0;
    }
}