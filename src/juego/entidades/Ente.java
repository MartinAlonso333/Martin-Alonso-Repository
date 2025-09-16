package juego.entidades;

import javafx.geometry.Rectangle2D;

public abstract class Ente {
    protected Coordenada posicion;
    protected Dimensiones dimensiones;

    public Ente(Coordenada posicion, Dimensiones dimensiones) {
        this.posicion = posicion;
        this.dimensiones = dimensiones;
    }

    public Rectangle2D getArea() {
        return new Rectangle2D(posicion.getX(), posicion.getY(),
                dimensiones.getAncho(), dimensiones.getAlto());
    }

    public boolean colisionaCon(Ente otro) {
        return this.getArea().intersects(otro.getArea());
    }

    public Coordenada getPosicion() { return posicion; }
    public void setPosicion(Coordenada pos) { this.posicion = pos; }
    public Dimensiones getDimensiones() { return dimensiones; }

    public abstract void actualizar();
    public abstract boolean estaDestruido();
}
