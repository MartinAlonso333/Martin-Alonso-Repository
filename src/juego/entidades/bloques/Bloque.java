package juego.entidades.bloques;

import juego.entidades.entes.Ente;
import juego.entidades.Coordenada;
import juego.entidades.Dimensiones;

public class Bloque extends Ente {

    private final TipoBloque tipo;
    private int vida;

    public Bloque(TipoBloque tipo, Coordenada posicion, Dimensiones dimensiones) {
        super(posicion, dimensiones);
        this.tipo = tipo;
        this.vida = tipo.getVidaInicial(); // vida según el tipo
    }

    public TipoBloque getTipo() {
        return tipo;
    }

    public int getVida() {
        return vida;
    }

    public boolean estaDestruido() {
        return vida <= 0 && tipo.esDestructible();
    }

    public void recibirDanio(int cantidad) {
        if (tipo.esDestructible()) {
            vida -= cantidad;
        }
    }

    public boolean permitePaso() {
        return tipo.permitePaso();
    }

    public boolean permiteBalas() {
        return tipo.permiteBalas();
    }

    @Override
    public void actualizar() {
        // Bloques estáticos: no necesitan actualizar nada
    }
}
