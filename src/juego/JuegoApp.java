package juego;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import juego.entidades.*;

public class JuegoApp extends Application {

    private Juego juego;
    private Canvas canvas;
    private double ancho = 800, alto = 600;

    @Override
    public void start(Stage primaryStage) {
        juego = new Juego();
        inicializarJugadores();

        Pane root = new Pane();
        canvas = new Canvas(ancho, alto);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Yet Another Battle City");
        primaryStage.show();

        configurarControles(scene);
        iniciarBucleJuego();
    }

    private void inicializarJugadores() {
        juego.agregarTanque(new Tanque(new Coordenada(100,100), new Dimensiones(20,20), 5, juego));
        juego.agregarTanque(new Tanque(new Coordenada(700,100), new Dimensiones(20,20), 5, juego));
    }

    private void configurarControles(Scene scene) {
        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            if      (code == KeyCode.UP)      juego.moverJugador(0, Direccion.ARRIBA);
            else if (code == KeyCode.DOWN)    juego.moverJugador(0, Direccion.ABAJO);
            else if (code == KeyCode.LEFT)    juego.moverJugador(0, Direccion.IZQUIERDA);
            else if (code == KeyCode.RIGHT)   juego.moverJugador(0, Direccion.DERECHA);
            else if (code == KeyCode.SPACE)   juego.jugadorDispara(0);

            else if (code == KeyCode.W)       juego.moverJugador(1, Direccion.ARRIBA);
            else if (code == KeyCode.S)       juego.moverJugador(1, Direccion.ABAJO);
            else if (code == KeyCode.A)       juego.moverJugador(1, Direccion.IZQUIERDA);
            else if (code == KeyCode.D)       juego.moverJugador(1, Direccion.DERECHA);
            else if (code == KeyCode.CONTROL) juego.jugadorDispara(1);
        });
    }

    private void iniciarBucleJuego() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        AnimationTimer timer = new AnimationTimer() {
            private long lastTime = 0;
            @Override
            public void handle(long now) {
                if (lastTime == 0) lastTime = now;
                double deltaTime = (now - lastTime)/1_000_000_000.0;
                last
