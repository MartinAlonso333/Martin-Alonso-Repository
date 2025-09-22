package juego.gestores;

import juego.entidades.Ente;
import java.util.ArrayList;
import java.util.List;

public class SistemaColision {

    private final ColisionHandler colisionHandler = new ColisionHandler();
    private final List<Ente> entes = new ArrayList<>();

    public void agregarEnte(Ente e) { entes.add(e); }
    public void removerEnte(Ente e) { entes.remove(e); }

    public void chequearColisiones(Ente mover) {
        for (Ente otro : entes) {
            if (otro != mover && mover.intersecta(otro)) {
                colisionHandler.manejarColision(mover, otro);
            }
        }
    }
}
