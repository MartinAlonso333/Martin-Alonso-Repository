package org;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.controlador.*;
import org.modelo.eventos.EventoManager;
import org.modelo.eventos.TipoEvento;
import org.modelo.input.GestorInput;
import org.vista.pantallas.*;

public class JuegoApp extends Application {

    @Override
    public void start(Stage stage) {
        Pane root = new Pane();
        root.setPrefSize(800, 600);
        Scene scene = new Scene(root);

        // Pantallas
        PantallaMenu pantallaMenu = new PantallaMenu(root);
        PantallaJuego pantallaJuego = new PantallaJuego(root);
        PantallaFinPartida pantallaFin = new PantallaFinPartida(root);

        // Gestor de estados
        GestorEstados gestor = new GestorEstados();
        gestor.setGestorInput(new GestorInput(scene, gestor));

        // Suscripción a eventos
        EventoManager em = EventoManager.getInstancia();

        em.registrar(TipoEvento.MOSTRAR_MENU, o -> pantallaMenu.mostrar());
        em.registrar(TipoEvento.MOSTRAR_PARTIDA, o -> {
            int numJugadores = o != null ? (int) o : 1;
            EstadoPartida partida = new EstadoPartida(numJugadores);
            gestor.cambiarAEstado(partida);
            pantallaJuego.setJuego(partida.getJuego());
            pantallaJuego.mostrar();
        });
        em.registrar(TipoEvento.MOSTRAR_FIN_PARTIDA, o -> pantallaFin.mostrar((Boolean) o));

        // Mostrar menú inicial
        pantallaMenu.mostrar();

        stage.setScene(scene);
        stage.setTitle("Yet Another Battle City");
        stage.show();

        // Loop principal
        final long[] lastTime = {System.nanoTime()};
        new javafx.animation.AnimationTimer() {
            @Override
            public void handle(long now) {
                double deltaTime = (now - lastTime[0]) / 1e9;
                lastTime[0] = now;

                gestor.actualizar(deltaTime);
                pantallaJuego.actualizar(deltaTime);
            }
        }.start();
    }

    public static void main(String[] args) {
        launch();
    }
}
