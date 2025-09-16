package juego;

import javafx.application.Application;
import javafx.stage.Stage;
import juego.ui.PantallaInicio;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        PantallaInicio inicio = new PantallaInicio(primaryStage);
        inicio.mostrar();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
