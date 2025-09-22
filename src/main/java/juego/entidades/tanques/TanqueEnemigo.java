package main.java.juego.entidades.tanques;


import main.java.juego.entidades.TipoEnte;
import main.java.juego.utilidades.Coordenada;
import main.java.juego.utilidades.Dimensiones;
import main.java.juego.utilidades.Direccion;
import main.java.juego.utilidades.EventManager;

public class TanqueEnemigo extends Tanque {

    private long tiempoConducta;
    private long inicioConducta;
    private Direccion direccion;
    private Coordenada ultimaPosicionChequear;
    private long ultimoTiempoQuieto;

    public TanqueEnemigo(Coordenada posicion, Dimensiones dimensiones, int vida, int danio, int velocidad,int velocidadDeDisparo) {
        super(posicion, dimensiones, vida, danio, velocidad,velocidadDeDisparo);
        this.tiempoConducta = sortearTiempoConducta();
        this.inicioConducta = System.currentTimeMillis();
        this.direccion = sortearDireccion();
        this.ultimaPosicionChequear = new Coordenada(posicion.getX(), posicion.getY());
        this.ultimoTiempoQuieto = System.currentTimeMillis();
    }

    @Override
    public Bala disparar() {
        if (puedeDisparar(velocidadDeDisparo)) {
            registrarDisparo();
            Coordenada origen = getPuntoDeDisparo();
            Bala bala = new Bala(direccion, getDanio(),origen, new Dimensiones(8, 8), 5.0
            );
            EventManager.getInstancia().notificar("nueva_bala", bala);
        }
        return null;
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
