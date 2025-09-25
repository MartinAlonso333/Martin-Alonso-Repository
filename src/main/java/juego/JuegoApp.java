package juego;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import juego.estados.GestorEstados;
import juego.input.GestorInput;

public class JuegoApp extends Application {

    private GestorEstados gestor;

    @Override
    public void start(Stage stage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, 800, 600);

        gestor = new GestorEstados(scene); // gestor controla el estado actual del juego
        new GestorInput(scene, gestor);    // conecta los KeyCode con el estado actual

        stage.setScene(scene);
        stage.setTitle("Yet Another Battle City");
        stage.show();

        // Loop principal
        AnimationTimer loop = new AnimationTimer() {
            private long ultimoFrame = 0;

            @Override
            public void handle(long ahora) {
                if (ultimoFrame == 0) {
                    ultimoFrame = ahora;
                    return;
                }
                double deltaTime = (ahora - ultimoFrame) / 1_000_000_000.0;
                ultimoFrame = ahora;

                gestor.actualizar(deltaTime); // ✅ Aquí se actualiza TODO según el estado activo
            }
        };
        loop.start();
    }

    public static void main(String[] args) {
        launch();
    }
}
