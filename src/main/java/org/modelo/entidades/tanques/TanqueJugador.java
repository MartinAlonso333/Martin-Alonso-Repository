package org.modelo.entidades.tanques;

import org.modelo.entidades.TipoEnte;
import org.modelo.eventos.GestorEventos;
import org.modelo.eventos.TipoEvento;
import org.modelo.utilidades.Coordenada;
import org.modelo.utilidades.Dimensiones;
import org.modelo.utilidades.Direccion;

public class TanqueJugador extends Tanque {
    private boolean invulnerable;
    private boolean disparoMejorado;
    private final int idJugador;
    private final GestorEventos em;

    public TanqueJugador(Coordenada posicion, Dimensiones dimensiones, Direccion direccionInicial, int idJugador, TipoTanque tipoTanque, GestorEventos gestorEventos) {
        super(posicion, dimensiones, tipoTanque, direccionInicial, gestorEventos);
        this.em = gestorEventos;
        this.idJugador = idJugador;
    }

    @Override
    public Bala disparar() {
        if (puedeDisparar()) {
            registrarDisparo();
            int danioDisparo = disparoMejorado ? getDanio() * 10 : getDanio();
            Bala bala = new Bala(getDireccion(), danioDisparo, getPuntoDeDisparo(), new Dimensiones(6, 6), this);
            em.notificar(TipoEvento.BALA_DISPARADA, bala);
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
