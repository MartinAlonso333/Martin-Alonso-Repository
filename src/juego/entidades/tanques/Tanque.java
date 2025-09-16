package juego.entidades.tanques;

import juego.entidades.Ente;
import java.util.List;

public abstract class Tanque extends Ente {
    private int vida;
    private int danio;
    private long ultimoDisparo;
    private long tiempoConducta;
    private long inicioConducta;
    private String direccion;

    public Tanque(Coordenada posicion, Dimensiones dimensiones, int vida, int danio){
        super(posicion, dimensiones);
        this.vida = vida;
        this.danio = danio;
        this.ultimoDisparo = System.currentTimeMillis();
        this.tiempoConducta = sortearTiempoConducta();
        this.inicioConducta = System.currentTimeMillis();
        this.direccion = sortearDireccion();
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
            // lógica para destruir tanque
        }
    }

    public int getDanio() { return danio; }

    @Override
    public boolean estaDestruido() {
        return vida <= 0;
    }
}
