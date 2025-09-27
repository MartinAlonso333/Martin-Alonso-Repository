package org.vista.controladores;

import org.modelo.eventos.EventoManager;
import org.modelo.eventos.TipoEvento;
import javafx.scene.media.AudioClip;
import java.net.URL;

public class GestorSonidos {

    private AudioClip musicaFondo;

    public GestorSonidos() {
        EventoManager em = EventoManager.getInstancia();

        // Suscribirse a eventos de sonido (efectos puntuales)
        em.registrar(TipoEvento.BALA_DISPARADA, obj -> reproducir("laser-gun-280344.mp3"));
        em.registrar(TipoEvento.TANQUE_DESTRUIDO, obj -> reproducir("bang-43964.mp3"));
        em.registrar(TipoEvento.BASE_DESTRUIDA, obj -> reproducir("explosion-42132.mp3"));
        em.registrar(TipoEvento.TANQUE_BLINDADO_IMPACTADO, obj -> reproducir("glass-cling-08-83792.mp3"));
        em.registrar(TipoEvento.BLOQUE_ACERO_IMPACTADO, obj -> reproducir("glass-cling-08-83792.mp3"));
        em.registrar(TipoEvento.BLOQUE_DESTRUIDO, obj -> reproducir("wood-impact-84721.mp3"));

        // Cargar música de fondo
        musicaFondo = cargarClip("tribe-drum-loop-103173.mp3");
    }

    private void reproducir(String archivo) {
        AudioClip clip = cargarClip(archivo);
        if (clip != null) {
            clip.play();
        }
    }

    private AudioClip cargarClip(String archivo) {
        try {
            URL resource = getClass().getResource("/sounds/" + archivo);
            if (resource != null) {
                return new AudioClip(resource.toString());
            } else {
                System.err.println("No se encontró el archivo: " + archivo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // --- MÚSICA DE FONDO ---
    public void reproducirMusicaLoop() {
        if (musicaFondo != null) {
            musicaFondo.setCycleCount(AudioClip.INDEFINITE); // loop infinito
            musicaFondo.play();
        }
    }

    public void detenerMusica() {
        if (musicaFondo != null) {
            musicaFondo.stop();
        }
    }
}
