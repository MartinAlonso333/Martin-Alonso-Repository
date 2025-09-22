package main.java.juego.gestores;

import juego.entidades.Ente;
import juego.entidades.bloques.Bloque;
import juego.entidades.powerups.PowerUp;
import juego.entidades.tanques.Bala;
import juego.entidades.tanques.Tanque;
import main.java.juego.entidades.Ente;
import main.java.juego.entidades.tanques.Tanque;

public interface ColisionVisitor {
    void visit(Tanque tanque, Ente otro);
    void visit(Bala bala, Ente otro);
    void visit(Bloque bloque, Ente otro);
    void visit(PowerUp powerUp, Ente otro);

    void visit(main.java.juego.entidades.tanques.Tanque tanque, main.java.juego.entidades.Ente otro);
}
