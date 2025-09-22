package juego.eventos;

public abstract class Evento {
    private final Object source;

    public Evento(Object source) {
        this.source = source;
    }

    public Object getSource() {
        return source;
    }
}
