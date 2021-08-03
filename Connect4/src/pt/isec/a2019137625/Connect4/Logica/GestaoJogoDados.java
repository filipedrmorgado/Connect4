package pt.isec.a2019137625.Connect4.Logica;

import javafx.scene.paint.Color;
import pt.isec.a2019137625.Connect4.Logica.Estados.IEstado;
import pt.isec.a2019137625.Connect4.Logica.dados.Coordenada;
import pt.isec.a2019137625.Connect4.Logica.dados.Enum.Disco;
import pt.isec.a2019137625.Connect4.Logica.dados.Enum.QuemJoga;
import pt.isec.a2019137625.Connect4.Logica.dados.Enum.Situacao;
import pt.isec.a2019137625.Connect4.Logica.memento.ICareTaker;
import pt.isec.a2019137625.Connect4.Logica.memento.Memento;

import java.io.*;
import java.nio.file.FileSystems;
import java.util.*;

public class GestaoJogoDados implements ICareTaker, Serializable{
    private MaquinaEstados maqEstadosOriginator;
    // Care Taker here
    private Deque<Memento> stackHist = new ArrayDeque<>();
    private Deque<Memento> stackJogo = new ArrayDeque<>();

    public GestaoJogoDados() {
        this.maqEstadosOriginator = new MaquinaEstados();
    }

    // Maquina de Estados
    public void ComecaJogo(){
        maqEstadosOriginator = new MaquinaEstados();
        maqEstadosOriginator.ComecaJogo();
    }

    public void EscolheTipoJog(int tipo){
        maqEstadosOriginator.EscolheTipoJog(tipo);
    }
    public void EscolheNomes(String nomeJog1,String nomeJog2, String corJog1, String corJog2){
        maqEstadosOriginator.EscolheNomes(nomeJog1,nomeJog2,corJog1,corJog2);
    }
    public void EfetuaJogada(int coluna){
        gravaMemento();
        gravaJogo();
        maqEstadosOriginator.EfetuaJogada(coluna);
    }
    public void JogaPecaEspecial(int coluna){
        gravaMemento();
        gravaJogo();
        maqEstadosOriginator.JogaPecaEspecial(coluna);
    }
    public void RespostaMiniJogo(String resposta){
        maqEstadosOriginator.RespostaMiniJogo(resposta);
    }
    public void vaiJogar(){
        maqEstadosOriginator.vaiJogar();
    }
    public void RecomecaJogo() {
        stackHist = new ArrayDeque<>();
        stackJogo = new ArrayDeque<>();
        maqEstadosOriginator.RecomecaJogo();
    }

    public void ReplayJogo(int sizeOfStackJogo){
        maqEstadosOriginator.ReplayJogo(sizeOfStackJogo);
    }

    // Métodos de consulta
    public String imprimeJogoAtual(){
        return maqEstadosOriginator.imprimeJogoAtual();
    }
    public Situacao getSituacao(){
        return maqEstadosOriginator.getSituacao();
    }
    public String getHistorico() {
        return maqEstadosOriginator.getHistorico();
    }
    public String getNomeQuemJoga() { return maqEstadosOriginator.getNomeQuemJoga(); }
    public String getVencedor() { return maqEstadosOriginator.getVencedor(); }
    public void limpaHistorico(){
        maqEstadosOriginator.limpaHistorico();
    }
    public String getEstadoJogo() { return maqEstadosOriginator.getEstadoJogo(); }
    public Map<Coordenada, Disco> getBoard() {
        return maqEstadosOriginator.getBoard();
    }
    public int getNumeroJogadas() { return maqEstadosOriginator.getNumeroJogadas();}

    //GET MINI JOGOS
    public String getNomeMiniJogo(){ return maqEstadosOriginator.getNomeMiniJogo(); }
    public String getPerguntaMiniJogo(){ return maqEstadosOriginator.getPerguntaMiniJogo(); }
    public String getAcertouPergunta(){return maqEstadosOriginator.getAcertouPergunta();}
    public int getCreditosJogador(){return maqEstadosOriginator.getCreditosJogador(); }
    public String getTipoJogador() { return maqEstadosOriginator.getTipoJogador(); }
    public int getQtPecasEspJog() { return maqEstadosOriginator.getQtPecasEspJog(); }
    public long getTempoAcerto() { return  maqEstadosOriginator.getTempoAcerto(); }


