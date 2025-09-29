package org;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.modelo.estados.GestorEstados;
import org.modelo.input.GestorInput;


public class JuegoApp extends Application {

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new StackPane(), 800, 600);
        stage.setScene(scene);

        GestorEstados gestor = new GestorEstados(scene, stage);
        new GestorInput(scene, gestor);
        gestor.cambiarAMenu();

        stage.setResizable(false);
        stage.setWidth(800);
        stage.setHeight(600);
        stage.centerOnScreen();

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}