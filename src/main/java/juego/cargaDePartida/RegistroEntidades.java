package juego.cargaDePartida;

import juego.entidades.*;
import juego.entidades.bloques.*;
import juego.entidades.tanques.*;
import juego.utilidades.Coordenada;
import juego.utilidades.Dimensiones;
import juego.utilidades.Direccion;

import javafx.scene.image.Image;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class RegistroEntidades {

    private static final Map<TipoEnte, BiFunction<Coordenada, Direccion, Ente>> registro = new HashMap<>();
    private static final Map<String, TipoEnte> tipoStrToEnte = new HashMap<>();

    static {
        // --- Map de string a TipoEnte ---
        tipoStrToEnte.put("player1", TipoEnte.JUGADOR);
        tipoStrToEnte.put("player2", TipoEnte.JUGADOR);
        tipoStrToEnte.put("regularEnemy", TipoEnte.TANQUE_BASICO);
        tipoStrToEnte.put("fastEnemy", TipoEnte.TANQUE_RAPIDO);
        tipoStrToEnte.put("heavyEnemy", TipoEnte.TANQUE_BLINDADO);
        tipoStrToEnte.put("powerfulEnemy", TipoEnte.TANQUE_POTENTE);
        tipoStrToEnte.put("bullet", TipoEnte.BALA);
        tipoStrToEnte.put("steelBlock", TipoEnte.BLOQUE);
        tipoStrToEnte.put("brickBlock", TipoEnte.BLOQUE);
        tipoStrToEnte.put("waterBlock", TipoEnte.BLOQUE);
        tipoStrToEnte.put("forestBlock", TipoEnte.BLOQUE);
        tipoStrToEnte.put("baseBlock", TipoEnte.BLOQUE);
        tipoStrToEnte.put("powerup", TipoEnte.POWERUP);

        // --- Registro de constructores ---
        registro.put(TipoEnte.JUGADOR, (pos, dir) ->
                new TanqueJugador(pos, obtenerDimensionesSprite("player.png"), dir, 3, 1, 1, 2, dir));

        registro.put(TipoEnte.TANQUE_BASICO, (pos, dir) ->
                new TanqueBasico(pos, obtenerDimensionesSprite("basicEnemy.png"), dir));
        registro.put(TipoEnte.TANQUE_RAPIDO, (pos, dir) ->
                new TanqueRapido(pos, obtenerDimensionesSprite("fastEnemy.png"), dir));
        registro.put(TipoEnte.TANQUE_BLINDADO, (pos, dir) ->
                new TanqueBlindado(pos, obtenerDimensionesSprite("heavyEnemy.png"), dir));
        registro.put(TipoEnte.TANQUE_POTENTE, (pos, dir) ->
                new TanquePotente(pos, obtenerDimensionesSprite("powerfulEnemy.png"), dir));

        registro.put(TipoEnte.BLOQUE, (pos, dir) ->
                new Bloque(TipoBloque.TANQUE_DESTRUIDO, pos, obtenerDimensionesSprite("emptyBlock.png")));
    }

    public static Ente crearEntidad(TipoEnte tipo, Coordenada pos, Direccion dir) {
        BiFunction<Coordenada, Direccion, Ente> constructor = registro.get(tipo);
        if (constructor == null) {
            throw new IllegalArgumentException("Tipo de entidad desconocido: " + tipo);
        }
        return constructor.apply(pos, dir);
    }

    // Sobrecarga para bloques y objetos sin dirección
    public static Ente crearEntidad(TipoEnte tipo, Coordenada pos) {
        return crearEntidad(tipo, pos, null);
    }

    public static TipoEnte tipoDesdeString(String str) {
        TipoEnte tipo = tipoStrToEnte.get(str);
        if (tipo == null) throw new IllegalArgumentException("Tipo desconocido: " + str);
        return tipo;
    }

    private static Dimensiones obtenerDimensionesSprite(String spritePath) {
        try (InputStream is = RegistroEntidades.class.getResourceAsStream("/sprites/" + spritePath)) {
            if (is == null) {
                System.err.println("No se encontró el sprite: " + spritePath);
                return new Dimensiones(32, 32);
            }
            Image img = new Image(is);
            return new Dimensiones((int) img.getWidth(), (int) img.getHeight());
        } catch (Exception e) {
            e.printStackTrace();
            return new Dimensiones(32, 32);
        }
    }
}