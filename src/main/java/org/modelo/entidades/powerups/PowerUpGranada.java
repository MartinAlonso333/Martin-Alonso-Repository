package org.modelo.entidades.powerups;

import org.modelo.entidades.tanques.TanqueJugador;
import org.modelo.eventos.GestorEventos;
import org.modelo.eventos.TipoEvento;

public class PowerUpGranada implements PowerUpEfecto {

    @Override
    public void aplicar(TanqueJugador jugador, GestorEventos em) {
        em.notificar(TipoEvento.GRANADA_RECOGIDA, null);
    }

    @Override
    public void remover(TanqueJugador jugador, GestorEventos em) {
        // No hace nada
    }

    @Override
    public String getClaveSprite() {
        return "PowerUp-Grenade";
    }

    @Override
    public double getDuracion() {
        return 0;
    }
}