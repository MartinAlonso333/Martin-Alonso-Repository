package org.vista;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.modelo.entidades.powerups.PowerUp;
import org.modelo.estados.GestorEstados;
import org.modelo.entidades.Ente;
import org.modelo.entidades.bloques.Bloque;
import org.modelo.entidades.tanques.Bala;
import org.modelo.entidades.tanques.TanqueEnemigo;
import org.modelo.entidades.tanques.TanqueJugador;
import org.modelo.estados.EstadoJuego;
import org.modelo.estados.EstadoPartida;
import org.modelo.eventos.EventoManager;
import org.modelo.eventos.TipoEvento;
import org.modelo.utilidades.Direccion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import static org.modelo.entidades.bloques.TipoBloque.*;
import static org.modelo.entidades.tanques.TipoTanqueEnemigo.*;

public class PantallaJuego extends Pantalla {

    private final GestorEstados gestorEstados;
    private final StackPane root;
    private final Canvas canvas;
    private final GraphicsContext gc;
    private AnimationTimer timer;
    private long ultimoTiempoNano = 0;

    // Vista propia: maneja EnteVista y renderizado
    private final JuegoVista juegoVista = new JuegoVista();
    private final EventoManager em = EventoManager.getInstancia();

    public PantallaJuego(Stage stage, GestorEstados gestorEstados, int ancho, int alto) {
        super(stage);
        this.gestorEstados = gestorEstados;

        root = new StackPane();
        canvas = new Canvas(ancho, alto);
        gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);

        // Suscribirse a eventos del modelo para crear vistas dinámicamente
        em.registrar(TipoEvento.ENTE_AGREGADO, (Consumer<Object>) (obj) -> {
            System.out.println("Evento ENTE_AGREGADO recibido para objeto: " + obj);
            if (obj instanceof Ente ente) {
                String tipoStr = "desconocido";
                try {
                    tipoStr = obtenerTipoStrDesdeEnte(ente);
                    Map<Direccion, List<String>> rutas = SpriteConfig.getConfig(tipoStr);
                    Map<Direccion, List<Image>> animaciones = cargarAnimaciones(rutas);
                    EnteVista ev = new EnteVista(ente, animaciones);
                    juegoVista.agregarEnteVista(ev);
                } catch (IllegalArgumentException e) {
                    System.err.println("Sin config de sprites para ente: " + tipoStr + ". Error: " + e.getMessage());
                }
            }
        });

        // Limpiar vistas al cargar nuevo nivel (evita acumulación)
        em.registrar(TipoEvento.NIVEL_CARGADO, (Consumer<Object>) (obj) -> {
            juegoVista.limpiar();  // Limpia vistas anteriores
        });
    }

    @Override
    public void mostrar() {

        if (stage.getScene() == null) {
            System.err.println("ERROR: Stage no tiene Scene asignado al llamar a mostrar()");
        } else {
            System.out.println("Stage tiene Scene asignado con root actual: " + stage.getScene().getRoot());
        }
        stage.getScene().setRoot(root);
        stage.setTitle("Juego - Nivel " + obtenerNivelActual());

        root.requestFocus();

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (ultimoTiempoNano == 0) {
                    ultimoTiempoNano = now;
                    return;
                }
                double deltaTime = (now - ultimoTiempoNano) / 1_000_000_000.0;
                ultimoTiempoNano = now;

                // Limpiar pantalla primero
                gc.setFill(Color.BLACK);
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

                // Dibujar imagen fija para prueba (deberías verla en 100,100)
                Image prueba = GestorSprites.obtenerSprite("Player1Tank0_20x20.png");
                if (prueba != null) {
                    gc.drawImage(prueba, 100, 100, 20, 20);
                }

                // Actualizar lógica vía controlador (desacoplado)
                gestorEstados.actualizar(deltaTime);

                // Actualizar y dibujar vistas
                juegoVista.actualizar(deltaTime);
                juegoVista.dibujar(gc);
            }
        };
        timer.start();
    }

    private int obtenerNivelActual() {
        EstadoJuego estado = gestorEstados.getEstadoActual();
        if (estado instanceof EstadoPartida ep) {
            return ep.nivelActual;
        }
        return 0;
    }

    public void detener() {
        if (timer != null) {
            timer.stop();
        }
    }

    // Método auxiliar: Obtener tipoStr para SpriteConfig basado en el ente
    private String obtenerTipoStrDesdeEnte(Ente ente) {
        if (ente instanceof TanqueJugador jugador) {
            return "player" + jugador.getId();  // e.g., "player1"
        } else if (ente instanceof TanqueEnemigo enemigo) {
            // Usa el enum TipoTanqueEnemigo con switch (cubre todos los casos)
            return switch (enemigo.getTipoTanqueEnemigo()) {
                case BASICO -> "regularEnemy";
                case RAPIDO -> "fastEnemy";
                case POTENTE -> "powerfulEnemy";
                case BLINDADO -> "heavyEnemy";
                default -> "regularEnemy";
            };
        } else if (ente instanceof Bloque bloque) {
            // Usa el enum TipoBloque con switch (cubre todos los casos, incluyendo TANQUE_DESTRUIDO)
            return switch (bloque.getTipoBloque()) {  // Asume getTipoBloque() retorna TipoBloque
                case LADRILLO -> "brickBlock";
                case ACERO -> "steelBlock";
                case AGUA -> "waterBlock";
                case BOSQUE -> "forestBlock";
                case BASE -> "baseBlock";
                case TANQUE_DESTRUIDO -> "tanque_destruido";  // Ajusta el string según tu SpriteConfig
                default -> "brickBlock";
            };
        } else if (ente instanceof Bala) {
            return "bullet";
        } else if (ente instanceof PowerUp) {
            return "powerup";
        }
        // Default para otros entes desconocidos
        return "brickBlock";
    }

    // Método auxiliar: Convertir rutas de sprites (strings) a imágenes cargadas
    private Map<Direccion, List<Image>> cargarAnimaciones(Map<Direccion, List<String>> rutasFrames) {
        Map<Direccion, List<Image>> animaciones = new HashMap<>();
        for (Direccion dir : rutasFrames.keySet()) {
            List<Image> frames = rutasFrames.get(dir).stream()
                    .map(GestorSprites::obtenerSprite)
                    .filter(Objects::nonNull)
                    .toList();
            System.out.println("Animación para " + dir + " tiene " + frames.size() + " frames.");
            animaciones.put(dir, frames);
        }
        return animaciones;
    }
}