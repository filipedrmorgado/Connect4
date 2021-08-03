package pt.isec.a2019137625.Connect4.Logica.Estados;

import javafx.scene.paint.Color;
import pt.isec.a2019137625.Connect4.Logica.dados.Jogo;

import java.io.Serial;
import java.io.Serializable;

public abstract class EstadoAdapter implements IEstado, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    protected Jogo jogo;

    protected EstadoAdapter(Jogo jogo){
        this.jogo = jogo;
    }

    @Override
    public IEstado ComecaJogo() {
        return this;
    }

    @Override
    public IEstado VerReplayJogo(int sizeOfStackJogo) {
        return this;
    }

    @Override
    public IEstado EscolheTipoJog(int tipo){
        return this;
    }

    @Override
    public IEstado EscolheNomes(String nomeJog1, String nomeJog2, String corJog1, String corJog2) {
        return this;
    }

    @Override
    public IEstado EfetuaJogada(int coluna) {
        return this;
    }

    @Override
    public IEstado JogaPecaEspecial(int coluna) {
        return this;
    }

    //Mini jogo
    @Override
    public IEstado vaiJogar() { return this; }

    @Override
    public IEstado RespostaMiniJogo(String resposta) { return this; }

    @Override
    public IEstado RecomecaJogo(){ return this; }

}
