package juego;

import juego.entidades.*;
import java.util.*;

public class Juego {
    private List<Tanque> tanques = new ArrayList<>();
    private List<Enemigo> enemigos = new ArrayList<>();
    private List<Bloque> bloques = new ArrayList<>();
    private List<PowerUp> powerUps = new ArrayList<>();

    public Juego() {}

    public void actualizar(double deltaTime) {
        // Actualizar todos los entes polimórficamente
        for (Tanque t : tanques) t.actualizar(deltaTime);
        for (Enemigo e : enemigos) e.actualizar(deltaTime);
        for (PowerUp p : powerUps) if (p.activo()) p.actualizar(deltaTime);

        for (Tanque t : tanques) {
            for (PowerUp pu : powerUps) {
                if (pu.activo() && colision(t, pu)) {
                    pu.aplicar(t, gestorPowerUps); // ¡el power-up se maneja solo!
                }
            }
        }
        gestorPowerUps.actualizar(deltaTime);

    }

    public void moverJugador(int indice, Direccion dir) {
        if (indice >= 0 && indice < tanques.size())
            tanques.get(indice).mover(dir);
    }

    public void jugadorDispara(int indice) {
        if (indice >= 0 && indice < tanques.size())
            tanques.get(indice).disparar();
    }

    // Agregar entidades
    public void agregarTanque(Tanque t) { tanques.add(t); }
    public void agregarEnemigo(Enemigo e) { enemigos.add(e); }
    public void agregarBloque(Bloque b) { bloques.add(b); }
    public void agregarPowerUp(PowerUp p) { powerUps.add(p); }

    // Getters
    public List<Tanque> getTanques() { return tanques; }
    public List<Enemigo> getEnemigos() { return enemigos; }
    public List<Bloque> getBloques() { return bloques; }
    public List<PowerUp> getPowerUps() { return powerUps; }
}
