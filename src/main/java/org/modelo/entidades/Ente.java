package org.modelo.entidades;

import org.modelo.utilidades.Coordenada;
import org.modelo.utilidades.Dimensiones;
import org.modelo.utilidades.Direccion;

public abstract class Ente {
    protected Coordenada posicion;
    protected Dimensiones dimensiones;
    private boolean activo = true;

    private static final int MARGEN = 1;

    public Ente(Coordenada posicion, Dimensiones dimensiones) {
        this.posicion = posicion;
        this.dimensiones = dimensiones;
    }
    public boolean estaMoviendo() {
        return false;
    }
    public Coordenada getPosicion() { return posicion; }
    public void setPosicion(Coordenada nuevaPos) { this.posicion = nuevaPos; }
    public Dimensiones getDimensiones() { return dimensiones; }
    public boolean estaActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public boolean intersecta(Ente otro) {
        return this.posicion.getPixelX() + MARGEN < otro.posicion.getPixelX() + otro.dimensiones.getAncho() - MARGEN &&
                this.posicion.getPixelX() + this.dimensiones.getAncho() - MARGEN > otro.posicion.getPixelX() + MARGEN &&
                this.posicion.getPixelY() + MARGEN < otro.posicion.getPixelY() + otro.dimensiones.getAlto() - MARGEN &&
                this.posicion.getPixelY() + this.dimensiones.getAlto() - MARGEN > otro.posicion.getPixelY() + MARGEN;
    }


    public void revertirMovimiento(Coordenada ultimaPosicion) {
        if (ultimaPosicion != null)
            posicion.setCoordenada(ultimaPosicion.getPixelX(), ultimaPosicion.getPixelY());
    }

    public abstract void actualizar(double deltaTime);
    public abstract boolean estaDestruido();
    public abstract TipoEnte getTipoEnte();
    public Direccion getDireccion() { return null; }
    public boolean permitePaso() { return false; }
    public Enum<?> getSubtipo() { return null; }
}
