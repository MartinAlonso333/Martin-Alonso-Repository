package org;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.controlador.*;
import org.modelo.Juego;
import org.modelo.eventos.EventoManager;
import org.modelo.eventos.GestorEventos;
import org.modelo.eventos.TipoEvento;
import org.modelo.input.GestorInput;
import org.vista.GestorSprites;
import org.vista.JuegoVista;
import org.vista.pantallas.PantallaFinPartida;
import org.vista.pantallas.PantallaJuego;
import org.vista.pantallas.PantallaMenu;
import org.vista.sonidos.GestorSonidos;

public class JuegoApp extends Application {

    private boolean juegoActivo = false;

    @Override
    public void start(Stage stage) {
        Pane root = new Pane();
        root.setPrefSize(800, 600);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new javafx.scene.image.Image(getClass().getResourceAsStream("/sprites/logo.png")));

        // Gestor de eventos
        GestorEventos em = new EventoManager();

        // Gestor de estados
        GestorEstados gestorEstados = new GestorEstados();
        EstadoMenu estadoMenu = new EstadoMenu(em);
        gestorEstados.cambiarAEstado(estadoMenu);
        gestorEstados.setGestorInput(new GestorInput(scene, gestorEstados));

        // Pantallas
        PantallaMenu pantallaMenu = new PantallaMenu(stage, root, estadoMenu);
        PantallaJuego pantallaJuego = new PantallaJuego(stage, root);
        PantallaFinPartida pantallaFin = new PantallaFinPartida(stage, root, em);

        GestorSonidos gestorSonidos = new GestorSonidos(em);
        GestorSprites gestorSprites= new GestorSprites();
        // Suscripción a eventos


        em.registrar(TipoEvento.MOSTRAR_MENU, o -> {
            gestorEstados.cambiarAEstado(estadoMenu);
            pantallaMenu.mostrar();
        });

        em.registrar(TipoEvento.MOSTRAR_PARTIDA, o -> {
            int numJugadores = o != null ? (int) o : 1;
            EstadoPartida partida = new EstadoPartida(numJugadores, em);
            gestorEstados.cambiarAEstado(partida);

            Juego juego = partida.getJuego();
            JuegoVista juegoVista = new JuegoVista(juego, em,gestorSprites);
            pantallaJuego.setJuego(juegoVista);
            pantallaJuego.mostrar();

            juegoActivo = true;
        });

        em.registrar(TipoEvento.MOSTRAR_FIN_PARTIDA, o -> {
            juegoActivo = false;
            pantallaJuego.ocultar();
            gestorEstados.cambiarAEstado(new EstadoFinPartida((Boolean) o));
            pantallaFin.mostrar((Boolean) o);
        });

        em.registrar(TipoEvento.NIVEL_CARGADO, o -> {
            Juego juegoNuevo = (Juego) o;
            JuegoVista juegoVistaNuevo = new JuegoVista(juegoNuevo, em,gestorSprites);
            pantallaJuego.setJuego(juegoVistaNuevo);
            pantallaJuego.mostrar();
        });

        // Mostrar menú inicial
        pantallaMenu.mostrar();

        stage.setTitle("Yet Another Battle City");
        stage.show();

        // Loop principal
        final long[] lastTime = {System.nanoTime()};
        new javafx.animation.AnimationTimer() {
            @Override
            public void handle(long now) {
                double deltaTime = (now - lastTime[0]) / 1e9;
                lastTime[0] = now;

                gestorEstados.actualizar(deltaTime);

                JuegoVista juegoVista = pantallaJuego.getJuegoVista();
                if (juegoVista != null && juegoActivo) {
                    juegoVista.actualizar(deltaTime);
                    pantallaJuego.actualizar();
                }
            }
        }.start();
    }

    public static void main(String[] args) {
        launch();
    }
}