package juego;

import juego.entidades.*;
import java.util.*;

public class GestorPowerUps {

    private List<PowerUpActivo> activos = new ArrayList<>();

    public void activarPowerUp(Tanque t, TipoPowerUp tipo, double duracion) {
        PowerUpActivo pa = new PowerUpActivo(t, tipo, duracion);
        activos.add(pa);
        pa.aplicar();
    }

    public void actualizar(double deltaTime) {
        Iterator<PowerUpActivo> it = activos.iterator();
        while(it.hasNext()) {
            PowerUpActivo pa = it.next();
            if(pa.actualizar(deltaTime)) it.remove();
        }
    }

    private static class PowerUpActivo {
        Tanque tanque;
        TipoPowerUp tipo;
        double tiempoRestante;

        PowerUpActivo(Tanque t, TipoPowerUp tipo, double duracion) {
            this.tanque = t;
            this.tipo = tipo;
            this.tiempoRestante = duracion;
        }

        void aplicar() {
            tipo.aplicar(tanque); // aplica efecto inicial
        }

        boolean actualizar(double deltaTime) {
            if(tiempoRestante <= 0) return false;
            tiempoRestante -= deltaTime;
            if(tiempoRestante <= 0) {
                tipo.finalizar(tanque); // finaliza efecto temporal
                System.out.println("PowerUp " + tipo + " finalizado para tanque");
                return true; // indica que se debe remover
            }
            return false;
        }
    }
}
