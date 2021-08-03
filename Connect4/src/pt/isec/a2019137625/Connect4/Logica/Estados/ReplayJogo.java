package pt.isec.a2019137625.Connect4.Logica.Estados;

import pt.isec.a2019137625.Connect4.Logica.dados.Enum.Situacao;
import pt.isec.a2019137625.Connect4.Logica.dados.Jogo;

import java.io.Serial;

public class ReplayJogo extends EstadoAdapter{
    @Serial
    private static final long serialVersionUID = 1L;

    protected ReplayJogo(Jogo jogo) {
        super(jogo);
        //jogo.recorda("ReplayJogo")
    }

    @Override
    public IEstado VerReplayJogo(int sizeOfStackJogo) {
        if(sizeOfStackJogo == 0){
            jogo.recorda("ReplayJogo: Replay do jogo terminou.");
            return new Inicio(jogo);
        }
        return this;
    }

    @Override
    public IEstado RecomecaJogo() {
        return new Inicio(jogo);
    }

    @Override
    public Situacao getSituacao() {
        return Situacao.ReplayJogo;
    }
}
