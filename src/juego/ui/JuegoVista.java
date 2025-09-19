package juego.vista;

import juego.entidades.Ente;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;

public class JuegoVista {

    private final List<EnteVista> vistas = new ArrayList<>();

    public void agregarEnteVista(EnteVista ev) {
        vistas.add(ev);
    }

    public void dibujar(GraphicsContext gc) {
        for (EnteView ev : vistas) {
            if (ev.estaVisible()) {
                ev.dibujar(gc);
            }
        }
    }

    public void actualizar() {
        for (EnteVista ev : vistas) {
            ev.actualizar();
        }
    }
}
