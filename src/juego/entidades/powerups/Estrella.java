package juego.entidades.powerups;

import juego.utilidades.Coordenada;

public class Estrella extends PowerUp {
    public Estrella(Coordenada posicion, Runnable efecto) {
        super(posicion, "Estrella", efecto);
    }
}