    // Metodos de apoio a parte gráfica
    public Situacao getEstadoAnterior(){
        return maqEstadosOriginator.getEstadoAnterior();
    }
    public String getCorJog1() { return maqEstadosOriginator.getCorJog1(); }
    public String getCorJog2() { return maqEstadosOriginator.getCorJog2(); }
    public String getCorJogAtual() { return maqEstadosOriginator.getCorJogAtual(); }



    // Tratamento de Gravações, Replays e ficheiros.

    // Listar ficheiros existentes para Gravações e Replays
    public String listaFicheiros(int listar){
        File folder;
        if(listar == 0) // Listar gravações
            folder = new File(FileSystems.getDefault().getPath("." + File.separator +"Gravacoes").toString());
        else            // Listar Replays
            folder = new File(FileSystems.getDefault().getPath("."+File.separator+"Replays").toString());
        File [] listOfFiles = folder.listFiles();
        return getListOfFiles(listOfFiles);
    }

    private String getListOfFiles(File[] listOfFiles) {
        StringBuilder lista = new StringBuilder();
        if (listOfFiles != null) {
            for (File file : listOfFiles)
                if (file.isFile())
                    lista.append(file.getName()).append("\n");
        }
        if(lista.isEmpty())
            return "Sem gravações disponíveis!\n";

        lista.insert(0,"Ficheiros para escolha:\n");
        return lista.toString();
    }

    // Gravação do jogo
    public void gravaJogo(){
        try {
            stackJogo.push(maqEstadosOriginator.getMementoJogo());
        } catch (Exception e) {
            System.err.println("Erro na gravação do Memento do jogo!");
            stackJogo.clear();
        }
    }
    // Função para avançar no jogo
    public String avancaMemJogo(){
        if(stackJogo.isEmpty())
            return "A gravação está vazia!";
        try {
            Memento avancaEstado = stackJogo.removeLast();
            maqEstadosOriginator.setMementoJogo(avancaEstado);
            if(stackJogo.isEmpty())
                return "Vai visualizar o final do jogo! Vai voltar ao inicio de seguida!";
            else
                return "Avancou uma jogada!";
        } catch(IOException | ClassNotFoundException ex) {
            stackJogo.clear();
            return "Ocorreu um erro a avançar o jogo!";
        }
    }

    // Método para obter tamanho do stack para saber quando acabar o replay para reiniciar jogo
    public int getSizeStackJogo(){
        return stackJogo.size();
    }

