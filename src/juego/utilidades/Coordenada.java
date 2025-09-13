package juego.utilidades
public class Coordenada {
    int celdaX, celdaY;
    double pixelX, pixelY;

    private static final int TAM_CELDA = 20; // tamaño de celda

    // Constructor por grilla
    public Coordenada(int celdaX, int celdaY) {
        this.celdaX = celdaX;
        this.celdaY = celdaY;
        this.pixelX = celdaX * SIZE;
        this.pixelY = celdaY * SIZE;
    }

    // Constructor por píxeles
    public Coordenada(double x, double y) {
        this.pixelX = x;
        this.pixelY = y;
        this.celdaX = (int)(x / SIZE);
        this.celdaY = (int)(x / SIZE);
    }

    // Getters
    public int getCeldaX() { return celdaX; }
    public int getCeldaY() { return celdaY; }
    public double getX() { return x; }
    public double getY() { return y; }

    // Setters
    public void setPixeles(double x, double y) {
        this.x = x;
        this.y = y;
        this.celdaX = (int)(x / SIZE);
        this.celdaY = (int)(y / SIZE);
    }

    public void setCelda(int celdaX, int celdaY) {
        this.celdaX = celdaX;
        this.celdaY = celdaY;
        this.x = celdaX * SIZE;
        this.y = celdaY * SIZE;
    }
}