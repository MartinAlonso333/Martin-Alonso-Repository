// EstadoMenu.java
package org.modelo.estados;

import javafx.stage.Stage;
import org.vista.Pantalla;
import org.vista.PantallaMenu;
public class EstadoMenu implements EstadoJuego {

    public EstadoMenu(GestorEstados gestor, Stage stage) {
        // Pasamos un solo Consumer<Integer> para manejar 1 o 2 jugadores
        Pantalla pantalla = new PantallaMenu(stage, gestor::iniciarPartida, stage::close);
        pantalla.mostrar();
    }

    @Override
    public void actualizar(double deltaTime) { }

    @Override
    public void manejarInput(String input) { }
}