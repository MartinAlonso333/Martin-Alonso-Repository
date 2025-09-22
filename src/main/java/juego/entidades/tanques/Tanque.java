package main.java.juego.entidades.tanques;


import main.java.juego.entidades.Ente;
import main.java.juego.gestores.ColisionVisitor;
import main.java.juego.utilidades.Coordenada;
import main.java.juego.utilidades.Dimensiones;
import main.java.juego.utilidades.Direccion;
import main.java.juego.utilidades.EventManager;

public abstract class Tanque extends Ente {
    private int vida;
    private final int danio;
    private Coordenada ultimaPosicion;
    private long ultimoDisparo;
    protected final int velocidad;
    protected final int velocidadDeDisparo;
    private Direccion direccion;
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
                getPosicion().getX() + getDimensiones().ancho() / 2,
                getPosicion().getY() + getDimensiones().alto() / 2
        );
    }

    public Direccion getDireccion() {
        return direccion;
    }
    public void mover(Direccion dir) {
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

    protected void destruir() {
        vida = 0;
        setActivo(false);
        EventManager.getInstancia().notificar("tanque_destruido", this);
    }

    @Override
    public boolean estaDestruido() {
        return vida <= 0;
    }

    @Override
    public void aceptar(ColisionVisitor visitor, Ente otro) {
        visitor.visit(this, otro);
    }
}
