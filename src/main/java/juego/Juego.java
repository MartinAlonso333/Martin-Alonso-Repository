package juego;

import juego.entidades.*;
import juego.entidades.bloques.Bloque;
import juego.entidades.powerups.PowerUp;
import juego.entidades.tanques.TanqueEnemigo;
import juego.entidades.tanques.TanqueJugador;
import juego.gestores.GestorPowerUp;
import juego.gestores.SistemaColision;
import juego.utilidades.EventManager;

import java.util.*;

public class Juego {

    private final SistemaColision sistemaColision = new SistemaColision();
    private final GestorPowerUp gestorPowerUps = new GestorPowerUp();

    private final List<Ente> entes = new ArrayList<>();
    private final Map<TipoEnte, List<Ente>> entesPorTipo = new EnumMap<>(TipoEnte.class);

    public Juego() {
        for (TipoEnte te : TipoEnte.values()) entesPorTipo.put(te, new ArrayList<>());

        EventManager.getInstancia().suscribir("granada_activada", datos -> {
            for (Ente e : entesPorTipo.get(TipoEnte.ENEMIGO)) {
                ((TanqueEnemigo) e).recibirDanio(((TanqueEnemigo) e).getVida());
            }
        });
    }

    public void agregarEnte(Ente e) {
        entes.add(e);
        sistemaColision.registrarEnte(e);
        entesPorTipo.get(e.getTipo()).add(e);
    }

    public void quitarEnte(Ente e) {
        entes.remove(e);
        sistemaColision.desregistrarEnte(e);
        entesPorTipo.get(e.getTipo()).remove(e);
    }

    public void actualizar(double deltaTime) {
        for (Ente e : new ArrayList<>(entes)) e.actualizar();
        gestorPowerUps.actualizar(deltaTime);
    }

    public List<Ente> getEntes() { return entes; }
    public List<Ente> getEntes(TipoEnte tipo) { return entesPorTipo.get(tipo); }
}
