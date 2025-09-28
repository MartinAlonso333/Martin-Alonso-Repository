package org.modelo.entidades.tanques;


import org.modelo.entidades.Ente;
import org.modelo.entidades.TipoEnte;
import org.modelo.utilidades.Coordenada;
import org.modelo.utilidades.Dimensiones;
import org.modelo.utilidades.Direccion;

public class Bala extends Ente {
    private final Direccion direccion;
    private int danio;
    private boolean activo;
    private final double velocidad;
    private Tanque duenio;


    public Bala(Direccion direccion, int danio, Coordenada posicion, Dimensiones dimensiones, double velocidad, Tanque duenio) {
        super(posicion, dimensiones);
        this.direccion = direccion;
        this.danio = danio;
        this.velocidad = velocidad;
        this.activo = true;
        this.duenio = duenio;
    }

    public Tanque getDuenio() { return duenio; }
    public int getDanio() { return danio; }
    public boolean estaActivo() { return activo; }
    public Direccion getDireccion() { return direccion; }

    public void setActivo(boolean activo) { this.activo = activo; }


    @Override
    public void actualizar() { direccion.aplicarMovimiento(posicion, velocidad); }

    @Override
    public TipoEnte getTipo() { return TipoEnte.BALA; }

    @Override
    public boolean estaDestruido() { return !activo; }
}
