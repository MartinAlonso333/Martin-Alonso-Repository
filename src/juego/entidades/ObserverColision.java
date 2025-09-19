package juego.entidades;

public interface ObserverColision {
    void enteSeMovio(Ente e);
}

public interface ObservableColision {
    void agregarObserverColision(ObserverColision observer);
    void notificarColision();
}
