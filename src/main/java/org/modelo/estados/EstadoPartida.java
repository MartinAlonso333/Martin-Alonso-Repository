package org.modelo.estados;

import org.modelo.Juego;
import org.vista.cargaDePartida.ParserXML;
import org.modelo.entidades.tanques.TanqueEnemigo;
import org.modelo.entidades.tanques.TanqueJugador;

public class EstadoPartida implements EstadoJuego {

    private final GestorEstados gestor;
    private final int nivelActual;
    private final int totalJugadores;
    private final int TOTAL_NIVELES = 3;

    private final Juego juego;
    private final PantallaJuego pantalla;

    public EstadoPartida(GestorEstados gestor, int nivel, int numJugadores) {
        this.gestor = gestor;
        this.nivelActual = nivel;
        this.totalJugadores = numJugadores;

        // Crear el juego y cargar el nivel
        this.juego = new Juego();
        String archivoNivel = "niveles/nivel" + nivel + ".xml";
        ParserXML.cargarNivel(juego, numJugadores, archivoNivel);

        // Crear la pantalla de juego asociada a este estado
        this.pantalla = new PantallaJuego(gestor.getStage(), numJugadores, juego);
        this.pantalla.mostrar();
    }

    @Override
    public void actualizar(double deltaTime) {
        juego.actualizar(deltaTime);

        // Chequear si terminó el nivel o la partida
        if (nivelTerminado()) {
            if (nivelActual < TOTAL_NIVELES) {
                gestor.cambiarANivel(nivelActual + 1, totalJugadores);
            } else {
                gestor.cambiarAFinPartida();
            }
        } else if (juegoTerminado()) {
            gestor.cambiarAFinPartida();
        }
    }

    @Override
    public void manejarInput(String input) {

    }


    private boolean juegoTerminado() {
        return juego.getEntesDeTipo(TanqueJugador.class).isEmpty();
    }

    private boolean nivelTerminado() {
        return juego.getEntesDeTipo(TanqueEnemigo.class).isEmpty();
    }
}