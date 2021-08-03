package pt.isec.a2019137625.Connect4.Logica.dados.Player;

import pt.isec.a2019137625.Connect4.Logica.dados.Enum.Disco;

import java.io.Serial;

public class Humano extends Jogador {
    @Serial
    private static final long serialVersionUID = 1L;
    private int numPecasEspeciais = 0;
    private int creditos = 5;
    // Não vai gravar o valor desta variável na serialização devido a keyword transient!

    public Humano(String nome, Disco tipoDisco) {
        super(nome,tipoDisco);
    }

    @Override
    public void ganhaPecaEspecial() {
        ++numPecasEspeciais;
    }

    @Override
    public void perdePecaEspecial() {
        --numPecasEspeciais;
    }

    @Override
    public void decrementaCredito(int valorDecrementar) { this.creditos -= valorDecrementar; }

    @Override
    public int getNumCreditos() {return creditos;}

    @Override
    public void setNumCreditos(int creditos) {
        this.creditos = creditos;
    }

    @Override
    public int getNumPecasEspeciais() {
        return numPecasEspeciais;
    }

    @Override
    public void setPecasEspJogadas(int pecasEsp){
        this.numPecasEspeciais = pecasEsp;
    }


    @Override
    public String toString() {
        return " Humano{ " + super.toString() +
                "     Peças Especiais:[" + numPecasEspeciais +
                "]     Creditos:[" + creditos + "] }";
    }
}
