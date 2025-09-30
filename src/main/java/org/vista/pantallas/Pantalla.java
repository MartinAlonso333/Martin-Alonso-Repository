package org.vista.pantallas;

import javafx.stage.Stage;

public abstract class Pantalla {
    protected Stage stage;

    public Pantalla(Stage stage) {
        this.stage = stage;
    }

    public abstract void mostrar();
}
