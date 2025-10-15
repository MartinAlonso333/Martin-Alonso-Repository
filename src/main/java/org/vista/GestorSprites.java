package org.vista;

import javafx.scene.image.Image;
import org.modelo.entidades.ConSprite;
import org.modelo.entidades.Ente;
import org.modelo.entidades.bloques.Bloque;
import org.modelo.entidades.bloques.TipoBloque;
import org.modelo.entidades.powerups.PowerUp;
import org.modelo.entidades.powerups.TipoPowerUp;
import org.modelo.entidades.tanques.TanqueEnemigo;
import org.modelo.entidades.tanques.TanqueJugador;
import org.modelo.entidades.tanques.TipoTanque;
import org.modelo.entidades.tanques.Bala;
import org.modelo.utilidades.Direccion;

import java.io.InputStream;
import java.util.*;
import java.util.function.Function;

public class GestorSprites {

    private final Map<String, SpriteConfig> configs = new HashMap<>();
    private static final Map<String, Image> cache = new HashMap<>();
    private static final String RUTA_SPRITES = "/sprites/";

    public GestorSprites() {
        inicializarConfigs();
    }

    private void inicializarConfigs() {
        // BLOQUES
        configs.put("SteelBlock", new SpriteConfig("SteelBlock", 1));
        configs.put("BrickBlock", new SpriteConfig("BrickBlock", 1));
        configs.put("BaseBlock", new SpriteConfig("BaseBlock", 1));
        configs.put("ForestBlock", new SpriteConfig("ForestBlock", 1));
        configs.put("WaterBlock", new SpriteConfig("WaterBlock", 1));
        configs.put("TankDestroyed", new SpriteConfig("TankDestroyed", 1));

        // ENEMIGOS
        configs.put("EnemyTankRegular", new SpriteConfig("EnemyTankRegular", 2));
        configs.put("EnemyTankFast", new SpriteConfig("EnemyTankFast", 2));
        configs.put("EnemyTankPowerful", new SpriteConfig("EnemyTankPowerful", 2));
        configs.put("EnemyTankHeavy", new SpriteConfig("EnemyTankHeavy", 2));

        // JUGADORES
        configs.put("player1", new SpriteConfig("Player1Tank", 2));
        configs.put("player2", new SpriteConfig("Player2Tank", 2));

        // BALA
        configs.put("bullet", new SpriteConfig("Shot", 1));

        // POWERUPS
        configs.put("PowerUp-Helmet", new SpriteConfig("PowerUp-Helmet", 1));
        configs.put("PowerUp-Star", new SpriteConfig("PowerUp-Star", 1));
        configs.put("PowerUp-Grenade", new SpriteConfig("PowerUp-Grenade", 1));
        configs.put("InvulnerableRing.png", new SpriteConfig("InvulnerableRing", 1));
    }

    public Map<Direccion, List<Image>> getAnimacionesPara(ConSprite ente) {
        String clave = ente.getClaveSprite();
        SpriteConfig config = configs.get(clave);
        if (config == null) {
            throw new IllegalArgumentException("No hay configuración de sprite para la clave: " + clave);
        }

        Map<Direccion, List<Image>> animaciones = new EnumMap<>(Direccion.class);
        for (Direccion dir : Direccion.values()) {
            List<Image> frames = new ArrayList<>();
            if (config.frames() > 1) {
                for (int i = 0; i < config.frames(); i++) {
                    frames.add(obtenerSprite(config.nombreBase() + i + ".png"));
                }
            } else {
                frames.add(obtenerSprite(config.nombreBase() + ".png"));
            }
            animaciones.put(dir, frames);
        }
        return animaciones;
    }

    public static Image obtenerSprite(String nombreArchivo) {
        if (cache.containsKey(nombreArchivo)) return cache.get(nombreArchivo);
        String ruta = RUTA_SPRITES + nombreArchivo;
        try (InputStream is = GestorSprites.class.getResourceAsStream(ruta)) {
            if (is == null) {
                System.err.println("No se encontró el sprite en: " + ruta);
                return null;
            }
            Image img = new Image(is);
            cache.put(nombreArchivo, img);
            return img;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
