package org.modelo.entidades.tanques;


import org.modelo.eventos.GestorEventos;
import org.modelo.eventos.TipoEvento;
public enum TipoTanque {
    JUGADOR1("player1", 3, 1, 100.0, 1500),  // Clave para Jugador1
    JUGADOR2("player2", 3, 1, 100.0, 1500),  // Clave para Jugador2
    BASICO("EnemyTankRegular", 1, 1, 50.0, 3000),
    RAPIDO("EnemyTankFast", 1, 1, 150.0, 2000),
    POTENTE("EnemyTankPowerful", 1, 1, 100, 1000),
    BLINDADO("EnemyTankHeavy", 3, 1, 100, 2000) {
        @Override
        public void emitirEvento(GestorEventos em, Tanque tanque) {
            em.notificar(TipoEvento.TANQUE_BLINDADO_IMPACTADO, tanque);
        }
    };
    private final String claveSprite;
    private final int vida;
    private final int danio;
    private final double velocidad;
    private final int velocidadDisparo;
    TipoTanque(String claveSprite, int vida, int danio, double velocidad, int velocidadDisparo) {
        this.claveSprite = claveSprite;
        this.vida = vida;
        this.danio = danio;
        this.velocidad = velocidad;
        this.velocidadDisparo = velocidadDisparo;
    }
    public String getClaveSprite() {
        return claveSprite;
    }
    public int getVida() { return vida; }
    public int getDanio() { return danio; }
    public double getVelocidad() { return velocidad; }
    public int getVelocidadDisparo() { return velocidadDisparo; }
    public void emitirEvento(GestorEventos em, Tanque tanque) {}
}