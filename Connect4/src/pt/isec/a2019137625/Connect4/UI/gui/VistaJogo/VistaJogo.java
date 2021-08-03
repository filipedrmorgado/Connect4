package pt.isec.a2019137625.Connect4.UI.gui.VistaJogo;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import pt.isec.a2019137625.Connect4.Connect4Observer;
import pt.isec.a2019137625.Connect4.Logica.dados.Coordenada;
import pt.isec.a2019137625.Connect4.Logica.dados.Enum.Disco;
import pt.isec.a2019137625.Connect4.Logica.dados.Enum.Situacao;
import pt.isec.a2019137625.Connect4.UI.gui.VistaJogo.Replay.ToolBarReplay;
import pt.isec.a2019137625.Connect4.UI.gui.VistaJogo.ToolBarVista.ToolBarHumano;
import pt.isec.a2019137625.Connect4.UI.gui.VistaJogo.ToolBarVista.ToolBarInfo;
import pt.isec.a2019137625.Connect4.UI.gui.VistaJogo.ToolBarVista.ToolBarVirtual;

import java.io.File;
import java.util.Map;

import static pt.isec.a2019137625.Connect4.Logica.Constantes.*;
import static pt.isec.a2019137625.Connect4.Logica.Constantes.PROPRIEDADE_ESTADO;
import static pt.isec.a2019137625.Connect4.UI.gui.ConstantesGUI.*;
import static pt.isec.a2019137625.Connect4.UI.gui.Resources.Utility.*;

public class VistaJogo extends BorderPane {
    private final Connect4Observer C4Observer;
    // Board
    private GridPane gameBoardGrid;
    private final BorderPane[][] gameBoardPanes = new BorderPane[nr_colunas][nr_linhas];
    private final Circle[][] gameBoardCircles = new Circle[nr_colunas][nr_linhas];
    private StackPane bottomBoard;
    private VBox vBoxTopBar;
    // Menu
    private MenuBar menuBar;
    private Menu jogoMenu,ajudaMenu;
    private MenuItem terminarJogoMI,gravarJogoMI,comoJogarMI,criadorMI;

    public VistaJogo(Connect4Observer c4Observer){
        this.C4Observer = c4Observer;
        criarComponentes();
        criacaoMenuJogo();
        criacaoMenuAjuda();
        disporVista();
        registarListeners();
        registarListenersMenu();
        registaObserver();
        changeBackground(this,Color.LIGHTSKYBLUE);
    }

    private void registaObserver() {
        C4Observer.addPropertyChangeListener(
                PROPRIEDADE_ESTADO,
                e-> {if(C4Observer.getSituacao() == Situacao.EsperaJogada || C4Observer.getSituacao() == Situacao.JogaMiniJogo ||
                       C4Observer.getSituacao() == Situacao.DecideJogaMiniJogo || C4Observer.getSituacao() == Situacao.ReplayJogo){
                    if(C4Observer.getSituacao() == Situacao.EsperaJogada || C4Observer.getSituacao() == Situacao.ReplayJogo)
                        pintaBoard();
                        this.setVisible(true);
                    }else
                        this.setVisible(false);
                });
        C4Observer.addPropertyChangeListener(
                PROPRIEDADE_DADOS,
                e-> {if(C4Observer.getSituacao() == Situacao.EsperaJogada)
                        pintaBoard();
                });
        C4Observer.addPropertyChangeListener(
                PROPRIEDADE_REPLAY,
                e-> {if(C4Observer.getSituacao() == Situacao.ReplayJogo)
                        pintaBoard();
                });
    }

    private void setupBoard() {
        Lighting lighting = new Lighting(new Light.Distant(100.0,100.0,null));
        lighting.setSurfaceScale(5.0);
        for(int i = 0; i < nr_colunas; i++)
            for(int j = 0; j < nr_linhas; j++) {
                gameBoardCircles[i][j] = new Circle(CIRCLE_RADIUS,Color.WHITE);
                gameBoardPanes[i][j] = new BorderPane(gameBoardCircles[i][j]);
                gameBoardPanes[i][j].setPrefSize(PANE_WIDTH,PANE_HEIGHT);

                gameBoardCircles[i][j].setEffect(lighting);
                changeBackground(gameBoardPanes[i][j],Color.web(PANE_BG_COLOR));
                gameBoardGrid.add(gameBoardPanes[i][j], i, j);
            }
    }

    // Sempre que janela alterar a dimenção, atualiza a board um tamanho valido
    ChangeListener procSize = (observableValue, o, t1) -> atualizaRadius();

