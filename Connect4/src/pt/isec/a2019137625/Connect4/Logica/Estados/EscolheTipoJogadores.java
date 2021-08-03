package pt.isec.a2019137625.Connect4.Logica.Estados;

import pt.isec.a2019137625.Connect4.Logica.dados.Enum.Situacao;
import pt.isec.a2019137625.Connect4.Logica.dados.Jogo;

import java.io.Serial;

public class EscolheTipoJogadores extends EstadoAdapter{
    @Serial
    private static final long serialVersionUID = 1L;

    protected EscolheTipoJogadores(Jogo jogo) {
        super(jogo);
        // jogo.recorda("EsperaTipoJogadores");
    }

    @Override
    public IEstado EscolheTipoJog(int tipo) {
        jogo.recorda("EscolheTipoJogadores: Tipo de jogo " + tipo +" foi escolhido.");
        jogo.setJogadores(tipo);
        return new DefineNomes(jogo);
    }

    @Override
    public IEstado RecomecaJogo() {
        return new Inicio(jogo);
    }

    @Override
    public Situacao getSituacao() {
        return Situacao.EscolheTipoJogadores;
    }
}
