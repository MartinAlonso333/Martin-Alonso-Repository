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
        sincronizarVistas();
    }

    private void sincronizarVistas() {
        // Remover entes eliminados
        entesVista.removeIf(ev -> !juego.getEntes().contains(ev.getEnte()));

        // Agregar nuevos entes
        for (Ente e : juego.getEntes()) {
            boolean yaExiste = entesVista.stream().anyMatch(ev -> ev.getEnte() == e);
            if (!yaExiste) {
                @SuppressWarnings("unchecked")
                Map<Direccion, List<Image>> anims = (Map<Direccion, List<Image>>) (Map<?, ?>) GestorSprites.getAnimacionesPara(e);
                entesVista.add(new EnteVista(e, anims));
            }
        }
    }

    public void actualizar(double deltaTime) {
        sincronizarVistas();
        for (EnteVista ev : entesVista) {
            ev.actualizar(deltaTime);
        }
    }

    public List<EnteVista> getEntesVista() {
        return new ArrayList<>(entesVista);
    }
}
