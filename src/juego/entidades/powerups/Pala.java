package juego.entidades.powerups;

import juego.utilidades.Coordenada;

public class Pala extends PowerUp {
    public Pala(Coordenada posicion, Runnable efecto) {
        super(posicion, "Pala", efecto);
    }
}
