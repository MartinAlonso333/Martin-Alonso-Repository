package juego.utilidades;

public class Dimensiones {
    public final int ancho;
    public final int alto;

    public Dimensiones(int ancho, int alto) {
        if (ancho <= 0 || alto <= 0) {
            throw new IllegalArgumentException("Las dimensiones deben ser positivas");
        }
        this.ancho = ancho;
        this.alto = alto;
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }

    public int getArea() {
        return ancho * alto;
    }
}
