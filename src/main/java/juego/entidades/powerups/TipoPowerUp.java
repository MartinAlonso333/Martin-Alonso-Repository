package juego.entidades.powerups;

import juego.entidades.tanques.TanqueJugador;
import juego.eventos.EventoManager;
import juego.eventos.TipoEvento;

public enum TipoPowerUp {

    CASCO(5000) {
        @Override
        public void aplicar(TanqueJugador jugador) {
            jugador.setInvulnerabilidad(true);
        }
        @Override
        public void remover(TanqueJugador jugador) {
            jugador.setInvulnerabilidad(false);
        }
        @Override
        public boolean esInstantaneo() { return false; }
    },

    ESTRELLA(7000) {
        @Override
        public void aplicar(TanqueJugador jugador) {
            jugador.setDisparoMejorado(true);
        }
        @Override
        public void remover(TanqueJugador jugador) {
            jugador.setDisparoMejorado(false);
        }
        @Override
        public boolean esInstantaneo() { return false; }
    },

    GRANADA(0) { // efecto global, instantáneo
        @Override
        public void aplicar(TanqueJugador jugador) {
            EventoManager.getInstancia().notificar(TipoEvento.GRANADA_RECOGIDA);
        }
        @Override
        public void remover(TanqueJugador jugador) { /* no hace nada */ }
        @Override
        public boolean esInstantaneo() { return true; }
    };

    private final double duracionMs;

    TipoPowerUp(double duracionMs) {
        this.duracionMs = duracionMs;
    }

    public double getDuracionMs() { return duracionMs; }

    // Métodos abstractos que cada power-up debe implementar
    public abstract void aplicar(TanqueJugador jugador);
    public abstract void remover(TanqueJugador jugador);

    // Método para indicar si es instantáneo o duradero
    public abstract boolean esInstantaneo();
}
