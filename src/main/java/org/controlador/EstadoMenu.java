package org.controlador;

public class EstadoMenu implements EstadoJuego {

    private final GestorEstados gestor;
    private int opcionJugadores = 0; // 0 = nada, 1 = 1 jugador, 2 = 2 jugadores

    public EstadoMenu(GestorEstados gestor) {
        this.gestor = gestor;
    }

    @Override
    public void actualizar(double deltaTime) {
        // No hay lógica de actualización en el menú
    }

    @Override
    public void manejarInput(String input, boolean presionada) {
        if (!presionada) return;

        switch (input) {
            case "UN_JUGADOR" -> opcionJugadores = 1;
            case "DOS_JUGADORES" -> opcionJugadores = 2;
        }

        if (opcionJugadores > 0) {
            gestor.iniciarPartida(opcionJugadores);
        }
    }

    public int getOpcionJugadores() {
        return opcionJugadores;
    }
}
