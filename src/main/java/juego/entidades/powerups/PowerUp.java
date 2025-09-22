package juego.entidades.powerups;

import juego.entidades.Ente;
import juego.entidades.TipoEnte;
import juego.entidades.tanques.TanqueJugador;
import juego.eventos.EventoManager;
import juego.utilidades.Coordenada;
import juego.utilidades.Dimensiones;

public class PowerUp extends Ente {

    private final TipoPowerUp tipo;

    public PowerUp(Coordenada pos, Dimensiones dim, TipoPowerUp tipo) {
        super(pos, dim);
        this.tipo = tipo;
    }

    public TipoPowerUp getTipoPowerUp() { return tipo; }

    public void aplicar(TanqueJugador jugador) {
        tipo.aplicar(jugador);
        EventoManager.getInstancia().notificar("tipo_powerup", tipo);
        setActivo(false);
    }

    @Override
    public void actualizar() {}

    @Override
    public boolean estaDestruido() { return !estaActivo(); }

    @Override
    public TipoEnte getTipo() { return TipoEnte.POWERUP; }
}