    public boolean loadReplay(String nomeJogo){
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("." + File.separator +"Replays" + File.separator + nomeJogo + ".bin"));
            stackJogo = (Deque<Memento>) ois.readObject();
            ois.close();
            return true;
        } catch (FileNotFoundException ex) {
            System.err.println("O ficheiro não existe, insira um correto !");
        } catch (IOException | ClassNotFoundException e){
            System.err.println("Erro na leitura do ficheiro!");
        }
        return false;
    }


    public String carregarReplay(File filename){
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
            stackJogo = (Deque<Memento>) ois.readObject();
            ois.close();
            return "Replay carregado com sucesso!";
        } catch (FileNotFoundException ex) {
            return "O ficheiro não existe, insira um correto !";
        }catch (IOException | ClassNotFoundException e){
            return "Erro a ler !";
        }
    }


    public String gravaReplay() {
        //Para guardar o estado final do jogo, a vitória
        gravaJogo();
        // Certificar que tem apenas os 5 ficheiros mais recentes
        verificaNumeroFicheiros();
        File novoFicheiro = null;
        try {
            novoFicheiro = new File("." + File.separator + "Replays" + File.separator +"replay" + (System.currentTimeMillis()/10000L) + ".bin");
            if(!novoFicheiro.createNewFile())
                return "Já existe um ficheiro com esse nome!";
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(novoFicheiro, false));
            oos.writeObject(stackJogo);
            oos.close();
            return "Gravação efetuada com sucesso!";
        }catch (NotSerializableException e){
            novoFicheiro.delete();
            return "Erro na serialização";
        }catch (Exception e) {
            if(novoFicheiro != null)
                novoFicheiro.delete();
            return "Erro na gravação do ficheiro";
        }
    }
    
    // Gravar Memento
    public void gravaMemento() {
        try {
            stackHist.push(maqEstadosOriginator.getMemento());
        }catch (IOException e) {
            System.err.println("Erro na gravação do Memento de estado e jogo!");
            stackHist.clear();
        }
    }

    public String undo(int voltarAtras) {
        if(voltarAtras == 0)
            return "O jogador decidiu não voltar atrás.";
        if (stackHist.isEmpty())
            return "Stack vazia! Não pode voltar atrás !";
        //Caso não tenha undos suficientes para o pedido ou o jogador nao tenha os creditos pedidos, não faz nada
        if(stackHist.size() < voltarAtras)
            return "Apenas pode voltar atrás " + stackHist.size() + " vezes e não mais!";

        int contadorUndos = voltarAtras;
        try {
            Memento anterior;
            // Vai ser grava um historico extra do jogo devido ao facto do undo atualizar a jogada anterior feito com
            // o a versão do jogo pós undo.
            stackJogo.push(maqEstadosOriginator.getMementoJogo());
            do{
                --contadorUndos;
                anterior = stackHist.pop();
            }while(contadorUndos > 0);
            // Atualiza o estado e jogo para  -"voltarAtras" vezes
            maqEstadosOriginator.setMemento(anterior,voltarAtras);
        } catch (IOException | ClassNotFoundException ex) {
            stackHist.clear();
            return "Erro a dar Undo!";
        }
        return "Undo efetuado com sucesso!";
    }

    public void verificaNumeroFicheiros(){
        File folder = new File(FileSystems.getDefault().getPath("." + File.separator +"Replays").toString());
        File [] listOfFiles = folder.listFiles();
        // Manutenção dos 5 ficheiros mais recentes
        if(listOfFiles != null && listOfFiles.length >= 5){
            long dataMaisAntiga = Long.MAX_VALUE;
            File fileMaisAntigo = null;
            for(File file : listOfFiles)
                if(file.lastModified() < dataMaisAntiga){
                    dataMaisAntiga = file.lastModified();
                    fileMaisAntigo = file;
                }
            if(fileMaisAntigo != null)
                fileMaisAntigo.delete();
        }
    }

    // Metodos para carregar e gravar estado atual do jogo
    public String gravaEstadoJogoFich(String nomeGravacao){
        File novoFicheiro = null;
        try {
            novoFicheiro = new File("." + File.separator +"Gravacoes" + File.separator + nomeGravacao + ".bin");
            if(!novoFicheiro.createNewFile()) // Se ficheiro ja existe não faz nada
                return "Já existe um ficheiro com esse nome!";
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(novoFicheiro,false));
            oos.writeObject(maqEstadosOriginator);
            // Gravado ambos os stacks para poder voltar atrás e gravar futuramente o replay
            oos.writeObject(stackHist);
            oos.writeObject(stackJogo);
            oos.close();
            return "Jogo gravado com sucesso!";
        }catch (NotSerializableException e){
            novoFicheiro.delete();
            return "Erro na serialização";
        }catch (Exception e) {
            if(novoFicheiro != null)
                novoFicheiro.delete();
            return "Erro a gravar!";
        }
    }

    public String carregaEstadoJogoFich(String nomeGravacao){
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("." + File.separator +"Gravacoes" + File.separator + nomeGravacao + ".bin"));
            maqEstadosOriginator = (MaquinaEstados) ois.readObject();
            // Gravado ambos os stacks para poder voltar atrás e gravar futuramente o replay
            stackHist = (Deque<Memento>) ois.readObject();
            stackJogo = (Deque<Memento>) ois.readObject();
            ois.close();
            return "Jogo carregado com sucesso!\n";
        } catch (FileNotFoundException ex) {
            return "O ficheiro não existe, insira um correto !";
        }catch (NotSerializableException e){
            return "Erro na serialização";
        } catch (IOException | ClassNotFoundException e){
            return "Erro a ler !";
        }
    }

    @Override
    public String toString() {
        return "stackHist=" + stackHist.size() + "\t"
                +"stackJogo=" + stackJogo.size()
                + maqEstadosOriginator.toString();
    }
}
