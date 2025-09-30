package org;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.controlador.EstadoPartida;
import org.controlador.GestorEstados;
import org.modelo.input.GestorInput;
import org.vista.pantallas.PantallaFinPartida;
import org.vista.pantallas.PantallaJuego;
import org.vista.pantallas.PantallaMenu;

public class JuegoApp extends Application {

    private GestorEstados gestorEstados;

    @Override
    public void start(Stage stage) {
        gestorEstados = new GestorEstados();

        Pane root = new Pane();
        root.setPrefSize(800, 600);
        Scene scene = new Scene(root);

        // Input
        GestorInput gestorInput = new GestorInput(scene, gestorEstados);
        gestorEstados.setGestorInput(gestorInput);

        // Pantallas
        PantallaMenu pantallaMenu = new PantallaMenu(root);
        PantallaJuego pantallaJuego = new PantallaJuego(root);
        PantallaFinPartida pantallaFinPartida = new PantallaFinPartida(root);

        // Eventos del menú
        pantallaMenu.setOnJugar1Jugador(() -> iniciarPartida(1, pantallaJuego));
        pantallaMenu.setOnJugar2Jugadores(() -> iniciarPartida(2, pantallaJuego));

        // Evento fin de partida
        pantallaFinPartida.setOnVolverAlMenu(pantallaMenu::mostrar);

        pantallaMenu.mostrar();

        stage.setScene(scene);
        stage.setTitle("Yet Another Battle City");
        stage.show();

        // Loop principal
        final long[] lastTime = {System.nanoTime()};
        javafx.animation.AnimationTimer gameLoop = new javafx.animation.AnimationTimer() {
            @Override
            public void handle(long now) {
                double deltaTime = (now - lastTime[0]) / 1e9;
                lastTime[0] = now;

                // Procesar input y actualizar lógica
                gestorEstados.actualizar(deltaTime);

                // Actualizar pantalla de juego si hay juego activo
                if (pantallaJuego != null) pantallaJuego.actualizar(deltaTime);
            }
        };
        gameLoop.start();
    }

    private void iniciarPartida(int numJugadores, PantallaJuego pantallaJuego) {
        // Crear estado de partida
        EstadoPartida partida = new EstadoPartida(1, numJugadores);

        // Pasarle el juego a la pantalla
        pantallaJuego.setJuego(partida.getJuego());

        // Eventos del juego
        partida.setOnJuegoTerminado(() -> {
            pantallaFinPartida().mostrar(partida.getJuego().getEntesDeTipo(org.modelo.entidades.tanques.TanqueEnemigo.class).isEmpty());
        });

        // Registramos el estado en el gestor
        gestorEstados.iniciarPartida(numJugadores);

        // Mostrar pantalla de juego
        pantallaJuego.mostrar();
    }

    private PantallaFinPartida pantallaFinPartida() {
        return new PantallaFinPartida(new Pane()); // Aquí podés usar la misma root si querés
    }

    public static void main(String[] args) {
        launch();
    }
}
