package pt.isec.a2019137625.Connect4.Logica.dados;

import javafx.scene.paint.Color;
import pt.isec.a2019137625.Connect4.Logica.dados.Enum.Disco;
import pt.isec.a2019137625.Connect4.Logica.dados.Enum.QuemJoga;
import pt.isec.a2019137625.Connect4.Logica.dados.Jogos.Matematica;
import pt.isec.a2019137625.Connect4.Logica.dados.Jogos.MiniJogo;
import pt.isec.a2019137625.Connect4.Logica.dados.Jogos.Palavras;
import pt.isec.a2019137625.Connect4.Logica.dados.Player.Jogador;
import pt.isec.a2019137625.Connect4.Logica.dados.Player.Humano;
import pt.isec.a2019137625.Connect4.Logica.dados.Player.Virtual;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Jogo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Map<Coordenada, Disco> board;
    private static final int nr_linhas = 6;
    private static final int nr_colunas = 7;
    private ArrayList<String> historico;
    //Jogadores
    private Jogador jogador1;
    private Jogador jogador2;
    private QuemJoga quemJoga;
    // Mini jogo, vai começar com o jogo das palavras
    private int miniJogoJog1;
    private int miniJogoJog2;
    private MiniJogo miniJogo;

    //Estado do jogo
    public Jogo(){
        iniciaJogo();
    }

    public void iniciaJogo(){
        board = new HashMap<>();
        inicializaBoard();
        historico = new ArrayList<>();
        jogador1 = null;
        jogador2 = null;
        // Escolha aleatoria do 1º a jogar + 1º miniJogo para jog1
        if(Math.random() < 0.5){
            quemJoga = QuemJoga.Jogador_1;
            miniJogoJog1 = 1;
        }
        else{
            miniJogoJog1 = 2;
            quemJoga = QuemJoga.Jogador_2;
        }
        // Escolha aleatoria para 1º miniJogo jog1
        if(Math.random() < 0.5)
            miniJogoJog2 = 1;
        else
            miniJogoJog2 = 2;
    }

    // Funções relacionadas com o miniJogo
    public void iniciaMiniJogo(){
        if(quemJoga == QuemJoga.Jogador_1){
            if(miniJogoJog1 == 2)
                miniJogo = new Matematica();
            else
                miniJogo = new Palavras();
        }else{
            if(miniJogoJog2 == 2)
                miniJogo = new Matematica();
            else
                miniJogo = new Palavras();
        }
    }

    public long getTempoAcerto(){
       return miniJogo.getTempoAcerto();
    }

    public Map<Coordenada, Disco> getBoard() {
        return board;
    }

    public void trocaJogo(){
        if(quemJoga == QuemJoga.Jogador_1) {
            if (miniJogoJog1 == 1)
                miniJogoJog1 = 2;
            else
                miniJogoJog1 = 1;
        }else{
            if (miniJogoJog2 == 1)
                miniJogoJog2 = 2;
            else
                miniJogoJog2 = 1;
        }
    }

    public String getNomeMiniJogo() {

        if(quemJoga == QuemJoga.Jogador_1){
            if(miniJogoJog1 == 1)
                return "Jogo das Palavras";
            else
                return "Jogo da Matemática";
        }else{
            if(miniJogoJog2 == 1)
                return "Jogo das Palavras";
            else
                return "Jogo da Matemática";
        }
    }

    public String getPerguntaMiniJogo(){
        return miniJogo.getPergunta();
    }

    public void enviaResposta(String resposta){
        miniJogo.confirmaResposta(resposta);
    }

    public String getAcertouPergunta(){
        if(miniJogo.getInfoAcertou()){
            return "Acertou na pergunta!";
        }
        return "Errou na pergunta!";
    }

    public int MiniJogoChegouAoFim() { return miniJogo.chegouAoFim(); }

    public String getEstadoJogo(){
        return miniJogo.getEstadoJogo();
    }

    // Lógica jogadores + jogo
    // Reset jogadas após undos.
    public void resetNumJogadas(QuemJoga resetQuemJoga){
        if(resetQuemJoga == QuemJoga.Jogador_1)
            jogador1.resetNumJogadas();
        else
            jogador2.resetNumJogadas();
    }
    public int getNumeroJogadas(){
        if(quemJoga == QuemJoga.Jogador_1)
            return jogador1.getNumJogadas();
        else
            return jogador2.getNumJogadas();
    }

    // Funções para manter as jogadas do adversário no undo.
    public int getNumeroJogadasAdversario(){
        if(quemJoga == QuemJoga.Jogador_1)
            return jogador2.getNumJogadas();
        else
            return jogador1.getNumJogadas();
    }


    // Manter as jogadas atualizadas do adversario, sabendo quem fez undo, atualiza o oposto.
    public void setNumeroJogadas(int nJog,QuemJoga setQuemJoga){
        if(setQuemJoga == QuemJoga.Jogador_1)
            jogador2.setNumJogadas(nJog);
        else
            jogador1.setNumJogadas(nJog);
    }

    public int getNumPecasEspJogadorAtual(){
        if(quemJoga == QuemJoga.Jogador_1)
            return jogador1.getNumPecasEspeciais();
        else
            return jogador2.getNumPecasEspeciais();
    }

    public void passaVez(){
        if(quemJoga == QuemJoga.Jogador_1)
            quemJoga = QuemJoga.Jogador_2;
        else
            quemJoga = QuemJoga.Jogador_1;
    }

    public Disco getTipoDisco(){
        if(quemJoga == QuemJoga.Jogador_1)
            return Disco.X;
        return Disco.Y;
    }

    public void incrementaNumJogadas(){
        if(quemJoga == QuemJoga.Jogador_1)
            jogador1.incNumJogadas();
        else
            jogador2.incNumJogadas();
    }

    public void setJogadores(int tipoJog) {
        switch(tipoJog){
            case 1 -> {
                jogador1 = new Humano("jog1",Disco.X);
                jogador2 = new Humano("jog2",Disco.Y);
            }
            case 2 -> {
                jogador1 = new Humano("jog1",Disco.X);
                jogador2 = new Virtual("jog2",Disco.Y);
            }
            default -> {
                jogador1 = new Virtual("jog1",Disco.X);
                jogador2 = new Virtual("jog2",Disco.Y);
            }
        }
        if(Math.random() >= 0.5)
            quemJoga = QuemJoga.Jogador_1;
        else
            quemJoga = QuemJoga.Jogador_2;
    }

    public void escolheNomes(String nomeJog1, String nomeJog2){
        jogador1.setNome(nomeJog1);
        jogador2.setNome(nomeJog2);
    }

    public void setCorPecaJog(String colorJog1,String colorJog2){
        jogador1.setColor(colorJog1);
        jogador2.setColor(colorJog2);
    }

    public String getCorJog1(){
        return jogador1.getCorJog();
    }
    public String getCorJog2(){
        return jogador2.getCorJog();
    }

    public String getCorJogAtual(){
        if(quemJoga == QuemJoga.Jogador_1)
            return jogador1.getCorJog();
        return jogador2.getCorJog();
    }

    public String getQuemJogaType(){
        if(quemJoga == QuemJoga.Jogador_1)
            return jogador1.getClass().getSimpleName();
        return jogador2.getClass().getSimpleName();
    }

    //Getters
    public String getNomeQuemJoga() {
        if(quemJoga == QuemJoga.Jogador_1)
            return jogador1.getNome();
        return jogador2.getNome();
    }

    public void ganhaPecaEspecial(){
        if(quemJoga == QuemJoga.Jogador_1)
            jogador1.ganhaPecaEspecial();
        else
            jogador2.ganhaPecaEspecial();
    }

    public void perdePecaEspecial(){
        if(quemJoga == QuemJoga.Jogador_1)
            jogador1.perdePecaEspecial();
        else
            jogador2.perdePecaEspecial();
    }

    public String getVencedor(){
        if(quemJoga == QuemJoga.Nenhum)
            return "O jogo ficou empatado! Não existe vencedor !";
        return "O jogador " + getNomeQuemJoga() + " venceu o jogo!";
    }

    // Obter numero de creditos de quem esta a jogar
    public int getNumCreditos() {
        if(quemJoga == QuemJoga.Jogador_1)
            return jogador1.getNumCreditos();
        return jogador2.getNumCreditos();
    }

    public void decrementaCredito(int valorDecrementar, QuemJoga jogAtual){
        if(jogAtual == QuemJoga.Jogador_1)
            jogador1.decrementaCredito(valorDecrementar);
        else
            jogador2.decrementaCredito(valorDecrementar);
    }

    public QuemJoga getQuemJoga(){
        return quemJoga;
    }

    // Para manter créditos jogador
    public int getCreditosJogador(int qualJog){
        if(qualJog == 1)
            return jogador1.getNumCreditos();
        else
            return jogador2.getNumCreditos();
    }

    public void setNumCreditosJogador(int qualJog,int creditos){
        if(qualJog == 1)
            jogador1.setNumCreditos(creditos);
        else
            jogador2.setNumCreditos(creditos);
    }

    // Para manter peças especiais
    public int getPecasEspJogador(int qualJog){
        if(qualJog == 1)
            return jogador1.getNumPecasEspeciais();
        else
            return jogador2.getNumPecasEspeciais();
    }

    public void setPecasEspJogador(int qualJog,int pecasEsp){
        if(qualJog == 1)
            jogador1.setPecasEspJogadas(pecasEsp);
        else
            jogador2.setPecasEspJogadas(pecasEsp);
    }

    // Lógica de jogo!
    // Inicializar a board com nenhum disco
    public void inicializaBoard() {
        for (int i = 0; i < nr_linhas; i++) {
            for (int j = 0; j < nr_colunas; j++) {
                board.put(new Coordenada(i,j),Disco.none);
            }
        }
    }

    public boolean adicionaPeca(int coluna){
        int linha = 0;
        while (board.get(new Coordenada(linha,coluna)) != Disco.none && linha < nr_linhas)
            ++linha;
        // Significa que a coluna está cheia !
        if(linha == nr_linhas)
            return false;
        //Incrementa numero de jogadas feitas
        incrementaNumJogadas();
        board.put(new Coordenada(linha,coluna), getTipoDisco());
        return true;
    }

    public boolean jogaPecaEspecial(int coluna){
        int linha = 0;
        while (board.get(new Coordenada(linha,coluna)) != Disco.none && linha < nr_linhas)
            ++linha;
        if(linha == nr_linhas)
            return false;
        int i;
        for (i = 0; i < nr_linhas; i++)
            board.put(new Coordenada(i,coluna), Disco.none);
        return true;
    }

    // Verificar se jogo acabou ou não
    public boolean verificaFim(){
        for (int linha = 0; linha < nr_linhas; linha++) {
            for (int col = 0; col < nr_colunas; col++) {
                if(horizontalDireita(linha,col) | horizontalEsquerda(linha,col) | verticalSup(linha,col)| verticalInf(linha,col)
                   | diagSupDir(linha,col) | diagInfDir(linha,col) | diagSupEsq(linha,col) | diagInfEsq(linha,col)) {
                    return true;
                }
            }
        }
        // Como passou o ciclo anterior, logo, não terminou o jogo, verifica aqui se encheu o tabuleiro.
        // Se tiver cheio, vai alterar quem joga para nenhum para na verificação do vencedor se saiba que empatou.
        if(verificaCheio()){
            quemJoga = QuemJoga.Nenhum;
            return true;
        }
        return false;
    }
    // Verifica se ainda tem espaço na tabela,se não tiver retorna true, significando que estão todas preenchidas
    public boolean verificaCheio(){
        for (int i = 0; i < nr_linhas; i++) {
            for (int j = 0; j < nr_colunas; j++) {
                if(board.get(new Coordenada(i,j)) == Disco.none)
                    return false;
            }
        }
        return true;
    }
    // Verifica horizontal Direita
    private boolean horizontalDireita(int linha, int col) {
        Disco corVerificar = board.get(new Coordenada(linha,col));
        if(corVerificar == Disco.none)
            return false;
        if(col > 4) //Verificar lado direito
            return false;
        for (int i = 0; i < 4; i++) {
            if (board.get(new Coordenada(linha, col + i)) != corVerificar)
                return false;
        }
        return true;
    }
    // Verifica horizontal Esquerda
    private boolean horizontalEsquerda(int linha, int col) {
        Disco corVerificar = board.get(new Coordenada(linha,col));
        if(corVerificar == Disco.none)
            return false;

        if(col < 4) //Verificar lado direito
            return false;
        for (int i = 0; i < 4; i++)
            if(board.get(new Coordenada(linha,col - i)) != corVerificar)
                return false;
        return true;
    }
    // Verifica vertical Superior
    private boolean verticalSup(int linha, int col) {
        Disco corVerificar = board.get(new Coordenada(linha,col));
        if(corVerificar == Disco.none)
            return false;
        if(linha < 3) //Verificar lado direito
            return false;
        for (int i = 0; i < 4; i++)
            if(board.get(new Coordenada(linha - i,col)) != corVerificar)
                return false;
        return true;
    }
    // Verificar vertical Inferior
    private boolean verticalInf(int linha, int col) {
        Disco corVerificar = board.get(new Coordenada(linha,col));
        if(corVerificar == Disco.none)
            return false;
        if(linha > 2) //Verificar lado direito
            return false;
        for (int i = 0; i < 4; i++) {
            if (board.get(new Coordenada(linha + i, col)) != corVerificar)
                return false;
        }
        return true;
    }
    // Verificar diagonais superior direitas
    private boolean diagSupDir(int linha, int col){
        Disco corVerificar = board.get(new Coordenada(linha,col));
        if(corVerificar == Disco.none)
            return false;
        if(linha < 2 && col > 3) //Verificar se tem espaço para estar na diagonal sup dir
            return false;
        for (int i = 0; i < 4; i++) {
            if(board.get(new Coordenada(linha - i,col + i)) != corVerificar)
                return false;
        }
        return true;
    }
    // Verificar diagonais inferior direita
    private boolean diagInfDir(int linha, int col){
        Disco corVerificar = board.get(new Coordenada(linha,col));
        if(corVerificar == Disco.none)
            return false;
        if(linha > 2 && col > 3) //Verificar se tem espaço para estar na diagonal inf dir
            return false;
        for (int i = 0; i < 4; i++) {
            if(board.get(new Coordenada(linha - i,col - i)) != corVerificar)
                return false;
        }
        return true;
    }
    //Verificar diagonais superior esquerda
    private boolean diagSupEsq(int linha, int col){
        Disco corVerificar = board.get(new Coordenada(linha,col));
        if(corVerificar == Disco.none)
            return false;
        if(linha < 2 && col < 3) //Verificar se tem espaço para estar na diagonal sup dir
            return false;
        for (int i = 0; i < 4; i++) {
            if(board.get(new Coordenada(linha - i,col - i)) != corVerificar)
                return false;
        }
        return true;
    }
    // Verificar diagonais inferior esquerda
    private boolean diagInfEsq(int linha, int col){
        Disco corVerificar = board.get(new Coordenada(linha,col));
        if(corVerificar == Disco.none)
            return false;
        if(linha > 2 && col < 3) //Verificar se tem espaço para estar na diagonal inf dir
            return false;
        for (int i = 0; i < 4; i++) {
            if(board.get(new Coordenada(linha + i,col - i)) != corVerificar)
                return false;
        }
        return true;
    }
    //Imprimir jogo
    public String imprimeJogo(){
        StringBuilder printBoard = new StringBuilder("\n");
        for (int linha = nr_linhas - 1; linha >= 0; linha--) {
            printBoard.append("|");
            for (int coluna = 0; coluna < nr_colunas; coluna++) {
                final Coordenada location = new Coordenada(linha, coluna);
                if (board.containsKey(location))
                    printBoard.append(printDisc(board.get(location)));
                else
                    printBoard.append(".");
            }
            printBoard.append("|\n");
        }
        printBoard.append(" ");
        /*for (int i = 0; i < nr_colunas; i++)
            printBoard.append("---");*/
        printBoard.append("---".repeat(nr_colunas));

        printBoard.append("\n");
        return printBoard.toString();
    }

    private String printDisc(final Disco disc) {
        if(disc == Disco.X)
            return " X ";
        else if(disc == Disco.Y)
            return " O ";
        else
            return " - ";
    }

    // Histórico
    public void limpaHistorico(){
        historico.clear();
    }

    public String getHistorico(){
        StringBuilder strHist = new StringBuilder("\nHistorico:\n");
        for (int i = 0; i < historico.size() && i < 6; i++)
            // Certificar que vai buscar os últimos (size() - 1) 6 históricos sempre ( - i )
            strHist.append(i + 1).append("-> ").append(historico.get(historico.size() - 1 - i)).append("\n");
        return strHist.toString();
    }

    public void recorda(String evolucao){
        historico.add(evolucao);
    }

    @Override
    public String toString() {
        return "\nJogador1->" + jogador1 +
                "\njogador2->" + jogador2 +
                "\nVez de jogar: [" + quemJoga +
                "]";
    }
}
