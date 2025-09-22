package main.java.juego.utilidades;

import java.util.*;
import java.util.function.Consumer;

public class EventManager {

    private static final EventManager instancia = new EventManager();

    public static EventManager getInstancia() { return instancia; }

    private final Map<String, List<Consumer<?>>> listeners = new HashMap<>();

    private EventManager() {}

    // Suscribirse a un evento con tipo específico
    public <T> void suscribir(String evento, Consumer<T> listener) {
        listeners.computeIfAbsent(evento, k -> new ArrayList<>()).add(listener);
    }

    // Notificar evento con datos tipados
    @SuppressWarnings("unchecked")
    public <T> void notificar(String evento, T datos) {
        List<Consumer<?>> eventListeners = listeners.get(evento);
        if (eventListeners != null) {
            for (Consumer<?> c : eventListeners) {
                ((Consumer<T>) c).accept(datos);
            }
        }
    }

    // Notificar evento sin datos
    public void notificar(String evento) {
        notificar(evento, null);
    }
}
