package juego.entidades.tanques;
import juego.entidades.entes.Ente;

public abstract class Tanque {
    private Ente entecontrolador;
    private int vida;
    private int danio;
    private long ultimoDisparo;
    private long tiempoConducta;
    private long inicioConducta;
    private String direccion;

    public Tanque(Ente entecontrolador, int vida, int danio){
        this.entecontrolador = entecontrolador;
        this.vida = vida;
        this.danio = danio;
        this.ultimoDisparo = System.currentTimeMillis();
        this.tiempoConducta = sortearTiempoConducta();
        this.inicioConducta = System.currentTimeMillis();
        this.direccion=sortearDireccion();

    }
    public abstract void disparar();
    public abstract void mover();

    public long sortearTiempoConducta(){
        return(1+(int)(Math.random()*5))*1000L;
    }

    public String sortearDireccion(){
        String[] direcciones = {"↑", "↓", "←", "→"};
        return direcciones[(int)(Math.random() * 4)];
    }

    public boolean puedeDisparar(int intervaloMs){
        return  System.currentTimeMillis()-ultimoDisparo>=intervaloMs;

    }

    protected void registrarDisparo(){
        ultimoDisparo = System.currentTimeMillis();
    }
    public int getVida() {
        return vida;
    }

    public void recibirDanio(int cantidad){
        this.vida -= cantidad;
    }

    public int getDanio() {
        return danio;
    }

    public Ente getEntecontrolador(){
        return entecontrolador;
    }
}
