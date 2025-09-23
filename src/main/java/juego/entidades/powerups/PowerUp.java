package juego.entidades.powerups;

import juego.entidades.Ente;
import juego.entidades.TipoEnte;
import juego.utilidades.Coordenada;
import juego.utilidades.Dimensiones;

public class PowerUp extends Ente {
    private static final int DURACION_DISPARO_MEJORADO = 10000;
    private static final int DURACION_INVULNERABILIDAD = 10000;

    public void aplicarEn(TanqueJugador jugador) {
        switch (tipo) {
            case CASCO -> jugador.activarInvulnerabilidad(DURACION_INVULNERABILIDAD);
            case ESTRELLA -> jugador.mejorarDisparo(DURACION_DISPARO_MEJORADO);
            case GRANADA -> ;
        }
    }

    private final TipoPowerUp tipo;

    public PowerUp(Coordenada pos, Dimensiones dim, TipoPowerUp tipo) {
        super(pos, dim);
        this.tipo = tipo;
    }

    public TipoPowerUp getTipoPowerUp() { return tipo; }

    @Override
    public void actualizar() {}

    @Override
    public boolean estaDestruido() { return !estaActivo(); }

    @Override
    public TipoEnte getTipo() { return TipoEnte.POWERUP; }
}
