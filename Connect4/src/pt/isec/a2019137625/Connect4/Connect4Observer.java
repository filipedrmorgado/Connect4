package pt.isec.a2019137625.Connect4;

import pt.isec.a2019137625.Connect4.Logica.GestaoJogoDados;
import pt.isec.a2019137625.Connect4.Logica.dados.Coordenada;
import pt.isec.a2019137625.Connect4.Logica.dados.Enum.Disco;
import pt.isec.a2019137625.Connect4.Logica.dados.Enum.Situacao;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.util.Map;

import static pt.isec.a2019137625.Connect4.Logica.Constantes.*;

public class Connect4Observer {
    private final PropertyChangeSupport propertyChangeSupport;
    private GestaoJogoDados gestorJogoDados;

    public Connect4Observer(GestaoJogoDados gestorJogoDados){
        this.gestorJogoDados = gestorJogoDados;
        propertyChangeSupport = new PropertyChangeSupport(gestorJogoDados);
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
    }
    public void removeListener(String propertyName,PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(propertyName,listener);
    }

    // Maquina de Estados
    public void ComecaJogo(){
        this.gestorJogoDados.ComecaJogo();
        propertyChangeSupport.firePropertyChange( PROPRIEDADE_ESTADO, null, null);
    }
    public void EscolheTipoJog(int tipo){
        this.gestorJogoDados.EscolheTipoJog(tipo);
        if(this.gestorJogoDados.getSituacao() != Situacao.EscolheTipoJogadores)
            propertyChangeSupport.firePropertyChange( PROPRIEDADE_ESTADO, null, null);
    }

    public void EscolheNomes(String nomeJog1, String nomeJog2, String corJog1, String corJog2){
        this.gestorJogoDados.EscolheNomes(nomeJog1,nomeJog2, corJog1, corJog2);
        if(this.gestorJogoDados.getSituacao() != Situacao.DefineNomes)
            propertyChangeSupport.firePropertyChange( PROPRIEDADE_ESTADO, null, null);
    }

    public void EfetuaJogada(int coluna){
        this.gestorJogoDados.EfetuaJogada(coluna);
        if(this.gestorJogoDados.getEstadoAnterior() == Situacao.EsperaJogada && this.gestorJogoDados.getSituacao() == Situacao.EsperaJogada)
            propertyChangeSupport.firePropertyChange( PROPRIEDADE_DADOS, null, null);
        else
            propertyChangeSupport.firePropertyChange( PROPRIEDADE_ESTADO, null, null);
    }

    public void JogaPecaEspecial(int coluna){
        this.gestorJogoDados.JogaPecaEspecial(coluna);
        if(this.gestorJogoDados.getEstadoAnterior() == Situacao.EsperaJogada && this.gestorJogoDados.getSituacao() == Situacao.EsperaJogada)
            propertyChangeSupport.firePropertyChange( PROPRIEDADE_DADOS, null, null);
        else
            propertyChangeSupport.firePropertyChange( PROPRIEDADE_ESTADO, null, null);
    }

    public void RespostaMiniJogo(String resposta){
        this.gestorJogoDados.RespostaMiniJogo(resposta);
        // Caso o estado anterior seja o jogador a jogar o mini jogo, e o atual agora passe a ser outro, para lançar uma "PROPRIEDADE_ESTADO" e não uma "PROPRIEDADE_MINIJOGO"
        if(this.gestorJogoDados.getEstadoAnterior() == Situacao.JogaMiniJogo && this.gestorJogoDados.getSituacao() == Situacao.JogaMiniJogo)
            propertyChangeSupport.firePropertyChange( PROPRIEDADE_MINIJOGO, null, null);
        else
            propertyChangeSupport.firePropertyChange( PROPRIEDADE_ESTADO, null, null);
    }

    public void vaiJogar(){
        this.gestorJogoDados.vaiJogar();
        propertyChangeSupport.firePropertyChange( PROPRIEDADE_ESTADO, null, null);
    }

    public void RecomecaJogo() {
        this.gestorJogoDados.RecomecaJogo();
        propertyChangeSupport.firePropertyChange( PROPRIEDADE_ESTADO, null, null);
    }

