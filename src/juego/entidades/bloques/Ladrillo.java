package juego.entidades.bloques;

public class Ladrillo extends Bloque{
    public Ladrillo(Coordenada pos) {
        super(pos, 3, TipoBloque.LADRILLO);
    }

    @Override
    public boolean permitePaso() {
        return false;
    }

    @Override
    public boolean permiteDisparo() {
        return false;
    }

    @Override
    public void recibirDisparo(){
        this.vida--;
        if (estaDestruido()){
            destruir();
        }
    }
}
