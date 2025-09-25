// MenuVista.java
package juego.vista;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import java.util.function.Consumer;

public class MenuVista {

    private VBox layout;
    private Scene escena;

    /**
     * @param stage          La ventana principal
     * @param iniciarJugador Acción que recibe la cantidad de jugadores
     */
    public MenuVista(Stage stage, Consumer<Integer> iniciarJugador) {
        // Layout vertical centrado
        layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);

        // Botones
        Button btn1Jugador = new Button("1 Jugador");
        Button btn2Jugadores = new Button("2 Jugadores");
        Button btnSalir = new Button("Salir");

        // Conectar botones a la lógica pasada desde EstadoMenu
        btn1Jugador.setOnAction(e -> iniciarJugador.accept(1));
        btn2Jugadores.setOnAction(e -> iniciarJugador.accept(2));
        btnSalir.setOnAction(e -> System.exit(0));

        layout.getChildren().addAll(btn1Jugador, btn2Jugadores, btnSalir);

        // Escena y diseño básico
        escena = new Scene(layout, 800, 600);

        // Ejemplo de estilo gráfico (puedes usar CSS externo también)
        layout.setStyle("-fx-background-color: linear-gradient(to bottom, #1e1e1e, #3c3c3c);");
        btn1Jugador.setStyle("-fx-font-size: 18px; -fx-padding: 10 20;");
        btn2Jugadores.setStyle("-fx-font-size: 18px; -fx-padding: 10 20;");
        btnSalir.setStyle("-fx-font-size: 18px; -fx-padding: 10 20;");

        // Mostrar la escena
        stage.setScene(escena);
        stage.show();
    }
}
