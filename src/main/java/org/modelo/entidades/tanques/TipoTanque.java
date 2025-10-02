package org.modelo.entidades.tanques;

public enum TipoTanque {
    JUGADOR1(3, 1, 100.0, 1500),
    JUGADOR2(3, 1, 100.0, 1500),
    BASICO(1, 1, 50.0, 3000),
    RAPIDO(1, 1, 150.0, 1000),
    POTENTE(2, 2, 50.0, 2000),
    BLINDADO(5, 1, 50.0, 2000);

    private final int vida;
    private final int danio;
    private final double velocidad;   // píxeles por segundo
    private final int velocidadDisparo; // ms entre disparos

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
