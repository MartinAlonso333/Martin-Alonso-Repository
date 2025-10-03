package org.modelo.utilidades;

import java.util.Objects;

public class Coordenada {
    private int celdaX, celdaY;
    private double pixelX, pixelY;
    public static final int TAM_CELDA = 20;

    public Coordenada(double x, double y) {
        setCoordenada(x, y);
    }

    public double getPixelX() { return pixelX; }
    public double getPixelY() { return pixelY; }
    public int getCeldaX() { return celdaX; }
    public int getCeldaY() { return celdaY; }

    public void setCoordenada(double x, double y) {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("Las coordenadas no pueden ser negativas: x=" + x + ", y=" + y);
        }
        this.pixelX = x;
        this.pixelY = y;
        this.celdaX = (int) (pixelX / TAM_CELDA);
        this.celdaY = (int) (pixelY / TAM_CELDA);
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Coordenada)) return false;
        Coordenada other = (Coordenada) obj;
        return celdaX == other.celdaX && celdaY == other.celdaY;
    }

    @Override
    public int hashCode() {
        return Objects.hash(celdaX, celdaY);
    }

    @Override
    public String toString() {
        return String.format("Celda(%d,%d) Pixel(%.2f,%.2f)", celdaX, celdaY, pixelX, pixelY);
    }
}
