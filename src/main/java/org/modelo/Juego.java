package org.modelo;

import org.modelo.entidades.*;
import org.modelo.entidades.bloques.Bloque;
import org.modelo.entidades.powerups.PowerUp;
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

    private final int maxEnemigosTotales = 3;
    private int enemigosSpawneados = 0;

    public Juego() {
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
        porTipo.getOrDefault(e.getClass(), List.of()).remove(e);
        sistemaColision.removerEnte(e);
    }

    public <T extends Ente> List<T> getEntesDeTipo(Class<T> tipo) {
        return porTipo.getOrDefault(tipo, List.of()).stream()
                .map(tipo::cast)
                .collect(Collectors.toList());
    }

    public List<Ente> getEntes() { return new ArrayList<>(entes); }

    // ------------------ ACCIONES ------------------
    public void moverJugador(int indice, Direccion dir) {
        List<TanqueJugador> jugadores = getEntesDeTipo(TanqueJugador.class);
        if (indice < 0 || indice >= jugadores.size()) return;

        TanqueJugador jugador = jugadores.get(indice);
        Coordenada antes = new Coordenada(jugador.getPosicion().getPixelX(), jugador.getPosicion().getPixelY());
        jugador.mover(dir);
        sistemaColision.actualizarPosicion(jugador, antes);
        sistemaColision.chequearColisiones(jugador);
        em.notificar(TipoEvento.TANQUE_MOVIDO, jugador);
    }

    public void dispararJugador(int indice) {
        List<TanqueJugador> jugadores = getEntesDeTipo(TanqueJugador.class);
        if (indice < 0 || indice >= jugadores.size()) return;

        Bala bala = jugadores.get(indice).disparar();
        if (bala != null) {
            agregarEnte(bala);
            sistemaColision.chequearColisiones(bala);
            em.notificar(TipoEvento.BALA_DISPARADA, bala);
        }
    }

    // ------------------ ACTUALIZACIÓN ------------------
    public void actualizar(double deltaTime) {
        List<Bala> nuevasBalas = new ArrayList<>();

        // Actualizar jugadores
        for (TanqueJugador jugador : getEntesDeTipo(TanqueJugador.class)) {
            Coordenada antes = new Coordenada(jugador.getPosicion().getPixelX(), jugador.getPosicion().getPixelY());
            jugador.actualizar(deltaTime);
            sistemaColision.actualizarPosicion(jugador, antes);
            sistemaColision.chequearColisiones(jugador);
            if (jugador.estaDestruido()) {
                removerEnte(jugador);
                em.notificar(TipoEvento.TANQUE_DESTRUIDO, jugador);
            }
        }

        // Actualizar enemigos
        for (TanqueEnemigo enemigo : getEntesDeTipo(TanqueEnemigo.class)) {
            Coordenada antes = new Coordenada(enemigo.getPosicion().getPixelX(), enemigo.getPosicion().getPixelY());
            enemigo.actualizar(deltaTime);
            sistemaColision.actualizarPosicion(enemigo, antes);
            sistemaColision.chequearColisiones(enemigo);

            Bala b = enemigo.disparar();
            if (b != null) nuevasBalas.add(b);

            if (enemigo.estaDestruido()) {
                removerEnte(enemigo);
                em.notificar(TipoEvento.TANQUE_DESTRUIDO, enemigo);
            }
        }

        // Spawn enemigos
        if (System.currentTimeMillis() - ultimoSpawnEnemigo > INTERVALO_SPAWN_ENEMIGO
                && enemigosSpawneados < maxEnemigosTotales) {
            TanqueEnemigo nuevo = crearEnemigoAleatorio();
            if (nuevo != null) {
                agregarEnte(nuevo);
                enemigosSpawneados++;
            }
            ultimoSpawnEnemigo = System.currentTimeMillis();
        }

        // Actualizar balas
        for (Bala bala : getEntesDeTipo(Bala.class)) {
            Coordenada antes = new Coordenada(bala.getPosicion().getPixelX(), bala.getPosicion().getPixelY());
            bala.actualizar(deltaTime);
            sistemaColision.actualizarPosicion(bala, antes);
            sistemaColision.chequearColisiones(bala);
            if (bala.estaDestruido()) removerEnte(bala);
        }

        // Actualizar powerups
        gestorPowerUp.actualizar(deltaTime);
        for (PowerUp pu : getEntesDeTipo(PowerUp.class)) {
            if (pu.estaDestruido()) removerEnte(pu);
        }

        // Agregar nuevas balas
        for (Bala b : nuevasBalas) agregarEnte(b);
    }

    // ------------------ ENEMIGOS ------------------
    private TanqueEnemigo crearEnemigoAleatorio() {
        TipoTanque tipo = TipoTanque.values()[(int) (Math.random() * TipoTanque.values().length)];
        Direccion dir = Direccion.values()[(int) (Math.random() * Direccion.values().length)];

        int intentos = 0;
        while (intentos < 50) {
            Coordenada pos = new Coordenada(
                    (int) (Math.random() * ANCHO_MAPA),
                    (int) (Math.random() * ALTO_MAPA)
            );
            TanqueEnemigo nuevo = new TanqueEnemigo(pos, new Dimensiones(20, 20), dir, tipo);
            if (esPosicionValida(nuevo)) return nuevo;
            intentos++;
        }
        return null;
    }

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
