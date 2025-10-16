package org.modelo.entidades.tanques;

import org.modelo.entidades.TipoEnte;
import org.modelo.eventos.GestorEventos;
import org.modelo.eventos.TipoEvento;
import org.modelo.utilidades.Coordenada;
import org.modelo.utilidades.Dimensiones;
import org.modelo.utilidades.Direccion;

public class TanqueJugador extends Tanque {
    private final int idJugador;
    private boolean invulnerable;
    private boolean disparoMejorado;
    private final GestorEventos em;
    private long tiempoQuieto = 0;

    public TanqueJugador(int idJugador, Coordenada posicion, Dimensiones dimensiones, Direccion direccionInicial, TipoTanque tipoTanque, GestorEventos gestorEventos) {
        super(posicion, dimensiones, tipoTanque, direccionInicial, gestorEventos);
        this.idJugador = idJugador;
        this.em = gestorEventos;
    }

    public int getIdJugador() { return idJugador; }

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

    @Override
    public void mover(Direccion dir) {
        // Bloquea movimiento si está aturdido
        if (System.currentTimeMillis() < tiempoQuieto) {
            detenerMovimiento();
            return;
        }
        super.mover(dir);
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
    protected boolean manejarAturdimiento(Bala bala) {
        if (bala.getDuenio().getTipoEnte() == TipoEnte.JUGADOR) {
            aturdir(2000);
            bala.setActivo(false);
            return true;
        }
        return false;
    }

    private void aturdir(long duracionMs) {
        tiempoQuieto = System.currentTimeMillis() + duracionMs;
        detenerMovimiento();
    }

    @Override
    public TipoEnte getTipoEnte() { return TipoEnte.JUGADOR; }
    public TipoTanque getSubtipo() { return getTipoTanque(); }

    @Override
    public String getClaveSprite() {
        return getTipoTanque().getClaveSprite();
    }

    @Override
    public void destruir() {
        super.destruir();
        em.notificar(TipoEvento.TANQUE_JUGADOR_DESTRUIDO, this);
    }
}
