package org.controlador;

import org.modelo.Juego;
import org.modelo.entidades.tanques.TanqueJugador;
import org.modelo.entidades.tanques.TanqueEnemigo;
import org.modelo.eventos.EventoManager;
import org.modelo.eventos.TipoEvento;
import org.modelo.utilidades.Direccion;

public class EstadoPartida implements EstadoJuego {

    private final Juego juego;

    public EstadoPartida(int numJugadores) {
        this.juego = new Juego();
    }

    @Override
    public void actualizar(double deltaTime) {
        juego.actualizar(deltaTime);

        if (juego.getEntesDeTipo(TanqueJugador.class).isEmpty() ||
                juego.getEntesDeTipo(TanqueEnemigo.class).isEmpty()) {

            boolean victoria = juego.getEntesDeTipo(TanqueEnemigo.class).isEmpty();
            EventoManager.getInstancia().notificar(TipoEvento.MOSTRAR_FIN_PARTIDA, victoria);
        }
    }

    @Override
    public void manejarInput(String input, boolean presionada) {
        if (!presionada) return; // Solo actuamos al presionar

        switch (input) {
            // Jugador 1
            case "J1_ARRIBA" -> juego.moverJugador(0, Direccion.ARRIBA);
            case "J1_ABAJO" -> juego.moverJugador(0, Direccion.ABAJO);
            case "J1_IZQUIERDA" -> juego.moverJugador(0, Direccion.IZQUIERDA);
            case "J1_DERECHA" -> juego.moverJugador(0, Direccion.DERECHA);
            case "J1_DISPARO" -> juego.dispararJugador(0);

            // Jugador 2 (solo si hay 2 jugadores)
            case "J2_ARRIBA" -> { if (numJugadores > 1) juego.moverJugador(1, Direccion.ARRIBA); }
            case "J2_ABAJO" -> { if (numJugadores > 1) juego.moverJugador(1, Direccion.ABAJO); }
            case "J2_IZQUIERDA" -> { if (numJugadores > 1) juego.moverJugador(1, Direccion.IZQUIERDA); }
            case "J2_DERECHA" -> { if (numJugadores > 1) juego.moverJugador(1, Direccion.DERECHA); }
            case "J2_DISPARO" -> { if (numJugadores > 1) juego.dispararJugador(1); }
        }
    }


    public Juego getJuego() {
        return juego;
    }
}
