package pt.isec.a2019137625.Connect4.UI.gui.VistaJogo.ToolBarVista;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import pt.isec.a2019137625.Connect4.Connect4Observer;
import pt.isec.a2019137625.Connect4.Logica.dados.Coordenada;
import pt.isec.a2019137625.Connect4.Logica.dados.Enum.Disco;
import pt.isec.a2019137625.Connect4.Logica.dados.Enum.Situacao;
import pt.isec.a2019137625.Connect4.UI.gui.Resources.CSSManager;
import pt.isec.a2019137625.Connect4.UI.gui.Resources.FontsManager;
import pt.isec.a2019137625.Connect4.UI.gui.Resources.MusicPlayer;

import java.util.Map;

import static pt.isec.a2019137625.Connect4.Logica.Constantes.*;
import static pt.isec.a2019137625.Connect4.UI.gui.ConstantesGUI.*;
import static pt.isec.a2019137625.Connect4.UI.gui.Resources.Utility.*;

public class ToolBarHumano extends AnchorPane {
    private final Connect4Observer C4Observer;
    private Button btnVoltarAtras;
    private Label lblTipoPeca;
    private TextField txtVoltarAtras;
    private HBox hBoxJogadas, hBoxVoltaAtras;
    private ToggleGroup radioGroup;
    private RadioButton rbPecaNormal,rbPecaEsp;
    // Board
    private final BorderPane[][] gameBoardPanes;
    private final Circle[][] gameBoardCircles;

    public ToolBarHumano(Connect4Observer C4Observer, BorderPane[][] gameBoardPanes, Circle[][] gameBoardCircles){
        this.C4Observer = C4Observer;
        this.gameBoardPanes = gameBoardPanes;
        this.gameBoardCircles = gameBoardCircles;
        criarLayout();
        disporVista();
        registarListeners();
        registaObservador();
        registaListenerJogadas();
        CSSManager.setCSS(this,CSS_MAIN_PLAY);
    }

    private void registaObservador() {
        C4Observer.addPropertyChangeListener(
                PROPRIEDADE_ESTADO,
                e-> {if(C4Observer.getSituacao() == Situacao.EsperaJogada && C4Observer.getTipoJogador().equalsIgnoreCase("Humano") ||
                       C4Observer.getSituacao() == Situacao.JogaMiniJogo && C4Observer.getTipoJogador().equalsIgnoreCase("Humano") ||
                       C4Observer.getSituacao() == Situacao.DecideJogaMiniJogo && C4Observer.getTipoJogador().equalsIgnoreCase("Humano")){
                        atualizaVista();
                        this.setVisible(true);
                    }else
                        this.setVisible(false);
                });

        C4Observer.addPropertyChangeListener(
                PROPRIEDADE_DADOS,
                e-> {
                    if(C4Observer.getSituacao() == Situacao.EsperaJogada && C4Observer.getTipoJogador().equalsIgnoreCase("Humano")){
                        atualizaVista();
                        this.setVisible(true);
                    }else
                        this.setVisible(false);
                });
    }

    private void criarLayout() {
        // Btn VoltarAtras
        btnVoltarAtras = new Button("Voltar Atrás");
        btnVoltarAtras.setPrefSize(BTN_WIDTH_HUM,BTN_HEIGHT_HUM);
        // TextField Voltar Atrás
        txtVoltarAtras = new TextField();
        txtVoltarAtras.setPrefSize(TEXTFIELD_WIDTH_MEDIUM,TEXTFIELD_HEIGHT_MEDIUM);
        // Checkbox jogadas
        lblTipoPeca = new Label("Tipo de peça:");
        rbPecaNormal = new RadioButton("Peça normal");
        rbPecaEsp = new RadioButton("Peça especial");
        radioGroup = new ToggleGroup();
        rbPecaNormal.setToggleGroup(radioGroup);
        rbPecaEsp.setToggleGroup(radioGroup);
        setHboxes();
        setFonts();
        listenersInput();
    }

