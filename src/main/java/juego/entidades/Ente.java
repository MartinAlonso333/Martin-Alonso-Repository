package juego.entidades;

import juego.gestores.ColisionVisitor;
import juego.utilidades.Coordenada;
import juego.utilidades.Dimensiones;
import juego.utilidades.EventManager;

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
        EventManager.getInstancia().notificar("ente_movido:" + this.hashCode());
    }

    public Dimensiones getDimensiones() { return dimensiones; }

    public boolean estaActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public boolean intersecta(Ente otro) {
        return this.posicion.getX() < otro.posicion.getX() + otro.dimensiones.ancho() &&
                this.posicion.getX() + this.dimensiones.ancho() > otro.posicion.getX() &&
                this.posicion.getY() < otro.posicion.getY() + otro.dimensiones.alto() &&
                this.posicion.getY() + this.dimensiones.alto() > otro.posicion.getY();
    }

    public abstract void aceptar(ColisionVisitor visitor, Ente otro);
    public abstract void actualizar();
    public abstract boolean estaDestruido();
    public abstract TipoEnte getTipo();
}
