package juego.entidades.powerups;

import juego.entidades.tanques.TanqueJugador;
import juego.eventos.EventoManager;
public enum TipoPowerUp {

    CASCO(10000) {
        @Override
        public void aplicar(TanqueJugador jugador) {
            jugador.activarInvulnerabilidad(getDuracionMs());
        }
    },
    ESTRELLA(10000) {
        @Override
        public void aplicar(TanqueJugador jugador) {
            jugador.mejorarDisparo(getDuracionMs());
        }
    },
    GRANADA(0) {
        @Override
        public void aplicar(TanqueJugador jugador) {
            EventoManager.getInstancia().triggerExplosionGranada(jugador.getPosicion());
        }
    };
    private final int duracion;

    TipoPowerUp(int duracion) {
        this.duracion = duracion;
    }

    public int getDuracionMs() {
        return duracion;
    }

    public abstract void aplicar(TanqueJugador jugador);
}