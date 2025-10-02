package org.modelo.entidades.tanques;

import org.modelo.entidades.TipoEnte;
import org.modelo.utilidades.Coordenada;
import org.modelo.utilidades.Dimensiones;
import org.modelo.utilidades.Direccion;

public class TanqueEnemigo extends Tanque {

    private long tiempoConducta;
    private long inicioConducta;
    private Coordenada ultimaPosicionChequear;
    private long ultimoTiempoQuieto;

    public TanqueEnemigo(Coordenada posicion, Dimensiones dimensiones, Direccion direccionInicial, TipoTanque tipo) {
        super(posicion, dimensiones, tipo, direccionInicial);
        this.tiempoConducta = sortearTiempoConducta();
        this.inicioConducta = System.currentTimeMillis();
        this.ultimaPosicionChequear = new Coordenada(posicion.getPixelX(), posicion.getPixelY());
        this.ultimoTiempoQuieto = System.currentTimeMillis();
    }

    @Override
    public Bala disparar() {
        if (puedeDisparar()) {
            registrarDisparo();
            return new Bala(getDireccion(), getDanio(), getPuntoDeDisparo(), new Dimensiones(6, 6), this);
        }
        return null;
    }

    @Override
    public void actualizar(double deltaTime) {
        long ahora = System.currentTimeMillis();

        if (ahora - inicioConducta >= tiempoConducta) {
            Direccion nueva = sortearDireccion();
            while (nueva == getDireccion()) {
                nueva = sortearDireccion();
            }
            mover(nueva);
            tiempoConducta = sortearTiempoConducta();
            inicioConducta = ahora;
        }
        if (getPosicion().equals(ultimaPosicionChequear)) {
            if (ahora - ultimoTiempoQuieto >= 2000) {
                Direccion nueva = sortearDireccion();
                while (nueva == getDireccion()) {
                    nueva = sortearDireccion();
                }
                mover(nueva);
                ultimaPosicionChequear = new Coordenada(getPosicion().getPixelX(), getPosicion().getPixelY());
                ultimoTiempoQuieto = ahora;
            }
        } else {
            ultimaPosicionChequear = new Coordenada(getPosicion().getPixelX(), getPosicion().getPixelY());
            ultimoTiempoQuieto = ahora;
        }

        super.actualizar(deltaTime);
    }

    @Override
    public TipoEnte getTipoEnte() { return TipoEnte.ENEMIGO; }
    public TipoTanque getSubtipo() { return getTipoTanque(); }

    private long sortearTiempoConducta() { return (1 + (int)(Math.random() * 5)) * 1000L; }

    private Direccion sortearDireccion() {
        Direccion[] direcciones = Direccion.values();
        return direcciones[(int)(Math.random() * direcciones.length)];
    }
}