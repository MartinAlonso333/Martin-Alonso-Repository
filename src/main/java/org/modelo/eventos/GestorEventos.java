package org.modelo.eventos;

public interface GestorEventos {
    void notificar(TipoEvento tipo, Object datos);
    void registrar(TipoEvento tipo, Listener listener);
}
