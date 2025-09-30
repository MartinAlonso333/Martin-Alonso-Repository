package org.modelo.entidades.tanques;

public enum TipoTanque {
    JUGADOR(3, 1, 1.0, 2000),
    BASICO(1, 1, 1.0, 2000),
    RAPIDO(1, 1, 3.0, 1000),
    POTENTE(3, 2, 1.0, 2000),
    BLINDADO(5, 1, 1.0, 2000);

    private final int vida;
    private final int danio;
    private final double velocidad;
    private final int velocidadDisparo;

    TipoTanque(int vida, int danio, double velocidad, int velocidadDisparo) {
        this.vida = vida;
        this.danio = danio;
        this.velocidad = velocidad;
        this.velocidadDisparo = velocidadDisparo;
    }

    public int getVida() { return vida; }
    public int getDanio() { return danio; }
    public double getVelocidad() { return velocidad; }
    public int getVelocidadDisparo() { return velocidadDisparo; }
}
