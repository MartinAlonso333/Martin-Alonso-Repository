package juego;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import juego.estados.GestorEstados;

public class JuegoApp extends Application {

    public void start(Stage stage) {
        Scene scene = new Scene(new StackPane(), 800, 600);
        stage.setScene(scene);

        GestorEstados gestor = new GestorEstados(scene, stage);
        gestor.cambiarAMenu();

        // 🔹 Configuración de la ventana
        stage.setResizable(false);         // no se puede redimensionar
        stage.setWidth(800);               // ancho fijo
        stage.setHeight(600);              // alto fijo
        stage.centerOnScreen();            // centrar ventana en la pantalla

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}