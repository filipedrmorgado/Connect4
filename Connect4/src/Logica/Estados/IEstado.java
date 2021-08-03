package pt.isec.a2019137625.Connect4.Logica.Estados;

import javafx.scene.paint.Color;
import pt.isec.a2019137625.Connect4.Logica.dados.Enum.Situacao;

public interface IEstado {

    IEstado ComecaJogo();   // Inicio do jogo
    IEstado EscolheTipoJog(int tipo); // Escolher tipo jogador
    IEstado EscolheNomes(String nomeJog1, String nomeJog2, String corJog1, String corJog2); // Escolher nomes
    IEstado EfetuaJogada(int coluna); // Efetuar jogada
    IEstado JogaPecaEspecial(int coluna); // Jogar peça especial
    IEstado VerReplayJogo(int sizeOfStackJogo); // Ver replay do jogo
    IEstado vaiJogar(); // Decisão jogar ou não
    IEstado RespostaMiniJogo(String resposta); // Jogar
    IEstado RecomecaJogo();
    // Obtenção do estado atual do jogo !
    Situacao getSituacao();

}
