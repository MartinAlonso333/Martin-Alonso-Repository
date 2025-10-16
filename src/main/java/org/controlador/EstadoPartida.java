package org.controlador;

import org.modelo.Juego;
import org.modelo.entidades.TipoEnte;
import org.modelo.entidades.tanques.TanqueJugador;
import org.modelo.entidades.tanques.TanqueEnemigo;
import org.modelo.eventos.EventoManager;
import org.modelo.eventos.GestorEventos;
import org.modelo.eventos.TipoEvento;
import org.modelo.utilidades.Direccion;
import org.modelo.cargaDePartida.ParserXML;
import org.modelo.entidades.RegistroEntidades;

public class EstadoPartida implements EstadoJuego {

    private static final int CANTIDAD_NIVELES = 3;
    private Juego juego;
    private final int numJugadores;
    private int nivelActual;
    private RegistroEntidades registro;
    private GestorEventos em;

    public EstadoPartida(int numJugadores, GestorEventos gestorEventos) {
        this.numJugadores = numJugadores;
        this.registro = new RegistroEntidades(numJugadores, gestorEventos);
        this.nivelActual = 1;
        em = gestorEventos;
        iniciarNivel(nivelActual);
        em.registrar(TipoEvento.BASE_DESTRUIDA, (obj) -> partidaPerdida());
    }

    @Override
    public void actualizar(double deltaTime) {
        juego.actualizar(deltaTime);

        if (nivelTerminado()) cambiarNivel();
        if (derrota()) partidaPerdida();
    }

    private void iniciarNivel(int nivelActual) {
        this.juego = new Juego(em);
        ParserXML.cargarNivel("nivel" + nivelActual, juego, registro);
    }

    private void cambiarNivel() {
        if (nivelActual >= CANTIDAD_NIVELES) {
            partidaGanada();
            return;
        }
        nivelActual++;
        iniciarNivel(nivelActual);

        em.notificar(TipoEvento.NIVEL_CARGADO, juego);
    }

    private boolean nivelTerminado() {
        return juego.getEntesDeTipo(TipoEnte.ENEMIGO).isEmpty();
    }

    private boolean derrota() {
        return juego.getJugadoresMap().values().stream()
                .allMatch(TanqueJugador::estaDestruido);
    }


    private void partidaGanada() {
        em.notificar(TipoEvento.MOSTRAR_FIN_PARTIDA, true);
    }

    private void partidaPerdida() {
        em.notificar(TipoEvento.MOSTRAR_FIN_PARTIDA, false);
    }

    @Override
    public void manejarInput(String input, boolean presionada) {
        if (!presionada) {
            switch (input) {
                case "J1_ARRIBA", "J1_ABAJO", "J1_IZQUIERDA", "J1_DERECHA" ->
                        juego.detenerJugador(0);
                case "J2_ARRIBA", "J2_ABAJO", "J2_IZQUIERDA", "J2_DERECHA" -> {
                    if (numJugadores > 1)
                        juego.detenerJugador(1);
                }
            }
            return;
        }

        // Jugador 1
        TanqueJugador j1 = juego.getJugadoresMap().get(0);
        if (j1 != null && j1.estaActivo()) {
            switch (input) {
                case "J1_ARRIBA" -> juego.moverJugador(0, Direccion.ARRIBA);
                case "J1_ABAJO" -> juego.moverJugador(0, Direccion.ABAJO);
                case "J1_IZQUIERDA" -> juego.moverJugador(0, Direccion.IZQUIERDA);
                case "J1_DERECHA" -> juego.moverJugador(0, Direccion.DERECHA);
                case "J1_DISPARO" -> juego.dispararJugador(0);
            }
        }

        // Jugador 2
        TanqueJugador j2 = juego.getJugadoresMap().get(1);
        if (j2 != null && j2.estaActivo() && numJugadores > 1) {
            switch (input) {
                case "J2_ARRIBA" -> juego.moverJugador(1, Direccion.ARRIBA);
                case "J2_ABAJO" -> juego.moverJugador(1, Direccion.ABAJO);
                case "J2_IZQUIERDA" -> juego.moverJugador(1, Direccion.IZQUIERDA);
                case "J2_DERECHA" -> juego.moverJugador(1, Direccion.DERECHA);
                case "J2_DISPARO" -> juego.dispararJugador(1);
            }
        }
    }


    public Juego getJuego() { return juego; }
}
