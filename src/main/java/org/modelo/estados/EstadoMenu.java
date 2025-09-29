// EstadoMenu.java
package org.modelo.estados;



public class EstadoMenu implements EstadoJuego {

    private final GestorEstados gestor;

    public EstadoMenu(GestorEstados gestor) {
        this.gestor = gestor;
    }

    @Override
    public void actualizar(double deltaTime) {
        // No hay lógica de actualización en menú por ahora
    }

    @Override
    public void manejarInput(String input) {
        // Si quieres, aquí puedes manejar input de menú (ejemplo: seleccionar opción)
    }
}