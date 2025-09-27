package org.modelo;

import org.modelo.entidades.*;
import org.modelo.entidades.bloques.Bloque;
import org.modelo.entidades.bloques.TipoBloque;
import org.modelo.entidades.powerups.PowerUp;
import org.modelo.entidades.powerups.TipoPowerUp;
import org.modelo.entidades.tanques.*;
import org.modelo.eventos.EventoManager;
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
    private final Map<Class<? extends Ente>, List<Ente>> porTipo = new HashMap<>();
    private final SistemaColisionGrilla sistemaColision = new SistemaColisionGrilla();
    private final GestorPowerUp gestorPowerUp = new GestorPowerUp();
    private final EventoManager em = EventoManager.getInstancia();

    private final int INTERVALO_SPAWN_ENEMIGO = 5000; // ms
    private long ultimoSpawnEnemigo = 0;

    private final int maxEnemigosTotales = 3; // máximo de enemigos a spawnear por nivel
    private int enemigosSpawneados = 0;

    // ------------------ CONSTRUCTOR ------------------
    public Juego() {
        // Suscripción al evento de granada
        em.registrar(TipoEvento.GRANADA_RECOGIDA, (data) -> destruirTodosEnemigos());
    }

    // ------------------ GESTIÓN DE ENTES ------------------
    public void agregarEnte(Ente e) {
        entes.add(e);
        porTipo.computeIfAbsent(e.getClass(), k -> new ArrayList<>()).add(e);
        sistemaColision.agregarEnte(e);
    }

    public void removerEnte(Ente e) {
        entes.remove(e);
        List<Ente> lista = porTipo.get(e.getClass());
        if (lista != null) lista.remove(e);
        sistemaColision.removerEnte(e);
    }

    public <T extends Ente> List<T> getEntesDeTipo(Class<T> tipo) {
        return porTipo.getOrDefault(tipo, List.of())
                .stream()
                .map(tipo::cast)
                .collect(Collectors.toList());
    }

    public List<Ente> getEntes() {
        return new ArrayList<>(entes);
    }

    // ------------------ ACCIONES ------------------
    public void agregarJugador(TanqueJugador jugador) {
        agregarEnte(jugador);
    }

    public void moverJugador(int indice, Direccion dir) {
        List<TanqueJugador> jugadores = getEntesDeTipo(TanqueJugador.class);
        if (indice >= 0 && indice < jugadores.size()) {
            TanqueJugador jugador = jugadores.get(indice);
            Coordenada antes = new Coordenada(jugador.getPosicion().getX(), jugador.getPosicion().getY());
            jugador.mover(dir);
            sistemaColision.actualizarPosicion(jugador, antes);
            sistemaColision.chequearColisiones(jugador);
            em.notificar(TipoEvento.TANQUE_MOVIDO, jugador);
        }
    }

    public void dispararJugador(int indice) {
        List<TanqueJugador> jugadores = getEntesDeTipo(TanqueJugador.class);
        if (indice >= 0 && indice < jugadores.size()) {
            Bala bala = jugadores.get(indice).disparar();
            if (bala != null) {
                agregarEnte(bala);
                sistemaColision.chequearColisiones(bala);
                em.notificar(TipoEvento.BALA_DISPARADA, bala);
            }
        }
    }

    // ------------------ ACTUALIZACIÓN ------------------
    public void actualizar(double deltaTime) {
        List<Bala> nuevasBalas = new ArrayList<>();

        // --- Actualizar jugadores ---
        for (TanqueJugador jugador : getEntesDeTipo(TanqueJugador.class)) {
            jugador.actualizar();
            sistemaColision.chequearColisiones(jugador);

            if (jugador.estaDestruido()) {
                removerEnte(jugador);
                em.notificar(TipoEvento.TANQUE_DESTRUIDO, jugador);
            }
        }

        // --- Actualizar enemigos ---
        for (TanqueEnemigo enemigo : getEntesDeTipo(TanqueEnemigo.class)) {
            enemigo.actualizar();
            sistemaColision.chequearColisiones(enemigo);

            Bala b = enemigo.disparar();
            if (b != null) {
                nuevasBalas.add(b);
                em.notificar(TipoEvento.BALA_DISPARADA, b);
            }

            if (enemigo.estaDestruido()) {
                removerEnte(enemigo);
                em.notificar(TipoEvento.TANQUE_DESTRUIDO, enemigo);
            }
        }

        // --- Spawn de enemigos (máximo 3 por nivel) ---
        if (System.currentTimeMillis() - ultimoSpawnEnemigo > INTERVALO_SPAWN_ENEMIGO) {
            if (enemigosSpawneados < maxEnemigosTotales) {
                TanqueEnemigo nuevo = crearEnemigoAleatorio();
                if (nuevo != null) {
                    agregarEnte(nuevo);
                    enemigosSpawneados++;
                }
            }
            ultimoSpawnEnemigo = System.currentTimeMillis();
        }

        // --- Actualizar balas ---
        for (Bala bala : getEntesDeTipo(Bala.class)) {
            Coordenada antes = new Coordenada(bala.getPosicion().getX(), bala.getPosicion().getY());
            bala.actualizar();
            sistemaColision.actualizarPosicion(bala, antes);
            sistemaColision.chequearColisiones(bala);
            if (bala.estaDestruido()) removerEnte(bala);
        }

        // --- Actualizar powerups ---
        gestorPowerUp.actualizar(deltaTime);
        for (PowerUp pu : getEntesDeTipo(PowerUp.class)) {
            if (pu.estaDestruido()) removerEnte(pu);
        }

        // --- Agregar nuevas balas ---
        for (Bala b : nuevasBalas) agregarEnte(b);
    }

    // ------------------ MÉTODOS DE ENEMIGOS ------------------
    private TanqueEnemigo crearEnemigoAleatorio() {
        TipoTanqueEnemigo tipo = TipoTanqueEnemigo.values()[(int)(Math.random() * TipoTanqueEnemigo.values().length)];
        Direccion dir = Direccion.values()[(int)(Math.random() * Direccion.values().length)];

        TanqueEnemigo nuevo = null;
        int intentos = 0;
        while (intentos < 50) {
            Coordenada pos = new Coordenada(
                    (int)(Math.random() * ANCHO_MAPA),
                    (int)(Math.random() * ALTO_MAPA)
            );
            nuevo = new TanqueEnemigo(pos, new Dimensiones(32, 32), dir, tipo);
            if (esPosicionValida(nuevo)) {
                return nuevo;
            }
            intentos++;
            nuevo = null;
        }
        return null;
    }

    // ------------------ MÉTODOS DE POWERUP ------------------
    private boolean esPosicionValida(Ente ente) {
        for (Bloque bloque : getEntesDeTipo(Bloque.class)) {
            if (!bloque.permitePaso() && ente.intersecta(bloque)) return false;
        }
        return true;
    }

    // ------------------ EVENTO GRANADA ------------------
    private void destruirTodosEnemigos() {
        for (TanqueEnemigo enemigo : getEntesDeTipo(TanqueEnemigo.class)) {
            enemigo.destruir();
            removerEnte(enemigo);
            em.notificar(TipoEvento.TANQUE_DESTRUIDO, enemigo);
        }
    }

    // ------------------ GETTERS ------------------
    public int getEnemigosSpawneados() { return enemigosSpawneados; }
    public int getMaxEnemigosTotales() { return maxEnemigosTotales; }
}
