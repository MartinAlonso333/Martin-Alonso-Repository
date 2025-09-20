package juego.gestores;

import juego.entidades.Ente;
import juego.utilidades.EventManager;
import java.util.HashSet;
import java.util.Set;

public class SistemaColision {

    private final ColisionHandler manejador = new ColisionHandler();
    private final Set<Ente> entesActivos = new HashSet<>();

    public void registrarEnte(Ente ente) {
        entesActivos.add(ente);
        EventManager.getInstancia().suscribir("ente_movido:" + ente.hashCode(),
                datos -> verificarColisiones(ente));
    }

    public void desregistrarEnte(Ente ente) {
        entesActivos.remove(ente);
    }

    private void verificarColisiones(Ente enteMovido) {
        if (!enteMovido.estaActivo()) return;

        for (Ente otro : entesActivos) {
            if (otro != enteMovido && otro.estaActivo() && enteMovido.intersecta(otro)) {
                enteMovido.aceptar(manejador, otro);
            }
        }
    }
}
