package juego;

import juego.entidades.*;
import java.util.*;

public class Juego {

    private Tablero tablero;
    private List<Tanque> tanques = new ArrayList<>();
    private List<Enemigo> enemigos = new ArrayList<>();
    private List<Bloque> bloques = new ArrayList<>();
    private List<PowerUp> powerUps = new ArrayList<>();

    private Map<TipoPowerUp, Consumer<Tanque>> efectos = new HashMap<>();

    private List<EventoJuegoListener> listeners = new ArrayList<>();

    public Juego(int ancho, int alto) {
        this.tablero = new Tablero(ancho, alto);
        agregarEfectos();
    }

    private void agregarEfectos() {
        efectos.put(TipoPowerUp.GRANADA, t -> System.out.println("Boom! Todos los enemigos reciben daño"));
        efectos.put(TipoPowerUp.CASCO, t -> System.out.println("Jugador gana +50 de vida"));
        efectos.put(TipoPowerUp.VELOCIDAD, t -> System.out.println("Velocidad aumentada"));
        efectos.put(TipoPowerUp.ESCUDO, t -> System.out.println("Escudo activado"));
    }

    // Actualiza todos los entes del juego
    public void actualizar() {
        // Actualizar jugadores
        jugadores.forEach(t -> {
            t.actualizar();

            // Chequear power-ups
            powerUps.stream()
                    .filter(PowerUp::activo)
                    .filter(t::colisionaCon)
                    .forEach(p -> {
                        Consumer<Tanque> efecto = efectos.get(p.getTipo());
                        if (efecto != null) efecto.accept(t);
                        p.desactivar();
                    });

            // Mantener dentro del tablero
            if (!tablero.dentroDelTablero(t) || tablero.colisionaConOtros(t)) {
                // Revertir movimiento
                t.setPosicion(t.getPosicion()); // o lógica de retroceso según necesidad
            }
        });

        // Actualizar enemigos
        enemigos.forEach(t -> {
            t.actualizar();
            if (!tablero.dentroDelTablero(t) || tablero.colisionaConOtros(t)) {
                // Revertir o cambiar dirección
            }
        });

        // Actualizar power-ups si es necesario
        powerUps.forEach(PowerUp::actualizar);
    }

    // Métodos de agregación
    public void agregarJugador(Tanque t) {
        jugadores.add(t);
        tablero.agregarEnte(t);
    }
    public void agregarEnemigo(Tanque t) {
        enemigos.add(t);
        tablero.agregarEnte(t);
    }
    public void agregarPowerUp(PowerUp p) {
        powerUps.add(p);
        tablero.agregarEnte(p);
    }
    public void agregarBloque(Bloque b) { tablero.agregarEnte(b); }

    // Getters
    public List<Tanque> getJugadores() { return jugadores; }
    public List<Tanque> getEnemigos() { return enemigos; }
    public List<PowerUp> getPowerUps() { return powerUps; }
    public Tablero getTablero() { return tablero; }
}
