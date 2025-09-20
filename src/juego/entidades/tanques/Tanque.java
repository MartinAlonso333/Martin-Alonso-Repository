package juego.entidades.tanques;

import juego.entidades.Ente;
import juego.utilidades.*;

public abstract class Tanque extends Ente {
    private int vida;
    private int danio;
    private Coordenada ultimaPosicion; // para manejar colisiones
    private long ultimoDisparo;
    private String spriteNormal;
    private String spriteDestruido;
    private final int velocidad;

    public Tanque(Coordenada posicion, Dimensiones dimensiones, int vida, int danio,
                  String spriteNormal, String spriteDestruido, int velocidad) {
        super(posicion, dimensiones);
        this.vida = vida;
        this.danio = danio;
        this.ultimoDisparo = System.currentTimeMillis();
        this.spriteNormal = spriteNormal;
        this.spriteDestruido = spriteDestruido;
        this.velocidad = velocidad;

        this.setSprite(spriteNormal);
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

    @Override
    public void actualizar(double deltaTime) {
        // Por defecto no hace nada, lo implementan las subclases
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
        this.vida -= cantidad;
        if (this.vida <= 0) {
            destruir();
        }
    }

    protected void destruir() {
        this.vida = 0;
        this.setSprite(spriteDestruido);
    }

    @Override
    public boolean estaDestruido() {
        return vida <= 0;
    }
}
