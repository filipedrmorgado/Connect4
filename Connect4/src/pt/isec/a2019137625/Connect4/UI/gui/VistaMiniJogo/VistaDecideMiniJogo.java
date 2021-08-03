package pt.isec.a2019137625.Connect4.UI.gui.VistaMiniJogo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import pt.isec.a2019137625.Connect4.Connect4Observer;
import pt.isec.a2019137625.Connect4.Logica.dados.Enum.Situacao;
import pt.isec.a2019137625.Connect4.UI.gui.Resources.CSSManager;
import pt.isec.a2019137625.Connect4.UI.gui.Resources.FontsManager;
import pt.isec.a2019137625.Connect4.UI.gui.Resources.ImageLoader;

import static pt.isec.a2019137625.Connect4.Logica.Constantes.PROPRIEDADE_ESTADO;
import static pt.isec.a2019137625.Connect4.UI.gui.ConstantesGUI.*;

public class VistaDecideMiniJogo extends BorderPane {
    private final Connect4Observer C4Observer;
    private Label questao,vezDeJogar;
    private Button btnSim, btnNao;
    private HBox hBoxDecide;
    private VBox vBoxDecisao;

    public VistaDecideMiniJogo(Connect4Observer c4Observer){
        this.C4Observer = c4Observer;
        criarComponentes();
        disporVista();
        registarListeners();
        registaObservador();
        CSSManager.setCSS(this,CSS_DESIGN_V2);
        imagemFundo();
    }

    private void imagemFundo(){
        Image imgTeste = ImageLoader.getImage(BG_MATH);
        BackgroundSize backgroundSize = new BackgroundSize(this.getWidth(), this.getHeight(), true, true, true, true);
        if(imgTeste != null)
            this.setBackground(new Background(new BackgroundImage(imgTeste,BackgroundRepeat.SPACE, BackgroundRepeat.SPACE, BackgroundPosition.CENTER,backgroundSize)));
    }


    private void registaObservador(){
        C4Observer.addPropertyChangeListener(
                PROPRIEDADE_ESTADO,
                e-> {
                    if(C4Observer.getSituacao() == Situacao.DecideJogaMiniJogo){
                        atualizaDados();
                        this.setVisible(true);
                    }
                    else
                        this.setVisible(false);
                });
    }

    private void atualizaDados(){
        vezDeJogar.setText("Vez do jogador " + C4Observer.getNomeQuemJoga() + " decidir.");
        questao.setText("Quer jogar o mini jogo " + C4Observer.getNomeMiniJogo() + " ?");
    }

    private void criarComponentes() {
        vezDeJogar = new Label();
        questao = new Label();
        btnNao = new Button("Não");
        btnNao.setPrefSize(BTN_QUEST_WIDTH,BTN_QUEST_HEIGHT);
        btnSim = new Button("Sim");
        btnSim.setPrefSize(BTN_QUEST_WIDTH,BTN_QUEST_HEIGHT);
        hBoxDecide = new HBox();
        hBoxDecide.getChildren().addAll(btnSim,btnNao);
        vBoxDecisao = new VBox();
        vBoxDecisao.getChildren().addAll(questao,hBoxDecide);
        setFont();
    }

    private void setFont(){
        vezDeJogar.setFont(FontsManager.loadFont(HEADER_FONT,TXT_FONT_SIZE_HIGH));
        questao.setFont(FontsManager.loadFont(TXT_FONT,TXT_FONT_SIZE_HIGH));
    }

    private void disporVista() {
        setAlignment(vezDeJogar,Pos.CENTER);
        vBoxDecisao.setAlignment(Pos.CENTER);
        hBoxDecide.setAlignment(Pos.CENTER);
        hBoxDecide.setPadding(new Insets(HBOX_PADDING_MEDIUM));
        hBoxDecide.setSpacing(HBOX_SPACING_V3);
        this.setPadding(new Insets(HBOX_PADDING_TOP));
        this.setTop(vezDeJogar);
        this.setCenter(vBoxDecisao);
    }

    private void registarListeners() {
        btnSim.setOnAction(e-> C4Observer.vaiJogar());
        btnNao.setOnAction(e-> C4Observer.EfetuaJogada(0));
    }
}
