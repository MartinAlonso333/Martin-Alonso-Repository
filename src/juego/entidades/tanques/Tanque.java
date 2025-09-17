package juego.entidades.tanques;

import juego.entidades.Ente;
import java.util.List;
import juego.utilidades.Coordenada;
import juego.utilidades.Dimensiones;

public abstract class Tanque extends Ente {
    private int vida;
    private int danio;
    private long ultimoDisparo;
    private long tiempoConducta;
    private long inicioConducta;
    private String direccion;
    private String spriteNormal;
    private String spriteDestruido;

    public Tanque(Coordenada posicion, Dimensiones dimensiones, int vida, int danio, String spriteNormal, String spriteDestruido) {
        super(posicion, dimensiones);
        this.vida = vida;
        this.danio = danio;
        this.ultimoDisparo = System.currentTimeMillis();
        this.tiempoConducta = sortearTiempoConducta();
        this.inicioConducta = System.currentTimeMillis();
        this.direccion = sortearDireccion();
        this.spriteNormal = spriteNormal;
        this.spriteDestruido = spriteDestruido;

        this.setSprite(spriteNormal);
    }

    public abstract void disparar();

    /** Lógica de movimiento según dirección y deltaTiempo */
    public abstract void mover();

    @Override
    public void actualizar() {
        mover();
        // Las colisiones se chequean desde Tablero o Juego
        // Se podrían actualizar powerups activos aquí
    }

    public long sortearTiempoConducta(){
        return (1 + (int)(Math.random()*5)) * 1000L;
    }

    public String sortearDireccion(){
        String[] direcciones = {"↑", "↓", "←", "→"};
        return direcciones[(int)(Math.random() * 4)];
    }

    public boolean puedeDisparar(int intervaloMs){
        return System.currentTimeMillis() - ultimoDisparo >= intervaloMs;
    }

    protected void registrarDisparo(){
        ultimoDisparo = System.currentTimeMillis();
    }

    public int getVida() { return vida; }

    public void recibirDanio(int cantidad){
        this.vida -= cantidad;
        if (this.vida <= 0) {
            destruir();
        }
    }

    protected void destruir(){
        this.vida = 0;
        this.setSprite(spriteDestruido);
    }
    public int getDanio() { return danio; }

    @Override
    public boolean estaDestruido() {
        return vida <= 0;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
