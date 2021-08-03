package pt.isec.a2019137625.Connect4.Logica;

import javafx.scene.paint.Color;
import pt.isec.a2019137625.Connect4.Logica.dados.Coordenada;
import pt.isec.a2019137625.Connect4.Logica.dados.Enum.Disco;
import pt.isec.a2019137625.Connect4.Logica.dados.Enum.QuemJoga;
import pt.isec.a2019137625.Connect4.Logica.dados.Enum.Situacao;
import pt.isec.a2019137625.Connect4.Logica.Estados.IEstado;
import pt.isec.a2019137625.Connect4.Logica.Estados.Inicio;
import pt.isec.a2019137625.Connect4.Logica.dados.Jogo;
import pt.isec.a2019137625.Connect4.Logica.memento.IMementoOriginator;
import pt.isec.a2019137625.Connect4.Logica.memento.Memento;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

public class MaquinaEstados implements IMementoOriginator, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private IEstado estadoAtual;
    private IEstado estadoAnterior;
    private Jogo jogo;

    public MaquinaEstados(){
        jogo = new Jogo();
        estadoAtual = new Inicio(jogo);
    }

    public void ComecaJogo(){
        setEstado(estadoAtual.ComecaJogo());
    }
    public void EscolheTipoJog(int tipo){
        setEstado(estadoAtual.EscolheTipoJog(tipo));
    }
    public void EscolheNomes(String nomeJog1, String nomeJog2, String corJog1, String corJog2){
        setEstado(estadoAtual.EscolheNomes(nomeJog1,nomeJog2,corJog1,corJog2));
    }
    public void EfetuaJogada(int coluna){
        setEstado(estadoAtual.EfetuaJogada(coluna));
    }
    public void JogaPecaEspecial(int coluna){
        setEstado(estadoAtual.JogaPecaEspecial(coluna));
    }
    // Mini Jogo
    public void RespostaMiniJogo(String resposta){ setEstado(estadoAtual.RespostaMiniJogo(resposta));}
    public void vaiJogar(){ setEstado(estadoAtual.vaiJogar()); }
    // Replay do jogo
    public void ReplayJogo(int sizeOfStackJogo){
        setEstado(estadoAtual.VerReplayJogo(sizeOfStackJogo));
    }
    public void RecomecaJogo() {setEstado(estadoAtual.RecomecaJogo());}
    // Métodos de consulta
    public String imprimeJogoAtual(){
        return jogo.imprimeJogo();
    }
    public Situacao getSituacao(){
        return estadoAtual.getSituacao();
    }
    public String getHistorico() {
        return jogo.getHistorico();
    }
    public String getNomeQuemJoga() { return jogo.getNomeQuemJoga(); }
    public String getVencedor() { return jogo.getVencedor(); }
    public void limpaHistorico(){
        jogo.limpaHistorico();
    }
    public String getTipoJogador() { return jogo.getQuemJogaType(); }
    public int getQtPecasEspJog() { return jogo.getNumPecasEspJogadorAtual(); }
    public String getEstadoJogo() { return jogo.getEstadoJogo();}
    public Map<Coordenada, Disco> getBoard() {
        return jogo.getBoard();
    }
    public int getNumeroJogadas() { return jogo.getNumeroJogadas();}

    // Métodos de consulta mini jogo
    public String getPerguntaMiniJogo(){ return jogo.getPerguntaMiniJogo(); }
    public String getAcertouPergunta(){return jogo.getAcertouPergunta();}
    public String getNomeMiniJogo(){ return jogo.getNomeMiniJogo(); }
    public long getTempoAcerto() { return jogo.getTempoAcerto(); }

    // regista novo estado activo
    private void setEstado(IEstado estado) {
        this.estadoAnterior = estadoAtual;
        this.estadoAtual = estado;
    }

    //Voltar atrás
    public int getCreditosJogador(){return jogo.getNumCreditos();}

    //Metodos de apoio a parte grafica
    public Situacao getEstadoAnterior(){
        return estadoAnterior.getSituacao();
    }
    public String getCorJog1() { return jogo.getCorJog1();}
    public String getCorJog2() { return jogo.getCorJog2();}
    public String getCorJogAtual() { return jogo.getCorJogAtual(); }


    // Grava jogo
    public Memento getMementoJogo() throws IOException  {
        return new Memento(jogo);
    }
    public void setMementoJogo(Memento m) throws IOException, ClassNotFoundException {
        jogo = (Jogo) m.getSnapshot();
    }
    //IMementoOriginator
    @Override
    public Memento getMemento() throws IOException {
        return new Memento(new Object[] {jogo,estadoAtual});
    }
    @Override
    public void setMemento(Memento m, int voltarAtras) throws IOException, ClassNotFoundException {
        Object [] set = (Object[]) m.getSnapshot();
        // Obter quem joga antes de atualizar a jogada, para atualizar os creditos APÓS atualizar estado
        QuemJoga quemjoga = jogo.getQuemJoga();
        String nomeQuemJoga = jogo.getNomeQuemJoga();
        // Obter creditos de quem joga antes de atualizar jogo
        int creditosJog1 = jogo.getCreditosJogador(1);
        int creditosJog2 = jogo.getCreditosJogador(2);
        // Obter peças especiais para manter atualizadas
        int pecasEspJog1 = jogo.getPecasEspJogador(1);
        int pecasEspJog2 = jogo.getPecasEspJogador(2);
        // Obter numero de jogadas do adversário antes do undo, para atualizar pós undo
        int nJogadasAdversario = jogo.getNumeroJogadasAdversario();
        // Dar undo para o jogo X jogadas atrás
        jogo = (Jogo) set[0];
        // Colocar quantidade de creditos igual como estava antes do undo
        jogo.setNumCreditosJogador(1,creditosJog1);
        jogo.setNumCreditosJogador(2,creditosJog2);
        // Voltar a colocar as peças especiais
        jogo.setPecasEspJogador(1,pecasEspJog1);
        jogo.setPecasEspJogador(2,pecasEspJog2);
        // Manter as jogadas do adversario
        jogo.setNumeroJogadas(nJogadasAdversario,quemjoga);
        // Reset numero de jogadas de quem joga
        jogo.resetNumJogadas(quemjoga);
        // Remove créditos ao jogador que os usou
        jogo.decrementaCredito(voltarAtras,quemjoga);
        jogo.recorda("Undo: Jogador " + nomeQuemJoga + " decidiu recuar " + voltarAtras + " vezes.");
        estadoAtual = (IEstado) set[1];
    }

    @Override
    public String toString() {
        return jogo.toString() + "\n";
    }
}
