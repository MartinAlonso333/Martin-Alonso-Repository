package juego;

import juego.entidades.*;
import juego.entidades.tanques.Bala;
import juego.entidades.tanques.TanqueJugador;
import juego.gestores.SistemaColision;
import juego.utilidades.Direccion;

import java.util.ArrayList;
import java.util.List;

public class Juego {
    private final List<Ente> entes = new ArrayList<>();
    private final List<TanqueJugador> jugadores = new ArrayList<>();
    private final SistemaColision sistemaColision = new SistemaColision();

    /** Agrega un jugador al juego */
    public void agregarJugador(TanqueJugador jugador) {
        jugadores.add(jugador);
        agregarEnte(jugador);
    }

    /** Agrega cualquier ente */
    public void agregarEnte(Ente e) { entes.add(e); }

    /** Remueve un ente del juego */
    public void removerEnte(Ente e) { entes.remove(e); }

    /** Mueve un jugador según su índice en la lista de jugadores */
    public void moverJugador(int indice, Direccion dir) {
        if (indice >= 0 && indice < jugadores.size()) {
            jugadores.get(indice).mover(dir);
        }
    }

    /** Hace disparar a un jugador según su índice */
    public void dispararJugador(int indice) {
        if (indice >= 0 && indice < jugadores.size()) {
            Bala bala = jugadores.get(indice).disparar();
            if (bala != null) agregarEnte(bala);
        }
    }

    /** Actualiza todos los entes y colisiones */
    public void actualizar(double deltaTime) {
        List<Bala> nuevasBalas = new ArrayList<>();

        for (Ente e : entes) {
            e.actualizar();
            Bala b = e.disparar();
            if (b != null) nuevasBalas.add(b);
        }

        for (Bala b : nuevasBalas) agregarEnte(b);

        for (Ente e : entes) {
            if (e.estaActivo()) sistemaColision.chequearColisiones(e);
        }

        entes.removeIf(Ente::estaDestruido);
    }

    public List<Ente> getEntes() {
        return new ArrayList<>(entes);
    }
}
