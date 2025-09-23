package juego;

import juego.entidades.*;
import juego.entidades.bloques.Bloque;
import juego.entidades.powerups.PowerUp;
import juego.entidades.powerups.TipoPowerUp;
import juego.entidades.tanques.*;
import juego.eventos.EventoManager;
import juego.eventos.TipoEvento;
import juego.gestores.SistemaColision;
import juego.gestores.GestorPowerUp;
import juego.utilidades.Direccion;
import juego.utilidades.Coordenada;
import juego.utilidades.Dimensiones;

import java.util.*;
import java.util.stream.Collectors;

public class Juego {

    private final List<Ente> entes = new ArrayList<>();
    private final Map<Class<? extends Ente>, List<Ente>> porTipo = new HashMap<>();
    private final SistemaColision sistemaColision = new SistemaColision();
    private final GestorPowerUp gestorPowerUp = new GestorPowerUp();

    // Spawn temporal
    private double contadorPowerUp = 0;
    private final double INTERVALO_SPAWN_POWERUP = 5000; // ms

    // ------------------ GESTIÓN DE ENTES ------------------
    public void agregarEnte(Ente e) {
        entes.add(e);
        porTipo.computeIfAbsent(e.getClass(), k -> new ArrayList<>()).add(e);
    }

    public void removerEnte(Ente e) {
        entes.remove(e);
        List<Ente> lista = porTipo.get(e.getClass());
        if (lista != null) lista.remove(e);
    }

    public <T extends Ente> List<T> getEntesDeTipo(Class<T> tipo) {
        return porTipo.getOrDefault(tipo, List.of())
                .stream()
                .map(tipo::cast)
                .collect(Collectors.toList());
    }

    public List<Ente> getEntes() { return new ArrayList<>(entes); }

    // ------------------ ACCIONES ------------------
    public void agregarJugador(TanqueJugador jugador) {
        agregarEnte(jugador);
    }

    public void moverJugador(int indice, Direccion dir) {
        List<TanqueJugador> jugadores = getEntesDeTipo(TanqueJugador.class);
        if (indice >= 0 && indice < jugadores.size()) {
            jugadores.get(indice).mover(dir);
            sistemaColision.chequearColisiones(jugadores.get(indice));
            EventoManager.getInstancia().notificar(TipoEvento.TANQUE_MOVIDO, jugadores.get(indice));
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
                    gestorPowerUp.activarPowerUp(jugador, pu.getTipoPowerUp(), pu.getTipoPowerUp().getDuracionMs());
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
            }
        }

        // --- Actualizar balas ---
        for (Bala bala : getEntesDeTipo(Bala.class)) {
            bala.actualizar();
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

        // --- Spawn temporal de PowerUps ---
        contadorPowerUp += deltaTime;
        if (contadorPowerUp >= INTERVALO_SPAWN_POWERUP) {
            contadorPowerUp = 0;
            PowerUp nuevo = generarPowerUpAleatorio();
            agregarEnte(nuevo);
            EventoManager.getInstancia().notificar(TipoEvento.POWERUP_SPAWN, nuevo);
        }

        // --- Actualizar efectos de powerups activos ---
        gestorPowerUp.actualizar(deltaTime);
    }

    private PowerUp generarPowerUpAleatorio() {
        // Posición aleatoria en el mapa (ajustar según dimensiones del mapa)
        Coordenada pos = new Coordenada((int)(Math.random() * 800), (int)(Math.random() * 600));
        Dimensiones dim = new Dimensiones(16, 16);

        // Tipo aleatorio
        TipoPowerUp tipo = TipoPowerUp.values()[(int)(Math.random() * TipoPowerUp.values().length)];

        return new PowerUp(pos, dim, tipo);
    }
}
