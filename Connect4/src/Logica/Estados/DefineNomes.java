package pt.isec.a2019137625.Connect4.Logica.Estados;

import javafx.scene.paint.Color;
import pt.isec.a2019137625.Connect4.Logica.dados.Enum.Situacao;
import pt.isec.a2019137625.Connect4.Logica.dados.Jogo;

import java.io.Serial;

public class DefineNomes extends EstadoAdapter {
    @Serial
    private static final long serialVersionUID = 1L;

    protected DefineNomes(Jogo jogo) {
        super(jogo);
        //jogo.recorda("EsperaNomes");
    }

    @Override
    public IEstado EscolheNomes(String nomeJog1, String nomeJog2, String corJog1,String corJog2) {
        jogo.escolheNomes(nomeJog1,nomeJog2);
        jogo.setCorPecaJog(corJog1,corJog2);
        jogo.recorda("DefineNomes: Definição dos nomes efetuada. Jogador1: [" + nomeJog1 + "] Jogador2: [" + nomeJog2 +"]");
        return new EsperaJogada(jogo);
    }

    @Override
    public IEstado RecomecaJogo() {
        return new Inicio(jogo);
    }

    @Override
    public Situacao getSituacao() {
        return Situacao.DefineNomes;
    }
}
