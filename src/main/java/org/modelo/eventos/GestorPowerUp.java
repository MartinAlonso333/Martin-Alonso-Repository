package org.modelo.eventos;

import org.modelo.entidades.powerups.PowerUp;
import org.modelo.entidades.powerups.TipoPowerUp;
import org.modelo.entidades.tanques.TanqueJugador;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GestorPowerUp {

    private final List<PowerUpActivo> activos = new ArrayList<>();

    public void activarPowerUp(TanqueJugador jugador, PowerUp powerUp) {
        PowerUpActivo pa = new PowerUpActivo(jugador, powerUp.getTipoPowerUp());
        activos.add(pa);
        pa.aplicar();
    }

    public void actualizar(double deltaTime) {
        Iterator<PowerUpActivo> it = activos.iterator();
        while (it.hasNext()) {
            PowerUpActivo pa = it.next();
            if (pa.actualizar(deltaTime)) {
                pa.remover();
                it.remove();
            }
        }
    }
}
