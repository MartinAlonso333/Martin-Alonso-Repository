package juego.gestores;

import juego.entidades.powerups.TipoPowerUp;
import juego.entidades.tanques.TanqueJugador;

import java.util.ArrayList;
import java.util.List;

public class GestorPowerUp {

    private final List<PowerUpActivo> activos = new ArrayList<>();

    public void activarPowerUp(TanqueJugador t, TipoPowerUp tipo, double duracionMs) {
        PowerUpActivo pa = new PowerUpActivo(t, tipo, duracionMs);
        activos.add(pa);
        pa.aplicar();
    }

    public void actualizar(double deltaTime) {
        activos.removeIf(pa -> pa.actualizar(deltaTime));
    }

    private static class PowerUpActivo {
        private final TanqueJugador tanque;
        private final TipoPowerUp tipo;
        private double tiempoRestante;

        PowerUpActivo(TanqueJugador t, TipoPowerUp tipo, double duracion) {
            this.tanque = t;
            this.tipo = tipo;
            this.tiempoRestante = duracion;
        }

        void aplicar() { tipo.aplicar(tanque); }

        boolean actualizar(double deltaTime) {
            tiempoRestante -= deltaTime;
            return tiempoRestante <= 0;
        }
    }
}
