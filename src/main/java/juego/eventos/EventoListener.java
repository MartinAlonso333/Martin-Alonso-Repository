package juego.eventos;

import juego.entidades.Ente;
import juego.entidades.powerups.PowerUp;

public interface EventoListener {
    default void onEnteMovido(Ente ente) {}
    default void onEnteDestruido(Ente ente) {}
    default void onBalaCreada(Ente bala) {}
    default void onPowerUpRecolectado(PowerUp powerUp, Ente recolector) {}
}
