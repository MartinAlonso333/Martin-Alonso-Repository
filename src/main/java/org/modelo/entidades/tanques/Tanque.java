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
    protected final double velocidad; // en píxeles por segundo
    protected final int velocidadDeDisparo;
    protected Direccion direccion;
    private long tiempoQuieto = 0;

    private boolean estaMoviendo = false; // indica si el tanque debe moverse

    protected final TipoTanque tipo; // 🔹 ahora guardamos el tipo

    public Tanque(Coordenada posicion, Dimensiones dimensiones, TipoTanque tipo, Direccion direccionInicial) {
        super(posicion, dimensiones);
        this.tipo = tipo;
        this.vida = tipo.getVida();
        this.danio = tipo.getDanio();
        this.velocidad = tipo.getVelocidad();
        this.velocidadDeDisparo = tipo.getVelocidadDisparo();
        this.ultimoDisparo = System.currentTimeMillis();
        this.posicion = posicion;
        this.direccion = direccionInicial;
    }

    public abstract Bala disparar();

    protected Coordenada getPuntoDeDisparo() {
        return new Coordenada(
                getPosicion().getPixelX() + getDimensiones().getAncho() / 2,
                getPosicion().getPixelY() + getDimensiones().getAlto() / 2
        );
    }

    public Direccion getDireccion() { return direccion; }

    public void mover(Direccion dir) {
        if (System.currentTimeMillis() < tiempoQuieto) {
            estaMoviendo = false;
            return;
        }
        this.direccion = dir;
        estaMoviendo = true;
    }

    public void detenerMovimiento() {
        estaMoviendo = false;
    }

    public void actualizar(double deltaTime) {
        if (!estaMoviendo) return;
        ultimaPosicion = new Coordenada(posicion.getPixelX(), posicion.getPixelY());
        Coordenada nuevaPos = new Coordenada(posicion.getPixelX(), posicion.getPixelY());
        direccion.aplicarMovimiento(nuevaPos, velocidad * deltaTime);
        setPosicion(nuevaPos);
    }

    public void revertirMovimiento() {
        if (ultimaPosicion != null) {
            setPosicion(new Coordenada(ultimaPosicion.getPixelX(), ultimaPosicion.getPixelY()));
        }
    }

    public void aturdir(long duracionMs) {
        tiempoQuieto = System.currentTimeMillis() + duracionMs;
        estaMoviendo = false;
    }

    public boolean puedeDisparar(int intervaloMs) {
        return System.currentTimeMillis() - ultimoDisparo >= intervaloMs;
    }

    protected void registrarDisparo() {
        ultimoDisparo = System.currentTimeMillis();
    }

    public int getVida() { return vida; }
    public int getDanio() { return danio; }
    public TipoTanque getTipoTanque() { return tipo; } // 🔹 nuevo getter

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
