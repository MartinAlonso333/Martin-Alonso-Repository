package juego.niveles;

import juego.Juego;
import juego.entidades.Ente;
import juego.entidades.TipoEnte;
import juego.entidades.tanques.TanqueJugador;
import juego.utilidades.Coordenada;
import juego.utilidades.Direccion;
import org.w3c.dom.*;

import javax.xml.parsers.*;
import java.io.File;

public class ParserXML {

    public static void cargarNivel(Juego juego, String pathXml) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(new File(pathXml));
            doc.getDocumentElement().normalize();

            // --- Jugadores ---
            NodeList players = doc.getElementsByTagName("player");
            for (int i = 0; i < players.getLength(); i++) {
                Element p = (Element) players.item(i);
                Coordenada pos = new Coordenada(
                        Integer.parseInt(p.getAttribute("x")),
                        Integer.parseInt(p.getAttribute("y"))
                );
                Direccion dir = p.hasAttribute("direccion") ?
                        Direccion.valueOf(p.getAttribute("direccion")) :
                        Direccion.ARRIBA;

                Ente jugador = RegistroEntidades.crearEntidad(TipoEnte.JUGADOR, pos, dir);
                juego.agregarJugador((TanqueJugador) jugador);
            }

            // --- Enemigos ---
            NodeList enemies = doc.getElementsByTagName("enemy");
            for (int i = 0; i < enemies.getLength(); i++) {
                Element e = (Element) enemies.item(i);
                Coordenada pos = new Coordenada(
                        Integer.parseInt(e.getAttribute("x")),
                        Integer.parseInt(e.getAttribute("y"))
                );
                Direccion dir = e.hasAttribute("direccion") ?
                        Direccion.valueOf(e.getAttribute("direccion")) :
                        Direccion.ARRIBA;

                String typeStr = e.getAttribute("type");
                TipoEnte tipo = RegistroEntidades.tipoDesdeString(typeStr);
                Ente enemigo = RegistroEntidades.crearEntidad(tipo, pos, dir);
                juego.agregarEnte(enemigo);
            }

            // --- Bloques y objetos estáticos ---
            NodeList staticObjects = doc.getElementsByTagName("staticObject");
            for (int i = 0; i < staticObjects.getLength(); i++) {
                Element b = (Element) staticObjects.item(i);
                Coordenada pos = new Coordenada(
                        Integer.parseInt(b.getAttribute("x")),
                        Integer.parseInt(b.getAttribute("y"))
                );

                String typeStr = b.getAttribute("type");
                TipoEnte tipo = RegistroEntidades.tipoDesdeString(typeStr);
                Ente bloque = RegistroEntidades.crearEntidad(tipo, pos);
                juego.agregarEnte(bloque);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
