package juego.entidades.powerups;

import juego.entidades.tanques.TanqueJugador;

public enum TipoPowerUp {

    CASCO(5000) {
        @Override
        public void aplicar(TanqueJugador jugador) { jugador.setInvulnerabilidad(true); }
        @Override
        public void remover(TanqueJugador jugador) { jugador.setInvulnerabilidad(false); }
    },

    ESTRELLA(7000) {
        @Override
        public void aplicar(TanqueJugador jugador) { jugador.setDisparoMejorado(true); }
        @Override
        public void remover(TanqueJugador jugador) { jugador.setDisparoMejorado(false); }
    },

    GRANADA(0) { // efecto global, no se maneja en GestorPowerUp
        @Override
        public void aplicar(TanqueJugador jugador) { /* no hace nada */ }
        @Override
        public void remover(TanqueJugador jugador) { /* no hace nada */ }
    };

    private final double duracionMs;

    TipoPowerUp(double duracionMs) { this.duracionMs = duracionMs; }
    public double getDuracionMs() { return duracionMs; }

    public abstract void aplicar(TanqueJugador jugador);
    public abstract void remover(TanqueJugador jugador);
}
