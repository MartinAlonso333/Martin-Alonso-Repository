package org.modelo.estados;

import org.modelo.Juego;
import org.vista.cargaDePartida.ParserXML;
import org.modelo.entidades.tanques.TanqueEnemigo;
import org.modelo.entidades.tanques.TanqueJugador;
import org.vista.controladores.GestorSonidos;
import org.modelo.utilidades.Direccion;

public class EstadoPartida implements EstadoJuego {

    private final GestorEstados gestor;
    private final Juego juego;
    private final int nivelActual;
    private final int totalJugadores;
    private final int TOTAL_NIVELES = 3;
    private final GestorSonidos sonidos;

    public EstadoPartida(GestorEstados gestor, int nivel, int numJugadores) {
        this.gestor = gestor;
        this.juego = new Juego();
        this.nivelActual = nivel;
        this.totalJugadores = numJugadores;
        this.sonidos = new GestorSonidos();

        String archivoNivel = "niveles/nivel" + nivel + ".xml";
        ParserXML.cargarNivel(juego, numJugadores, archivoNivel);

        sonidos.reproducirMusicaLoop();
    }

    @Override
    public void actualizar(double deltaTime) {
        juego.actualizar(deltaTime);

        if (juegoTerminado()) {
            gestor.cambiarAPantallaPerder();
        }

        if (nivelTerminado()) {
            if (nivelActual < TOTAL_NIVELES) {
                gestor.cambiarANivel(nivelActual + 1, totalJugadores);
            } else {
                gestor.cambiarAFinPartida();
            }
        }
    }

    @Override
    public void manejarInput(String input) {
        // Jugadores
        if (totalJugadores >= 1) {
            switch (input) {
                case "J1_ARRIBA"    -> juego.moverJugador(0, Direccion.ARRIBA);
                case "J1_ABAJO"  -> juego.moverJugador(0, Direccion.ABAJO);
                case "J1_IZQUIERDA"  -> juego.moverJugador(0, Direccion.IZQUIERDA);
                case "J1_DERECHA" -> juego.moverJugador(0, Direccion.DERECHA);
                case "J1_DISPARO"  -> juego.dispararJugador(0);
            }
        }
        if (totalJugadores == 2) {
            switch (input) {
                case "J2_ARRIBA"    -> juego.moverJugador(1, Direccion.ARRIBA);
                case "J2_ABAJO"  -> juego.moverJugador(1, Direccion.ABAJO);
                case "J2_IZQUIERDA"  -> juego.moverJugador(1, Direccion.IZQUIERDA);
                case "J2_DERECHA" -> juego.moverJugador(1, Direccion.DERECHA);
                case "J2_DISPARO"  -> juego.dispararJugador(1);
            }
        }
    }

    private boolean juegoTerminado() {
        return juego.getEntesDeTipo(TanqueJugador.class).isEmpty();
    }

    private boolean nivelTerminado() {
        // Termina el nivel si no hay enemigos vivos y ya se spawneó la cantidad máxima
        return juego.getEntesDeTipo(TanqueEnemigo.class).isEmpty() &&
                juego.getEnemigosSpawneados() >= juego.getMaxEnemigosTotales();
    }
}
