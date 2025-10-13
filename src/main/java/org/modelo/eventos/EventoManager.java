package org.modelo.eventos;

import java.util.*;
import java.util.function.Consumer;

/** Clase singleton que maneja eventos y listeners */
public class EventoManager implements GestorEventos {

    private final Map<TipoEvento, List<Listener>> listeners = new HashMap<>();

    public EventoManager() {}

    /** Registra un listener para un tipo de evento */
    @Override
    public void notificar(TipoEvento tipo, Object datos) {
        List<Listener> lista = listeners.get(tipo);
        if (lista != null) {
            for (Listener l : lista) l.onEvento(datos);
        }
    }

    /** Notifica a todos los listeners que ocurrió un evento con un objeto asociado */
    public void registrar(TipoEvento tipo, Listener listener) {
        listeners.computeIfAbsent(tipo, k -> new ArrayList<>()).add(listener);
    }

    /** Sobrecarga para notificar eventos sin objeto */
    public void notificar(TipoEvento evento) {
        notificar(evento, null);
    }
}
