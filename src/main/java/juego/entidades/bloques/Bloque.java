package main.java.juego.entidades.bloques;

import juego.entidades.Ente;
import juego.entidades.TipoEnte;
import juego.gestores.ColisionVisitor;
import juego.utilidades.Coordenada;
import juego.utilidades.Dimensiones;
import juego.utilidades.EventManager;

public class Bloque extends Ente {
    private final TipoBloque tipo;
    private int vida;

    public Bloque(TipoBloque tipo, Coordenada posicion, Dimensiones dimensiones) {
        super(posicion, dimensiones);
        this.tipo = tipo;
        this.vida = tipo.getVidaInicial();
    }

    public TipoBloque getTipoBloque() { return tipo; }
    public int getVida() { return vida; }
    public boolean permitePaso() { return tipo.permitePaso(); }
    public boolean permiteBalas() { return tipo.permiteBalas(); }
    public boolean esDestructible() { return tipo.esDestructible(); }

    public void recibirDanio(int cantidad) {
        if (tipo.esDestructible()) {
            vida -= cantidad;
            if (estaDestruido()) {
                setActivo(false);
                EventManager.getInstancia().notificar("bloque_destruido", this);
            }
        }
    }

    @Override
    public void actualizar() {}

    @Override
    public boolean estaDestruido() { return tipo.esDestructible() && vida <= 0; }

    @Override
    public void aceptar(ColisionVisitor visitor, Ente otro) { visitor.visit(this, otro); }

    @Override
    public TipoEnte getTipo() { return TipoEnte.BLOQUE; }
}
