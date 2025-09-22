package main.java.juego.entidades.powerups;

import juego.entidades.tanques.TanqueJugador;
import juego.utilidades.EventManager;

public enum TipoPowerUp {

    CASCO {
        @Override
        public void aplicar(TanqueJugador jugador) {
            jugador.activarInvulnerabilidad();
        }
    },

    ESTRELLA {
        @Override
        public void aplicar(TanqueJugador jugador) {
            jugador.mejorarDisparo();
        }
    },

    GRANADA {
        @Override
        public void aplicar(TanqueJugador jugador) {
            // Dispara un evento global que el juego puede escuchar
            EventManager.getInstancia().notificar("granada_activada", jugador);
        }
    };

    // Todos los power-ups aplican su efecto sobre el tanque
    public abstract void aplicar(TanqueJugador jugador);
}
