package org.modelo;

import org.modelo.entidades.*;
import org.modelo.entidades.bloques.Bloque;
import org.modelo.entidades.bloques.TipoBloque;
import org.modelo.entidades.powerups.PowerUp;
import org.modelo.entidades.powerups.TipoPowerUp;
import org.modelo.entidades.tanques.*;
import org.modelo.eventos.EventoManager;
import org.modelo.eventos.GestorEventos;
import org.modelo.eventos.TipoEvento;
import org.modelo.colisiones.SistemaColisionGrilla;
import org.modelo.eventos.GestorPowerUp;
import org.modelo.utilidades.Direccion;
import org.modelo.utilidades.Coordenada;
import org.modelo.utilidades.Dimensiones;

import java.util.*;
import java.util.stream.Collectors;

public class Juego {

    private static final int ANCHO_MAPA = 800;
    private static final int ALTO_MAPA = 600;

    private final List<Ente> entes = new ArrayList<>();
    private final Map<Integer, TanqueJugador> jugadores = new HashMap<>();
    private final GestorPowerUp gestorPowerUp = new GestorPowerUp();
    private final SistemaColisionGrilla sistemaColision;
    private final GestorEventos em;

    private int powerupsActivos = 0;
    private final int INTERVALO_SPAWN_ENEMIGO = 10000; // ms
    private long ultimoSpawnEnemigo = 9000;
    private final int MAX_ENEMIGOS_SPAWNEADOS = 3;
    private int enemigosSpawneados = 0;
    List<Ente> nuevasEntidades = new ArrayList<>();

    public Juego(GestorEventos gestorEventos) {
        em = gestorEventos;
        sistemaColision = new SistemaColisionGrilla(gestorPowerUp, em);
        em.registrar(TipoEvento.GRANADA_RECOGIDA, (data) -> destruirTodosEnemigos());
        em.registrar(TipoEvento.POWERUP_RECOGIDO, (data) -> powerupsActivos = 0);
        em.registrar(TipoEvento.TANQUE_DESTRUIDO, (data) -> {
            Tanque tanque = (Tanque) data;
            removerEnte(tanque);
            crearBloqueDestruido(tanque);
            PowerUp nuevo = spawnPowerUpAleatorio();
            if (nuevo != null) nuevasEntidades.add(nuevo);
        });
        em.registrar(TipoEvento.TANQUE_JUGADOR_DESTRUIDO, (data) -> {
            TanqueJugador tanque = (TanqueJugador) data;
            jugadores.remove(tanque.getIdJugador());  // elimina del mapa por ID
            removerEnte(tanque);               // sigue removiendo del listado de entes
            crearBloqueDestruido(tanque);
        });

        em.registrar(TipoEvento.BALA_DISPARADA, (data) -> {
            Bala bala = (Bala) data;
            nuevasEntidades.add(bala);
        });
    }
    private void crearBloqueDestruido(Tanque tanque) {
        Bloque bloqueNuevo = new Bloque(TipoBloque.TANQUE_DESTRUIDO,
                new Coordenada(tanque.getPosicion().getPixelX(),
                        tanque.getPosicion().getPixelY()),
                new Dimensiones(20, 20));
        nuevasEntidades.add(bloqueNuevo); // agregado temporal
    }

    // ------------------ GESTIÓN DE ENTES ------------------
    public void agregarJugador(int id, Ente e) {
        jugadores.put(id, (TanqueJugador) e);
        agregarEnte(e);
    }

    public void agregarEnte(Ente e) {
        entes.add(e);
        sistemaColision.agregarEnte(e);
    }

    public void removerEnte(Ente e) {
        entes.remove(e);
        sistemaColision.removerEnte(e);
    }

    public List<Ente> getEntes() { return new ArrayList<>(entes); }

    public Map<Integer, TanqueJugador> getJugadoresMap() {
        return Collections.unmodifiableMap(jugadores);
    }

    public List<Ente> getEntesDeTipo(TipoEnte tipo) {
        return entes.stream()
                .filter(e -> e.getTipoEnte() == tipo)
                .collect(Collectors.toList());
    }

    // ------------------ ACCIONES ------------------
    public void moverJugador(int id, Direccion dir) {
        TanqueJugador jugador = jugadores.get(id);
        if (jugador == null) return;

        Coordenada antes = new Coordenada(jugador.getPosicion().getPixelX(), jugador.getPosicion().getPixelY());
        jugador.mover(dir);
        sistemaColision.actualizarPosicion(jugador, antes);
        sistemaColision.chequearColisiones(jugador);
    }

    public void dispararJugador(int id) {
        TanqueJugador jugador = jugadores.get(id);
        if (jugador == null) return;

        Bala bala = jugador.disparar();
        if (bala != null) {
            agregarEnte(bala);
            sistemaColision.chequearColisiones(bala);
            em.notificar(TipoEvento.BALA_DISPARADA, bala);
        }
    }

