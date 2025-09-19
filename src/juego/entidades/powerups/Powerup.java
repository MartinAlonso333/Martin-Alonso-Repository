package juego.entidades.powerups;

import juego.entidades.tanques.Tanque;
import juego.utilidades.*;

public class PowerUp extends Ente {

    private boolean activo = true;
    private final TipoPowerUp tipo;

    public PowerUp(Coordenada pos, Dimensiones dim, TipoPowerUp tipo) {
        super(pos, dim);
        this.tipo = tipo;
    }

    public boolean activo() { return activo; }

    public void desactivar() {
        activo = false;
        notificarColision(); // para que el juego sepa que desapareció
    }

    public TipoPowerUp getTipo() { return tipo; }

    public void aplicar(Tanque tanque, GestorPowerUps gestor) {
        tipo.aplicar(tanque, gestor);
        desactivar();
    }

    @Override
    public void actualizar(double deltaTime) {
        // Solo notificar si hay cambios de estado o movimiento relevante
        tipo.actualizar(deltaTime);
    }

    @Override
    public boolean estaDestruido() {
        return !activo;
    }

    @Override
    public TipoEnte getTipo() {
        return TipoEnte.POWERUP;
    }
}
