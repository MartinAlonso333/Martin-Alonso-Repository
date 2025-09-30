package org.vista.cargaDePartida;

import org.modelo.entidades.Ente;
import org.modelo.entidades.bloques.Bloque;
import org.modelo.entidades.bloques.TipoBloque;
import org.modelo.entidades.tanques.TanqueEnemigo;
import org.modelo.entidades.tanques.TanqueJugador;
import org.modelo.entidades.tanques.TipoTanque;
import org.modelo.utilidades.Coordenada;
import org.modelo.utilidades.Dimensiones;
import org.modelo.utilidades.Direccion;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.vista.GestorSprites;
import org.modelo.entidades.TipoEnte;

public class RegistroEntidades {

    private final Map<String, Function<Element, Ente>> registro = new HashMap<>();
    private final int jugadoresMaximos;

    public RegistroEntidades(int jugadoresMaximos) {
        this.jugadoresMaximos = jugadoresMaximos;
        inicializarRegistro();
    }

    private void inicializarRegistro() {
        // BLOQUES
        registrarBloque("steelBlock", TipoBloque.ACERO);
        registrarBloque("brickBlock", TipoBloque.LADRILLO);
        registrarBloque("baseBlock", TipoBloque.BASE);
        registrarBloque("forestBlock", TipoBloque.BOSQUE);
        registrarBloque("waterBlock", TipoBloque.AGUA);

        // ENEMIGOS
        registrarTanqueEnemigo("regularEnemy", TipoTanque.BASICO);
        registrarTanqueEnemigo("enemyFast", TipoTanque.RAPIDO);
        registrarTanqueEnemigo("enemyStrong", TipoTanque.POTENTE);
        registrarTanqueEnemigo("enemyArmored", TipoTanque.BLINDADO);
    }

    private void registrarBloque(String clave, TipoBloque tipo) {
        registro.put(clave, elem -> {
            Dimensiones dim = GestorSprites.getDimensionesPara(TipoEnte.BLOQUE, tipo);
            return new Bloque(
                    tipo,
                    new Coordenada(
                            Integer.parseInt(elem.getAttribute("x")),
                            Integer.parseInt(elem.getAttribute("y"))
                    ),
                    dim
            );
        });
    }

    private void registrarTanqueEnemigo(String clave, TipoTanque tipo) {
        registro.put(clave, elem -> {
            Dimensiones dim = GestorSprites.getDimensionesPara(TipoEnte.ENEMIGO, tipo);
            return new TanqueEnemigo(
                    new Coordenada(
                            Integer.parseInt(elem.getAttribute("x")),
                            Integer.parseInt(elem.getAttribute("y"))
                    ),
                    dim,
                    Direccion.ABAJO,
                    tipo
            );
        });
    }

    public Ente crearEnte(Element elem) {
        String tipo = elem.getAttribute("type");

        // JUGADORES
        if (tipo.startsWith("player")) {
            int idJugador = Integer.parseInt(tipo.substring(6)); // player1 -> 1
            if (idJugador > jugadoresMaximos) return null;

            Dimensiones dim = GestorSprites.getDimensionesPara(TipoEnte.JUGADOR, null);

            return new TanqueJugador(
                    new Coordenada(
                            Integer.parseInt(elem.getAttribute("x")),
                            Integer.parseInt(elem.getAttribute("y"))
                    ),
                    dim,
                    Direccion.ARRIBA,
                    idJugador
            );
        }

        // BLOQUES / ENEMIGOS
        Function<Element, Ente> constructor = registro.get(tipo);
        if (constructor != null) return constructor.apply(elem);

        throw new IllegalArgumentException("Tipo de ente desconocido: " + tipo);
    }
}
