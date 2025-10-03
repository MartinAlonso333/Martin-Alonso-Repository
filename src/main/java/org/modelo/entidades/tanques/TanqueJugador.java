package org.modelo.entidades.tanques;

import org.modelo.entidades.TipoEnte;
import org.modelo.utilidades.Coordenada;
import org.modelo.utilidades.Dimensiones;
import org.modelo.utilidades.Direccion;

public class TanqueJugador extends Tanque {
    private boolean invulnerable;
    private boolean disparoMejorado;
    private final int idJugador;

    public TanqueJugador(Coordenada posicion, Dimensiones dimensiones, Direccion direccionInicial, int idJugador, TipoTanque tipoTanque) {
        super(posicion, dimensiones, tipoTanque, direccionInicial);
        this.idJugador = idJugador;
    }

    @Override
    public Bala disparar() {
        if (puedeDisparar()) {
            registrarDisparo();
            int danioDisparo = disparoMejorado ? getDanio() * 10 : getDanio();
            return new Bala(getDireccion(), danioDisparo, getPuntoDeDisparo(), new Dimensiones(6, 6), this);
        }
        return null;
    }

    public void setInvulnerabilidad(boolean estado) {
        invulnerable = estado;
    }

    public void setDisparoMejorado(boolean estado) { disparoMejorado = estado; }

    @Override
    public void actualizar(double deltaTime) { super.actualizar(deltaTime); }

    @Override
    public void recibirDanio(int cantidad) {
        if (!invulnerable) super.recibirDanio(cantidad);
    }

    @Override
    public TipoEnte getTipoEnte() { return TipoEnte.JUGADOR; }
    public TipoTanque getSubtipo() { return getTipoTanque(); }
    public int getIdJugador() { return idJugador; }
}
