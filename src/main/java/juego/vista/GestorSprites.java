package juego.vista;

import javafx.scene.image.Image;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class GestorSprites {

    private static final String RUTA_SPRITES = "/sprites/";
    private static final Map<String, Image> cache = new HashMap<>();

    /**
     * Devuelve un sprite a partir del nombre real del archivo.
     * Si ya está cargado, lo devuelve del cache.
     */
    public static Image obtenerSprite(String archivo) {
        if (cache.containsKey(archivo)) {
            return cache.get(archivo);
        }

        try (InputStream is = GestorSprites.class.getResourceAsStream(RUTA_SPRITES + archivo)) {
            if (is == null) {
                System.err.println("No se encontró el sprite: " + archivo);
                return null;
            }
            Image img = new Image(is);
            cache.put(archivo, img);
            return img;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}