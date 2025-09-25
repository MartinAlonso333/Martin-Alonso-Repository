package juego;

import juego.entidades.*;
import juego.entidades.bloques.Bloque;
import juego.entidades.powerups.PowerUp;
import juego.entidades.powerups.TipoPowerUp;
import juego.entidades.tanques.*;
import juego.eventos.EventoManager;
import juego.eventos.TipoEvento;
import juego.gestores.SistemaColisionGrilla;
import juego.gestores.GestorPowerUp;
import juego.utilidades.Direccion;
import juego.utilidades.Coordenada;
import juego.utilidades.Dimensiones;

import java.util.*;
import java.util.stream.Collectors;

public class Juego {

    private static final int ANCHO_MAPA = 800;
    private static final int ALTO_MAPA = 600;

    private final List<Ente> entes = new ArrayList<>();
    private final Map<Class<? extends Ente>, List<Ente>> porTipo = new HashMap<>();
    private final SistemaColisionGrilla sistemaColision = new SistemaColisionGrilla();
    private final GestorPowerUp gestorPowerUp = new GestorPowerUp();

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
            int oldX = jugador.getPosicion().getX();
            int oldY = jugador.getPosicion().getY();

            jugador.mover(dir);

            // Actualiza la grilla
            sistemaColision.actualizarPosicion(jugador, oldX, oldY);
            sistemaColision.chequearColisiones(jugador);

            EventoManager.getInstancia().notificar(TipoEvento.TANQUE_MOVIDO, jugador);
        }
    }

    public void dispararJugador(int indice) {
        List<TanqueJugador> jugadores = getEntesDeTipo(TanqueJugador.class);
        if (indice >= 0 && indice < jugadores.size()) {
            Bala bala = jugadores.get(indice).disparar();
            if (bala != null) {
                agregarEnte(bala);
                sistemaColision.chequearColisiones(bala);
                EventoManager.getInstancia().notificar(TipoEvento.BALA_DISPARADA, bala);
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

            // Chequear colisión con powerups
            for (PowerUp pu : getEntesDeTipo(PowerUp.class)) {
                if (pu.estaActivo() && jugador.intersecta(pu)) {
                    if (pu.getTipoPowerUp() == TipoPowerUp.GRANADA) {
                        for (TanqueEnemigo enemigo : getEntesDeTipo(TanqueEnemigo.class)) {
                            enemigo.destruir();
                        }
                        EventoManager.getInstancia().notificar(TipoEvento.GRANADA_EXPLOTADA, pu);
                    } else {
                        gestorPowerUp.activarPowerUp(jugador, pu.getTipoPowerUp());
                    }
                    pu.setActivo(false);
                    EventoManager.getInstancia().notificar(TipoEvento.POWERUP_RECOGIDO, pu);
                }
            }

            if (jugador.estaDestruido()) {
                removerEnte(jugador);
                EventoManager.getInstancia().notificar(TipoEvento.TANQUE_DESTRUIDO, jugador);
            }
        }

        // --- Actualizar enemigos ---
        for (TanqueEnemigo enemigo : getEntesDeTipo(TanqueEnemigo.class)) {
            enemigo.actualizar();
            sistemaColision.chequearColisiones(enemigo);

            Bala b = enemigo.disparar();
            if (b != null) {
                nuevasBalas.add(b);
                EventoManager.getInstancia().notificar(TipoEvento.BALA_DISPARADA, b);
            }

            if (enemigo.estaDestruido()) {
                removerEnte(enemigo);
                EventoManager.getInstancia().notificar(TipoEvento.TANQUE_DESTRUIDO, enemigo);

                // Intentar spawnear powerup con probabilidad del 20%
                if (Math.random() < 0.2 && getEntesDeTipo(PowerUp.class).isEmpty()) {
                    PowerUp nuevo = generarPowerUpAleatorio();
                    if (nuevo != null) {
                        agregarEnte(nuevo);
                        EventoManager.getInstancia().notificar(TipoEvento.POWERUP_SPAWN, nuevo);
                    }
                }
            }
        }

        // --- Actualizar balas ---
        for (Bala bala : getEntesDeTipo(Bala.class)) {
            int oldX = bala.getPosicion().getX();
            int oldY = bala.getPosicion().getY();
            bala.actualizar();
            sistemaColision.actualizarPosicion(bala, oldX, oldY);
            sistemaColision.chequearColisiones(bala);

            if (bala.estaDestruido()) removerEnte(bala);
        }

        // --- Actualizar powerups ---
        for (PowerUp pu : getEntesDeTipo(PowerUp.class)) {
            pu.actualizar();
            if (pu.estaDestruido()) removerEnte(pu);
        }

        // --- Actualizar bloques ---
        for (Bloque bloque : getEntesDeTipo(Bloque.class)) {
            bloque.actualizar();
            sistemaColision.chequearColisiones(bloque);
            if (bloque.estaDestruido()) {
                removerEnte(bloque);
                EventoManager.getInstancia().notificar(TipoEvento.BLOQUE_DESTRUIDO, bloque);
            }
        }

        // --- Agregar nuevas balas ---
        for (Bala b : nuevasBalas) agregarEnte(b);

        // --- Actualizar efectos de powerups activos ---
        gestorPowerUp.actualizar(deltaTime);
    }

    // ------------------ GENERAR POWERUP ------------------
    private PowerUp generarPowerUpAleatorio() {
        Dimensiones dim = new Dimensiones(16, 16);
        TipoPowerUp tipo = TipoPowerUp.values()[(int)(Math.random() * TipoPowerUp.values().length)];

        int intentos = 0;
        PowerUp nuevo = null;

        while (intentos < 50) {
            Coordenada pos = new Coordenada(
                    (int)(Math.random() * ANCHO_MAPA),
                    (int)(Math.random() * ALTO_MAPA)
            );
            nuevo = new PowerUp(pos, dim, tipo);
            if (esPosicionValida(nuevo)) break;
            nuevo = null;
            intentos++;
        }
        return nuevo;
    }

    private boolean esPosicionValida(PowerUp pu) {
        for (Bloque bloque : getEntesDeTipo(Bloque.class)) {
            if (!bloque.permitePaso() && pu.intersecta(bloque)) {
                return false;
            }
        }
        return true;
    }
}