    public void detenerJugador(int id) {
        TanqueJugador jugador = jugadores.get(id);
        if (jugador != null) jugador.detenerMovimiento();
    }


    // ------------------ ACTUALIZACIÓN ------------------
    public void actualizar(double deltaTime) {
        // Actualizar power-ups
        gestorPowerUp.actualizar(deltaTime);

        // Generar enemigos
        spawnEnemigos();

        // Actualizar todos los entes
        actualizarEntes(deltaTime);

        // Agregar nuevas entidades
        agregarNuevasEntidades();
    }

    private void spawnEnemigos() {
        long ahora = System.currentTimeMillis();
        if (ahora - ultimoSpawnEnemigo > INTERVALO_SPAWN_ENEMIGO
                && enemigosSpawneados < MAX_ENEMIGOS_SPAWNEADOS) {
            TanqueEnemigo nuevo = crearEnemigoAleatorio();
            if (nuevo != null) {
                nuevasEntidades.add(nuevo);
                enemigosSpawneados++;
            }
            ultimoSpawnEnemigo = ahora;
        }
    }

    private void actualizarEntes(double deltaTime) {
        List<Ente> eliminar = new ArrayList<>();
        List<Ente> snapshot = new ArrayList<>(entes);

        for (Ente e : snapshot) {
            Coordenada antes = new Coordenada(e.getPosicion().getPixelX(), e.getPosicion().getPixelY());
            e.actualizar(deltaTime);

            sistemaColision.actualizarPosicion(e, antes);
            sistemaColision.chequearColisiones(e);

            if (e.estaDestruido()) {
                eliminar.add(e);
            }
        }

        eliminar.forEach(this::removerEnte);
    }

    private void agregarNuevasEntidades() {
        if (!nuevasEntidades.isEmpty()) {
            nuevasEntidades.forEach(this::agregarEnte);
            nuevasEntidades.clear();
        }
    }

    // ------------------ SPAWN ------------------
    private TanqueEnemigo crearEnemigoAleatorio() {
        // Tomar solo los tipos que no sean JUGADOR
        TipoTanque[] tiposEnemigos = Arrays.stream(TipoTanque.values())
                .filter(t -> t != TipoTanque.JUGADOR1 && t != TipoTanque.JUGADOR2)
                .toArray(TipoTanque[]::new);

        TipoTanque tipo = tiposEnemigos[(int) (Math.random() * tiposEnemigos.length)];
        Direccion dir = Direccion.values()[(int) (Math.random() * Direccion.values().length)];

        int intentos = 0;
        while (intentos < 50) {
            Coordenada pos = new Coordenada(
                    (int) (Math.random() * ANCHO_MAPA),
                    (int) (Math.random() * ALTO_MAPA)
            );
            TanqueEnemigo nuevo = new TanqueEnemigo(pos, new Dimensiones(20, 20), dir, tipo, em);
            if (esPosicionValida(nuevo)) return nuevo;
            intentos++;
        }
        return null;
    }


    private PowerUp spawnPowerUpAleatorio() {
        if (Math.random() < 0.2 &&  powerupsActivos < 1) { // 20% de probabilidad y max 1 powerup en mapa
            TipoPowerUp tipo = TipoPowerUp.values()[(int) (Math.random() * TipoPowerUp.values().length)];

            int intentos = 0;
            while (intentos < 50) {
                PowerUp pu = new PowerUp(new Coordenada(
                        (int) (Math.random() * (ANCHO_MAPA - 20)),
                        (int) (Math.random() * (ALTO_MAPA - 20))
                ), new Dimensiones(20, 20),
                        tipo);
                if (esPosicionValida(pu)) {
                    powerupsActivos = 1;
                    return pu;
                }
                intentos++;
            }
        }
        return null;
    }

    private boolean esPosicionValida(Ente ente) {
        for (Ente e : entes) {
            if (e != ente && !e.permitePaso() && ente.intersecta(e)) {
                return false;
            }
        }
        return true;
    }


    // ------------------ EVENTO GRANADA ------------------
    private void destruirTodosEnemigos() {
        List<Ente> snapshot = new ArrayList<>(entes);
        List<Ente> eliminar = new ArrayList<>();

        for (Ente e : snapshot) {
            if (e.getTipoEnte() == TipoEnte.ENEMIGO) {
                TanqueEnemigo enemigo = (TanqueEnemigo) e;
                enemigo.destruir();  // solo marca como destruido
                eliminar.add(enemigo);
            }
        }

        for (Ente e : eliminar) {
            em.notificar(TipoEvento.TANQUE_DESTRUIDO, e);
        }
    }


}
