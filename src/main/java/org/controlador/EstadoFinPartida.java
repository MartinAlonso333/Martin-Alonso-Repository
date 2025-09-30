package org.controlador;

public class EstadoFinPartida implements EstadoJuego {

    private final GestorEstados gestor;
    private final boolean victoria;

    public EstadoFinPartida(GestorEstados gestor, boolean victoria) {
        this.gestor = gestor;
        this.victoria = victoria;
    }

    @Override
    public void actualizar(double deltaTime) {
        // No hay lógica de actualización necesaria
    }

    @Override
    public void manejarInput(String input, boolean presionada) {
    }

    public void volverAlMenu() {
        gestor.cambiarAMenu();
    }

    public boolean esVictoria() {
        return victoria;
    }
}
