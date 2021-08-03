package pt.isec.a2019137625.Connect4.UI.texto;

import javafx.scene.paint.Color;
import pt.isec.a2019137625.Connect4.Logica.GestaoJogoDados;
import pt.isec.a2019137625.Connect4.Utils.Util;

public class Connect4UI {
    private final GestaoJogoDados maquinaEstados;
    private boolean sair;

    public Connect4UI(GestaoJogoDados maquinaEstados){
        this.maquinaEstados = maquinaEstados;
        this.sair = false;
        System.out.println("\nBem vindo ao Quatro em Linha !\nPara ganhar terá que conectar 4 peças " +
                "iguais na vertical, horizontal ou diagonal! Boa sorte!");
    }

    public void comeca(){
        maquinaEstados.limpaHistorico();
        while(!sair){
            System.out.print(maquinaEstados.getHistorico());
            System.out.println("\n" + maquinaEstados);
            switch (maquinaEstados.getSituacao()){
                case Inicio -> uiInicio();
                case ReplayJogo -> uiReplayJogo();
                case EscolheTipoJogadores -> uiEscolheTipoJogadores();
                case DefineNomes -> uiDefineNomes();
                case EsperaJogada -> uiEsperaJogada();
                case DecideJogaMiniJogo -> uiDecideJogaMiniJogo();
                case JogaMiniJogo -> uiJogaMiniJogo();
                case FimJogo -> uiFimJogo();
            }
        }
    }

    private void uiInicio() {
        System.out.println("Iniciou o jogo: ");
        switch(Util.escolheOpcao("Jogar","Replay de um jogo","Carregamento de um jogo","Sair")){
            case 1 -> maquinaEstados.ComecaJogo();
            case 2 -> {
                System.out.println(maquinaEstados.listaFicheiros(1));
                String nomeJogo = Util.pedeString("Indique qual dos ultimos jogos gravados quer repetir ou escreva 'fim' para sair: ");
                if(!nomeJogo.equalsIgnoreCase("fim"))
                    if(maquinaEstados.loadReplay(nomeJogo))
                        maquinaEstados.ReplayJogo(maquinaEstados.getSizeStackJogo());
            }
            case 3 -> {
                System.out.println(maquinaEstados.listaFicheiros(0));
                String nomeFich = Util.pedeString("Indique o nome do ficheiro a carregar ou escreva 'fim' para sair: ");
                if(!nomeFich.equalsIgnoreCase("fim"))
                    System.out.println(maquinaEstados.carregaEstadoJogoFich(nomeFich));
            }
            default -> sair = true;
        }
    }

    private void uiReplayJogo(){
        switch (Util.escolheOpcao("Avancar no jogo","Terminar replay","Sair")){
            case 1 -> {
                System.out.println(maquinaEstados.avancaMemJogo());
                System.out.println(maquinaEstados.imprimeJogoAtual());
                maquinaEstados.ReplayJogo(maquinaEstados.getSizeStackJogo());
            }
            case 2 -> maquinaEstados.RecomecaJogo();
            default -> sair = true;
        }
    }

    private void uiEscolheTipoJogadores() {
        System.out.println("Escolha o tipo de jogadores:");
        switch (Util.escolheOpcao("2 jogadores humanos","1 jogador humano e 1 jogador virtual","2 jogadores virtuais","Terminar jogo","Sair")){
            case 1 -> maquinaEstados.EscolheTipoJog(1);
            case 2 -> maquinaEstados.EscolheTipoJog(2);
            case 3 -> maquinaEstados.EscolheTipoJog(3);
            case 4 -> maquinaEstados.RecomecaJogo();
            default -> sair = true;
        }
    }

