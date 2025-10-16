package org.modelo.entidades.powerups;

import org.modelo.entidades.Ente;
import org.modelo.utilidades.Coordenada;
import org.modelo.utilidades.Dimensiones;
import org.modelo.entidades.TipoEnte;

public class PowerUp extends Ente {
    private final TipoPowerUp tipo;
    private final PowerUpEfecto efecto;

    public PowerUp(Coordenada pos, Dimensiones dim, TipoPowerUp tipo) {
        super(pos, dim);
        this.tipo = tipo;
        this.efecto = PowerUpFabrica.crearEfecto(tipo);
    }

    @Override
    public void actualizar(double deltaTime) {
        // Los powerups no se mueven, no hay nada que actualizar
    }

    @Override
    public boolean estaDestruido() {
        return !estaActivo();
    }

    @Override
    public TipoEnte getTipoEnte() {
        return TipoEnte.POWERUP;
    }

    @Override
    public String getClaveSprite() {
        return efecto.getClaveSprite();
    }

    public TipoPowerUp getTipoPowerUp() {
        return tipo;
    }

}
