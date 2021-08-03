package pt.isec.a2019137625.Connect4.UI.gui.VistaJogo.ToolBarVista;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import pt.isec.a2019137625.Connect4.Connect4Observer;
import pt.isec.a2019137625.Connect4.Logica.dados.Enum.Situacao;
import pt.isec.a2019137625.Connect4.UI.gui.Resources.FontsManager;

import static pt.isec.a2019137625.Connect4.Logica.Constantes.*;
import static pt.isec.a2019137625.Connect4.UI.gui.ConstantesGUI.*;

public class ToolBarInfo extends VBox {
    private final Connect4Observer C4Observer;
    private Label lblQuemJoga,lblCreditos,lblPecasEsp, lblNumJogHum,lblNumJogVir, lblCorJogHum,lblCorJogVirt;
    private Circle playerColorHum,playerColorVir;
    private HBox hBoxInfoHumano,hBoxInfoVirtual;
    private StackPane stackPane;

    public ToolBarInfo(Connect4Observer C4Observer){
        this.C4Observer = C4Observer;
        criarLayout();
        disporVista();
        registaObservador();
    }

    private void registaObservador() {
        C4Observer.addPropertyChangeListener(
                PROPRIEDADE_ESTADO,
                e-> {if(C4Observer.getSituacao() == Situacao.EsperaJogada ||
                        C4Observer.getSituacao() == Situacao.JogaMiniJogo ||
                        C4Observer.getSituacao() == Situacao.DecideJogaMiniJogo ||
                        C4Observer.getSituacao() == Situacao.ReplayJogo) {
                            atualizaDados();
                            this.setVisible(true);
                    }else
                            this.setVisible(false);});

        C4Observer.addPropertyChangeListener(
                PROPRIEDADE_DADOS,
                e-> {if (C4Observer.getSituacao() == Situacao.EsperaJogada){
                    atualizaDados();

                }});

        C4Observer.addPropertyChangeListener(
                PROPRIEDADE_REPLAY,
                e-> {if (C4Observer.getSituacao() == Situacao.ReplayJogo)
                    atualizaDados();});
    }

    private void atualizaDados() {
        lblQuemJoga.setText("Vez do jogador: " + C4Observer.getNomeQuemJoga());
        if(C4Observer.getTipoJogador().equalsIgnoreCase("Humano"))
            setViewHumano();
        else
            setViewVirtual();
    }

    private void setViewHumano(){
        lblCreditos.setText("Créditos: " + C4Observer.getCreditosJogador());
        lblPecasEsp.setText("Peças especiais: " + C4Observer.getQtPecasEspJog());
        lblNumJogHum.setText("Número de jogadas: " + C4Observer.getNumeroJogadas());
        playerColorHum.setFill(Color.web(C4Observer.getCorJogAtual()));
        hBoxInfoVirtual.setVisible(false);
        hBoxInfoHumano.setVisible(true);
    }

    private void setViewVirtual(){
        lblNumJogVir.setText("Número de jogadas: " + C4Observer.getNumeroJogadas());
        playerColorVir.setFill(Color.web(C4Observer.getCorJogAtual()));
        hBoxInfoVirtual.setVisible(true);
        hBoxInfoHumano.setVisible(false);
    }

    private void criarLayout() {
        lblQuemJoga = new Label();
        lblQuemJoga.setTextFill(Color.BLACK);
        lblCreditos = new Label();
        lblPecasEsp = new Label();
        lblNumJogHum = new Label();
        lblNumJogVir = new Label();
        lblCorJogHum = new Label("Cor da peça: ");
        lblCorJogVirt = new Label("Cor da peça: ");
        playerColorHum = new Circle(COLOR_RADIUS);
        playerColorVir = new Circle(COLOR_RADIUS);
        hBoxInfoHumano = new HBox(lblPecasEsp,lblCreditos, lblNumJogHum, lblCorJogHum, playerColorHum);
        hBoxInfoVirtual = new HBox(lblNumJogVir, lblCorJogVirt, playerColorVir);
        stackPane = new StackPane(hBoxInfoHumano,hBoxInfoVirtual);
        setFont();
    }

    private void setFont(){
        lblQuemJoga.setFont(FontsManager.loadFont(HEADER_FONT,TXT_FONT_SIZE_HIGH_PLUS));
        lblCreditos.setFont(FontsManager.loadFont(TXT_FONT,TXT_FONT_SIZE_HIGH));
        lblPecasEsp.setFont(FontsManager.loadFont(TXT_FONT,TXT_FONT_SIZE_HIGH));
        lblCorJogHum.setFont(FontsManager.loadFont(TXT_FONT,TXT_FONT_SIZE_HIGH));
        lblNumJogHum.setFont(FontsManager.loadFont(TXT_FONT,TXT_FONT_SIZE_HIGH));
        lblNumJogVir.setFont(FontsManager.loadFont(TXT_FONT,TXT_FONT_SIZE_HIGH));
        lblCorJogVirt.setFont(FontsManager.loadFont(TXT_FONT,TXT_FONT_SIZE_HIGH));
    }

    private void disporVista() {
        hBoxInfoHumano.setSpacing(VBOX_SPACING_SMALL);
        hBoxInfoHumano.setAlignment(Pos.CENTER);
        hBoxInfoVirtual.setSpacing(VBOX_SPACING_SMALL);
        hBoxInfoVirtual.setAlignment(Pos.CENTER);
        this.getChildren().addAll(lblQuemJoga, stackPane);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(VBOX_INSETS));
        this.setSpacing(VBOX_SPACING_SMALL);
    }
}