    private void uiDefineNomes() {
        switch (Util.escolheOpcao("Inserir o nome dos jogadores","Termina jogo","Sair")){
            case 1 -> {
                System.out.println("Insira nome diferentes:");
                String nomeJog1,nomeJog2;
                do {
                    nomeJog1 = Util.pedeString("Nome do jogador 1: ");
                    nomeJog2 = Util.pedeString("Nome do jogador 2: ");
                }while(nomeJog1.equalsIgnoreCase(nomeJog2));
                maquinaEstados.EscolheNomes(nomeJog1,nomeJog2, Color.RED.toString(), Color.RED.toString());
            }
            case 2 -> maquinaEstados.RecomecaJogo();
            default -> sair = true;
        }
    }
    private void uiEsperaJogada() {
        System.out.println("Vez do jogador [" + maquinaEstados.getNomeQuemJoga() + "] jogar.");
        System.out.println(maquinaEstados.imprimeJogoAtual());
        switch (maquinaEstados.getTipoJogador()){
            case "Humano" -> {
                switch (Util.escolheOpcao("Efetuar jogada","Voltar atras","Jogar peça especial. Tem [" + maquinaEstados.getQtPecasEspJog() +"] disponíveis.","Gravar jogo atual","Terminar jogo","Sair")){
                    case 1 -> {
                        // System.out.println(maquinaEstados.imprimeJogoAtual());
                        int coluna;
                        do {
                            coluna = Util.pedeInteiro("Indique a coluna para colocar a peça que seja válida [1-7 e com espaço]: ");
                        }while(coluna < 1 | coluna > 7);
                        maquinaEstados.EfetuaJogada(coluna);
                    }
                    case 2 -> {
                        int voltarAtras;
                        System.out.println("Pressione 0 para sair desta opção.");
                        do {
                            voltarAtras = Util.pedeInteiro("Quantas vezes quer voltar atrás (tem " + maquinaEstados.getCreditosJogador() + " créditos): ");
                        }while(voltarAtras > maquinaEstados.getCreditosJogador());
                        System.out.println(maquinaEstados.undo(voltarAtras));
                    }
                    case 3 -> {
                        int coluna;
                        System.out.println(maquinaEstados.imprimeJogoAtual());
                        do {
                            coluna = Util.pedeInteiro("Indique a coluna para colocar a peça especial [1-7]: ");
                        }while(coluna < 0 | coluna > 7);
                        maquinaEstados.JogaPecaEspecial(coluna);
                    }
                    case 4 -> {
                        String nomeFichGravar = Util.pedeString("Indique o nome do ficheiro para gravar o jogo: ");
                        System.out.println(maquinaEstados.gravaEstadoJogoFich(nomeFichGravar));
                    }
                    case 5 -> maquinaEstados.RecomecaJogo();
                    default -> sair = true;
                }
            }
            case "Virtual" -> {
                switch (Util.escolheOpcao("Avancar no jogo","Gravar jogo atual","Terminar jogo","Sair")){
                    case 1 -> {
                        System.out.println(maquinaEstados.imprimeJogoAtual());
                        maquinaEstados.EfetuaJogada(0);
                    }
                    case 2 -> {
                        String nomeFichGravar = Util.pedeString("Indique o nome do ficheiro para gravar o jogo: ");
                        System.out.println(maquinaEstados.gravaEstadoJogoFich(nomeFichGravar));
                    }
                    case 3 -> maquinaEstados.RecomecaJogo();
                    default -> sair = true;
                }
            }
        }
    }

    private void uiDecideJogaMiniJogo() {
        System.out.println("Vez do jogador [" + maquinaEstados.getNomeQuemJoga() + "] decidir se quer jogar o "+ maquinaEstados.getNomeMiniJogo() + " ou efetuar uma jogada normal.");
        switch(Util.escolheOpcao("Jogar miniJogo","Efetua Jogada","Terminar jogo","Sair")){
            case 1 -> {
                System.out.println("\nO jogador [" + maquinaEstados.getNomeQuemJoga() + "] vai jogar.");
                maquinaEstados.vaiJogar();
            }
            case 2 -> maquinaEstados.EfetuaJogada(0);
            case 3 -> maquinaEstados.RecomecaJogo();
            default -> sair = true;
        }
    }

    private void uiJogaMiniJogo() {
        System.out.println("\nO jogador [" + maquinaEstados.getNomeQuemJoga() + "] vai jogar o mini jogo.");
        System.out.println("Escreva [sair] a qualquer momento para terminar o jogo!");
        System.out.println(maquinaEstados.getPerguntaMiniJogo());
        String resposta = Util.pedeString("Resposta:");
        if ("sair".equalsIgnoreCase(resposta)) {
            maquinaEstados.RecomecaJogo();
        } else {
            maquinaEstados.RespostaMiniJogo(resposta);
            System.out.println(maquinaEstados.getAcertouPergunta());
            System.out.println(maquinaEstados.getEstadoJogo());
        }
    }

    private void uiFimJogo() {
        System.out.println(maquinaEstados.imprimeJogoAtual());
        System.out.println(maquinaEstados.getVencedor());
        System.out.println(maquinaEstados.gravaReplay());
        switch(Util.escolheOpcao("Voltar a jogar","Sair")){
            case 1 -> maquinaEstados.RecomecaJogo();
            default -> sair = true;
        }
    }
}
