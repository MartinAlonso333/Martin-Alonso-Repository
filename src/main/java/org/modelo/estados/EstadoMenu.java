// EstadoMenu.java
package org.modelo.estados;


public class EstadoMenu implements EstadoJuego {

    private GestorEstados gestor;
    private MenuVista vista;

    public EstadoMenu(GestorEstados gestor) {
        this.gestor = gestor;

        // Pasamos un solo Consumer<Integer> para manejar 1 o 2 jugadores
        this.vista = new MenuVista(stage,
                numJugadores -> gestor.iniciarPartida(numJugadores)),
                () -> gestor.cambiarAFinPartida()
        );
    }

    @Override
    public void actualizar(double deltaTime) {
        // Aquí podrías animar el fondo o botones si querés
    }

    @Override
    public void manejarInput(String input) {
        // No necesario si usamos botones
    }
}
