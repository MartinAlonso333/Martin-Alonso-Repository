package juego.utilidades;

import java.util.Objects;

public class Coordenada {
    private int celdaX, celdaY;
    private double pixelX, pixelY;

    private static final int TAM_CELDA = 20; // tamaño de celda

    // Constructor por grilla
    public Coordenada(int celdaX, int celdaY) {
        this.celdaX = celdaX;
        this.celdaY = celdaY;
        this.pixelX = celdaX * TAM_CELDA;
        this.pixelY = celdaY * TAM_CELDA;
    }

    // Constructor por píxeles
    public Coordenada(double x, double y) {
        this.pixelX = x;
        this.pixelY = y;
        this.celdaX = (int)(x / TAM_CELDA);
        this.celdaY = (int)(y / TAM_CELDA); // corregido: antes estaba x/x
    }

    // Getters
    public int getCeldaX() { return celdaX; }
    public int getCeldaY() { return celdaY; }
    public double getPixelX() { return pixelX; }
    public double getPixelY() { return pixelY; }

    // Setters
    public void setPixeles(double x, double y) {
        this.pixelX = x;
        this.pixelY = y;
        this.celdaX = (int)(x / TAM_CELDA);
        this.celdaY = (int)(y / TAM_CELDA);
    }

    public void setCelda(int celdaX, int celdaY) {
        this.celdaX = celdaX;
        this.celdaY = celdaY;
        this.pixelX = celdaX * TAM_CELDA;
        this.pixelY = celdaY * TAM_CELDA;
    }

    // Igualdad basada en celdas
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Coordenada other = (Coordenada) obj;
        return celdaX == other.celdaX && celdaY == other.celdaY;
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
