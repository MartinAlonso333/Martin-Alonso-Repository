package main.java.juego.entidades.tanques;


import main.java.juego.entidades.Ente;
import main.java.juego.entidades.TipoEnte;
import main.java.juego.gestores.ColisionVisitor;
import main.java.juego.utilidades.Coordenada;
import main.java.juego.utilidades.Dimensiones;
import main.java.juego.utilidades.Direccion;

public class Bala extends Ente {
    private final Direccion direccion;
    private int danio;
    private boolean activo;
    private final double velocidad;


    public Bala(Direccion direccion, int danio, Coordenada posicion, Dimensiones dimensiones, double velocidad) {
        super(posicion, dimensiones);
        this.direccion = direccion;
        this.danio = danio;
        this.velocidad = velocidad;
        this.activo = true;
    }

    public int getDanio() { return danio; }
    public boolean estaActivo() { return activo; }

    public void setDanio(int danio) { this.danio = danio; }
    public void setActivo(boolean activo) { this.activo = activo; }

    @Override
    public void aceptar(ColisionVisitor visitor, Ente otro) {
    }
    @Override
    public void actualizar() { direccion.aplicarMovimiento(posicion, velocidad); }

    @Override
    public TipoEnte getTipo() { return TipoEnte.BALA; }

    @Override
    public boolean estaDestruido() { return !activo; }

    @Override
    public void aceptar(juego.gestores.ColisionVisitor visitor, Ente otro) {
        visitor.visit(this, otro);
    }
}
