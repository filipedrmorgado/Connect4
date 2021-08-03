package pt.isec.a2019137625.Connect4.Logica.Estados;

import pt.isec.a2019137625.Connect4.Logica.dados.Enum.Situacao;
import pt.isec.a2019137625.Connect4.Logica.dados.Jogo;

import java.io.Serial;

public class EsperaJogada extends EstadoAdapter{
    @Serial
    private static final long serialVersionUID = 1L;

    protected EsperaJogada(Jogo jogo) {
        super(jogo);
        //jogo.recorda("EsperaJogada");
    }

    @Override
    public IEstado EfetuaJogada(int coluna) {
        if(jogo.getQuemJogaType().equalsIgnoreCase("Humano")){
            //Coluna cheia
            if(!jogo.adicionaPeca(coluna - 1)){
                jogo.recorda("EsperaJogada: Jogador " + jogo.getNomeQuemJoga() + " inseriu incorretamente a peça.");
              return this;
            }
            jogo.recorda("EsperaJogada: Jogador " + jogo.getNomeQuemJoga() +" jogou na coluna [" + coluna +"].");
        }else{ // Codigo para o virtual jogar
            int valorAleatorio;
            do{
                valorAleatorio = (int)Math.floor(Math.random()*(7)+1);
            }while(!jogo.adicionaPeca(valorAleatorio - 1));
            jogo.recorda("EsperaJogada: Jogador " + jogo.getNomeQuemJoga() +" jogou na coluna [" + valorAleatorio +"].");
        }
        if(jogo.verificaFim()){
            jogo.recorda("EsperaJogada: Jogo vai chegar ao fim.");
            return new FimJogo(jogo);
        }
        //Passa vez
        jogo.passaVez();
        // Verificar se depois de passar a vez, for a jogada que o próximo jogador tem a chance de decidir se quer jogar
        if( ((jogo.getNumeroJogadas() + 1) % 5) == 0 && jogo.getNumeroJogadas() != 0 && jogo.getQuemJogaType().equalsIgnoreCase("Humano")) {
            jogo.recorda("EsperaJogada: Jogador " + jogo.getNomeQuemJoga() + " vai decidir se quer jogar o miniJogo.");
            return new DecideJogaMiniJogo(jogo);
        }
        return new EsperaJogada(jogo);
    }

    @Override
    public IEstado JogaPecaEspecial(int coluna) {
        if(jogo.getNumPecasEspJogadorAtual() == 0){
            jogo.recorda("JogaPecaEspecial: Jogador não tem peças especiais.");
            return this;
        }
        if(coluna == 0){
            jogo.recorda("JogaPecaEspecial: Jogador decidiu não jogar peça especial.");
            return this;
        }
        // Se não for uma coluna valida
        if(!jogo.jogaPecaEspecial(coluna - 1)){
            jogo.recorda("JogaPecaEspecial: Jogador " + jogo.getNomeQuemJoga() + " inseriu a peça especial numa coluna cheia.");
            return this;
        }
        jogo.recorda("JogaPecaEspecial: Jogador " + jogo.getNomeQuemJoga() + " jogou a peça especial na coluna [" + coluna + "]");
        // Jogou com sucesso a peça, vai perde-la
        jogo.perdePecaEspecial();
        // Vai contar como uma jogada
        jogo.incrementaNumJogadas();
        // Passa vez
        jogo.passaVez();
        // Verificar se depois de passar a vez, for a jogada que o próximo jogador tem a chance de decidir se quer jogar
        if( ((jogo.getNumeroJogadas() + 1) % 5) == 0 && jogo.getNumeroJogadas() != 0 && jogo.getQuemJogaType().equalsIgnoreCase("Humano")) {
            jogo.recorda("EsperaJogada: Jogador " + jogo.getNomeQuemJoga() + " vai decidir se quer jogar o miniJogo.");
            return new DecideJogaMiniJogo(jogo);
        }
        return new EsperaJogada(jogo);
    }

    @Override
    public IEstado RecomecaJogo() {
        return new Inicio(jogo);
    }

    @Override
    public Situacao getSituacao() {
        return Situacao.EsperaJogada;
    }

}