    private void listenersInput(){
        txtVoltarAtras.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtVoltarAtras.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if(newValue.length() > 1 ) txtVoltarAtras.setText(oldValue);
        });
    }

    private void setHboxes(){
        // Hbox Jogada
        hBoxJogadas = new HBox();
        hBoxJogadas.getChildren().addAll(lblTipoPeca,rbPecaNormal,rbPecaEsp);
        hBoxJogadas.setSpacing(HBOX_SPACING_V1);
        hBoxJogadas.setPadding(new Insets(HBOX_PADDING_MEDIUM));
        // Hbox VoltarAtras
        hBoxVoltaAtras = new HBox();
        hBoxVoltaAtras.getChildren().addAll(txtVoltarAtras,btnVoltarAtras);
        hBoxVoltaAtras.setSpacing(HBOX_SPACING_V1);
        hBoxVoltaAtras.setPadding(new Insets(HBOX_PADDING_MEDIUM));
    }

    private void setFonts(){
        txtVoltarAtras.setFont(FontsManager.loadFont(TXT_FONT,TXT_FONT_SIZE_LOW));
        lblTipoPeca.setFont(FontsManager.loadFont(TXT_FONT,HEADER_FONT_SIZE));
        rbPecaNormal.setFont(FontsManager.loadFont(TXT_FONT,HEADER_FONT_SIZE));
        rbPecaEsp.setFont(FontsManager.loadFont(TXT_FONT,HEADER_FONT_SIZE));
    }

    private void disporVista(){
        this.getChildren().addAll(hBoxJogadas, hBoxVoltaAtras);
        AnchorPane.setLeftAnchor(hBoxJogadas,0.0);
        AnchorPane.setRightAnchor(hBoxVoltaAtras,0.0);
    }

    private void registarListeners() {
        btnVoltarAtras.setOnAction(e->{
            if(txtVoltarAtras.getText().isEmpty()){
                setAlert(4,"Celula de texto vazia, insira a coluna","Efetua Jogada","Inserção de dados");
                return;
            }
            try {
                String resultUndo = C4Observer.undo(getValue(txtVoltarAtras.getText().trim(),C4Observer.getCreditosJogador()));
                if(resultUndo.equalsIgnoreCase("Stack vazia! Não pode voltar atrás !"))
                    setAlert(4,"Não tem qualquer jogada efetuada! Jogue primeiro !","Efetua Jogada","Inserção de dados");
                if(resultUndo.equalsIgnoreCase("Apenas pode voltar atrás " + C4Observer.getSizeStackJogo() + " vezes e não mais!"))
                    setAlert(4,"O número de undos pedidos é superior ao das jogadas existentes [" + C4Observer.getSizeStackJogo() + "].","Efetua Jogada","Inserção de dados");
            }catch (NumberFormatException ex){
                setAlert(3,"Tem que inserir um número válido entre 1 e " + C4Observer.getCreditosJogador(),"Efetua Jogada","Inserção de dados");
            }
        });
    }

    private void registaListenerJogadas() {
        for(int i = 0; i < nr_colunas; i++)
            for(int j = 0; j < nr_linhas; j++) {
                final int coluna = i;
                gameBoardCircles[i][j].setOnMouseEntered(e-> preencheColunaCor(coluna, Color.rgb(44,255,133)));
                gameBoardCircles[i][j].setOnMouseExited(e-> preencheColunaCor(coluna,Color.WHITE));
                gameBoardPanes[i][j].setOnMouseClicked(e-> efetuaJogada(coluna));
            }
    }

    private void preencheColunaCor(int coluna, Color color){
        if(C4Observer.getTipoJogador().equalsIgnoreCase("Humano") && C4Observer.getSituacao() == Situacao.EsperaJogada){
            Map<Coordenada, Disco> board = C4Observer.getBoard();
            for(int i = 0; i < nr_linhas; i++)
                if(board.get(new Coordenada(i, coluna)) == Disco.none)
                    gameBoardCircles[coluna][inverteLinha(i)].setFill(color);
        }
    }

    private void efetuaJogada(int coluna){
        if(C4Observer.getTipoJogador().equalsIgnoreCase("Humano")){
            RadioButton selectedRadioButton = (RadioButton) radioGroup.getSelectedToggle();
            if(selectedRadioButton.equals(rbPecaNormal)){
                MusicPlayer.playMusic(INSER_NORM);
                C4Observer.EfetuaJogada(coluna + 1);
            }
            else{
                MusicPlayer.playMusic(INSER_ESP);
                C4Observer.JogaPecaEspecial(coluna + 1);
            }
        }
    }

    private void atualizaVista(){
        // Atualiza dados dos campos
        txtVoltarAtras.setPromptText(C4Observer.getCreditosJogador() + " créditos.");
        txtVoltarAtras.setText("");
        rbPecaEsp.setDisable(C4Observer.getQtPecasEspJog() == 0);
        btnVoltarAtras.setDisable(C4Observer.getCreditosJogador()==0);
        txtVoltarAtras.setDisable(C4Observer.getCreditosJogador()==0);
        rbPecaNormal.requestFocus();
        rbPecaNormal.setSelected(true);
    }
}
