package juego.entidades;

import juego.utilidades.Coordenada;
import juego.utilidades.Dimensiones;

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
        return this.posicion.getX() < otro.posicion.getX() + otro.dimensiones.getAncho() &&
                this.posicion.getX() + this.dimensiones.getAncho() > otro.posicion.getX() &&
                this.posicion.getY() < otro.posicion.getY() + otro.dimensiones.getAlto() &&
                this.posicion.getY() + this.dimensiones.getAlto() > otro.posicion.getY();
    }
    public abstract void actualizar();
    public abstract boolean estaDestruido();
    public abstract TipoEnte getTipo();
}
