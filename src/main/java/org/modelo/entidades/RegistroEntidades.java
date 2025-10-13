package org.modelo.entidades;

import org.modelo.entidades.bloques.Bloque;
import org.modelo.entidades.bloques.TipoBloque;
import org.modelo.entidades.powerups.PowerUp;
import org.modelo.entidades.powerups.TipoPowerUp;
import org.modelo.entidades.tanques.TanqueEnemigo;
import org.modelo.entidades.tanques.TanqueJugador;
import org.modelo.entidades.tanques.TipoTanque;
import org.modelo.entidades.tanques.Bala;
import org.modelo.eventos.GestorEventos;
import org.modelo.utilidades.Coordenada;
import org.modelo.utilidades.Direccion;
import org.modelo.utilidades.Dimensiones;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class RegistroEntidades {

    private final Map<String, Function<Element, Ente>> registro = new HashMap<>();
    private final int jugadoresMaximos;
    private GestorEventos em;

    public RegistroEntidades(int jugadoresMaximos, GestorEventos gestorEventos) {
        this.jugadoresMaximos = jugadoresMaximos;
        inicializarRegistro();
        this.em = gestorEventos;
    }

    private void inicializarRegistro() {
        // BLOQUES
        registrarBloque("steelBlock", TipoBloque.ACERO);
        registrarBloque("brickBlock", TipoBloque.LADRILLO);
        registrarBloque("baseBlock", TipoBloque.BASE);
        registrarBloque("forestBlock", TipoBloque.BOSQUE);
        registrarBloque("waterBlock", TipoBloque.AGUA);
        registrarBloque("tankDestroyed", TipoBloque.AGUA);

        // ENEMIGOS
        registrarTanqueEnemigo("regularEnemy", TipoTanque.BASICO);
        registrarTanqueEnemigo("fastEnemy", TipoTanque.RAPIDO);
        registrarTanqueEnemigo("powerfulEnemy", TipoTanque.POTENTE);
        registrarTanqueEnemigo("heavyEnemy", TipoTanque.BLINDADO);

        // POWERUPS
        registrarPowerUp("helmetPowerUp", TipoPowerUp.CASCO);
        registrarPowerUp("starPowerUp", TipoPowerUp.ESTRELLA);
        registrarPowerUp("grenadePowerUp", TipoPowerUp.GRANADA);

        // BALA
        registro.put("bullet", elem -> new Bala(
                Direccion.ABAJO,
                1,
                new Coordenada(
                        Double.parseDouble(elem.getAttribute("x")),
                        Double.parseDouble(elem.getAttribute("y"))
                ),
                new Dimensiones(6, 6),
                null // el dueño se asigna al disparar
        ));
    }

    private void registrarBloque(String clave, TipoBloque tipo) {
        registro.put(clave, elem -> new Bloque(
                tipo,
                new Coordenada(
                        Double.parseDouble(elem.getAttribute("x")),
                        Double.parseDouble(elem.getAttribute("y"))
                ),
                new Dimensiones(20, 20) // después GestorSprites da la real
        ));
    }

    private void registrarTanqueEnemigo(String clave, TipoTanque tipo) {
        registro.put(clave, elem -> new TanqueEnemigo(
                new Coordenada(
                        Double.parseDouble(elem.getAttribute("x")),
                        Double.parseDouble(elem.getAttribute("y"))
                ),
                new Dimensiones(20, 20),
                Direccion.ABAJO,
                tipo,
                em
        ));
    }

    private void registrarPowerUp(String clave, TipoPowerUp tipo) {
        registro.put(clave, elem -> new PowerUp(
                new Coordenada(
                        Double.parseDouble(elem.getAttribute("x")),
                        Double.parseDouble(elem.getAttribute("y"))
                )
                , new Dimensiones(20, 20),
                tipo
        ));
    }

    public Ente crearJugador(Element elem) {
        String id = elem.getAttribute("id"); // player1, player2
        int numJugador = Integer.parseInt(id.replace("player", ""));
        if (numJugador > jugadoresMaximos) return null;

        double x = Double.parseDouble(elem.getAttribute("x"));
        double y = Double.parseDouble(elem.getAttribute("y"));

        TipoTanque tipo;
        if (numJugador == 1) {
            tipo = TipoTanque.JUGADOR1;
        } else if (numJugador == 2) {
            tipo = TipoTanque.JUGADOR2;
        } else {
            tipo = TipoTanque.JUGADOR1;
        }

        return new TanqueJugador(
                new Coordenada(x, y),
                new Dimensiones(20, 20),
                Direccion.ARRIBA,
                numJugador,
                tipo,
                em
        );
    }

    public Ente crearEnteConTipo(Element elem, String tipo) {
        Function<Element, Ente> constructor = registro.get(tipo);
        if (constructor != null) {
            return constructor.apply(elem);
        }
        throw new IllegalArgumentException("Tipo de ente desconocido en XML: " + tipo);
    }

}
