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
            Coordenada coordenadasAntes = new Coordenada(jugador.getPosicion().getX(), jugador.getPosicion().getY());
            jugador.mover(dir);

            sistemaColision.actualizarPosicion(jugador, coordenadasAntes);
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

            // Chequear colisión con powerups
            for (PowerUp pu : getEntesDeTipo(PowerUp.class)) {
                if (pu.estaActivo() && jugador.intersecta(pu)) {
                    pu.getTipoPowerUp().aplicar(jugador); // granada notificará el evento
                    pu.setActivo(false);
                }
            }

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

                Coordenada coordenada = enemigo.getPosicion();
                agregarEnte(new Bloque(TipoBloque.TANQUE_DESTRUIDO, coordenada, new Dimensiones(20,20)));

                // Intentar spawnear powerup con probabilidad del 20%
                if (Math.random() < 0.2 && getEntesDeTipo(PowerUp.class).isEmpty()) {
                    PowerUp nuevo = generarPowerUpAleatorio();
                    if (nuevo != null) {
                        agregarEnte(nuevo);
                        em.notificar(TipoEvento.POWERUP_SPAWN, nuevo);
                    }
                }
            }
        }

        // --- Actualizar balas ---
        for (Bala bala : getEntesDeTipo(Bala.class)) {
            Coordenada coordenadasAntes = new Coordenada(bala.getPosicion().getX(), bala.getPosicion().getY());
            bala.actualizar();
            sistemaColision.actualizarPosicion(bala, coordenadasAntes);
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
                em.notificar(TipoEvento.BLOQUE_DESTRUIDO, bloque);
            }
        }

        // --- Agregar nuevas balas ---
        for (Bala b : nuevasBalas) agregarEnte(b);

        // --- Actualizar efectos de powerups activos ---
        gestorPowerUp.actualizar(deltaTime);
    }

    // ------------------ MÉTODOS DE POWERUP ------------------
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

    // ------------------ MÉTODO DE EVENTO GRANADA ------------------
    private void destruirTodosEnemigos() {
        for (TanqueEnemigo enemigo : getEntesDeTipo(TanqueEnemigo.class)) {
            enemigo.destruir();
            removerEnte(enemigo);
            em.notificar(TipoEvento.TANQUE_DESTRUIDO, enemigo);
        }
    }
}
