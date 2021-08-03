package pt.isec.a2019137625.Connect4.Logica.dados.Jogos;

import pt.isec.a2019137625.Connect4.Logica.dados.Jogo;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Palavras extends MiniJogo{
    @Serial
    private static final long serialVersionUID = 1L;
    private final ArrayList<String> palavras = new ArrayList<>();
    private final ArrayList<String> palavrasToGuess = new ArrayList<>();
    private final ArrayList<Integer> numerosEscolhidos = new ArrayList<>();
    private String pergunta;
    private long tempoAcerto;

    public Palavras(){
        iniciaJogo();
    }

    public void iniciaJogo(){
        abreFicheiroEConsulta();
        geraPalavrasParaPergunta();
        pergunta = "\nBem vindo ao jogo das Palavras !\nAcerte em 5 palavras em " + tempoAcerto + " segundos!\n\n" +
                   "Escreva as seguintes palavras o mais rapido possível: \n" + palavrasToGuess.toString();
    }

    public void geraPalavrasParaPergunta(){
        Random rand = new Random();
        int numGerado;
        for (int i = 0; i < 5; i++) {
            try{
                do {
                    numGerado = rand.nextInt(palavras.size());
                    if(!numerosEscolhidos.contains(numGerado)) {
                        numerosEscolhidos.add(numGerado);
                        palavrasToGuess.add(palavras.get(numGerado));
                        break;
                    }
                }while(palavrasToGuess.size() < 5);
                tempoAcerto += palavrasToGuess.get(i).length();
            }catch(Exception e){
                System.err.println("Erro na adição das palavras");
            }
        }
        tempoAcerto = ((tempoAcerto/2)* 1000L)/1000;
    }

    public long getTempoAcerto() {
        return tempoAcerto;
    }

    public void abreFicheiroEConsulta(){
        String palavraLida;
        try{
            URL fileURL= Jogo.class.getResource("palavrasParaEscolha.txt");
            File file = new File(fileURL.toURI());

            BufferedReader br = new BufferedReader(new FileReader(file));
            while((palavraLida = br.readLine()) != null){
                palavras.add(palavraLida);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Erro ao abrir o ficheiro");
        } catch (IOException e) {
            System.err.println("Erro na leitura");
        }catch (Exception e){
            System.err.println("Erro!");
        }
    }

    @Override
    public String getPergunta() {
        return pergunta;
    }

    @Override
    public void confirmaResposta(String resposta) {
        String [] splited = resposta.split("\\s+");
        for (String s : splited) {
            if (palavrasToGuess.contains(s)) {
                palavrasToGuess.remove(s);
                ++nrAcertos;
            }
        }
        acertou = nrAcertos == 5;
    }

    //return 0: fica sem tempo;  return 1: ainda tem tempo;  return 2: ganhou jogo
    @Override
    public int chegouAoFim() {
        elapsedTime = (new Date()).getTime() - startTime;
        if(elapsedTime > tempoAcerto*1000){
            chegouAoFim = 0;
            return 0;
        }
        if(nrAcertos == 5){
            chegouAoFim = 2;
            return 2;
        }
        chegouAoFim = 1;
        return 1;
    }

    @Override
    public String getEstadoJogo() {
        return switch (chegouAoFim){
            case 0:
                yield "Ficou sem tempo para responder! Perdeu!\n";
            case 1:
                yield "Errou na resposta! Perdeu!\n";
            default:
                yield "Parabéns! Ganhou o jogo!\n";
        };
    }
}
