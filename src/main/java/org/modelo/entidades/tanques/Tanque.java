package org.modelo.entidades.tanques;


import org.modelo.entidades.Ente;
import org.modelo.utilidades.Coordenada;
import org.modelo.utilidades.Dimensiones;
import org.modelo.utilidades.Direccion;

public abstract class Tanque extends Ente {
    private int vida;
    private final int danio;
    private Coordenada ultimaPosicion;
    private long ultimoDisparo;
    protected final int velocidad;
    protected final int velocidadDeDisparo;
    protected Direccion direccion;
    private long tiempoQuieto = 0;

    public Tanque(Coordenada posicion, Dimensiones dimensiones, int vida, int danio, int velocidad, int velocidadDeDisparo,Direccion direccionInicial) {
        super(posicion, dimensiones);
        this.vida = vida;
        this.danio = danio;
        this.velocidad = velocidad;
        this.velocidadDeDisparo = velocidadDeDisparo;
        this.ultimoDisparo = System.currentTimeMillis();
        this.posicion = posicion;
        this.direccion = direccionInicial;
    }

    public abstract Bala disparar();

    protected Coordenada getPuntoDeDisparo() {
        return new Coordenada(
                getPosicion().getX() + getDimensiones().getAncho() / 2,
                getPosicion().getY() + getDimensiones().getAlto() / 2
        );
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void mover(Direccion dir) {
        if (System.currentTimeMillis() < tiempoQuieto) {
            return;
        }

        ultimaPosicion = new Coordenada(posicion.getX(), posicion.getY());
        Coordenada nuevaPos = new Coordenada(posicion.getX(), posicion.getY());
        dir.aplicarMovimiento(nuevaPos, velocidad);
        setPosicion(nuevaPos);
    }


    public void revertirMovimiento() {
        if (ultimaPosicion != null) {
            setPosicion(new Coordenada(ultimaPosicion.getX(), ultimaPosicion.getY()));
        }
    }

    public void aturdir(long duracionMs) {
        tiempoQuieto = System.currentTimeMillis() + duracionMs;
    }

    public boolean puedeDisparar(int intervaloMs) {
        return System.currentTimeMillis() - ultimoDisparo >= intervaloMs;
    }

    protected void registrarDisparo() {
        ultimoDisparo = System.currentTimeMillis();
    }

    public int getVida() { return vida; }
    public int getDanio() { return danio; }

    public void recibirDanio(int cantidad) {
        if (vida <= 0) return;
        vida -= cantidad;
        if (vida <= 0) destruir();
    }

    public void destruir() {
        vida = 0;
        setActivo(false);
    }

    @Override
    public boolean estaDestruido() {
        return vida <= 0;
    }
}
