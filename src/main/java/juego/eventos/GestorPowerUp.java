package juego.eventos;

import juego.entidades.powerups.TipoPowerUp;
import juego.entidades.tanques.TanqueJugador;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GestorPowerUp {

    private final List<PowerUpActivo> activos = new ArrayList<>();

    public void activarPowerUp(TanqueJugador jugador, TipoPowerUp tipo) {
        if (tipo.getDuracionMs() <= 0) return; // globales no se gestionan aquí
        PowerUpActivo pa = new PowerUpActivo(jugador, tipo);
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

    private static class PowerUpActivo {
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
}
