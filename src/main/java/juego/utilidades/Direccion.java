package juego.utilidades;

public enum Direccion {
    ARRIBA(0, -1),
    ABAJO(0, 1),
    IZQUIERDA(-1, 0),
    DERECHA(1, 0);

    private final int dx;
    private final int dy;

    Direccion(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    // Aplica el movimiento a la posición con cierta velocidad
    public void aplicarMovimiento(Coordenada pos, double velocidad) {
        pos.setPixeles(pos.getX() + dx * velocidad, pos.getY() + dy * velocidad);
    }
}

