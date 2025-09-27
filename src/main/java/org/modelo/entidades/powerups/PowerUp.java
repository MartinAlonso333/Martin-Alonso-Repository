package org.modelo.entidades.powerups;

import org.modelo.entidades.Ente;
import org.modelo.entidades.TipoEnte;
import org.modelo.utilidades.Coordenada;
import org.modelo.utilidades.Dimensiones;

public class PowerUp extends Ente {
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
