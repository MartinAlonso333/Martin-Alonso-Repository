package juego.entidades.powerups;

import juego.entidades.tanques.TanqueJugador;
import juego.eventos.EventoManager;
public enum TipoPowerUp {
    CASCO(10000) {
        @Override
        public void aplicar(TanqueJugador jugador) { jugador.setInvulnerabilidad(true); }
        @Override
        public void remover(TanqueJugador jugador) { jugador.setInvulnerabilidad(false); }
    },
    ESTRELLA(10000) {
        @Override
        public void aplicar(TanqueJugador jugador) { jugador.setDisparoMejorado(true); }
        @Override
        public void remover(TanqueJugador jugador) { jugador.setDisparoMejorado(false); }
    },
    GRANADA(0) {
        @Override
        public void aplicar(TanqueJugador jugador) {
            EventoManager.getInstancia().triggerExplosionGranada(jugador.getPosicion());
        }
        @Override
        public void remover(TanqueJugador jugador) {
            // nada, porque es instantáneo
        }
    };

    private final int duracion;

    TipoPowerUp(int duracion) { this.duracion = duracion; }

    public int getDuracionMs() { return duracion; }

    public abstract void aplicar(TanqueJugador jugador);
    public abstract void remover(TanqueJugador jugador);
}