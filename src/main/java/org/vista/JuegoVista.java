package org.vista;

import javafx.scene.image.Image;
import org.modelo.Juego;
import org.modelo.entidades.ConSprite;
import org.modelo.entidades.Ente;
import org.modelo.eventos.GestorEventos;
import org.modelo.utilidades.Direccion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JuegoVista {

    private final Juego juego;
    private final List<EnteVista> entesVista = new ArrayList<>();
    protected GestorEventos em;
    private final GestorSprites gestorSprites;

    public JuegoVista(Juego juego, GestorEventos gestorEventos, GestorSprites gestorSprites) {
        this.juego = juego;
        this.em = gestorEventos;
        this.gestorSprites = gestorSprites;
        sincronizarVistas();
    }

    private void sincronizarVistas() {
        // Remover entes eliminados
        entesVista.removeIf(ev -> !juego.getEntes().contains(ev.getEnte()));

        // Agregar nuevos entes
        for (Ente e : juego.getEntes()) {
            boolean yaExiste = entesVista.stream().anyMatch(ev -> ev.getEnte() == e);
            if (!yaExiste) {
                Map<Direccion, List<Image>> anims = gestorSprites.getAnimacionesPara((ConSprite) e);
                entesVista.add(new EnteVista(e, anims, em));
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
