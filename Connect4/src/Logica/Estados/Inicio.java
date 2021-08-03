package pt.isec.a2019137625.Connect4.Logica.Estados;

import pt.isec.a2019137625.Connect4.Logica.dados.Enum.Situacao;
import pt.isec.a2019137625.Connect4.Logica.dados.Jogo;

import java.io.Serial;

public class Inicio extends EstadoAdapter{
    @Serial
    private static final long serialVersionUID = 1L;

    public Inicio(Jogo jogo) {
        super(jogo);
        // jogo.recorda("Inicio");
    }

    @Override
    public IEstado ComecaJogo() {
        jogo.iniciaJogo();
        jogo.recorda("Inicio: Jogo vai ser iniciado.");
        return new EscolheTipoJogadores(jogo);
    }

    @Override
    public IEstado VerReplayJogo(int sizeOfStack) {
        jogo.recorda("Inicio: Vai ser exibido o replay do jogo.");
        return new ReplayJogo(jogo);
    }

    //Situacao
    @Override
    public Situacao getSituacao() {
        return Situacao.Inicio;
    }

}
