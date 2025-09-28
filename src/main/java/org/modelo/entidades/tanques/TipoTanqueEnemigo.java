package org.modelo.entidades.tanques;

import org.modelo.utilidades.Direccion;

public enum TipoTanqueEnemigo {
    BASICO(1, 1, 1, 2000),     // vida, danio, velocidad, velocidadDisparo (ms)
    RAPIDO(2, 1, 3, 2000),
    POTENTE(2, 3, 1, 1000),
    BLINDADO(3, 1, 1, 2000);

    private final int vida;
    private final int danio;
    private final int velocidad;
    private final int velocidadDisparo;

    TipoTanqueEnemigo(int vida, int danio, int velocidad, int velocidadDisparo) {
        this.vida = vida;
        this.danio = danio;
        this.velocidad = velocidad;
        this.velocidadDisparo = velocidadDisparo;
    }

    public int getVida() { return vida; }
    public int getDanio() { return danio; }
    public int getVelocidad() { return velocidad; }
    public int getVelocidadDisparo() { return velocidadDisparo; }


}
