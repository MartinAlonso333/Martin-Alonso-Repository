package juego.estados;

import juego.Juego;
import juego.cargaDePartida.ParserXML;
import juego.entidades.tanques.TanqueEnemigo;
import juego.entidades.tanques.TanqueJugador;
import juego.utilidades.Direccion;

public class EstadoPartida implements EstadoJuego {

    private final GestorEstados gestor;
    private final Juego juego;
    private final int nivelActual;
    private final int totalJugadores;
    private final int TOTAL_NIVELES = 3;

    public EstadoPartida(GestorEstados gestor, int nivel, int numJugadores) {
        this.gestor = gestor;
        this.juego = new Juego();
        this.nivelActual = nivel;
        this.totalJugadores = numJugadores;

        String archivoNivel = "niveles/nivel" + nivel + ".xml";
        ParserXML.cargarNivel(juego, numJugadores, archivoNivel);
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
                case "J1_UP"    -> juego.moverJugador(0, Direccion.ARRIBA);
                case "J1_DOWN"  -> juego.moverJugador(0, Direccion.ABAJO);
                case "J1_LEFT"  -> juego.moverJugador(0, Direccion.IZQUIERDA);
                case "J1_RIGHT" -> juego.moverJugador(0, Direccion.DERECHA);
                case "J1_FIRE"  -> juego.dispararJugador(0);
            }
        }
        if (totalJugadores == 2) {
            switch (input) {
                case "J2_UP"    -> juego.moverJugador(1, Direccion.ARRIBA);
                case "J2_DOWN"  -> juego.moverJugador(1, Direccion.ABAJO);
                case "J2_LEFT"  -> juego.moverJugador(1, Direccion.IZQUIERDA);
                case "J2_RIGHT" -> juego.moverJugador(1, Direccion.DERECHA);
                case "J2_FIRE"  -> juego.dispararJugador(1);
            }
        }
    }

    private boolean juegoTerminado() {
        return juego.getEntesDeTipo(TanqueJugador.class).isEmpty();
    }
    private boolean nivelTerminado() {
        return juego.getEntesDeTipo(TanqueEnemigo.class).isEmpty();
    }
}
