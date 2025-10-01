package org.modelo.entidades.tanques;

import org.modelo.entidades.TipoEnte;
import org.modelo.utilidades.Coordenada;
import org.modelo.utilidades.Dimensiones;
import org.modelo.utilidades.Direccion;

public class TanqueJugador extends Tanque {

    private boolean invulnerable;
    private boolean disparoMejorado;
    private int idJugador;

    public TanqueJugador(Coordenada posicion, Dimensiones dimensiones, Direccion direccionInicial, int idJugador) {
        super(posicion, dimensiones, TipoTanque.JUGADOR, direccionInicial);
        this.idJugador = idJugador;
    }

    @Override
    public Bala disparar() {
        if (puedeDisparar(velocidadDeDisparo)) {
            registrarDisparo();
            Coordenada origen = new Coordenada(
                    getPosicion().getPixelX() + (double) getDimensiones().getAncho() / 2,
                    getPosicion().getPixelY() + (double) getDimensiones().getAlto() / 2
            );
            int danioDisparo = disparoMejorado ? getDanio() * 10 : getDanio();
            return new Bala(getDireccion(), danioDisparo, origen, new Dimensiones(8, 8), 8, this);
        }
        return null;
    }

    public void setInvulnerabilidad(boolean estado) {
        invulnerable = estado;
    }

    public void setDisparoMejorado(boolean estado) {
        disparoMejorado = estado;
    }

    @Override
    public void actualizar() {
        // lógica de actualización específica del jugador (powerups, efectos, etc.)
    }

    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }


    @Override
    public void recibirDanio(int cantidad) {
        if (!invulnerable) super.recibirDanio(cantidad);
    }

    @Override
    public TipoEnte getTipoEnte() {
        return TipoEnte.JUGADOR;
    }


    public TipoTanque getSubtipo() {
        return getTipoTanque();
    }
}
