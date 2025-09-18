package juego.entidades.powerups;

import juego.entidades.Coordenada;
import juego.entidades.Dimensiones;
import juego.entidades.Ente;

import java.util.Random;

public class PowerUp extends Ente {
    private boolean activo = true;
    private TipoPowerUp tipo;

    public PowerUp(Coordenada pos, Dimensiones dim, TipoPowerUp tipo) {
        super(pos, dim);
        this.tipo = tipo;
    }

    public boolean activo() { return activo; }
    public void desactivar() { activo = false; }
    public TipoPowerUp getTipo() { return tipo; }

    public void aplicar(Tanque t, GestorPowerUps gestor) {
        tipo.aplicar(t, gestor);
        desactivar();
    }

    @Override
    public void actualizar(double deltaTime) {
        tipo.actualizar(deltaTime);
    }
    @Override
    public boolean estaDestruido() { return !activo; }
}
