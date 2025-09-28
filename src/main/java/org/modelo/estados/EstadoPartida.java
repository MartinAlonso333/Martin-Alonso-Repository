package org.modelo.estados;

import org.modelo.Juego;
import org.modelo.entidades.Ente;
import org.modelo.entidades.TipoEnte;
import org.modelo.entidades.tanques.Bala;
import org.modelo.utilidades.Direccion;
import org.vista.EnteVista;
import org.vista.JuegoVista;
import org.vista.SpriteConfig;
import org.vista.cargaDePartida.ParserXML;
import org.modelo.entidades.tanques.TanqueEnemigo;
import org.modelo.entidades.tanques.TanqueJugador;
import org.vista.cargaDePartida.RegistroEntidades;

import java.util.List;
import java.util.Map;


public class EstadoPartida implements EstadoJuego {

    private final GestorEstados gestor;
    public final int nivelActual;
    private final int totalJugadores;
    private final int TOTAL_NIVELES = 3;

    private final Juego juego;
    private final JuegoVista juegoVista = new JuegoVista();

    public EstadoPartida(GestorEstados gestor, int nivel, int numJugadores) {
        this.gestor = gestor;
        this.nivelActual = nivel;
        this.totalJugadores = numJugadores;

        this.juego = new Juego();

        String archivoNivel = "levels/GeneratedLevels/nivel" + nivel + ".xml";
        List<ParserXML.ParEnteTipo> entesCargados = ParserXML.cargarNivel(numJugadores, archivoNivel);

        int idJugador = 1;
        for (ParserXML.ParEnteTipo par : entesCargados) {
            Ente ente = par.ente();
            String tipoStr = par.tipoStr();

            TipoEnte tipo = RegistroEntidades.tipoDesdeString(tipoStr);

            if (tipo == TipoEnte.JUGADOR) {
                // Crear jugador con id
                TanqueJugador jugador = RegistroEntidades.crearJugador(idJugador, ente.getPosicion(), Direccion.ARRIBA);
                juego.agregarJugador(jugador);

                // Crear vista con sprites
                try {
                    Map<Direccion, List<String>> rutas = SpriteConfig.getConfig(tipoStr);
                    EnteVista vista = new EnteVista(jugador, rutas);
                    juegoVista.agregarEnteVista(vista);
                } catch (IllegalArgumentException e) {
                    System.err.println("Sin config de sprites para: " + tipoStr + ". Error: " + e.getMessage());
                }

                idJugador++;
            } else {
                // Crear entidad normal (enemigos, bloques, etc)
                Ente entidad = RegistroEntidades.crearEntidad(tipoStr, ente.getPosicion(),Direccion.ARRIBA);
                juego.agregarEnte(entidad);

                // Crear vista con sprites
                try {
                    Map<Direccion, List<String>> rutas = SpriteConfig.getConfig(tipoStr);
                    EnteVista vista = new EnteVista(entidad, rutas);
                    juegoVista.agregarEnteVista(vista);
                } catch (IllegalArgumentException e) {
                    System.err.println("Sin config de sprites para: " + tipoStr + ". Error: " + e.getMessage());
                }
            }
        }
    }

    @Override
    public void actualizar(double deltaTime) {
        // Actualizar tanques para que se muevan frame a frame
        for (TanqueJugador jugador : juego.getEntesDeTipo(TanqueJugador.class)) {
            jugador.actualizar(deltaTime);
        }
        for (TanqueEnemigo enemigo : juego.getEntesDeTipo(TanqueEnemigo.class)) {
            enemigo.actualizar(deltaTime);
        }
        juego.actualizar(deltaTime);
        juegoVista.actualizar(deltaTime);
        if (nivelTerminado()) {
            if (nivelActual < TOTAL_NIVELES) {
                gestor.cambiarANivel(nivelActual + 1, totalJugadores);
            } else {
                gestor.cambiarAFinPartida();
            }
        } else if (juegoTerminado()) {
            gestor.cambiarAFinPartida();
        }
    }


    public void manejarInput(String input) {
        switch (input) {
            case "J1_ARRIBA" -> moverJugador(1, Direccion.ARRIBA);
            case "J1_ABAJO" -> moverJugador(1, Direccion.ABAJO);
            case "J1_IZQUIERDA" -> moverJugador(1, Direccion.IZQUIERDA);
            case "J1_DERECHA" -> moverJugador(1, Direccion.DERECHA);
            case "J1_DISPARO" -> dispararJugador(1);

            case "J2_ARRIBA" -> moverJugador(2, Direccion.ARRIBA);
            case "J2_ABAJO" -> moverJugador(2, Direccion.ABAJO);
            case "J2_IZQUIERDA" -> moverJugador(2, Direccion.IZQUIERDA);
            case "J2_DERECHA" -> moverJugador(2, Direccion.DERECHA);
            case "J2_DISPARO" -> dispararJugador(2);

            default -> {
                // Ignorar input desconocido
            }
        }
    }

    private void moverJugador(int jugadorId, Direccion direccion) {
        for (TanqueJugador jugador : juego.getEntesDeTipo(TanqueJugador.class)) {
            if (jugador.getId() == jugadorId) {
                jugador.mover(direccion);
                break;
            }
        }
    }

    private void dispararJugador(int jugadorId) {
        for (TanqueJugador jugador : juego.getEntesDeTipo(TanqueJugador.class)) {
            if (jugador.getId() == jugadorId) {
                Bala bala = jugador.disparar();
                if (bala != null) {
                    juego.agregarEnte(bala);
                }
                break;
            }
        }
    }

    private boolean juegoTerminado() {
        return juego.getEntesDeTipo(TanqueJugador.class).isEmpty();
    }

    private boolean nivelTerminado() {
        return juego.getEntesDeTipo(TanqueEnemigo.class).isEmpty();
    }

    public Juego getJuego() {
        return juego;
    }

    public JuegoVista getJuegoVista() {
        return juegoVista;
    }

    public void detenerMovimientoJugador(int jugadorId) {
        for (TanqueJugador jugador : juego.getEntesDeTipo(TanqueJugador.class)) {
            if (jugador.getId() == jugadorId) {
                jugador.detenerMovimiento();
                break;
            }
        }
    }
}