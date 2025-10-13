package org.modelo.eventos;

import org.modelo.entidades.powerups.PowerUp;
import org.modelo.entidades.tanques.TanqueJugador;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GestorPowerUp {

    private final List<PowerUpActivo> activos = new ArrayList<>();
    private GestorEventos em;

    public void activarPowerUp(TanqueJugador jugador, PowerUp powerUp, GestorEventos gestor) {
        PowerUpActivo pa = new PowerUpActivo(jugador, powerUp.getTipoPowerUp());
        activos.add(pa);
        this. em = gestor;
        pa.aplicar(em);
    }

    public void actualizar(double deltaTime) {
        Iterator<PowerUpActivo> it = activos.iterator();
        while (it.hasNext()) {
            PowerUpActivo pa = it.next();
            if (pa.actualizar(deltaTime)) {
                pa.remover(em);
                it.remove();
            }
        }
    }
}
