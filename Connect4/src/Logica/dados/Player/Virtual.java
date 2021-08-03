package pt.isec.a2019137625.Connect4.Logica.dados.Player;

import pt.isec.a2019137625.Connect4.Logica.dados.Enum.Disco;

import java.io.Serial;

public class Virtual extends Jogador {
    @Serial
    private static final long serialVersionUID = 1L;
    public Virtual(String nome, Disco tipoDisco) {
        super(nome,tipoDisco);
    }

    @Override
    public String toString() {
        return " Virtual{" + super.toString() +" }";
    }
}
