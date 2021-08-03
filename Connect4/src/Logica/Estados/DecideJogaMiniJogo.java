package pt.isec.a2019137625.Connect4.Logica.Estados;

import pt.isec.a2019137625.Connect4.Logica.dados.Enum.Situacao;
import pt.isec.a2019137625.Connect4.Logica.dados.Jogo;

import java.io.Serial;

public class DecideJogaMiniJogo extends EstadoAdapter {
    @Serial
    private static final long serialVersionUID = 1L;

    protected DecideJogaMiniJogo(Jogo jogo) {
        super(jogo);
        //jogo.recorda("EsperaDecisaoMiniJogo");
    }

    @Override
    public IEstado vaiJogar(){
        jogo.iniciaMiniJogo();
        jogo.recorda("DecideJogaMiniJogo: Jogador " + jogo.getNomeQuemJoga() + " decidiu jogar miniJogo");
        return new JogaMiniJogo(jogo);
    }

    @Override
    public IEstado RecomecaJogo() {
        return new Inicio(jogo);
    }

    @Override
    public IEstado EfetuaJogada(int coluna) {
        jogo.recorda("DecideJogaMiniJogo: Jogador " + jogo.getNomeQuemJoga() + " decidiu efetuar a jogada");
        return new EsperaJogada(jogo);
    }

    @Override
    public Situacao getSituacao() {
        return Situacao.DecideJogaMiniJogo;
    }
}
