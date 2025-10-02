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
            Bala bala = new Bala(getDireccion(), getDanio(), getPuntoDeDisparo(), new Dimensiones(6, 6), this);
            return bala;
        }
        return null;
    }

    @Override
    public void actualizar(double deltaTime) {
        long ahora = System.currentTimeMillis();

        // Cambiar dirección cada tiempo aleatorio
        if (ahora - inicioConducta >= tiempoConducta) {
            mover(sortearDireccion());
            tiempoConducta = sortearTiempoConducta();
            inicioConducta = ahora;
        }

        // Cambiar dirección si está atascado
        if (getPosicion().equals(ultimaPosicionChequear)) {
            if (ahora - ultimoTiempoQuieto >= 2000) {
                mover(sortearDireccion());
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