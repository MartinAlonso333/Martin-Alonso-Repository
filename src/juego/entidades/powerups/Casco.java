package juego.entidades.powerups;

import juego.utilidades.Coordenada;

public class Casco extends PowerUp {
    public Casco(Coordenada posicion, Runnable efecto) {
        super(posicion, "Casco", efecto);
    }
}
