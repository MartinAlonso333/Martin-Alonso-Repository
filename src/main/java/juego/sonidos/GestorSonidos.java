package juego.sonidos;

import juego.eventos.EventoManager;
import juego.eventos.TipoEvento;
import javafx.scene.media.AudioClip;
import java.net.URL;

public class GestorSonidos {

    public GestorSonidos() {
        EventoManager em = EventoManager.getInstancia();

        // Suscribirse a eventos de sonido
        em.registrar(TipoEvento.BALA_DISPARADA, obj -> reproducir("laser-gun-280344.mp3"));
        em.registrar(TipoEvento.TANQUE_DESTRUIDO, obj -> reproducir("bang-43964.mp3"));
        em.registrar(TipoEvento.BASE_DESTRUIDA, obj -> reproducir("explosion-42132.mp3"));
        em.registrar(TipoEvento.TANQUE_BLINDADO_IMPACTADO, obj -> reproducir("glass-cling-08-83792.mp3"));
        em.registrar(TipoEvento.BLOQUE_ACERO_IMPACTADO, obj -> reproducir("glass-cling-08-83792.mp3"));
        em.registrar(TipoEvento.BLOQUE_DESTRUIDO, obj -> reproducir("wood-impact-84721.mp3"));
    }

    private void reproducir(String archivo) {
        try {
            // Asumiendo que los mp3 están en resources/sounds/
            URL resource = getClass().getResource("/sounds/" + archivo);
            if (resource != null) {
                AudioClip clip = new AudioClip(resource.toString());
                clip.play();
            } else {
                System.err.println("No se encontró el archivo: " + archivo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
