package org.controlador;

import org.modelo.Juego;
import org.modelo.entidades.tanques.TanqueJugador;
import org.modelo.entidades.tanques.TanqueEnemigo;
import org.modelo.eventos.EventoManager;
import org.modelo.eventos.TipoEvento;
import org.modelo.utilidades.Direccion;
import org.vista.cargaDePartida.ParserXML;
import org.vista.cargaDePartida.RegistroEntidades;

public class EstadoPartida implements EstadoJuego {

    private static final int CANTIDAD_NIVELES = 3;
    private Juego juego = null;
    private final int numJugadores;
    private int nivelActual;
    private RegistroEntidades registro;

    public EstadoPartida(int numJugadores) {
        this.numJugadores = numJugadores;
        this.registro = new RegistroEntidades(numJugadores);
        this.nivelActual = 1;
        iniciarNivel(nivelActual);
    }

    @Override
    public void actualizar(double deltaTime) {
        juego.actualizar(deltaTime);

        if (nivelTerminado()) {
            cambiarNivel();
        }
        if (derrota()) {
            partidaPerdida();
        }
    }

    private void iniciarNivel(int nivelActual) {
        this.juego = new Juego();
        ParserXML.cargarNivel("nivel" + nivelActual, juego, registro);
    }

    private void cambiarNivel() {
        if (nivelActual >= CANTIDAD_NIVELES) {
            partidaGanada();
            return;
        }
        nivelActual++;
        iniciarNivel(nivelActual);
    }

    private boolean nivelTerminado() {
        return juego.getEntesDeTipo(TanqueEnemigo.class).isEmpty();
    }
    private boolean derrota() {
        return juego.getEntesDeTipo(TanqueJugador.class).isEmpty();
    }

    private void partidaGanada() {
        EventoManager.getInstancia().notificar(TipoEvento.MOSTRAR_FIN_PARTIDA, true);
    }

    private void partidaPerdida() {
        EventoManager.getInstancia().notificar(TipoEvento.MOSTRAR_FIN_PARTIDA, false);
    }

    @Override
    public void manejarInput(String input, boolean presionada) {
        if (!presionada) return;

        // Jugador 1
        switch (input) {
            case "J1_ARRIBA" -> juego.moverJugador(0, Direccion.ARRIBA);
            case "J1_ABAJO" -> juego.moverJugador(0, Direccion.ABAJO);
            case "J1_IZQUIERDA" -> juego.moverJugador(0, Direccion.IZQUIERDA);
            case "J1_DERECHA" -> juego.moverJugador(0, Direccion.DERECHA);
            case "J1_DISPARO" -> juego.dispararJugador(0);
        }

        // Jugador 2 solo si hay 2
        if (numJugadores > 1) {
            switch (input) {
                case "J2_ARRIBA" -> juego.moverJugador(1, Direccion.ARRIBA);
                case "J2_ABAJO" -> juego.moverJugador(1, Direccion.ABAJO);
                case "J2_IZQUIERDA" -> juego.moverJugador(1, Direccion.IZQUIERDA);
                case "J2_DERECHA" -> juego.moverJugador(1, Direccion.DERECHA);
                case "J2_DISPARO" -> juego.dispararJugador(1);
            }
        }
    }

    public Juego getJuego() {
        return juego;
    }
}
