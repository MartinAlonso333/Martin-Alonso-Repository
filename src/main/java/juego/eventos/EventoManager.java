package juego.eventos;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class EventoManager {

    private static EventoManager instancia;

    private final Map<TipoEvento, Set<Consumer<Object>>> listeners = new HashMap<>();

    private EventoManager() {}

    public static EventoManager getInstancia() {
        if (instancia == null) instancia = new EventoManager();
        return instancia;
    }

    /** Registra un listener para un tipo de evento */
    public void registrar(TipoEvento evento, Consumer<Object> listener) {
        listeners.computeIfAbsent(evento, k -> new HashSet<>()).add(listener);
    }

    /** Notifica a todos los listeners que ocurrió un evento con un objeto asociado */
    public void notificar(TipoEvento evento, Object objeto) {
        Set<Consumer<Object>> set = listeners.get(evento);
        if (set != null) {
            for (Consumer<Object> c : set) {
                c.accept(objeto);
            }
        }
    }

    /** Sobrecarga para notificar eventos sin objeto */
    public void notificar(TipoEvento evento) {
        notificar(evento, null);
    }
}
