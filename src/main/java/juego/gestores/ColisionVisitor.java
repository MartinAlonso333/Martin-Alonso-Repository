package juego.gestores;

import juego.entidades.Ente;
import juego.entidades.bloques.Bloque;
import juego.entidades.powerups.PowerUp;
import juego.entidades.tanques.Bala;
import juego.entidades.tanques.Tanque;

public interface ColisionVisitor {
    void visit(Tanque tanque, Ente otro);
    void visit(Bala bala, Ente otro);
    void visit(Bloque bloque, Ente otro);
    void visit(PowerUp powerUp, Ente otro);
}
