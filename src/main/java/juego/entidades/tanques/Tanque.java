package juego.entidades.tanques;

import juego.entidades.Ente;
import juego.gestores.ColisionVisitor;
import juego.utilidades.Coordenada;
import juego.utilidades.Dimensiones;
import juego.utilidades.Direccion;
import juego.utilidades.EventManager;

public abstract class Tanque extends Ente {
    private int vida;
    private final int danio;
    private Coordenada ultimaPosicion;
    private long ultimoDisparo;
    private final int velocidad;

    public Tanque(Coordenada posicion, Dimensiones dimensiones, int vida, int danio, int velocidad) {
        super(posicion, dimensiones);
        this.vida = vida;
        this.danio = danio;
        this.velocidad = velocidad;
        this.ultimoDisparo = System.currentTimeMillis();
    }

    public abstract void disparar();

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
