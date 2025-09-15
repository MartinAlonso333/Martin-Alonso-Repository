package juego.entidades.bloques;

public class Bloque {
    private final TipoBloque tipo;
    private final Coordenada posicion;
    private int vida;

    // Constructor con vida personalizada
    public Bloque(TipoBloque tipo, Coordenada posicion, int vida) {
        this.tipo = tipo;
        this.posicion = posicion;
        this.vida = tipo.esDestructible() ? vida : Integer.MAX_VALUE;
    }

    // Constructor por defecto según tipo
    public Bloque(TipoBloque tipo, Coordenada posicion) {
        this(tipo, posicion, valorVidaPorTipo(tipo));
    }

    private static int valorVidaPorTipo(TipoBloque tipo) {
        switch(tipo) {
            case BASE: return 1;
            case LADRILLO: return 3;
            default: return 0; // indestructibles no usan vida real
        }
    }

    public void recibirDisparo(int danio) {
        if (!tipo.esDestructible()) return;
        vida -= danio;
        if (vida < 0) vida = 0;
    }

    public boolean estaDestruido() {
        return tipo.esDestructible() && vida <= 0;
    }

    public boolean permitePaso() { return tipo.permitePaso(); }
    public boolean permiteBalas() { return tipo.permiteBalas(); }
    public boolean esDestructible() { return tipo.esDestructible(); }

    public TipoBloque getTipo() { return tipo; }
    public Coordenada getPosicion() { return posicion; }
    public int getVida() { return vida; }

    @Override
    public String toString() {
        return tipo.name() + " en " + posicion +
                (tipo.esDestructible() ? " (vida=" + vida + ")" : " (indestructible)");
    }
}
