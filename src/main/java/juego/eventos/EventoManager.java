package juego.eventos;

import juego.entidades.Ente;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class EventoManager {

    private static EventoManager instancia;

    private final Map<String, Set<Consumer<Object>>> listeners = new HashMap<>();

    private EventoManager() {}

    public static EventoManager getInstancia() {
        if (instancia == null) instancia = new EventoManager();
        return instancia;
    }

    // Registrar un listener para un tipo de evento
    public void registrar(String evento, Consumer<Object> listener) {
        listeners.computeIfAbsent(evento, k -> new HashSet<>()).add(listener);
    }

    // Notificar que ocurrió un evento con un objeto asociado
    public void notificar(String evento, Object objeto) {
        Set<Consumer<Object>> set = listeners.get(evento);
        if (set != null) {
            for (Consumer<Object> c : set) c.accept(objeto);
        }
    }

    // Sobrecarga para eventos sin objeto
    public void notificar(String evento) {
        notificar(evento, null);
    }
}
