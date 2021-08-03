package pt.isec.a2019137625.Connect4.Logica.dados.Jogos;

import java.io.Serial;
import java.util.*;

public class Matematica extends MiniJogo{
    @Serial
    private static final long serialVersionUID = 1L;
    private final ArrayList<String> operadores = new ArrayList<>(Arrays.asList("+","-","/","*"));
    private int randNum1,randNum2,randOperator;
    private final Random rand = new Random();
    private String pergunta;

    public Matematica(){
        setPergunta();
        pergunta = "\nBem vindo ao jogo da Matemática !\nTem 30 segundos para acertar em 5 cálculos!\n" + pergunta;
    }

    @Override
    public String getPergunta() {
       return pergunta;
    }
    @Override
    public long getTempoAcerto() {
        return 30L;
    }

    public void confirmaResposta(String resposta){
        int resultado = 0;
            switch(randOperator){
                case 0 -> resultado = randNum1 + randNum2;
                case 1 -> resultado = randNum1 - randNum2;
                case 2 -> resultado = randNum1 / randNum2;
                case 3 -> resultado = randNum1 * randNum2;
            }
        // Atualizar pergunta para a proxima questão
        setPergunta();
        if(resposta.equals(Integer.toString(resultado))){
            ++nrAcertos;
            acertou = true;
        }else
            acertou = false;
    }

    public void setPergunta(){
        randNum1 = rand.nextInt(99) + 1;
        randNum2 = rand.nextInt(99) + 1;
        randOperator = rand.nextInt(4);
        pergunta = "Qual é o resutlado de: " + randNum1 + operadores.get(randOperator) + randNum2;
    }

    //return 0: fica sem tempo;  return 1: ainda tem tempo;  return 2: ganhou jogo
    @Override
    public int chegouAoFim() {
        elapsedTime = (new Date()).getTime() - startTime;
        if(elapsedTime > 30 * 1000){
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
                yield "Ainda tem tempo para responder! Rápido !\n";
            default:
                yield "Parabéns! Ganhou o jogo!\n";
        };
    }

}