    private void criarComponentes() {
        gameBoardGrid = new GridPane();
        setupBoard();
        // Toolbar para humano, virtual e replay
        bottomBoard = new StackPane(new ToolBarHumano(C4Observer, gameBoardPanes, gameBoardCircles), new ToolBarVirtual(C4Observer), new ToolBarReplay(C4Observer));
        // Top bar
        menuBar = new MenuBar();
        jogoMenu = new Menu("Jogo");
        vBoxTopBar = new VBox(menuBar, new ToolBarInfo(C4Observer));
    }

    private void criacaoMenuJogo(){
        terminarJogoMI = new MenuItem("Terminar Jogo");
        terminarJogoMI.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));

        gravarJogoMI = new MenuItem("Gravar Jogo");
        gravarJogoMI.setAccelerator(new KeyCodeCombination(KeyCode.G, KeyCombination.CONTROL_DOWN));

        jogoMenu.getItems().addAll(gravarJogoMI,new SeparatorMenuItem(), terminarJogoMI);
    }

    private void criacaoMenuAjuda(){
        // menu ajuda
        ajudaMenu = new Menu("_Ajuda");

        comoJogarMI = new MenuItem("Como jogar");
        comoJogarMI.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));

        criadorMI = new MenuItem("Criador");
        criadorMI.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));

        ajudaMenu.getItems().addAll(comoJogarMI, criadorMI);
        menuBar.getMenus().addAll(jogoMenu,ajudaMenu);
    }

    private void registarListenersMenu(){
        terminarJogoMI.setOnAction((e)-> C4Observer.RecomecaJogo());
        gravarJogoMI.setOnAction(e-> gravaJogoAct());
        comoJogarMI.setOnAction(e-> setAlert(1,"Para ganhar terá que conectar 4 peças iguais na vertical, horizontal ou diagonal! Boa sorte!","Espera Jogada","Como Jogar"));
        criadorMI.setOnAction(e-> setAlert(1,"Filipe Morgado - a201913625@isec.pt - 2020/2021","Espera Jogada","Criador"));
    }

    private void gravaJogoAct(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."+ File.separator +"Gravacoes"));
        File selectedFile = fileChooser.showSaveDialog(null);
        if (selectedFile != null) {
            String resGravacao = C4Observer.gravarJogo(selectedFile);
            Alert alert = new Alert(Alert.AlertType.INFORMATION,resGravacao);
            alert.setTitle("Gravação do Jogo");
            alert.setHeaderText("Resultado da Gravação");
            alert.show();
        } else
            System.err.println("Gravacao cancelada");
    }

    private void disporVista() {
        this.setCenter(gameBoardGrid);
        gameBoardGrid.setAlignment(Pos.CENTER);
        this.setBottom(bottomBoard);
        this.setTop(vBoxTopBar);
    }

    private void atualizaRadius(){
        double raio;
        // Calculo feito para ter em conta o raio, visto que a BorderPane não é um quadrado perfeito, logo o raio
        // é feito tendo em conta a medida mais pequena
        if(gameBoardPanes[0][0].getWidth() > gameBoardPanes[0][0].getHeight())
            raio = gameBoardPanes[0][0].getHeight() / 2 - 0.1*gameBoardPanes[0][0].getHeight();
        else
            raio = gameBoardPanes[0][0].getWidth()/ 2 - 0.1*gameBoardPanes[0][0].getWidth();

        for(int i = 0; i < nr_colunas; i++)
            for(int j = 0; j < nr_linhas; j++)
                gameBoardCircles[i][j].setRadius(raio);
    }

    private void registarListeners() {
        this.widthProperty().addListener(procSize);
        this.heightProperty().addListener(procSize);
        gameBoardPanes[0][0].widthProperty().addListener(procSize);
        gameBoardPanes[0][0].heightProperty().addListener(procSize);
    }

    private void pintaBoard(){
        Map<Coordenada, Disco> board = C4Observer.getBoard();
        Disco discAux;
        int linha;
        for(int i = 0; i < nr_linhas; i++)
            for (int j = 0; j < nr_colunas; j++) {
                discAux = board.get(new Coordenada(i,j));
                Color corParaPreencher;
                if(discAux == Disco.X) // Logo é o disco do jogador1, preenche com a cor dele
                    corParaPreencher = Color.web(C4Observer.getCorJog1());
                else if(discAux == Disco.Y) // Logo é o disco do jogador2, preenche com a cor dele
                    corParaPreencher = Color.web(C4Observer.getCorJog2());
                else
                    corParaPreencher = Color.WHITE;
                linha = inverteLinha(i);
                gameBoardCircles[j][linha].setFill(corParaPreencher);
            }
    }
}
