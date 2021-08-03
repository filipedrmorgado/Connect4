package pt.isec.a2019137625.Connect4.Logica.dados.Player;

import javafx.scene.paint.Color;
import pt.isec.a2019137625.Connect4.Logica.dados.Enum.Disco;

import java.io.Serial;
import java.io.Serializable;

public abstract class Jogador implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String nome;
    private final Disco tipoDisco;
    private String cor;
    private int numJogadas;

    public Jogador(String nome, Disco tipoDisco){
        this.nome = nome;
        this.tipoDisco = tipoDisco;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getNome() {
        return nome;
    }
    public int getNumJogadas() { return numJogadas; }
    public void incNumJogadas(){++numJogadas;}
    public int getNumCreditos() {return 0;}
    public int getNumPecasEspeciais() {
        return 0;
    }
    public void setColor(String colorJog){
        this.cor = colorJog;
    }
    public String getCorJog(){
        return cor;
    }

    // PecasEspeciais + Créditos
    public void ganhaPecaEspecial() {}
    public void perdePecaEspecial() {}
    public void decrementaCredito(int valorDecrementar) {}
    public void setNumCreditos(int creditos) {}
    public void setPecasEspJogadas(int pecasEsp){}
    public void resetNumJogadas() {
        numJogadas = 0;
    }
    public void setNumJogadas(int nJog) {
        this.numJogadas = nJog;
    }

    @Override
    public String toString() {
        return "Nome: " + nome +
                "\t Tipo de disco: [" + tipoDisco + "]" + "Número de jogadas: [" + numJogadas + "]\t";
    }

}
