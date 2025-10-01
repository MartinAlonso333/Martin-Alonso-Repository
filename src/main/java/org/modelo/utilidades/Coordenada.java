package org.modelo.utilidades;

import java.util.Objects;

public class Coordenada {
    private int celdaX, celdaY;
    private double pixelX, pixelY;

    private static final int TAM_CELDA = 20; // tamaño de celda

    // Constructor por píxeles
    public Coordenada(double x, double y) {
        this.pixelX = x;
        this.pixelY = y;
        this.celdaX = (int)(x / TAM_CELDA);
        this.celdaY = (int)(y / TAM_CELDA);
    }

    // Getters
    public double getPixelX() { return pixelX; }
    public double getPixelY() { return pixelY; }
    public int getCeldaX() { return celdaX; }
    public int getCeldaY() { return celdaY; }

    // Setters
    public void setCoordenada(double x, double y) {
        this.pixelX = x;
        this.pixelY = y;
        this.celdaX = (int)(x / TAM_CELDA);
        this.celdaY = (int)(y / TAM_CELDA);
    }

    @Override
    public int hashCode() {
        return Objects.hash(celdaX, celdaY);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Coordenada)) return false;
        Coordenada other = (Coordenada) obj;
        return this.celdaX == other.celdaX && this.celdaY == other.celdaY;
    }

    @Override
    public String toString() {
        return String.format("Celda(%d,%d) Pixel(%.2f,%.2f)", celdaX, celdaY, pixelX, pixelY);
    }
}
