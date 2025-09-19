package juego;

import juego.entidades.*;
import juego.gestores.*;
import java.util.*;

public class Juego {

    private final List<Tanque> jugadores = new ArrayList<>();
    private final List<Tanque> enemigos = new ArrayList<>();
    private final List<Bloque> bloques = new ArrayList<>();
    private final List<PowerUp> powerUps = new ArrayList<>();
    private final List<Ente> entes = new ArrayList<>();

    private final SistemaColision sistemaColision;
    private final GestorPowerUps gestorPowerUps;

    public Juego() {
        this.gestorPowerUps = new GestorPowerUps();
        this.sistemaColision = new SistemaColision(gestorPowerUps);
    }

    // Métodos para agregar entidades
    public void agregarJugador(Tanque t) { agregarEnte(t, jugadores); }
    public void agregarEnemigo(Tanque e) { agregarEnte(e, enemigos); }
    public void agregarBloque(Bloque b) { agregarEnte(b, bloques); }
    public void agregarPowerUp(PowerUp p) { agregarEnte(p, powerUps); }
    public void agregarBala(Bala b) { registrarEnte(b); }

    private <T extends Ente> void agregarEnte(T ente, List<T> lista) {
        lista.add(ente);
        registrarEnte(ente);
    }

    private void registrarEnte(Ente e) {
        entes.add(e);
        sistemaColision.registrarEnte(e);
    }

    // Actualización del juego
    public void actualizar(double deltaTime) {
        entes.stream()
                .filter(Ente::estaActivo)
                .forEach(e -> e.actualizar(deltaTime));

        gestorPowerUps.actualizar(deltaTime);
    }

    // Control de jugadores
    public void moverJugador(int indice, Direccion dir) {
        if (indiceValido(indice, jugadores)) {
            jugadores.get(indice).mover(dir, jugadores.get(indice).getVelocidad());
        }
    }

    public void jugadorDispara(int indice) {
        if (indiceValido(indice, jugadores)) {
            jugadores.get(indice).disparar();
        }
    }

    private boolean indiceValido(int indice, List<?> lista) {
        return indice >= 0 && indice < lista.size();
    }

    // Estado del Nivel
    public boolean nivelTerminado() {
        return enemigos.stream().noneMatch(Ente::estaActivo);
    }

    // Getters
    public List<Tanque> getJugadores() { return Collections.unmodifiableList(jugadores); }
    public List<Tanque> getEnemigos() { return Collections.unmodifiableList(enemigos); }
    public List<Bloque> getBloques() { return Collections.unmodifiableList(bloques); }
    public List<PowerUp> getPowerUps() { return Collections.unmodifiableList(powerUps); }
    public List<Ente> getEntes() { return Collections.unmodifiableList(entes); }
}
