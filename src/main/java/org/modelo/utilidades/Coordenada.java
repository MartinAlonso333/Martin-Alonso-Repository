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
    public int getX() { return celdaX; }
    public int getY() { return celdaY; }

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
    public String toString() {
        return "Celda(" + celdaX + "," + celdaY + ")";
    }
}