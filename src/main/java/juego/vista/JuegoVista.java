package main.java.juego.vista;

import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;
import java.util.List;

public class JuegoVista {

    private final List<EnteVista> vistas = new ArrayList<>();

    public void agregarEnteVista(EnteVista ev) {
        vistas.add(ev);
    }

    public void dibujar(GraphicsContext gc) {
        for (EnteVista ev : vistas) {
            if (ev.estaVisible()) ev.dibujar(gc);
        }
    }

    public void actualizar(double deltaTime) {
        for (EnteVista ev : vistas) ev.actualizar(deltaTime);
    }
}
