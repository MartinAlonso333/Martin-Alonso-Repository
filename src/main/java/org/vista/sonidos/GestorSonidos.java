package org.vista.sonidos;

import org.modelo.eventos.EventoManager;
import org.modelo.eventos.TipoEvento;
import javafx.scene.media.AudioClip;
import java.net.URL;

public class GestorSonidos {

    private AudioClip musicaFondo;

    public GestorSonidos() {
        EventoManager em = EventoManager.getInstancia();

        try {
            URL resourceMusica = getClass().getResource("/sounds/tribe-drum-loop-103173.mp3");
            if (resourceMusica != null) {
                musicaFondo = new AudioClip(resourceMusica.toString());
                musicaFondo.setCycleCount(AudioClip.INDEFINITE);
            } else {
                System.err.println("No se encontró la música de fondo: tribe-drum-loop-103173.mp3");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        em.registrar(TipoEvento.BALA_DISPARADA, obj -> reproducir("laser-gun-280344.mp3"));
        em.registrar(TipoEvento.TANQUE_DESTRUIDO, obj -> reproducir("bang-43964.mp3"));
        em.registrar(TipoEvento.BASE_DESTRUIDA, obj -> reproducir("explosion-42132.mp3"));
        em.registrar(TipoEvento.TANQUE_BLINDADO_IMPACTADO, obj -> reproducir("glass-cling-08-83792.mp3"));
        em.registrar(TipoEvento.BLOQUE_ACERO_IMPACTADO, obj -> reproducir("glass-cling-08-83792.mp3"));
        em.registrar(TipoEvento.BLOQUE_DESTRUIDO, obj -> reproducir("wood-impact-84721.mp3"));

        em.registrar(TipoEvento.MOSTRAR_PARTIDA, obj -> {
            if (musicaFondo != null) {
                musicaFondo.play();
            }
        });

        em.registrar(TipoEvento.MOSTRAR_FIN_PARTIDA, obj -> {
            if (musicaFondo != null) {
                musicaFondo.stop();
            }
        });
    }

    private void reproducir(String archivo) {
        try {
            URL resource = getClass().getResource("/sounds/" + archivo);
            if (resource != null) {
                AudioClip clip = new AudioClip(resource.toString());
                // Opcional: clip.setVolume(0.8);  // Ajusta volumen por efecto si es necesario
                clip.play();
            } else {
                System.err.println("No se encontró el archivo de sonido: " + archivo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}