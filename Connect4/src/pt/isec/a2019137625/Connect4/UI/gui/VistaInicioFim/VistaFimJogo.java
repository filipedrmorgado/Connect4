package pt.isec.a2019137625.Connect4.UI.gui.VistaInicioFim;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.a2019137625.Connect4.Connect4Observer;
import pt.isec.a2019137625.Connect4.Logica.dados.Enum.Situacao;
import pt.isec.a2019137625.Connect4.UI.gui.Resources.CSSManager;
import pt.isec.a2019137625.Connect4.UI.gui.Resources.FontsManager;
import pt.isec.a2019137625.Connect4.UI.gui.Resources.ImageLoader;
import pt.isec.a2019137625.Connect4.UI.gui.Resources.MusicPlayer;

import static pt.isec.a2019137625.Connect4.Logica.Constantes.PROPRIEDADE_ESTADO;
import static pt.isec.a2019137625.Connect4.UI.gui.ConstantesGUI.*;
import static pt.isec.a2019137625.Connect4.UI.gui.ConstantesGUI.IMG_VIEW_WIDTH;
import static pt.isec.a2019137625.Connect4.UI.gui.Resources.Utility.changeBackground;

public class VistaFimJogo extends BorderPane {
    private final Connect4Observer C4Observer;
    private Label lblinfoFimJogo;
    private VBox vBoxCenter;
    private Button btnVoltJogar;
    private ImageView imageView;

    public VistaFimJogo(Connect4Observer c4Observer){
        this.C4Observer = c4Observer;
        criarComponentes();
        disporVista();
        registarListeners();
        registaObservador();
        CSSManager.setCSS(this,CSS_DESIGN_V2);
        changeBackground(this,Color.LIGHTSKYBLUE);
    }

    private void registaObservador(){
        C4Observer.addPropertyChangeListener(
                PROPRIEDADE_ESTADO,
                e-> {if(C4Observer.getSituacao() == Situacao.FimJogo){
                        trataFimJogo();
                        this.setVisible(true);
                    }else
                        this.setVisible(false);
                });
    }

    private void criarComponentes() {
        displayLogo();
        setAlignment(imageView, Pos.CENTER);
        lblinfoFimJogo = new Label();
        lblinfoFimJogo.setFont(FontsManager.loadFont(TXT_FONT,TXT_FONT_SIZE_HIGH));
        Label lblTitulo = new Label("Chegou ao fim do jogo!");
        lblTitulo.setFont(FontsManager.loadFont(HEADER_FONT,HEADER_FONT_SIZE));
        btnVoltJogar = new Button("Voltar a Jogar");
        vBoxCenter = new VBox(VBOX_SPACING_BIG, lblTitulo,lblinfoFimJogo,btnVoltJogar);
    }
    private void displayLogo(){
        imageView = new ImageView(ImageLoader.getImage(C4_LOGO));
        imageView.setFitHeight(IMG_VIEW_HEIGHT);
        imageView.setFitWidth(IMG_VIEW_WIDTH);
    }

    private void disporVista() {
        this.setTop(imageView);
        this.setCenter(vBoxCenter);
        vBoxCenter.setAlignment(Pos.CENTER);
    }
    private void registarListeners() {
        btnVoltJogar.setOnAction(e-> C4Observer.RecomecaJogo());
    }

    private void trataFimJogo(){
        C4Observer.gravaReplay();
        if(C4Observer.getVencedor().contains("empatado"))
            MusicPlayer.playMusic(FIM_EMPATE);
        else
            MusicPlayer.playMusic(FIM_WINNER);
        lblinfoFimJogo.setText(C4Observer.getVencedor());
    }
}
