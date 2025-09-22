package juego.entidades.powerups;

import juego.entidades.tanques.TanqueJugador;
import juego.eventos.EventoManager;

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
            EventoManager.getInstancia().triggerExplosionGranada(jugador.getPosicion());
        }
    };

    // Todos los power-ups aplican su efecto sobre el tanque
    public abstract void aplicar(TanqueJugador jugador);
}
