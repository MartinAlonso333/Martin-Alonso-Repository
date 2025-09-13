package juego.entidades.powerups;

import juego.utilidades.Coordenada;

public class Granada extends PowerUp {
    public Granada(Coordenada posicion, Runnable efecto) {
        super(posicion, "Granada", efecto);
    }
}