    public void ReplayJogo(int sizeOfStackJogo){
        this.gestorJogoDados.ReplayJogo(sizeOfStackJogo);
        // Se o estado anterior não foi o replay(1ºvez) ou o atual seja diferente do replay, quer dizer que está no inicio ou fim do replay, e necessita de atualizar o estado!
        if(this.gestorJogoDados.getEstadoAnterior() != Situacao.ReplayJogo || this.gestorJogoDados.getSituacao() != Situacao.ReplayJogo)
            propertyChangeSupport.firePropertyChange( PROPRIEDADE_ESTADO, null, null);
    }

    // Métodos de consulta
    public Situacao getSituacao(){
        return this.gestorJogoDados.getSituacao();
    }
    public String getNomeQuemJoga() { return this.gestorJogoDados.getNomeQuemJoga(); }
    public String getVencedor() { return this.gestorJogoDados.getVencedor(); }
    public String getEstadoJogo() { return this.gestorJogoDados.getEstadoJogo(); }
    public Map<Coordenada, Disco> getBoard() {
        return this.gestorJogoDados.getBoard();
    }
    public int getNumeroJogadas() { return this.gestorJogoDados.getNumeroJogadas();}

    //GET MINI JOGOS
    public String getNomeMiniJogo(){ return this.gestorJogoDados.getNomeMiniJogo(); }
    public String getPerguntaMiniJogo(){ return this.gestorJogoDados.getPerguntaMiniJogo(); }
    public int getCreditosJogador(){return this.gestorJogoDados.getCreditosJogador(); }
    public String getTipoJogador() { return this.gestorJogoDados.getTipoJogador(); }
    public int getQtPecasEspJog() { return this.gestorJogoDados.getQtPecasEspJog(); }
    public long getTempoAcerto() { return this.gestorJogoDados.getTempoAcerto(); }
    public String getAcertouPergunta() { return this.gestorJogoDados.getAcertouPergunta();}

    // Replay de jogos
    public int getSizeStackJogo(){
        return this.gestorJogoDados.getSizeStackJogo();
    }
    public String carregarReplay(File filename) { return this.gestorJogoDados.carregarReplay(filename); }

    // Métodos extra gráficos
    public String getCorJog1(){ return this.gestorJogoDados.getCorJog1(); }
    public String getCorJog2(){ return this.gestorJogoDados.getCorJog2(); }
    public String getCorJogAtual() { return this.gestorJogoDados.getCorJogAtual(); }

    public String avancaMemJogo() {
        String res = this.gestorJogoDados.avancaMemJogo();
        propertyChangeSupport.firePropertyChange( PROPRIEDADE_REPLAY, null, null);
        return res;
    }

    public String gravaReplay() { return this.gestorJogoDados.gravaReplay(); }

    public String undo(int voltarAtras){
        String result = this.gestorJogoDados.undo(voltarAtras);
        propertyChangeSupport.firePropertyChange(PROPRIEDADE_DADOS,null,null);
        return result;
    }

    // Gravação e Carregamento de jogo
    public String carregarJogo(File filename) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
            gestorJogoDados = (GestaoJogoDados) ois.readObject();
            ois.close();
            propertyChangeSupport.firePropertyChange(PROPRIEDADE_ESTADO, null, null);
            return "Jogo carregado com sucesso!\n";
        }catch (FileNotFoundException ex) {
            return "O ficheiro não existe, insira um correto !";
        }catch (NotSerializableException e){
            return "Erro na serialização";
        } catch (IOException | ClassNotFoundException e){
            return "Erro a ler !";
        }
    }

    public String gravarJogo(File filename)  {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
            oos.writeObject(gestorJogoDados);
            // Gravado ambos os stacks para poder voltar atrás e gravar futuramente o replay
            oos.close();
            return "Jogo gravado com sucesso!";
        }catch (NotSerializableException e){
            filename.delete();
            return "Erro na serialização";
        }catch (Exception e) {
            if(filename != null)
                filename.delete();
            return "Erro a gravar!";
        }
    }

    public void terminar(){
        propertyChangeSupport.firePropertyChange(PROPRIEDADE_TERMINAR, null, null);
    }
}
