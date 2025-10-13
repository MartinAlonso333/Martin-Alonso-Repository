package org.modelo.entidades.tanques;

import org.modelo.entidades.bloques.Bloque;
import org.modelo.eventos.GestorEventos;
import org.modelo.eventos.TipoEvento;

public enum TipoTanque {
    JUGADOR1(3, 1, 100.0, 1500),
    JUGADOR2(3, 1, 100.0, 1500),
    BASICO(1, 1, 50.0, 3000),
    RAPIDO(1, 1, 150.0, 2000),
    POTENTE(1, 1, 100, 1000),
    BLINDADO(3, 1, 100, 2000) {
        @Override
        public void emitirEvento(GestorEventos em, Tanque tanque) {
            em.notificar(TipoEvento.TANQUE_BLINDADO_IMPACTADO, tanque);
        }
    };

    private final int vida;
    private final int danio;
    private final double velocidad;   // píxeles por segundo
    private final int velocidadDisparo; // ms entre disparos

    TipoTanque(int vida, int danio, double velocidad, int velocidadDisparo) {
        this.vida = vida;
        this.danio = danio;
        this.velocidad = velocidad;
        this.velocidadDisparo = velocidadDisparo;
    }

    public int getVida() { return vida; }
    public int getDanio() { return danio; }
    public double getVelocidad() { return velocidad; }
    public int getVelocidadDisparo() { return velocidadDisparo; }
    public void emitirEvento(GestorEventos em, Tanque tanque) {}
}
