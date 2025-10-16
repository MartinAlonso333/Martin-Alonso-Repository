package org.modelo.entidades.powerups;

import org.modelo.entidades.tanques.TanqueJugador;
import org.modelo.eventos.GestorEventos;

public interface PowerUpEfecto {
    void aplicar(TanqueJugador jugador, GestorEventos em);
    void remover(TanqueJugador jugador, GestorEventos em);
    String getClaveSprite();
    double getDuracion();
}
