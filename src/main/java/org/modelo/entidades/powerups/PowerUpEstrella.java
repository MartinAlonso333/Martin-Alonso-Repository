package org.modelo.entidades.powerups;

import org.modelo.entidades.tanques.TanqueJugador;
import org.modelo.eventos.GestorEventos;

public class PowerUpEstrella implements PowerUpEfecto {

    @Override
    public void aplicar(TanqueJugador jugador, GestorEventos em) {
        jugador.setDisparoMejorado(true);
    }

    @Override
    public void remover(TanqueJugador jugador, GestorEventos em) {
        jugador.setDisparoMejorado(false);
    }

    @Override
    public String getClaveSprite() {
        return "PowerUp-Star";
    }

    @Override
    public double getDuracion() {
        return 7;
    }
}
