package juego.entidades.powerups;

import juego.entidades.Ente;
import juego.entidades.TipoEnte;
import juego.utilidades.Coordenada;
import juego.utilidades.Dimensiones;

public class PowerUp extends Ente {
    private static final int DURACION_DISPARO_MEJORADO = 10000;
    private static final int DURACION_INVULNERABILIDAD = 10000;

    public void aplicarEn(TanqueJugador jugador, GestorPowerUp gestor) {
        gestor.activarPowerUp(jugador, tipo, tipo.getDuracionMs());
    }

    public TipoPowerUp getTipoPowerUp() { return tipo; }

    @Override
    public void actualizar() {}

    @Override
    public boolean estaDestruido() { return !estaActivo(); }

    @Override
    public TipoEnte getTipo() { return TipoEnte.POWERUP; }
}
