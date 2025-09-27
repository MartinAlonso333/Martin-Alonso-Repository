package org.modelo.entidades.tanques;

import org.modelo.entidades.TipoEnte;
import org.modelo.utilidades.Coordenada;
import org.modelo.utilidades.Dimensiones;
import org.modelo.utilidades.Direccion;

public class TanqueEnemigo extends Tanque {

    private long tiempoConducta;
    private long inicioConducta;
    private Direccion direccion;
    private Coordenada ultimaPosicionChequear;
    private long ultimoTiempoQuieto;
    private final TipoTanqueEnemigo tipo;

    public TanqueEnemigo(Coordenada posicion, Dimensiones dimensiones, Direccion direccionInicial, TipoTanqueEnemigo tipo) {
        super(posicion, dimensiones, tipo.getVida(), tipo.getDanio(), tipo.getVelocidad(), tipo.getVelocidadDisparo(), direccionInicial);
        this.tipo = tipo;
        this.tiempoConducta = sortearTiempoConducta();
        this.inicioConducta = System.currentTimeMillis();
        this.direccion = direccionInicial;
        this.ultimaPosicionChequear = new Coordenada(posicion.getX(), posicion.getY());
        this.ultimoTiempoQuieto = System.currentTimeMillis();
    }

    @Override
    public Bala disparar() {
        if (puedeDisparar(velocidadDeDisparo)) {
            registrarDisparo();
            Coordenada origen = getPuntoDeDisparo();
            return new Bala(getDireccion(), getDanio(), origen, new Dimensiones(8, 8), 8.0, this);
        }
        return null;
    }

    @Override
    public void actualizar() {
        long ahora = System.currentTimeMillis();

        // Cambiar dirección si terminó tiempo de conducta
        if (ahora - inicioConducta >= tiempoConducta) {
            direccion = sortearDireccion();
            tiempoConducta = sortearTiempoConducta();
            inicioConducta = ahora;
        }

        // Cambiar dirección si se quedó quieto
        if (getPosicion().equals(ultimaPosicionChequear)) {
            if (ahora - ultimoTiempoQuieto >= 2000) {
                direccion = sortearDireccion();
            }
        } else {
            ultimaPosicionChequear = new Coordenada(getPosicion().getX(), getPosicion().getY());
            ultimoTiempoQuieto = ahora;
        }

        mover(direccion);
    }

    @Override
    public TipoEnte getTipo() {
        return TipoEnte.ENEMIGO;
    }

    public TipoTanqueEnemigo getTipoTanqueEnemigo() {
        return tipo;
    }

    private long sortearTiempoConducta() {
        return (1 + (int)(Math.random() * 5)) * 1000L;
    }

    private Direccion sortearDireccion() {
        Direccion[] direcciones = Direccion.values();
        return direcciones[(int)(Math.random() * direcciones.length)];
    }
}
