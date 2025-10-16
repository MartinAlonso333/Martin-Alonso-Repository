package org.modelo.eventos;

import org.modelo.entidades.powerups.PowerUpEfecto;
import org.modelo.entidades.powerups.PowerUpFabrica;
import org.modelo.entidades.powerups.TipoPowerUp;
import org.modelo.entidades.tanques.TanqueJugador;

public class PowerUpActivo {
    private final TanqueJugador jugador;
    private final TipoPowerUp tipo;
    private final PowerUpEfecto efecto;
    private double tiempoRestante;

    PowerUpActivo(TanqueJugador jugador, TipoPowerUp tipo) {
        this.jugador = jugador;
        this.tipo = tipo;
        this.efecto = PowerUpFabrica.crearEfecto(tipo);
        this.tiempoRestante = efecto.getDuracion();
    }

    void aplicar(GestorEventos em) {
        efecto.aplicar(jugador, em);
    }

    void remover(GestorEventos em) {
        efecto.remover(jugador, em);
    }

    /**
     * Resta tiempo, devuelve true cuando el efecto ya expiró (y el llamador debe invocar remover).
     */
    boolean actualizar(double deltaTime) {
        tiempoRestante -= deltaTime;
        return tiempoRestante <= 0;
    }

    public TipoPowerUp getTipo() {
        return tipo;
    }
}