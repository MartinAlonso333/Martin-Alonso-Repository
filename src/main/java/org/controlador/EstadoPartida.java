package org.controlador;

import org.modelo.Juego;
import org.modelo.entidades.tanques.TanqueJugador;
import org.modelo.entidades.tanques.TanqueEnemigo;
import org.modelo.utilidades.Direccion;
import org.vista.cargaDePartida.ParserXML;
import org.vista.cargaDePartida.RegistroEntidades;

public class EstadoPartida implements EstadoJuego {

    private int nivelActual;
    private final int totalJugadores;
    private Juego juego;
    private final int TOTAL_NIVELES = 3;

    // Eventos que la vista puede escuchar
    private Runnable onJuegoTerminado;
    private Runnable onNivelCompleto;

    public EstadoPartida(int nivel, int numJugadores) {
        this.nivelActual = nivel;
        this.totalJugadores = numJugadores;
        iniciarNivel(nivel);
    }

    private void iniciarNivel(int nivel) {
        this.juego = new Juego();
        RegistroEntidades registro = new RegistroEntidades(totalJugadores);
        ParserXML.cargarNivel("nivel" + nivel, juego, registro);
    }

    public Juego getJuego() {
        return juego;
    }

    public void setOnJuegoTerminado(Runnable callback) {
        this.onJuegoTerminado = callback;
    }

    public void setOnNivelCompleto(Runnable callback) {
        this.onNivelCompleto = callback;
    }

    @Override
    public void actualizar(double deltaTime) {
        juego.actualizar(deltaTime);

        if (nivelTerminado()) {
            if (nivelActual < TOTAL_NIVELES) {
                nivelActual++;
                iniciarNivel(nivelActual);
                if (onNivelCompleto != null) onNivelCompleto.run();
            } else {
                if (onJuegoTerminado != null) onJuegoTerminado.run();
            }
        } else if (juegoTerminado()) {
            if (onJuegoTerminado != null) onJuegoTerminado.run();
        }
    }

    @Override
    public void manejarInput(String input, boolean presionada) {
        if (!presionada) {
            switch (input) {
                case "J1_ARRIBA", "J1_ABAJO", "J1_IZQUIERDA", "J1_DERECHA" ->
                        juego.getEntesDeTipo(TanqueJugador.class).get(0).detenerMovimiento();
                case "J2_ARRIBA", "J2_ABAJO", "J2_IZQUIERDA", "J2_DERECHA" -> {
                    if (juego.getEntesDeTipo(TanqueJugador.class).size() > 1)
                        juego.getEntesDeTipo(TanqueJugador.class).get(1).detenerMovimiento();
                }
            }
            return;
        }

        switch (input) {
            case "J1_ARRIBA" -> juego.moverJugador(0, Direccion.ARRIBA);
            case "J1_ABAJO" -> juego.moverJugador(0, Direccion.ABAJO);
            case "J1_IZQUIERDA" -> juego.moverJugador(0, Direccion.IZQUIERDA);
            case "J1_DERECHA" -> juego.moverJugador(0, Direccion.DERECHA);
            case "J1_DISPARO" -> juego.dispararJugador(0);

            case "J2_ARRIBA" -> juego.moverJugador(1, Direccion.ARRIBA);
            case "J2_ABAJO" -> juego.moverJugador(1, Direccion.ABAJO);
            case "J2_IZQUIERDA" -> juego.moverJugador(1, Direccion.IZQUIERDA);
            case "J2_DERECHA" -> juego.moverJugador(1, Direccion.DERECHA);
            case "J2_DISPARO" -> juego.dispararJugador(1);
        }
    }

    private boolean juegoTerminado() {
        return juego.getEntesDeTipo(TanqueJugador.class).isEmpty();
    }

    private boolean nivelTerminado() {
        return juego.getEntesDeTipo(TanqueEnemigo.class).isEmpty();
    }
}
