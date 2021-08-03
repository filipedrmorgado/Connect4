// Filipe Morgado - 2019137625 - PA - Meta 1 & 2
package pt.isec.a2019137625.Connect4;

import pt.isec.a2019137625.Connect4.Logica.GestaoJogoDados;
import pt.isec.a2019137625.Connect4.UI.texto.Connect4UI;

public class Main {
    public static void main(String[] args) {
        GestaoJogoDados maquinaEstados = new GestaoJogoDados();
        Connect4UI connect4UI = new Connect4UI(maquinaEstados);
        connect4UI.comeca();
    }
}
