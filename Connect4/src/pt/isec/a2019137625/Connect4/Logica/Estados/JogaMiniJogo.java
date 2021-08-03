package pt.isec.a2019137625.Connect4.Logica.Estados;

import pt.isec.a2019137625.Connect4.Logica.dados.Enum.Situacao;
import pt.isec.a2019137625.Connect4.Logica.dados.Jogo;

import java.io.Serial;

public class JogaMiniJogo extends EstadoAdapter{
    @Serial
    private static final long serialVersionUID = 1L;

    protected JogaMiniJogo(Jogo jogo) {
        super(jogo);
        //jogo.recorda("JogaMiniJogo");
    }

    @Override
    public IEstado RespostaMiniJogo(String resposta){
        jogo.enviaResposta(resposta);
        int resMiniJogo = jogo.MiniJogoChegouAoFim();
        if(resMiniJogo == 0 || resMiniJogo == 1 && jogo.getNomeMiniJogo().equalsIgnoreCase("Jogo das Palavras")){
            //altera o jogo para o outro!
            jogo.trocaJogo();
            // Se perder, tem que incrementar numero de jogadas,pois vai passar a vez e o número de jogadas não incrementa
            jogo.incrementaNumJogadas();
            jogo.passaVez();
            // Verificar se depois de passar a vez, for a jogada que o próximo jogador tem a chance de decidir se quer jogar
            if( ((jogo.getNumeroJogadas() + 1) % 5) == 0 && jogo.getNumeroJogadas() != 0 && jogo.getQuemJogaType().equalsIgnoreCase("Humano")) {
                jogo.recorda("JogaMiniJogo: Perdeu o jogo. Jogador " + jogo.getNomeQuemJoga() + " vai decidir se quer jogar o miniJogo.");
                return new DecideJogaMiniJogo(jogo);
            }else{
                jogo.recorda("JogaMiniJogo: Perdeu o jogo. Passou a vez para o jogador " + jogo.getNomeQuemJoga());
                return new EsperaJogada(jogo);
            }
        } else if(resMiniJogo == 2){
            //altera o jogo para o outro!
            jogo.trocaJogo();
            jogo.recorda("JogaMiniJogo: Ganhou o jogo. Jogador " + jogo.getNomeQuemJoga() + " mantêm a vez de jogar.");
            // Neste caso, não necessita de incrementar o número de jogadas, pois o jogador mantém a vez!
            jogo.ganhaPecaEspecial();
            return new EsperaJogada(jogo);
        }
        jogo.recorda("JogaMiniJogo: Jogador " + jogo.getNomeQuemJoga() + " respondeu ao jogo com: [" + resposta + "]");
        return this;
    }

    @Override
    public IEstado RecomecaJogo() {
        return new Inicio(jogo);
    }

    @Override
    public Situacao getSituacao() {
        return Situacao.JogaMiniJogo;
    }
}
