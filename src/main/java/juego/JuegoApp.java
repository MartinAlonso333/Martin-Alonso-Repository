package juego;

import javafx.application.Application;
import javafx.stage.Stage;
import juego.vista.PantallaMenu;

public class JuegoApp extends Application {

    @Override
    public void start(Stage stage) {
        PantallaMenu menu = new PantallaMenu(stage);
        menu.mostrar();
    }

    public static void main(String[] args) {
        launch();
    }
}