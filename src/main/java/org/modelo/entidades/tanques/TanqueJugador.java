package org.modelo.entidades.tanques;

import org.modelo.entidades.TipoEnte;
import org.modelo.utilidades.Coordenada;
import org.modelo.utilidades.Dimensiones;
import org.modelo.utilidades.Direccion;

public class TanqueJugador extends Tanque {

    private boolean invulnerable;
    private boolean disparoMejorado;
    private int idJugador;
    public TanqueJugador(Coordenada posicion, Dimensiones dimensiones, Direccion direccion, double vida, int danio, int velocidad, int velocidadDeDisparo, Direccion direccionInicial, int idJugador) {
        super(posicion, dimensiones, 3, 1, 1,2,direccionInicial);
        this.idJugador = idJugador;
    }

    @Override
    public Bala disparar() {
        if (puedeDisparar(velocidadDeDisparo)) {
            registrarDisparo();

            // Punto de salida del disparo
            Coordenada origen = new Coordenada(
                    getPosicion().getX() + getDimensiones().getAncho() / 2,
                    getPosicion().getY() + getDimensiones().getAlto() / 2
            );
            int danioDisparo = disparoMejorado ? getDanio() * 10 : getDanio();

            return new Bala(getDireccion(), getDanio(), origen, new Dimensiones(8, 8), 8.0, this);
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

    }
    public int getId() {
        return idJugador;
    }
    @Override
    public void recibirDanio(int cantidad) {
        if (!invulnerable) super.recibirDanio(cantidad);
    }

    @Override
    public TipoEnte getTipo() { return TipoEnte.JUGADOR; }

    public void setId(int idJugador) {
        this.idJugador=idJugador;
    }
}
