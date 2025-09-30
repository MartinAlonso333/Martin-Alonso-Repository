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

    public TanqueEnemigo(Coordenada posicion, Dimensiones dimensiones, Direccion direccionInicial, TipoTanque tipo) {
        super(posicion, dimensiones, tipo, direccionInicial); // 🔹 usamos el enum directamente
        this.tiempoConducta = sortearTiempoConducta();
        this.inicioConducta = System.currentTimeMillis();
        this.direccion = direccionInicial;
        this.ultimaPosicionChequear = new Coordenada(posicion.getPixelX(), posicion.getPixelY());
        this.ultimoTiempoQuieto = System.currentTimeMillis();
    }

    @Override
    public Bala disparar() {
        if (puedeDisparar(velocidadDeDisparo)) {
            registrarDisparo();
            Coordenada origen = getPuntoDeDisparo();
            return new Bala(getDireccion(), getDanio(), origen, new Dimensiones(8, 8), 8, this);
        }
        return null;
    }

    @Override
    public void actualizar() {
        long ahora = System.currentTimeMillis();

        // Cambiar dirección si terminó el tiempo de conducta
        if (ahora - inicioConducta >= tiempoConducta) {
            direccion = sortearDireccion();
            tiempoConducta = sortearTiempoConducta();
            inicioConducta = ahora;
        }

        // Cambiar dirección si se quedó quieto demasiado tiempo
        if (getPosicion().equals(ultimaPosicionChequear)) {
            if (ahora - ultimoTiempoQuieto >= 2000) {
                direccion = sortearDireccion();
            }
        } else {
            ultimaPosicionChequear = new Coordenada(getPosicion().getPixelX(), getPosicion().getPixelY());
            ultimoTiempoQuieto = ahora;
        }

        mover(direccion);
    }

    @Override
    public TipoEnte getTipoEnte() {
        return TipoEnte.ENEMIGO;
    }


    public TipoTanque getSubtipo() {
        return getTipoTanque();
    }

    private long sortearTiempoConducta() {
        return (1 + (int)(Math.random() * 5)) * 1000L;
    }

    private Direccion sortearDireccion() {
        Direccion[] direcciones = Direccion.values();
        return direcciones[(int)(Math.random() * direcciones.length)];
    }
}
