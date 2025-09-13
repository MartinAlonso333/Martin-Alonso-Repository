package juego.entidades.bloques;

public abstract class Bloque {
    protected Coordenada posicion;
    protected int vida;
    protected TipoBloque tipo;

    public Bloque(Coordenada coordenada, int vida, TipoBloque tipo) {
        this.posicion = coordenada;
        this.vida = vida;
        this.tipo = tipo;
    }

    public Coordenada getPosicion(){
        return posicion;
    }

    public TipoBloque getTipo() {
        return tipo;
    }

    public abstract boolean permitePaso();

    public abstract boolean permiteDisparo();

    public abstract void recibirDisparo();

    public boolean esIndestructible(){
        return false;
    }

    public void destruir() {
        vida = 0;
        // efectos
    }

    public boolean estaDestruido(){
        return this.vida <= 0;
    }

}