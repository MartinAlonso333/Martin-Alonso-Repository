package org.modelo.cargaDePartida;

import org.modelo.Juego;
import org.modelo.entidades.Ente;
import org.modelo.entidades.RegistroEntidades;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Objects;

public class ParserXML {

    public static void cargarNivel(String nombreArchivo, Juego juego, RegistroEntidades registro) {
        try {
            File file = new File(Objects.requireNonNull(ParserXML.class.getResource("/levels/GeneratedLevels/" + nombreArchivo + ".xml")).toURI());
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
            Element root = doc.getDocumentElement();

            procesarNodo(root, registro, juego);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al cargar nivel: " + nombreArchivo);
        }
    }

    private static void procesarNodo(Element elem, RegistroEntidades registro, Juego juego) {
        Ente ente = crearEnteDesdeElemento(elem, registro);
        if (ente != null) {
            if (elem.getTagName().equals("player")){
                juego.agregarJugador(ente);
            }
            juego.agregarEnte(ente);
        }

        // Procesar hijos recursivamente
        NodeList hijos = elem.getChildNodes();
        for (int i = 0; i < hijos.getLength(); i++) {
            Node hijo = hijos.item(i);
            if (hijo.getNodeType() == Node.ELEMENT_NODE) {
                procesarNodo((Element) hijo, registro, juego);
            }
        }
    }

    private static Ente crearEnteDesdeElemento(Element elem, RegistroEntidades registro) {
        String tag = elem.getTagName();
        switch (tag) {
            case "player":
                return registro.crearJugador(elem);
            case "enemy":
            case "staticObject":
                String tipo = elem.getAttribute("type");
                return registro.crearEnteConTipo(elem, tipo);
            default:
                return null;
        }
    }


}
