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
    private final Map<Class<? extends Ente>, List<Ente>> porTipo = new HashMap<>();
    private final GestorPowerUp gestorPowerUp = new GestorPowerUp();
    private final SistemaColisionGrilla sistemaColision;
    private final GestorEventos em;

    private final int INTERVALO_SPAWN_ENEMIGO = 10000; // ms
    private long ultimoSpawnEnemigo = 0;
    private final int MAX_ENEMIGOS_SIMULTANEOS = 5;
    private final int MAX_ENEMIGOS_SPAWNEADOS = 3;
    private int enemigosSpawneados = 0;

    public Juego(GestorEventos gestorEventos) {
        em = gestorEventos;
        sistemaColision = new SistemaColisionGrilla(gestorPowerUp, em);
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
        if (lista != null) {
            lista.remove(e);
            if (lista.isEmpty()) {
                porTipo.remove(e.getClass());
            }
        }
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
            if (b != null) {
                nuevasBalas.add(b);
            }
            if (enemigo.estaDestruido()) {
                Coordenada posBloque = new Coordenada(enemigo.getPosicion().getPixelX(), enemigo.getPosicion().getPixelY());
                Dimensiones dimBloque = new Dimensiones(20, 20);
                Bloque bloqueNuevo = new Bloque(TipoBloque.TANQUE_DESTRUIDO, posBloque,dimBloque );
                removerEnte(enemigo);
                agregarEnte(bloqueNuevo);
                PowerUp nuevo = spawnPowerUpAleatorio();
                if (nuevo != null) agregarEnte(nuevo);
                em.notificar(TipoEvento.TANQUE_DESTRUIDO, enemigo);
            }
        }

        //Actualizar bloques
        for (Bloque bloque : getEntesDeTipo(Bloque.class)) {
            if (bloque.estaDestruido()) {
                removerEnte(bloque);
            }
        }

        // Spawn enemigos
        List<TanqueEnemigo> enemigosActivos = getEntesDeTipo(TanqueEnemigo.class);
        if (System.currentTimeMillis() - ultimoSpawnEnemigo > INTERVALO_SPAWN_ENEMIGO
                && enemigosSpawneados < MAX_ENEMIGOS_SPAWNEADOS
                && enemigosActivos.size() < MAX_ENEMIGOS_SIMULTANEOS) {
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
            if (bala.estaDestruido()) {
                removerEnte(bala);
            }
        }

        // Actualizar powerups
        gestorPowerUp.actualizar(deltaTime);
        for (PowerUp pu : getEntesDeTipo(PowerUp.class)) {
            if (pu.estaDestruido()) {
                removerEnte(pu);
            }
        }

        // Agregar nuevas balas
        for (Bala b : nuevasBalas) agregarEnte(b);
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
            TanqueEnemigo nuevo = new TanqueEnemigo(pos, new Dimensiones(20, 20), dir, tipo);
            if (esPosicionValida(nuevo)) return nuevo;
            intentos++;
        }
        return null;
    }


    private PowerUp spawnPowerUpAleatorio() {
        if (Math.random() < 0.2 &&  getEntesDeTipo(PowerUp.class).size() < 1) { // 20% de probabilidad y max 1 powerup en mapa
            TipoPowerUp tipo = TipoPowerUp.values()[(int) (Math.random() * TipoPowerUp.values().length)];

            int intentos = 0;
            while (intentos < 50) {
                PowerUp pu = new PowerUp(new Coordenada(
                        (int) (Math.random() * (ANCHO_MAPA - 20)),
                        (int) (Math.random() * (ALTO_MAPA - 20))
                ), new Dimensiones(20, 20),
                        tipo);
                if (esPosicionValida(pu)) return pu;
                intentos++;
            }
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
}
