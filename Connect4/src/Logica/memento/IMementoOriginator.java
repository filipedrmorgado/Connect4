package pt.isec.a2019137625.Connect4.Logica.memento;

import java.io.IOException;

public interface IMementoOriginator {
    Memento getMemento() throws IOException;
    void setMemento(Memento m, int voltarAtras) throws IOException, ClassNotFoundException;
}
