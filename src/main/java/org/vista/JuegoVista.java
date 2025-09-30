package org.vista;

import javafx.scene.image.Image;
import org.modelo.Juego;
import org.modelo.entidades.Ente;
import org.modelo.utilidades.Direccion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JuegoVista {

    private final Juego juego;
    private final List<EnteVista> entesVista = new ArrayList<>();

    public JuegoVista(Juego juego) {
        this.juego = juego;
        inicializarVistas();
    }

    private void inicializarVistas() {
        entesVista.clear();
        for (Ente e : juego.getEntes()) {
            Map<Direccion, List<Image>> animaciones = GestorSprites.getAnimacionesPara(e);
            entesVista.add(new EnteVista(e, animaciones));
        }
    }

    public void actualizar(double deltaTime) {
        // Eliminar entes destruidos
        entesVista.removeIf(ev -> !juego.getEntes().contains(ev.getEnte()));

        // Actualizar animaciones y dirección usando polimorfismo
        for (EnteVista ev : entesVista) {
            Direccion dir = ev.getEnte().getDireccion(); // Método polimórfico
            if (dir != null) {
                ev.setDireccion(dir);
            }
            ev.actualizar(deltaTime);
        }
    }

    public List<EnteVista> getEntesVista() {
        return new ArrayList<>(entesVista); // copia defensiva
    }

    public Juego getJuego() {
        return juego;
    }
}
