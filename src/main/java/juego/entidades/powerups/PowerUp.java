package main.java.juego.entidades.powerups;

import juego.entidades.Ente;
import juego.entidades.TipoEnte;
import juego.gestores.ColisionVisitor;
import juego.utilidades.Coordenada;
import juego.utilidades.Dimensiones;
import juego.utilidades.EventManager;

public class PowerUp extends Ente {
    private final TipoPowerUp tipo;

    public PowerUp(Coordenada pos, Dimensiones dim, TipoPowerUp tipo) {
        super(pos, dim);
        this.tipo = tipo;
    }

    public TipoPowerUp getTipoPowerUp() { return tipo; }

    @Override
    public void aceptar(ColisionVisitor visitor, Ente otro) { visitor.visit(this, otro); }

    @Override
    public void actualizar() {}

    @Override
    public boolean estaDestruido() { return !estaActivo(); }

    public void desactivar() {
        setActivo(false);
        EventManager.getInstancia().notificar("powerup_destruido", this);
    }

    @Override
    public TipoEnte getTipo() { return TipoEnte.POWERUP; }
}
