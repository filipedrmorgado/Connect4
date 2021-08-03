package pt.isec.a2019137625.Connect4.Logica.Estados;

import pt.isec.a2019137625.Connect4.Logica.dados.Enum.Situacao;
import pt.isec.a2019137625.Connect4.Logica.dados.Jogo;

import java.io.Serial;

public class FimJogo extends EstadoAdapter{
    @Serial
    private static final long serialVersionUID = 1L;

    protected FimJogo(Jogo jogo) {
        super(jogo);
        //jogo.recorda("FimJogo");
        jogo.recorda("FimJogo: Jogo terminou.");
    }

    @Override
    public IEstado RecomecaJogo(){
        jogo.iniciaJogo();
        return new Inicio(jogo);
    }

    @Override
    public Situacao getSituacao() {
        return Situacao.FimJogo;
    }
}
