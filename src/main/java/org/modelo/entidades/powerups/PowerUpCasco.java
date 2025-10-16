package org.modelo.entidades.powerups;


import org.modelo.entidades.tanques.TanqueJugador;
import org.modelo.eventos.GestorEventos;
import org.modelo.eventos.TipoEvento;

public class PowerUpCasco implements PowerUpEfecto {

    @Override
    public void aplicar(TanqueJugador jugador, GestorEventos em) {
        jugador.setInvulnerabilidad(true);
        em.notificar(TipoEvento.CASCO_RECOGIDO, jugador);
    }

    @Override
    public void remover(TanqueJugador jugador, GestorEventos em) {
        jugador.setInvulnerabilidad(false);
        em.notificar(TipoEvento.EFECTO_CASCO_TERMINADO, jugador);
    }

    @Override
    public String getClaveSprite() {
        return "PowerUp-Helmet";
    }

    @Override
    public double getDuracion() {
        return 5;
    }
}
