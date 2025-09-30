package org.modelo.entidades;

import org.modelo.utilidades.Coordenada;
import org.modelo.utilidades.Dimensiones;
import org.modelo.utilidades.Direccion;

public abstract class Ente {
    protected Coordenada posicion;
    protected Dimensiones dimensiones;
    private boolean activo = true;

    public Ente(Coordenada posicion, Dimensiones dimensiones) {
        this.posicion = posicion;
        this.dimensiones = dimensiones;
    }

    public Coordenada getPosicion() { return posicion; }

    public void setPosicion(Coordenada nuevaPos) {
        this.posicion = nuevaPos;
    }

    public Dimensiones getDimensiones() { return dimensiones; }

    public boolean estaActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public boolean intersecta(Ente otro) {
        return this.posicion.getPixelX() < otro.posicion.getPixelX() + otro.dimensiones.getAncho() &&
                this.posicion.getPixelX() + this.dimensiones.getAncho() > otro.posicion.getPixelX() &&
                this.posicion.getPixelY() < otro.posicion.getPixelY() + otro.dimensiones.getAlto() &&
                this.posicion.getPixelY() + this.dimensiones.getAlto() > otro.posicion.getPixelY();
    }

    public abstract void actualizar();
    public abstract boolean estaDestruido();
    public abstract TipoEnte getTipoEnte();

    public Enum<?> getSubtipo() {
        return null; // Por defecto, no hay subtipo
    }

    public Direccion getDireccion() {
        return null; // Por defecto, no hay dirección
    }
}
