package pt.isec.a2019137625.Connect4.Logica.memento;

public interface ICareTaker {
    void gravaMemento();
    String undo(int voltarAtras);
}
