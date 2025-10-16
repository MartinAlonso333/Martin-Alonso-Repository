package org.modelo.entidades.tanques;

import org.modelo.entidades.TipoEnte;
import org.modelo.eventos.GestorEventos;
import org.modelo.eventos.TipoEvento;
import org.modelo.utilidades.Coordenada;
import org.modelo.utilidades.Dimensiones;
import org.modelo.utilidades.Direccion;

public class TanqueEnemigo extends Tanque {

    private long tiempoConducta;
    private long inicioConducta;
    private Coordenada ultimaPosicionChequear;
    private long ultimoTiempoQuieto;
    private GestorEventos em;

    public TanqueEnemigo(Coordenada posicion, Dimensiones dimensiones, Direccion direccionInicial, TipoTanque tipo, GestorEventos gestorEventos) {
        super(posicion, dimensiones, tipo, direccionInicial, gestorEventos);
        this.em = gestorEventos;
        this.tiempoConducta = sortearTiempoConducta();
        this.inicioConducta = System.currentTimeMillis();
        this.ultimaPosicionChequear = new Coordenada(posicion.getPixelX(), posicion.getPixelY());
        this.ultimoTiempoQuieto = System.currentTimeMillis();
    }

    @Override
    public Bala disparar() {
        if (puedeDisparar()) {
            registrarDisparo();
            Bala bala = new Bala(getDireccion(), getDanio(), getPuntoDeDisparo(), new Dimensiones(6, 6), this);
            em.notificar(TipoEvento.BALA_DISPARADA, bala);
        }
        return null;
    }

    @Override
    public void actualizar(double deltaTime) {
        long ahora = System.currentTimeMillis();

        // Actualiza la conducta periódicamente
        manejarConducta(ahora);

        // Evita quedarse quieto demasiado tiempo
        manejarQuietud(ahora);

        disparar();

        super.actualizar(deltaTime);
    }

    private void manejarConducta(long ahora) {
        if (ahora - inicioConducta >= tiempoConducta) {
            moverDireccionAleatoria();
            tiempoConducta = sortearTiempoConducta();
            inicioConducta = ahora;
        }
    }

    private void manejarQuietud(long ahora) {
        if (getPosicion().equals(ultimaPosicionChequear)) {
            if (ahora - ultimoTiempoQuieto >= 2000) {
                moverDireccionAleatoria();
                actualizarUltimaPosicionYTiempo(ahora);
            }
        } else {
            actualizarUltimaPosicionYTiempo(ahora);
        }
    }

    private void moverDireccionAleatoria() {
        Direccion nueva = sortearDireccionDiferente();
        mover(nueva);
    }

    private Direccion sortearDireccionDiferente() {
        Direccion nueva = sortearDireccion();
        while (nueva == getDireccion()) {
            nueva = sortearDireccion();
        }
        return nueva;
    }

    private void actualizarUltimaPosicionYTiempo(long ahora) {
        ultimaPosicionChequear = new Coordenada(getPosicion().getPixelX(), getPosicion().getPixelY());
        ultimoTiempoQuieto = ahora;
    }

    @Override
    public TipoEnte getTipoEnte() { return TipoEnte.ENEMIGO; }
    public TipoTanque getSubtipo() { return getTipoTanque(); }

    @Override
    public String getClaveSprite() {
        return getTipoTanque().getClaveSprite();
    }

    private long sortearTiempoConducta() { return (1 + (int)(Math.random() * 5)) * 1000L; }

    private Direccion sortearDireccion() {
        Direccion[] direcciones = Direccion.values();
        return direcciones[(int)(Math.random() * direcciones.length)];
    }

    @Override
    public void destruir() {
        super.destruir();
        em.notificar(TipoEvento.TANQUE_DESTRUIDO, this);
    }
}