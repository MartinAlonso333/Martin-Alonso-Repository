package juego.entidades.tanques;

import juego.entidades.TipoEnte;
import juego.utilidades.Coordenada;
import juego.utilidades.Dimensiones;
import juego.utilidades.Direccion;

public class TanqueEnemigo extends Tanque {

    private long tiempoConducta;
    private long inicioConducta;
    private Direccion direccion;
    private Coordenada ultimaPosicionChequear;
    private long ultimoTiempoQuieto;

    public TanqueEnemigo(Coordenada posicion, Dimensiones dimensiones, int vida, int danio, int velocidad) {
        super(posicion, dimensiones, vida, danio, velocidad);
        this.tiempoConducta = sortearTiempoConducta();
        this.inicioConducta = System.currentTimeMillis();
        this.direccion = sortearDireccion();
        this.ultimaPosicionChequear = new Coordenada(posicion.getX(), posicion.getY());
        this.ultimoTiempoQuieto = System.currentTimeMillis();
    }

    @Override
    public void disparar() {
        if (puedeDisparar(1500)) { // 1.5s entre disparos
            System.out.println("Tanque enemigo disparó desde " + getPosicion());
            registrarDisparo();
        }
    }

    @Override
    public void actualizar() {
        long ahora = System.currentTimeMillis();
        if (ahora - inicioConducta >= tiempoConducta) {
            direccion = sortearDireccion();
            tiempoConducta = sortearTiempoConducta();
            inicioConducta = ahora;
        }

        if (getPosicion().equals(ultimaPosicionChequear)) {
            if (ahora - ultimoTiempoQuieto >= 2000) direccion = sortearDireccion();
        } else {
            ultimaPosicionChequear = new Coordenada(getPosicion().getX(), getPosicion().getY());
            ultimoTiempoQuieto = ahora;
        }

        mover(direccion);
        disparar();
    }

    @Override
    public TipoEnte getTipo() { return TipoEnte.ENEMIGO; }

    private long sortearTiempoConducta() { return (1 + (int)(Math.random()*5))*1000L; }
    private Direccion sortearDireccion() {
        Direccion[] direcciones = Direccion.values();
        return direcciones[(int)(Math.random() * direcciones.length)];
    }
}
